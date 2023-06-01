package net.exotia.plugins.economy.inventory_util.providers.bank;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.inventory_util.InventoryConfiguration;
import net.exotia.plugins.economy.inventory_util.InventoryOpener;
import net.exotia.plugins.economy.inventory_util.OpenableInventory;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class BankInventory implements OpenableInventory {
    @Inject private BankInventoryConfiguration inventoryConfiguration;

    @Override
    public Gui createGui(InventoryOpener inventoryOpener, String... params) {
        var builder = Gui.normal()
                .setStructure(this.inventoryConfiguration.getPattern())
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL);

        this.inventoryConfiguration.getItems().forEach((character, baseItem) -> {
            builder.addIngredient(character, new SimpleItem(new ItemBuilder(baseItem.getItemStack())));
        });

        return builder.build();
    }

    @Override
    public InventoryConfiguration getConfiguration() {
        return this.inventoryConfiguration;
    }
}
