package net.exotia.plugins.economy.inventory.providers.bank.deposit;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.inventory.InventoryConfiguration;
import net.exotia.plugins.economy.inventory.InventoryOpener;
import net.exotia.plugins.economy.inventory.OpenableInventory;
import net.exotia.plugins.economy.inventory.providers.bank.deposit.items.ConfirmDepositItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.inventory.VirtualInventory;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class BankDepositInventory implements OpenableInventory {
    @Inject private BankDepositInventoryConfiguration inventoryConfiguration;
    @Inject private Injector injector;

    @Override
    public Gui createGui(InventoryOpener inventoryOpener, String... params) {
        var builder = Gui.normal()
                .setStructure(this.inventoryConfiguration.getPattern())
                .addIngredient('x', new VirtualInventory(this.inventoryConfiguration.getEmptySlots()))
                .addIngredient('C', this.injector.createInstance(ConfirmDepositItem.class));
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
