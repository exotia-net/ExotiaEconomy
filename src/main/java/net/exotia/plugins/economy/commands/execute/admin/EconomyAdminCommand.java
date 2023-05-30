package net.exotia.plugins.economy.commands.execute.admin;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.bridge.api.user.ApiUser;
import net.exotia.bridge.api.user.ApiUserService;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.configuration.objects.Coin;
import net.exotia.plugins.economy.module.CoinsService;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "ecoadmin")
public class EconomyAdminCommand {
    @Inject private CoinsService coinsService;
    @Inject private ApiEconomyService economyService;
    @Inject private ApiUserService userService;
    @Inject private MessagesConfiguration messages;

    @Execute(route = "give_physical")
    public void giveCoin(@Arg Player player, @Arg Coin coin) {
        player.getInventory().addItem(this.coinsService.createCoin(coin));
    }

    @Execute(route = "give_virtual")
    public void giveMoney(CommandSender sender, @Arg Player player, @Arg Integer value) {
        this.economyService.give(player.getUniqueId(), value);
        this.economyService.save(player.getUniqueId());
        MessageUtil.send(sender, "&8&l>> &aDodałes {value} do konta gracza {player_name}"
                .replace("{value}", String.valueOf(value))
                .replace("{player_name}", player.getName())
        );
        MessageUtil.send(player, "&8&l>> &aNa twoje konto wplynelo {value}!".replace("{value}", String.valueOf(value)));
    }
    @Execute(route = "take")
    public void takeMoney(CommandSender sender, @Arg Player player, @Arg Integer value) {
        this.economyService.take(player.getUniqueId(), value);
        this.economyService.save(player.getUniqueId());
        MessageUtil.send(sender, "&8&l>> &aZabrales {value} z konta gracza {player_name}"
                .replace("{value}", String.valueOf(value))
                .replace("{player_name}", player.getName())
        );
        MessageUtil.send(player, "&8&l>> &aZ twojego konta zostalo zabrane {value}!".replace("{value}", String.valueOf(value)));
    }
    @Execute(route = "balance")
    public void balance(@Arg Player player) {
        ApiUser apiUser = this.userService.getUser(player.getUniqueId());
        this.messages.getBalance().stream()
                .map(message -> message
                        .replace("{account_balance}", String.valueOf(apiUser.getBalance()))
                        .replace("{physical_balance}", String.valueOf(this.coinsService.getPlayerPhysicalCoins(player))))
                .forEach(message -> MessageUtil.send(player, message));
    }
}
