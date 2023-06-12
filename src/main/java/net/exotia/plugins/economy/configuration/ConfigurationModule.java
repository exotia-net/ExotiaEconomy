package net.exotia.plugins.economy.configuration;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import net.exotia.plugins.economy.configuration.files.CoinsConfiguration;
import net.exotia.plugins.economy.configuration.files.ExchangeConfiguration;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.configuration.files.PluginConfiguration;
import net.exotia.plugins.economy.factory.ConfigurationFactory;
import org.bukkit.plugin.Plugin;

public class ConfigurationModule {
    @Inject private Plugin plugin;
    @Inject private Injector injector;

    @PostConstruct
    public void onConstruct() {
        ConfigurationFactory configurationFactory = new ConfigurationFactory(this.plugin.getDataFolder());
        this.injector.registerInjectable(configurationFactory);
        PluginConfiguration configuration = configurationFactory.produce(PluginConfiguration.class, "configuration.yml");
        configuration.injectSections(this.injector);
        this.injector.registerInjectable(configuration);
        this.injector.registerInjectable(configurationFactory.produce(CoinsConfiguration.class, "coins.yml"));
        this.injector.registerInjectable(configurationFactory.produce(MessagesConfiguration.class, "messages.yml"));
        this.injector.registerInjectable(configurationFactory.produce(ExchangeConfiguration.class, "exchange.yml"));
    }
}
