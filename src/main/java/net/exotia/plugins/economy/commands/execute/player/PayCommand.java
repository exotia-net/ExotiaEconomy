package net.exotia.plugins.economy.commands.execute.player;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.module.EconomyService;
import org.bukkit.entity.Player;

@Route(name = "pay")
public class PayCommand {
    @Inject private EconomyService economyService;

    @Execute
    public void pay(Player sender, @Arg Player target, @Arg Integer value) {

    }
}
