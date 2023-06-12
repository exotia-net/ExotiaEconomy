package net.exotia.plugins.economy.inventory.providers.exchange.items;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.plugins.economy.configuration.files.ExchangeConfiguration;
import net.exotia.plugins.economy.inventory.providers.exchange.ExchangeInventoryConfiguration;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class SellManyItem extends AbstractItem {
    @Inject private ExchangeInventoryConfiguration inventoryConfiguration;
    @Inject private ExchangeConfiguration exchangeConfiguration;
    @Inject private ApiEconomyService economyService;

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(this.inventoryConfiguration.getSellManyItem());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        AtomicInteger itemsCount = new AtomicInteger();
        AtomicInteger price = new AtomicInteger();
        this.exchangeConfiguration.getCategories().forEach((key, category) -> {
            category.getItems().forEach(item -> {
                int itemCount = Arrays.stream(player.getInventory().getContents()).filter(itemStack -> {
                    if (itemStack == null || itemStack.getType().equals(Material.AIR)) return false;
                    return itemStack.getType().equals(item.getMaterial());
                }).map(ItemStack::getAmount).mapToInt(i -> i).sum();

                int totalCost = (itemCount * item.getPrice()) / item.getCount();
                player.getInventory().remove(item.getMaterial());
                itemsCount.addAndGet(itemCount);
                price.addAndGet(totalCost);
            });
        });
        MessageUtil.send(player, "&8&l>> &aSprzedales {count} przedmiotow za laczna kwote za {price}"
                .replace("{count}", String.valueOf(itemsCount.get()))
                .replace("{price}", String.valueOf(price.get()))
        );
        this.economyService.give(player.getUniqueId(), price.get());
        this.economyService.save(player.getUniqueId());
    }
}