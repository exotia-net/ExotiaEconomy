package net.exotia.plugins.economy.configuration.objects;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public class ExchangeItem extends OkaeriConfig {
    private int count;
    private int price;
    private Material material;
}
