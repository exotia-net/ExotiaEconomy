package net.exotia.plugins.economy.inventory.providers.bank.withdraw.items;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.plugins.economy.inventory.providers.bank.withdraw.BankWithdrawInventoryConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class AddValueItem extends AbstractItem {
    @Inject private BankWithdrawInventoryConfiguration inventoryConfiguration;
    @Inject private ApiEconomyService economyService;
    @Inject private Plugin plugin;

    private int value;
    private Gui gui;
    private AcceptWithdrawItem acceptWithdrawItem;

    public AddValueItem parameters(int value, Gui gui, AcceptWithdrawItem acceptWithdrawItem) {
        this.value = value;
        this.gui = gui;
        this.acceptWithdrawItem = acceptWithdrawItem;
        return this;
    }

    @Override
    public ItemProvider getItemProvider() {
        ItemStack itemStack = this.inventoryConfiguration.getAddCoins().clone();
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(meta.getDisplayName().replace("{amount}", String.valueOf(this.value)));
        itemStack.setItemMeta(meta);
        return new ItemBuilder(itemStack);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
       int totalValue = this.acceptWithdrawItem.getValue() + this.value;
        if (!this.economyService.has(player.getUniqueId(), totalValue)) {
            this.showError(event.getSlot(), this.inventoryConfiguration.getNoEnoughCoins());
            return;
        }
        this.acceptWithdrawItem.updateItem(totalValue);
        this.notifyWindows();
    }

    private void showError(int slot, ItemStack itemStack) {
        this.gui.setItem(slot, new SimpleItem(new ItemBuilder(itemStack)));
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            this.gui.setItem(slot, this);
        },this.inventoryConfiguration.getErrorDisplayTime());
    }
}
