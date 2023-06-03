package net.exotia.plugins.economy.inventory.providers.bank;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.inventory.InventoryConfiguration;
import net.exotia.plugins.economy.inventory.InventoryOpener;
import net.exotia.plugins.economy.inventory.OpenableInventory;
import net.exotia.plugins.economy.inventory.providers.bank.deposit.BankDepositInventory;
import net.exotia.plugins.economy.inventory.providers.bank.withdraw.BankWithdrawInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class BankInventory implements OpenableInventory {
    @Inject private BankInventoryConfiguration inventoryConfiguration;
    @Inject private Injector injector;

    @Override
    public Gui createGui(InventoryOpener inventoryOpener, String... params) {
        var builder = Gui.normal()
                .setStructure(this.inventoryConfiguration.getPattern())
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL);

        this.inventoryConfiguration.getItems().forEach((character, baseItem) -> {
            builder.addIngredient(character, new AbstractItem() {
                @Override
                public ItemProvider getItemProvider() {
                    return new ItemBuilder(baseItem.getItemStack());
                }

                @Override
                public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
                    switch (character) {
                        case 'W' -> inventoryOpener.open(player, BankWithdrawInventory.class);
                        case 'D' -> inventoryOpener.open(player, BankDepositInventory.class);
                    }
                }
            });
        });

        return builder.build();
    }

    @Override
    public InventoryConfiguration getConfiguration() {
        return this.inventoryConfiguration;
    }
}
