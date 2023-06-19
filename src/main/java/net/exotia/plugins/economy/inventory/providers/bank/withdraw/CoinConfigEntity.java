package net.exotia.plugins.economy.inventory.providers.bank.withdraw;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.exotia.plugins.economy.utils.items.YourItem;

@Getter
@AllArgsConstructor
public class CoinConfigEntity extends OkaeriConfig {
    private YourItem yourItem;
    private Integer value;
}
