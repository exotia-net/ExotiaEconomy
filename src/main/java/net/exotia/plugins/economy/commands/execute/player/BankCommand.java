package net.exotia.plugins.economy.commands.execute.player;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.inventory.bank.BankInventory;
import org.bukkit.entity.Player;

@Route(name = "bank")
public class BankCommand {
    @Inject private Injector injector;

    @Execute
    public void bank(Player sender) {
        this.injector.createInstance(BankInventory.class).open(sender);
    }
}