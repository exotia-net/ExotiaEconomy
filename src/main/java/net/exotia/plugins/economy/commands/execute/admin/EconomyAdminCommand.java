package net.exotia.plugins.economy.commands.execute.admin;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "ecoadmin")
public class EconomyAdminCommand {
    @Execute(route = "give")
    public void giveCoin(CommandSender sender, @Arg Player player) {

    }
}
