package net.exotia.plugins.economy.inventory.providers.bank.deposit;

import de.tr7zw.changeme.nbtapi.NBTItem;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.inventory.InventoryConfiguration;
import net.exotia.plugins.economy.inventory.InventoryOpener;
import net.exotia.plugins.economy.inventory.OpenableInventory;
import net.exotia.plugins.economy.inventory.providers.bank.BankInventory;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.inventory.VirtualInventory;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import java.util.Arrays;

public class BankDepositInventory implements OpenableInventory {
    @Inject private BankDepositInventoryConfiguration inventoryConfiguration;
    @Inject private MessagesConfiguration messages;
    @Inject private ApiEconomyService economyService;
    @Inject private Plugin plugin;
    private VirtualInventory virtualInventory;

    @Override
    public Gui createGui(InventoryOpener inventoryOpener, String... params) {
        this.virtualInventory = new VirtualInventory(this.inventoryConfiguration.getEmptySlots());
        var builder = Gui.normal()
                .setStructure(this.inventoryConfiguration.getPattern())
                .addIngredient('x', this.virtualInventory);

        this.inventoryConfiguration.getItems().forEach((character, baseItem) -> {
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
        return () -> {
            int total = Arrays.stream(this.virtualInventory.getItems()).filter(item -> {
                if (item == null || item.getType().equals(Material.AIR)) return false;
                NBTItem coinItem = new NBTItem(item);
                if (!coinItem.getBoolean("isCoin")) player.getInventory().addItem(item);
                return coinItem.getBoolean("isCoin");
            }).map(item -> {
                NBTItem nbtItem = new NBTItem(item);
                int currency = nbtItem.getInteger("coinValue");
                return currency*item.getAmount();
            }).mapToInt(i -> i).sum();

            if (total <= 0) return;
            this.economyService.give(player.getUniqueId(), total);
            this.economyService.save(player.getUniqueId());
            MessageUtil.send(player, this.messages.getDeposit().replace("{count}", String.valueOf(total)));
            
            Bukkit.getScheduler().runTask(this.plugin, () -> inventoryOpener.open(player, BankInventory.class));
        };
    }
}
