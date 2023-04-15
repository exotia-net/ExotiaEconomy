package net.exotia.plugins.economy.commands.execute.player;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import net.exotia.plugins.economy.inventory.bank.BankInventory;
import org.bukkit.entity.Player;

@Route(name = "bank")
public class BankCommand {

    @Execute
    public void bank(Player sender) {
        new BankInventory().open(sender);
    }
}