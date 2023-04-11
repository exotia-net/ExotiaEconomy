package net.exotia.plugins.economy.configuration;

import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import net.exotia.plugins.economy.configuration.files.CoinsConfiguration;
import net.exotia.plugins.economy.configuration.files.PluginConfiguration;
import net.exotia.plugins.economy.factory.ConfigurationFactory;
import org.bukkit.plugin.Plugin;

public class ConfigurationModule {
    @Inject private Plugin plugin;

    @PostConstruct
    public void onConstruct() {
        ConfigurationFactory configurationFactory = new ConfigurationFactory(this.plugin.getDataFolder());
        configurationFactory.produce(PluginConfiguration.class, "configuration.yml");
        configurationFactory.produce(CoinsConfiguration.class, "coins.yml");
    }
}
