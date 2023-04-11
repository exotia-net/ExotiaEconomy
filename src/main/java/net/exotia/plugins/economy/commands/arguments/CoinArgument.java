package net.exotia.plugins.economy.commands.arguments;

import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.configuration.objects.Coin;
import net.exotia.plugins.economy.module.EconomyService;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import panda.std.Option;
import panda.std.Result;

import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("coin")
public class CoinArgument implements OneArgument<Coin> {
    @Inject private EconomyService economyService;

    @Override
    public Result<Coin, Object> parse(LiteInvocation invocation, String argument) {
        return Option.of(this.economyService.findCoin(Integer.parseInt(argument)))
                .toResult(MessageUtil.implementColors("&8&l>> &cNiepoprawna moneta!"));
    }
    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.economyService.findAllCoins().stream()
                .map(coin -> String.valueOf(coin.getValue()))
                .map(Suggestion::of)
                .collect(Collectors.toList());
    }
}