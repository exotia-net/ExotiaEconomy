package net.exotia.plugins.economy.inventory.providers.bank.withdraw;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.inventory.InventoryConfiguration;
import net.exotia.plugins.economy.inventory.InventoryOpener;
import net.exotia.plugins.economy.inventory.OpenableInventory;
import net.exotia.plugins.economy.inventory.providers.bank.withdraw.items.AcceptWithdrawItem;
import net.exotia.plugins.economy.inventory.providers.bank.withdraw.items.AddValueItem;
import net.exotia.plugins.economy.inventory.providers.bank.withdraw.items.RemoveValueItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.List;

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
            builder.addIngredient(character, new SimpleItem(new ItemBuilder(baseItem.getYourItem().getItem())));
        });

        AcceptWithdrawItem acceptWithdrawItem = this.injector.createInstance(AcceptWithdrawItem.class);
        builder.addIngredient('A', acceptWithdrawItem);

        Gui gui = builder.build();
        this.inventoryConfiguration.getAddValueSlots().forEach((slot, entity) -> {
            gui.setItem(slot, this.injector.createInstance(AddValueItem.class).parameters(entity, gui, acceptWithdrawItem));
        });
        this.inventoryConfiguration.getRemoveValueSlots().forEach((slot, entity) -> {
            gui.setItem(slot, this.injector.createInstance(RemoveValueItem.class).parameters(entity, gui, acceptWithdrawItem));
        });
        return gui;
    }

    @Override
    public InventoryConfiguration getConfiguration() {
        return this.inventoryConfiguration;
    }

    @Override
    public Runnable closeGuiHandler(Player player, InventoryOpener inventoryOpener) {
        return null;
    }
}
