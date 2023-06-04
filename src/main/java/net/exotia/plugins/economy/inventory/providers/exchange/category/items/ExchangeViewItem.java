package net.exotia.plugins.economy.inventory.providers.exchange.category.items;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.objects.ExchangeItem;
import net.exotia.plugins.economy.inventory.providers.exchange.category.ExchangeCategoryInventoryConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class ExchangeViewItem extends AbstractItem {
    @Inject private ExchangeCategoryInventoryConfiguration inventoryConfiguration;
    private ExchangeItem exchangeItem;

    public ExchangeViewItem parameters(ExchangeItem exchangeItem) {
        this.exchangeItem = exchangeItem;
        return this;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(this.inventoryConfiguration.getItem(this.exchangeItem));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        player.sendMessage(this.exchangeItem.getMaterial().name() + " " + exchangeItem.getPrice() + " x " + exchangeItem.getCount());
    }
}
