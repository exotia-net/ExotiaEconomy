package net.exotia.plugins.economy.inventory.providers.exchange.category.items;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.configuration.objects.ExchangeItem;
import net.exotia.plugins.economy.inventory.providers.exchange.category.ExchangeCategoryInventoryConfiguration;
import net.exotia.plugins.economy.utils.MessageUtil;
import net.exotia.plugins.economy.utils.items.ItemCreator;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.Arrays;

public class ExchangeViewItem extends AbstractItem {
    @Inject private ExchangeCategoryInventoryConfiguration inventoryConfiguration;
    @Inject private ApiEconomyService economyService;
    @Inject private Plugin plugin;
    @Inject private MessagesConfiguration messages;
    @Inject private BukkitAudiences bukkitAudiences;

    private ExchangeItem exchangeItem;

    public ExchangeViewItem parameters(ExchangeItem exchangeItem) {
        this.exchangeItem = exchangeItem;
        return this;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(MessageUtil.colorize(this.inventoryConfiguration.getItem(this.exchangeItem)));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        int itemsCount = Arrays.stream(player.getInventory().getContents()).filter(itemStack -> {
            if (itemStack == null || itemStack.getType().equals(Material.AIR)) return false;
            return itemStack.getType().equals(this.exchangeItem.getMaterial());
        }).map(ItemStack::getAmount).mapToInt(i -> i).sum();

        if (itemsCount < this.exchangeItem.getCount()) {
            this.showError(event, this.inventoryConfiguration.getItemsNotFound().getItem());
            return;
        }

        int stacksCount = itemsCount/this.exchangeItem.getCount();
        int totalCost = stacksCount*this.exchangeItem.getPrice();
        int amount = itemsCount-(this.exchangeItem.getCount()*stacksCount);

        this.economyService.give(player.getUniqueId(), totalCost);
        this.economyService.save(player.getUniqueId());

        player.getInventory().remove(this.exchangeItem.getMaterial());
        player.getInventory().addItem(new ItemCreator(this.exchangeItem.getMaterial()).amount(amount).build().getItem());

        this.bukkitAudiences.player(player).sendMessage(MessageUtil.deserialize(
                this.messages.getYouSoldItem()
                        .replace("{count}", String.valueOf(itemsCount))
                        .replace("{item_name}", this.exchangeItem.getMaterial().name())
                        .replace("{price}", String.valueOf(totalCost))
        ));
    }

    private void showError(InventoryClickEvent event, ItemStack itemStack) {
        event.setCurrentItem(itemStack);
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            event.setCurrentItem(this.getItemProvider().get());
        }, 20L);
    }
}
