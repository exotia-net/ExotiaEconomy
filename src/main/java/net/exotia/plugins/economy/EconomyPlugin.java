package net.exotia.plugins.economy;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import net.exotia.bridge.api.ExotiaBridgeInstance;
import net.exotia.bridge.api.ExotiaBridgeProvider;
import net.exotia.plugins.economy.commands.CommandsModule;
import net.exotia.plugins.economy.configuration.ConfigurationModule;
import net.exotia.plugins.economy.inventory.InventoryOpener;
import net.exotia.plugins.economy.listeners.PlaceCoinListener;
import net.exotia.plugins.economy.module.CoinsService;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public final class EconomyPlugin extends JavaPlugin {
    private final Injector injector = OkaeriInjector.create();

    @Override
    public void onEnable() {
        this.injector.registerInjectable(this.injector);
        this.injector.registerInjectable(this);
        this.injector.registerInjectable(BukkitAudiences.create(this));
        this.setupExotiaBridge();
        this.setupModules();
        this.getServer().getPluginManager().registerEvents(new PlaceCoinListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void setupExotiaBridge() {
        ExotiaBridgeInstance exotiaBridgeInstance = ExotiaBridgeProvider.getProvider();
        this.injector.registerInjectable(exotiaBridgeInstance.getUserService());
        this.injector.registerInjectable(exotiaBridgeInstance.getEconomyService());
    }
    private void setupModules() {
        this.injector.createInstance(ConfigurationModule.class);
        this.injector.registerInjectable(this.injector.createInstance(InventoryOpener.class));
        this.injector.registerInjectable(this.injector.createInstance(CoinsService.class));
        this.injector.createInstance(CommandsModule.class);
    }
}
