package net.exotia.plugins.economy.inventory;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import net.exotia.plugins.economy.factory.ConfigurationFactory;
import net.exotia.plugins.economy.inventory.providers.bank.BankInventoryConfiguration;
import net.exotia.plugins.economy.inventory.providers.bank.deposit.BankDepositInventoryConfiguration;
import net.exotia.plugins.economy.inventory.providers.bank.withdraw.BankWithdrawInventoryConfiguration;
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
        this.injector.registerInjectable(
                this.configurationFactory.produce(BankWithdrawInventoryConfiguration.class, "inventories/bank_withdraw_inventory.yml")
        );
        this.injector.registerInjectable(
                this.configurationFactory.produce(BankDepositInventoryConfiguration.class, "inventories/bank_deposit_inventory.yml")
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
