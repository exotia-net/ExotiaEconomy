package net.exotia.plugins.economy.configuration.objects;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;

public class ExchangeItem extends OkaeriConfig {
    private final int count;
    private final double price;
    private final Material material;

    public ExchangeItem(int count, double price, Material material) {
        this.count = count;
        this.price = price;
        this.material = material;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    public Material getMaterial() {
        return material;
    }
}
