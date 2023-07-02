package net.exotia.plugins.economy.commands.execute.player;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiEconomyService;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.configuration.files.PluginConfiguration;
import net.exotia.plugins.economy.module.CoinsService;
import net.exotia.plugins.economy.utils.MessageUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.entity.Player;

@Route(name = "pay")
public class PayCommand {
    @Inject private CoinsService coinsService;
    @Inject private ApiEconomyService economyService;
    @Inject private MessagesConfiguration messages;
    @Inject private PluginConfiguration configuration;
    @Inject private BukkitAudiences bukkitAudiences;

    @Execute
    public void pay(Player sender, @Arg Player target, @Arg Integer value) {
        if (sender.equals(target)) {
            this.bukkitAudiences.player(sender).sendMessage(MessageUtil.deserialize(this.messages.getYouCanNotTransferMoneySelf()));
            return;
        }
        if (value < this.configuration.getMinTransferAmount()) {
            this.bukkitAudiences.player(sender).sendMessage(MessageUtil.deserialize(
                    this.messages.getTooSmallAmount().replace("{min}", String.valueOf(this.configuration.getMinTransferAmount()))
            ));
            return;
        }
        if (!this.economyService.has(sender.getUniqueId(), value)) {
            this.bukkitAudiences.player(sender).sendMessage(MessageUtil.deserialize(this.messages.getYouDontHaveEnoughMoney()));
            return;
        }

        this.economyService.take(sender.getUniqueId(), value);
        double doubleValue = (double) value;
        double total = doubleValue - ((doubleValue*this.configuration.getTransferFee())/100);
        this.economyService.give(target.getUniqueId(), (int) total);

        this.bukkitAudiences.player(sender).sendMessage(MessageUtil.deserialize(
                this.messages.getSuccessfullyTransferred()
                        .replace("{value}", String.valueOf(value))
                        .replace("{player_name}", target.getName())
        ));
        this.bukkitAudiences.player(target).sendMessage(MessageUtil.deserialize(
                this.messages.getReceivedTransfer()
                        .replace("{value}", String.valueOf((int)total))
                        .replace("{player_name}", sender.getName())
        ));

        this.economyService.save(sender.getUniqueId());
        this.economyService.save(target.getUniqueId());
    }
}
