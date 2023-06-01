package net.exotia.plugins.economy.inventory_util;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import net.exotia.plugins.economy.factory.ConfigurationFactory;
import net.exotia.plugins.economy.inventory_util.providers.bank.BankInventoryConfiguration;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.window.Window;

public class InventoryOpener {
    @Inject private Injector injector;
    @Inject private ConfigurationFactory configurationFactory;

    @PostConstruct
    public void onConstruct() {
        // TODO: Tu maja byc rejestrowane pliki konfiguracyjne
        this.injector.registerInjectable(
                this.configurationFactory.produce(BankInventoryConfiguration.class, "inventories/bank_inventory.yml")
        );
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
}
