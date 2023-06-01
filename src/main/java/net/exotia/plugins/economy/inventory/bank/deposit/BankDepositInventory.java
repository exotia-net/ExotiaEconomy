package net.exotia.plugins.economy.inventory.bank.deposit;

import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.StorageGui;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.plugins.economy.inventory.bank.BankInventory;
import net.exotia.plugins.economy.module.CoinsService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;

public class BankDepositInventory {
    @Inject private BankDepositConfiguration inventoryConfiguration;
    @Inject private CoinsService coinsService;
    @Inject private ApiEconomyService economyService;
    @Inject private Injector injector;

    private int value;
    private StorageGui gui;

    public void open(Player player) {
        this.value = 0;
        this.gui = this.inventoryConfiguration.getGui();
        this.gui.setItem(this.inventoryConfiguration.getDepositButtonSlot(), this.deposit());
        this.gui.open(player);

        this.gui.setDefaultClickAction(event -> {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;
            NBTItem nbtItem = new NBTItem(event.getCurrentItem());
            if (!nbtItem.getBoolean("isCoin")) {
                event.setCancelled(true);
            }
        });
        this.gui.setCloseGuiAction(event -> {
            this.giveCoins(player, event.getInventory().getContents());
        });
    }

    private GuiItem deposit() {
        return ItemBuilder.from(this.inventoryConfiguration.getDepositButton(this.value)).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            this.gui.close(player);
            event.setCancelled(true);
        });
    }

    private void giveCoins(Player player, ItemStack[] contents) {
        int total = Arrays.stream(contents).filter(item -> {
            if (item == null || item.getType().equals(Material.AIR)) return false;
            NBTItem coinItem = new NBTItem(item);
            return coinItem.getBoolean("isCoin");
        }).map(item -> {
            NBTItem nbtItem = new NBTItem(item);
            int currency = nbtItem.getInteger("coinValue");
            return currency*item.getAmount();
        }).mapToInt(i -> i).sum();
        this.economyService.give(player.getUniqueId(), total);
        this.economyService.save(player.getUniqueId());
    }
}
