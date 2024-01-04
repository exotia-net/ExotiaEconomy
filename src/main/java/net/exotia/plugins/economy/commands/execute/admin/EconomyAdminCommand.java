package net.exotia.plugins.economy.commands.execute.admin;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.ConfigurationService;
import net.exotia.plugins.economy.configuration.objects.Coin;
import net.exotia.plugins.economy.module.CoinsService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "ecoadmin")
@Permission("exotia.economy.admin")
public class EconomyAdminCommand {
    @Inject private CoinsService coinsService;
    @Inject private Injector injector;

    @Execute(route = "give_physical")
    public void giveCoin(@Arg Player player, @Arg Coin coin) {
        player.getInventory().addItem(this.coinsService.createCoin(coin));
    }

    @Execute(route = "reload")
    public void reload(CommandSender sender) {
        this.injector.createInstance(ConfigurationService.class).reload();
    }
}
