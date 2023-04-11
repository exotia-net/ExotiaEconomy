package net.exotia.plugins.economy;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import net.exotia.plugins.economy.commands.CommandsModule;
import net.exotia.plugins.economy.configuration.ConfigurationModule;
import net.exotia.plugins.economy.module.EconomyService;
import org.bukkit.plugin.java.JavaPlugin;

public final class EconomyPlugin extends JavaPlugin {
    private final Injector injector = OkaeriInjector.create();

    @Override
    public void onEnable() {
        this.injector.registerInjectable(this.injector);
        this.injector.registerInjectable(this);
        this.setupModules();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void setupModules() {
        this.injector.createInstance(ConfigurationModule.class);
        this.injector.registerInjectable(this.injector.createInstance(EconomyService.class));
        this.injector.createInstance(CommandsModule.class);
    }
}
