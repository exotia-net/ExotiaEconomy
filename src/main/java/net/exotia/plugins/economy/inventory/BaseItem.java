package net.exotia.plugins.economy.inventory;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@AllArgsConstructor
public class BaseItem extends OkaeriConfig {
    private ItemStack itemStack;
    private List<String> actions;
}
