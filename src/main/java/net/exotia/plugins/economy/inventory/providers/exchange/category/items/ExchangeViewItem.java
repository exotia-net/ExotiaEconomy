package net.exotia.plugins.economy.inventory.providers.exchange.category.items;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.plugins.economy.configuration.objects.ExchangeItem;
import net.exotia.plugins.economy.inventory.providers.exchange.category.ExchangeCategoryInventoryConfiguration;
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

public class ExchangeViewItem extends AbstractItem {
    @Inject private ExchangeCategoryInventoryConfiguration inventoryConfiguration;
    @Inject private ApiEconomyService economyService;

    private ExchangeItem exchangeItem;

    public ExchangeViewItem parameters(ExchangeItem exchangeItem) {
        this.exchangeItem = exchangeItem;
        return this;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(this.inventoryConfiguration.getItem(this.exchangeItem));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        int itemsCount = Arrays.stream(player.getInventory().getContents()).filter(itemStack -> {
            if (itemStack == null || itemStack.getType().equals(Material.AIR)) return false;
            return itemStack.getType().equals(this.exchangeItem.getMaterial());
        }).map(ItemStack::getAmount).mapToInt(i -> i).sum();

        if (itemsCount <= 0) {
            MessageUtil.send(player, "&8&l>> &cNie masz takiego itemu!");
            return;
        }

        int totalCost = (itemsCount * this.exchangeItem.getPrice()) / this.exchangeItem.getCount();
        player.getInventory().remove(this.exchangeItem.getMaterial());
        this.economyService.give(player.getUniqueId(), totalCost);
        this.economyService.save(player.getUniqueId());
        MessageUtil.send(player, "&8&l>> &aSprzedales {count} {item_name} za {price}"
                .replace("{count}", String.valueOf(itemsCount))
                .replace("{item_name}", this.exchangeItem.getMaterial().name())
                .replace("{price}", String.valueOf(totalCost))
        );
    }
}
