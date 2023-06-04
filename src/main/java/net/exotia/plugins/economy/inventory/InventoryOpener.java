package net.exotia.plugins.economy.inventory;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import net.exotia.plugins.economy.configuration.files.ExchangeConfiguration;
import net.exotia.plugins.economy.factory.ConfigurationFactory;
import net.exotia.plugins.economy.inventory.providers.bank.BankInventoryConfiguration;
import net.exotia.plugins.economy.inventory.providers.bank.deposit.BankDepositInventoryConfiguration;
import net.exotia.plugins.economy.inventory.providers.bank.withdraw.BankWithdrawInventoryConfiguration;
import net.exotia.plugins.economy.inventory.providers.exchange.ExchangeInventoryConfiguration;
import net.exotia.plugins.economy.inventory.providers.exchange.category.ExchangeCategoryInventoryConfiguration;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryOpener {
    @Inject private Injector injector;
    @Inject private ConfigurationFactory configurationFactory;
    private final List<OkaeriConfig> configs = new ArrayList<>();

    @PostConstruct
    public void onConstruct() {
        // TODO: Tu maja byc rejestrowane pliki konfiguracyjne
        this.registerConfiguration(BankInventoryConfiguration.class, "main_inventory", "bank");
        this.registerConfiguration(BankWithdrawInventoryConfiguration.class, "withdraw_inventory", "bank");
        this.registerConfiguration(BankDepositInventoryConfiguration.class, "deposit_inventory", "bank");
        this.registerConfiguration(ExchangeInventoryConfiguration.class, "main_inventory", "exchange");
        this.registerConfiguration(ExchangeCategoryInventoryConfiguration.class, "category_inventory", "exchange");
    }

    public void open(Player player, Class<? extends OpenableInventory> openableInventory, String... params) {
        OpenableInventory inventory = this.injector.createInstance(openableInventory);
        Window window = Window.single()
                .setViewer(player)
                .setGui(inventory.createGui(this, params))
                .setTitle(inventory.getConfiguration().getTitle())
                .build();
        window.open();
    }
    private void registerConfiguration(Class<? extends OkaeriConfig> configuration, String name, String category) {
        OkaeriConfig config = this.configurationFactory.produce(configuration, String.format("inventories/%s/%s.yml", category, name));
        this.injector.registerInjectable(config);
        this.configs.add(config);
    }
    public void reloadConfigs() {
        this.configs.forEach(okaeriConfig -> okaeriConfig.load(true));
    }
}
