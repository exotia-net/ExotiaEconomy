package net.exotia.plugins.economy.inventory.providers.bank.withdraw.items;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.plugins.economy.inventory.providers.bank.withdraw.BankWithdrawInventoryConfiguration;
import net.exotia.plugins.economy.module.CoinsService;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class AcceptWithdrawItem extends AbstractItem {
    @Inject private BankWithdrawInventoryConfiguration inventoryConfiguration;
    @Inject private ApiEconomyService economyService;
    @Inject private CoinsService coinsService;
    private int value = 0;

    public AcceptWithdrawItem parameters(int value) {
        this.value = value;
        return this;
    }

    @Override
    public ItemProvider getItemProvider() {
        ItemStack itemStack = this.inventoryConfiguration.getAcceptWithdrawItem().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("{amount}", String.valueOf(this.value)));
        itemStack.setItemMeta(itemMeta);
        return new ItemBuilder(itemStack);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        if (!this.economyService.has(player.getUniqueId(), this.value) || this.value <= 0) return;
        this.calculateCoinsToWithdraw(this.value, player);
        event.getView().close();
    }

    public void updateItem(int value) {
        this.value = value;
        this.notifyWindows();
    }
    public int getValue() {
        return this.value;
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
}
