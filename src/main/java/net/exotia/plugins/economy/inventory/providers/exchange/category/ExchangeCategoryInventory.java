package net.exotia.plugins.economy.inventory.providers.exchange.category;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.ExchangeConfiguration;
import net.exotia.plugins.economy.configuration.objects.ExchangeItem;
import net.exotia.plugins.economy.inventory.InventoryConfiguration;
import net.exotia.plugins.economy.inventory.InventoryOpener;
import net.exotia.plugins.economy.inventory.OpenableInventory;
import net.exotia.plugins.economy.inventory.providers.exchange.ExchangeInventory;
import net.exotia.plugins.economy.inventory.providers.exchange.category.items.ExchangeViewItem;
import net.exotia.plugins.economy.inventory.providers.exchange.category.items.NextPageItem;
import net.exotia.plugins.economy.inventory.providers.exchange.category.items.PreviousPageItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

import java.util.List;
import java.util.stream.Collectors;

public class ExchangeCategoryInventory implements OpenableInventory {
    @Inject private ExchangeCategoryInventoryConfiguration inventoryConfiguration;
    @Inject private ExchangeConfiguration exchangeConfiguration;
    @Inject private Injector injector;

    @Override
    public Gui createGui(InventoryOpener inventoryOpener, String... params) {
        var builder = PagedGui.items()
                .setStructure(this.inventoryConfiguration.getPattern())
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('>', new NextPageItem(this.inventoryConfiguration))
                .addIngredient('<', new PreviousPageItem(this.inventoryConfiguration))
                .setContent(this.exchangeConfiguration.getCategories().get(params[0]).getItems().stream()
                        .map(item -> this.injector.createInstance(ExchangeViewItem.class).parameters(item))
                        .collect(Collectors.toList())
                );
        this.inventoryConfiguration.getItems().forEach((character, baseItem) -> {
            if (character == 'R') {
                builder.addIngredient(character, new AbstractItem() {
                    @Override
                    public ItemProvider getItemProvider() {
                        return new ItemBuilder(baseItem.getYourItem().getItem());
                    }
                    @Override
                    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
                        event.getView().close();
                        inventoryOpener.open(player, ExchangeInventory.class);
                    }
                });
                return;
            }
            builder.addIngredient(character, new SimpleItem(new ItemBuilder(baseItem.getYourItem().getItem())));
        });
        return builder.build();
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
