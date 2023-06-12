package net.exotia.plugins.economy.configuration.objects;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
@Builder
public class Coin extends OkaeriConfig {
    private int value;
    private ItemStack itemStack;
    private String oraxenId;
}
