package net.exotia.plugins.economy.module;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyService {
    private final Economy economy;

    public EconomyService(Plugin plugin) {
        boolean isVaultEnabled = plugin.getServer().getPluginManager().isPluginEnabled("Vault");
        if (!isVaultEnabled) {
            plugin.getLogger().severe("Payment method is VAULT but server don't have Vault or Economy manage plugin.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
        RegisteredServiceProvider<Economy> serviceProvider = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        this.economy = serviceProvider == null ? null : serviceProvider.getProvider();
        plugin.getLogger().info("Vault has hooked!");
    }

    public boolean has(Player player, double amount) {
        return !this.economy.has(player, amount);
    }
    public void take(Player player, double amount) {
        this.economy.withdrawPlayer(player, amount);
    }
    public void give(Player player, double amount) {
        this.economy.depositPlayer(player, amount);
    }
}
