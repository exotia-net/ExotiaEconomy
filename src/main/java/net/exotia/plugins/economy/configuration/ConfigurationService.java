package net.exotia.plugins.economy.configuration;

import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.ExchangeConfiguration;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.inventory.InventoryOpener;

public class ConfigurationService {
    @Inject private MessagesConfiguration messagesConfiguration;
    @Inject private InventoryOpener inventoryOpener;
    @Inject private ExchangeConfiguration exchangeConfiguration;

    public void reload() {
        try {
            this.messagesConfiguration.load(true);
            this.exchangeConfiguration.load(true);
            this.inventoryOpener.reloadConfigs();
        } catch (OkaeriException exception) {
            exception.printStackTrace();
        }
    }
}
