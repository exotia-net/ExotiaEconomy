package net.exotia.plugins.economy.configuration.files;

import de.tr7zw.changeme.nbtapi.NBTItem;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.exotia.plugins.economy.configuration.objects.Coin;
import net.exotia.plugins.economy.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CoinsConfiguration extends OkaeriConfig {
    private List<Coin> coins = this.loadCoins();

    private List<Coin> loadCoins() {
        List<Coin> list = new ArrayList<>();
        list.add(this.setupCoin(new ItemCreator(Material.SUNFLOWER).title("&6Moneta (1)").build(), 1));
        list.add(this.setupCoin(new ItemCreator(Material.SUNFLOWER).title("&6Moneta (5)").build(), 5));
        list.add(this.setupCoin(new ItemCreator(Material.SUNFLOWER).title("&6Moneta (10)").build(), 10));
        list.add(this.setupCoin(new ItemCreator(Material.SUNFLOWER).title("&6Moneta (50)").build(), 50));
        list.add(this.setupCoin(new ItemCreator(Material.SUNFLOWER).title("&6Moneta (100)").build(), 100));
        return list;
    }
    private Coin setupCoin(ItemStack itemStack, int value) {
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("isCoin", true);
        nbtItem.setInteger("coinValue", value);
        return Coin.builder().value(value).itemStack(nbtItem.getItem()).build();
    }
}
