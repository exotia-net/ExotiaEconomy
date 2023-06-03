package net.exotia.plugins.economy.configuration;

import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;

public class ConfigurationService {
    @Inject private MessagesConfiguration messagesConfiguration;

    public void reload() {
        try {
            this.messagesConfiguration.load(true);
        } catch (OkaeriException exception) {
            exception.printStackTrace();
        }
    }
}
