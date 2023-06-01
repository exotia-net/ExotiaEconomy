package net.exotia.plugins.economy.configuration.objects;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExchangeItem extends OkaeriConfig {
    private int count;
    private int price;
}
