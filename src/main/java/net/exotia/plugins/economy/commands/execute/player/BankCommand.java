package net.exotia.plugins.economy.commands.execute.player;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.inventory_util.InventoryOpener;
import net.exotia.plugins.economy.inventory_util.providers.bank.BankInventory;
import org.bukkit.entity.Player;

@Route(name = "bank")
public class BankCommand {
    @Inject private InventoryOpener inventoryOpener;

    @Execute
    public void bank(Player sender) {
        this.inventoryOpener.open(sender, BankInventory.class);
    }
}