package net.exotia.plugins.economy.commands.execute.admin;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.objects.Coin;
import net.exotia.plugins.economy.module.EconomyService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "ecoadmin")
public class EconomyAdminCommand {
    @Inject private EconomyService economyService;

    @Execute(route = "give")
    public void giveCoin(CommandSender sender, @Arg Player player, @Arg Coin coin) {
        player.getInventory().addItem(this.economyService.createCoin(coin));
    }
}
