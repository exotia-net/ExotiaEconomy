package net.exotia.plugins.economy.inventory.bank.withdraw;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiUser;
import net.exotia.bridge.api.user.ApiUserService;
import net.exotia.plugins.economy.module.CoinsService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BankWithdrawInventory {
    @Inject private BankWithdrawConfiguration inventoryConfiguration;
    @Inject private CoinsService coinsService;
    @Inject private Plugin plugin;
    @Inject private ApiUserService userService;

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
            int temp = adding ? this.value + count : this.value - count;
            if(temp < 1) {
                GuiItem item = this.gui.getGuiItem(event.getSlot());
                assert item != null;
                item.setItemStack(this.inventoryConfiguration.getErrorItem());
                this.gui.updateItem(event.getSlot(), item);
                Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                    this.gui.updateItem(event.getSlot(), this.buildItem(count, adding));
                },this.inventoryConfiguration.getErrorDisplayTime());
                return;
            }
            this.value = temp;
            this.gui.updateItem(this.inventoryConfiguration.getAcceptTransactionSlot(), this.acceptTransition());
        });
    }
    private GuiItem acceptTransition() {
        return ItemBuilder.from(this.inventoryConfiguration.getAcceptTransaction(this.value)).asGuiItem(event -> {
            this.calculateCoinsToWithdraw(this.value, (Player) event.getWhoClicked());
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
            player.getInventory().addItem(itemStack);
        }
    }

    public GuiItem test(Player player) {
        ApiUser user = this.userService.getUser(player.getUniqueId());
        player.sendMessage("Twoje saldo: " + user.getBalance());
        return this.acceptTransition();
    }
}
