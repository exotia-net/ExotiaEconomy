package net.exotia.plugins.economy.configuration;

import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.configuration.files.PluginConfiguration;
import net.exotia.plugins.economy.inventory.bank.deposit.BankDepositConfiguration;
import net.exotia.plugins.economy.inventory.bank.withdraw.BankWithdrawConfiguration;
import net.exotia.plugins.economy.inventory.exchange.ExchangeInventoryConfiguration;

public class ConfigurationService {
    @Inject private MessagesConfiguration messagesConfiguration;
    @Inject private BankWithdrawConfiguration bankWithdrawConfiguration;
    @Inject private BankDepositConfiguration bankDepositConfiguration;
    @Inject private ExchangeInventoryConfiguration exchangeInventoryConfiguration;

    public void reload() {
        try {
            this.messagesConfiguration.load(true);
            this.bankWithdrawConfiguration.load(true);
            this.bankDepositConfiguration.load(true);
            this.exchangeInventoryConfiguration.load(true);
        } catch (OkaeriException exception) {
            exception.printStackTrace();
        }
    }
}
