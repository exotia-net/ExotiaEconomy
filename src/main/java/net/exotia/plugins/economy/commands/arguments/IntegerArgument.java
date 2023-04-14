package net.exotia.plugins.economy.commands.arguments;

import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import net.exotia.plugins.economy.utils.MessageUtil;
import panda.std.Option;
import panda.std.Result;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("kwota")
public class IntegerArgument implements OneArgument<Integer> {
    @Override
    public Result<Integer, Object> parse(LiteInvocation invocation, String argument) {
        return Option.of(Integer.parseInt(argument)).toResult(MessageUtil.implementColors("&8&l>> &cNiepoprawna kwota!"));
    }
    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Arrays.asList(1, 5, 10, 50, 100).stream()
                .map(String::valueOf)
                .map(Suggestion::of)
                .collect(Collectors.toList());
    }
}