package net.exotia.plugins.economy.module;

import de.tr7zw.changeme.nbtapi.NBTItem;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.CoinsConfiguration;
import net.exotia.plugins.economy.configuration.objects.Coin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EconomyService {
    @Inject private CoinsConfiguration coinsConfiguration;

    public Coin findCoin(Integer value) {
        return this.coinsConfiguration.getCoins().stream().filter(coin -> coin.getValue() == value).findFirst().orElse(null);
    }
    public List<Coin> findAllCoins() {
        return this.coinsConfiguration.getCoins();
    }
    public ItemStack createCoin(Coin coin) {
        NBTItem nbtItem = new NBTItem(coin.getItemStack());
        nbtItem.setBoolean("isCoin", true);
        nbtItem.setInteger("coinValue", coin.getValue());
        return nbtItem.getItem();
    }
    public int getPlayerPhysicalCoins(Player player) {
        int totalAmount = 0;
        for (ItemStack content : player.getInventory().getContents()) {
            if (content == null) continue;
            NBTItem nbtItem = new NBTItem(content);
            if (!nbtItem.getBoolean("coinValue")) continue;
            totalAmount += content.getAmount() * nbtItem.getInteger("coinValue");
        }
        return totalAmount;
    }

    public int getBalance(Player player) {
        return 0;
    }
}
