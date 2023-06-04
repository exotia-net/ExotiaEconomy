package net.exotia.plugins.economy.configuration.objects;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExchangeCategory extends OkaeriConfig {
    private String name;
    private Material icon;
    private List<ExchangeItem> items;
}
