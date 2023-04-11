package net.exotia.plugins.economy.commands.execute.player;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.module.EconomyService;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.entity.Player;

@Route(name = "balance")
public class BalanceCommand {
    @Inject private EconomyService economyService;
    @Inject private MessagesConfiguration messages;

    @Execute
    public void balance(Player player) {
        this.messages.getBalance().stream()
                .map(message -> message
                        .replace("{account_balance}", String.valueOf(this.economyService.getBalance(player)))
                        .replace("{physical_balance}", String.valueOf(this.economyService.getPlayerPhysicalCoins(player))))
                .forEach(message -> MessageUtil.send(player, message));
    }
}
