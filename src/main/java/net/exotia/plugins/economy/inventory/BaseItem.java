package net.exotia.plugins.economy.inventory;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.exotia.plugins.economy.utils.items.YourItem;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@AllArgsConstructor
public class BaseItem extends OkaeriConfig {
    private YourItem yourItem;
    private List<String> actions;
}
