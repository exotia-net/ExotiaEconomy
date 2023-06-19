package net.exotia.plugins.economy.configuration.files;

import de.tr7zw.changeme.nbtapi.NBTItem;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.exotia.plugins.economy.configuration.objects.Coin;
import net.exotia.plugins.economy.utils.items.ItemCreator;
import net.exotia.plugins.economy.utils.items.YourItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CoinsConfiguration extends OkaeriConfig {
    private List<Coin> coins = this.loadCoins();

    private List<Coin> loadCoins() {
        List<Coin> list = new ArrayList<>();
        list.add(this.setupCoin(new ItemCreator(Material.SUNFLOWER).title("&6Moneta (1) test").oraxenId("ekonomia_moneta_brazowa").build(), 1));
        list.add(this.setupCoin(new ItemCreator(Material.SUNFLOWER).title("&6Moneta (5)").oraxenId("ekonomia_moneta_brazowa").build(), 5));
        list.add(this.setupCoin(new ItemCreator(Material.SUNFLOWER).title("&6Moneta (10)").oraxenId("ekonomia_moneta_srebrna").build(), 10));
        list.add(this.setupCoin(new ItemCreator(Material.SUNFLOWER).title("&6Moneta (50)").oraxenId("ekonomia_moneta_srebrna").build(), 50));
        list.add(this.setupCoin(new ItemCreator(Material.SUNFLOWER).title("&6Moneta (100)").oraxenId("ekonomia_moneta_zlota").build(), 100));
        return list;
    }

    private Coin setupCoin(YourItem yourItem, int value) {
        NBTItem nbtItem = new NBTItem(yourItem.getItem());
        nbtItem.setBoolean("isCoin", true);
        nbtItem.setInteger("coinValue", value);
        return Coin.builder().value(value).itemStack(nbtItem.getItem()).oraxenId(yourItem.getOraxenId()).build();
    }
}
