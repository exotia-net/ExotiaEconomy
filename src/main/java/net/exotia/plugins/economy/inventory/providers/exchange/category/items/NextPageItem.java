package net.exotia.plugins.economy.inventory.providers.exchange.category.items;

import net.exotia.plugins.economy.inventory.providers.exchange.category.ExchangeCategoryInventoryConfiguration;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class NextPageItem extends PageItem {
    private final ExchangeCategoryInventoryConfiguration inventoryConfiguration;

    public NextPageItem(ExchangeCategoryInventoryConfiguration inventoryConfiguration) {
        super(true);
        this.inventoryConfiguration = inventoryConfiguration;
    }

    @Override
    public ItemProvider getItemProvider(PagedGui<?> gui) {
        return new ItemBuilder(this.inventoryConfiguration.getNextPageItem().getItem());
    }
}
