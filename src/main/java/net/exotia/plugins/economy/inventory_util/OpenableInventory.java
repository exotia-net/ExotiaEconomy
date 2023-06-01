package net.exotia.plugins.economy.inventory_util;

import xyz.xenondevs.invui.gui.Gui;

public interface OpenableInventory {
    Gui createGui(InventoryOpener inventoryOpener, String... params);
    InventoryConfiguration getConfiguration();
}
