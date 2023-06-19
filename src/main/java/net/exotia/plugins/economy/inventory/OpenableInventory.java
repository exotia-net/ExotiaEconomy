package net.exotia.plugins.economy.inventory;

import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;

public interface OpenableInventory {
    Gui createGui(InventoryOpener inventoryOpener, String... params);
    InventoryConfiguration getConfiguration();
    Runnable closeGuiHandler(Player player, InventoryOpener inventoryOpener);
}
