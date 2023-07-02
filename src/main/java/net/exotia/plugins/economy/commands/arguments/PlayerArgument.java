package net.exotia.plugins.economy.commands.arguments;

import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import panda.std.Option;
import panda.std.Result;

import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("gracz")
public class PlayerArgument implements OneArgument<Player> {
    @Inject private MessagesConfiguration messages;

    @Override
    public Result<Player, Object> parse(LiteInvocation invocation, String argument) {
        return Option.of(Bukkit.getPlayer(argument))
                .toResult(MessageUtil.serialize(this.messages.getPlayerIsOffline()));
    }
    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .map(Suggestion::of)
                .collect(Collectors.toList());
    }
}