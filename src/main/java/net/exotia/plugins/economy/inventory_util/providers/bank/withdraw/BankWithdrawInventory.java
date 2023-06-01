package net.exotia.plugins.economy.inventory_util.providers.bank.withdraw;

import net.exotia.plugins.economy.inventory_util.InventoryConfiguration;
import net.exotia.plugins.economy.inventory_util.InventoryOpener;
import net.exotia.plugins.economy.inventory_util.OpenableInventory;
import xyz.xenondevs.invui.gui.Gui;

public class BankWithdrawInventory implements OpenableInventory {
    @Override
    public Gui createGui(InventoryOpener inventoryOpener, String... params) {
        return null;
    }

    @Override
    public InventoryConfiguration getConfiguration() {
        return null;
    }
}
