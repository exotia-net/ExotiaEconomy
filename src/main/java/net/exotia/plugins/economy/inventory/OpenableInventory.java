package net.exotia.plugins.economy.inventory;

import xyz.xenondevs.invui.gui.Gui;

public interface OpenableInventory {
    Gui createGui(InventoryOpener inventoryOpener, String... params);
    InventoryConfiguration getConfiguration();
}
