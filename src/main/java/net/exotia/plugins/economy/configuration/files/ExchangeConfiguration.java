package net.exotia.plugins.economy.configuration.files;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.exotia.plugins.economy.configuration.objects.ExchangeCategory;
import net.exotia.plugins.economy.configuration.objects.ExchangeItem;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;

@Getter
public class ExchangeConfiguration extends OkaeriConfig {
    private HashMap<String, ExchangeCategory> categories = this.setup();

    private HashMap<String, ExchangeCategory> setup() {
        HashMap<String, ExchangeCategory> hashMap = new HashMap<>();
        hashMap.put("drop", new ExchangeCategory("Drop z mobów", Material.ROTTEN_FLESH, List.of(
                new ExchangeItem(1, 1, Material.ROTTEN_FLESH),
                new ExchangeItem(12, 30, Material.MAGMA_CREAM),
                new ExchangeItem(12, 25, Material.SLIME_BALL)
        )));
        hashMap.put("ores", new ExchangeCategory("Mineraly", Material.DIAMOND, List.of(
                new ExchangeItem(16, 20, Material.LAPIS_LAZULI)
        )));
        return hashMap;
    }
}
