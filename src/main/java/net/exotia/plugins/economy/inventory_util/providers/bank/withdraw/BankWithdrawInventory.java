package net.exotia.plugins.economy.inventory_util.providers.bank.withdraw;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.inventory_util.InventoryConfiguration;
import net.exotia.plugins.economy.inventory_util.InventoryOpener;
import net.exotia.plugins.economy.inventory_util.OpenableInventory;
import net.exotia.plugins.economy.inventory_util.providers.bank.withdraw.items.AcceptWithdrawItem;
import net.exotia.plugins.economy.inventory_util.providers.bank.withdraw.items.AddValueItem;
import net.exotia.plugins.economy.inventory_util.providers.bank.withdraw.items.RemoveValueItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class BankWithdrawInventory implements OpenableInventory {
    @Inject private BankWithdrawInventoryConfiguration inventoryConfiguration;
    @Inject private Injector injector;
    private int totalValue;

    @Override
    public Gui createGui(InventoryOpener inventoryOpener, String... params) {
        this.totalValue = 0;
        var builder = Gui.normal()
                .setStructure(this.inventoryConfiguration.getPattern())
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL);
        this.inventoryConfiguration.getItems().forEach((character, baseItem) -> {
            builder.addIngredient(character, new SimpleItem(new ItemBuilder(baseItem.getItemStack())));
        });

        AcceptWithdrawItem acceptWithdrawItem = this.injector.createInstance(AcceptWithdrawItem.class);
        builder.addIngredient('A', acceptWithdrawItem);

        Gui gui = builder.build();
        this.inventoryConfiguration.getAddValueSlots().forEach((slot, value) -> {
            gui.setItem(slot, this.injector.createInstance(AddValueItem.class).parameters(value, gui, acceptWithdrawItem));
        });
        this.inventoryConfiguration.getRemoveValueSlots().forEach((slot, value) -> {
            gui.setItem(slot, this.injector.createInstance(RemoveValueItem.class).parameters(value, gui, acceptWithdrawItem));
        });
        return gui;
    }

    @Override
    public InventoryConfiguration getConfiguration() {
        return this.inventoryConfiguration;
    }
}
