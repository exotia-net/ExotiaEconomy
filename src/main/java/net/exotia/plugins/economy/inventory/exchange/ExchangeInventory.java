package net.exotia.plugins.economy.inventory.exchange;

import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;

public class ExchangeInventory {
    @Inject private ExchangeInventoryConfiguration inventoryConfiguration;

    private PaginatedGui gui;

    public void open(Player player) {
        this.gui = this.inventoryConfiguration.getGui();
        this.gui.setItem(this.inventoryConfiguration.getSellDifferentPageSlot(), new GuiItem(this.inventoryConfiguration.getSellDifferentItem(), event -> {

        }));

        this.inventoryConfiguration.getItems().forEach((material, exchangeItem) -> {
            player.sendMessage(material.name());
            this.gui.addItem(new GuiItem(this.inventoryConfiguration.getItem(material, exchangeItem), event -> {
                player.sendMessage(material.name());
            }));
        });

        this.gui.open(player);
    }
}
