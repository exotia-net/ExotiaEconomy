package net.exotia.plugins.economy.commands.execute.player;

import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiUser;
import net.exotia.bridge.api.user.ApiUserService;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.utils.MessageUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.exotia.plugins.economy.module.CoinsService;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

@Route(name = "balance")
public class BalanceCommand {
    @Inject private CoinsService coinsService;
    @Inject private ApiUserService userService;
    @Inject private MessagesConfiguration messages;
    @Inject private BukkitAudiences bukkitAudiences;

    @Execute
    public void balance(Player player, @Joiner String test) {
        ApiUser apiUser = this.userService.getUser(player.getUniqueId());
        this.messages.getBalance().stream()
                .map(message -> message
                        .replace("{account_balance}", String.valueOf(apiUser.getBalance()))
                        .replace("{physical_balance}", String.valueOf(this.coinsService.getPlayerPhysicalCoins(player))))
                .map(MessageUtil::deserialize)
                .forEach(component -> this.bukkitAudiences.player(player).sendMessage(component));
    }
}
