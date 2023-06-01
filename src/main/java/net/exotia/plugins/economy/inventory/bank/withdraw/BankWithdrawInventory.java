package net.exotia.plugins.economy.inventory.bank.withdraw;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.plugins.economy.module.CoinsService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BankWithdrawInventory {
    @Inject private BankWithdrawConfiguration inventoryConfiguration;
    @Inject private CoinsService coinsService;
    @Inject private Plugin plugin;
    @Inject private ApiEconomyService economyService;

    private int value;
    private Gui gui;

    public void open(Player player) {
        this.value = 0;
        this.gui = this.inventoryConfiguration.getGui();
        this.inventoryConfiguration.getAddValueSlots().forEach((slot, count) -> this.gui.setItem(slot, this.buildItem(count,true)));
        this.inventoryConfiguration.getRemoveValueSlots().forEach((slot, count) -> this.gui.setItem(slot, this.buildItem(count, false)));
        this.gui.setItem(this.inventoryConfiguration.getAcceptTransactionSlot(), this.acceptTransition());
        this.gui.open(player);
    }

    private GuiItem buildItem(int count, boolean adding) {
        return ItemBuilder.from(this.inventoryConfiguration.getButton(adding, count)).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            int temp = adding ? this.value + count : this.value - count;
            if(temp < 1) {
                this.showError(event.getSlot(), this.inventoryConfiguration.getErrorItem(), this.buildItem(count, adding));
                return;
            }
            if (!this.economyService.has(player.getUniqueId(), temp)) {
                this.showError(event.getSlot(), this.inventoryConfiguration.getNoEnoughCoins(), this.buildItem(count, adding));
                return;
            }
            this.value = temp;
            this.gui.updateItem(this.inventoryConfiguration.getAcceptTransactionSlot(), this.acceptTransition());
        });
    }
    private GuiItem acceptTransition() {
        return ItemBuilder.from(this.inventoryConfiguration.getAcceptTransaction(this.value)).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            if (!this.economyService.has(player.getUniqueId(), this.value) || this.value <= 0) return;
            this.calculateCoinsToWithdraw(this.value, player);
            this.gui.close(player);
        });
    }

    private void calculateCoinsToWithdraw(int value, Player player) {
        int[] denominations = this.coinsService.getValues();
        int[] coinsUsed = new int[denominations.length];
        int remainder = value;

        for (int i = 0; i < denominations.length; i++) {
            coinsUsed[i] = remainder / denominations[i];
            remainder = remainder % denominations[i];
        }

        for (int i = 0; i < denominations.length; i++) {
            ItemStack itemStack = this.coinsService.createCoin(this.coinsService.findCoin(denominations[i]));
            itemStack.setAmount(coinsUsed[i]);
            this.economyService.take(player.getUniqueId(), denominations[i]*coinsUsed[i]);
            player.getInventory().addItem(itemStack);
        }
        this.economyService.save(player.getUniqueId());
    }

    private void showError(int slot, ItemStack itemStack, GuiItem guiItem) {
        GuiItem item = this.gui.getGuiItem(slot);
        assert item != null;
        item.setItemStack(itemStack);
        this.gui.updateItem(slot, item);
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            this.gui.updateItem(slot, guiItem);
        },this.inventoryConfiguration.getErrorDisplayTime());
    }
}
