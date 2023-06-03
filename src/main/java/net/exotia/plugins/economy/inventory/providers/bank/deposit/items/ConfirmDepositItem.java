package net.exotia.plugins.economy.inventory.providers.bank.deposit.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.plugins.economy.inventory.providers.bank.deposit.BankDepositInventoryConfiguration;
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

public class ConfirmDepositItem extends AbstractItem {
    @Inject private BankDepositInventoryConfiguration inventoryConfiguration;
    @Inject private ApiEconomyService economyService;

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(this.inventoryConfiguration.getConfirmDepositItem());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        ItemStack[] contents = event.getInventory().getContents();
        int total = Arrays.stream(contents).filter(item -> {
            if (item == null || item.getType().equals(Material.AIR)) return false;
            NBTItem coinItem = new NBTItem(item);
            return coinItem.getBoolean("isCoin");
        }).map(item -> {
            NBTItem nbtItem = new NBTItem(item);
            event.getInventory().remove(item);
            int currency = nbtItem.getInteger("coinValue");
            return currency*item.getAmount();
        }).mapToInt(i -> i).sum();
        if (total <= 0) return;
        this.economyService.give(player.getUniqueId(), total);
        this.economyService.save(player.getUniqueId());
    }
}
