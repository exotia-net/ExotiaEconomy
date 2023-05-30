package net.exotia.plugins.economy.commands.execute.player;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.bridge.api.user.ApiUserService;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.module.CoinsService;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.entity.Player;

@Route(name = "pay")
public class PayCommand {
    @Inject private CoinsService coinsService;
    @Inject private ApiEconomyService economyService;
    @Inject private MessagesConfiguration messages;

    @Execute
    public void pay(Player sender, @Arg Player target, @Arg Integer value) {
        if (!this.economyService.has(sender.getUniqueId(), value)) {
            MessageUtil.send(sender, "&8&l>> &cNie masz tyle pieniedzy!");
            return;
        }
        this.economyService.take(sender.getUniqueId(), value);
        this.economyService.give(target.getUniqueId(), value);
        MessageUtil.send(sender, "&8&l>> &aPrzelales {value} na konto gracza {player_name}"
                .replace("{value}", String.valueOf(value))
                .replace("{player_name}", target.getName())
        );
        MessageUtil.send(sender, "&8&l>> &aOtrzymales {value} od gracza {player_name}"
                .replace("{value}", String.valueOf(value))
                .replace("{player_name}", sender.getName())
        );
        this.economyService.save(sender.getUniqueId());
        this.economyService.save(target.getUniqueId());
    }
}
