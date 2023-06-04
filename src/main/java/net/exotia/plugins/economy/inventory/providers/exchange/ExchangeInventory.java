package net.exotia.plugins.economy.inventory.providers.exchange;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.ExchangeConfiguration;
import net.exotia.plugins.economy.inventory.InventoryConfiguration;
import net.exotia.plugins.economy.inventory.InventoryOpener;
import net.exotia.plugins.economy.inventory.OpenableInventory;
import net.exotia.plugins.economy.inventory.providers.exchange.category.ExchangeCategoryInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class ExchangeInventory implements OpenableInventory {
    @Inject private ExchangeInventoryConfiguration inventoryConfiguration;
    @Inject private ExchangeConfiguration exchangeConfiguration;

    @Override
    public Gui createGui(InventoryOpener inventoryOpener, String... params) {
        var builder = Gui.normal()
                .setStructure(this.inventoryConfiguration.getPattern())
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL);
        this.inventoryConfiguration.getItems().forEach((character, baseItem) -> {
            builder.addIngredient(character, new SimpleItem(new ItemBuilder(baseItem.getItemStack())));
        });
        Gui gui = builder.build();
        this.exchangeConfiguration.getCategories().forEach((key, category) -> {
            gui.addItems(new AbstractItem() {
                @Override
                public ItemProvider getItemProvider() {
                    return new ItemBuilder(inventoryConfiguration.getCategoryItem(category));
                }
                @Override
                public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
                    inventoryOpener.open(player, ExchangeCategoryInventory.class, key);
                }
            });
        });
        return gui;
    }

    @Override
    public InventoryConfiguration getConfiguration() {
        return this.inventoryConfiguration;
    }
}
