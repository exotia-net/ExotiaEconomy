package net.exotia.plugins.economy.commands;

import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.litecommands.bukkit.tools.BukkitPlayerArgument;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import net.exotia.plugins.economy.commands.arguments.CoinArgument;
import net.exotia.plugins.economy.commands.arguments.IntegerArgument;
import net.exotia.plugins.economy.commands.execute.admin.EconomyAdminCommand;
import net.exotia.plugins.economy.commands.execute.player.BankCommand;
import net.exotia.plugins.economy.commands.execute.player.ExchangeCommand;
import net.exotia.plugins.economy.commands.handler.InvalidCommandUsageHandler;
import net.exotia.plugins.economy.commands.handler.UnauthorizedCommandHandler;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.configuration.files.sections.CommandsSection;
import net.exotia.plugins.economy.configuration.objects.Coin;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandsModule {
    @Inject private Plugin plugin;
    @Inject private Injector injector;
    @Inject private CommandsSection commandsSection;
    @Inject private MessagesConfiguration messages;

    @PostConstruct
    public void onConstruct() {
        var builder = LiteBukkitFactory.builder(this.plugin.getServer(), this.plugin.getName())
                .argument(Player.class, new BukkitPlayerArgument<>(this.plugin.getServer(), MessageUtil.serialize(this.messages.getPlayerIsOffline())))
                .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>(MessageUtil.serialize(this.messages.getOnlyForPlayer())))

                .argument(Coin.class, this.injector.createInstance(CoinArgument.class))
                .argument(Integer.class, this.injector.createInstance(IntegerArgument.class))

                .commandInstance(
                        this.injector.createInstance(EconomyAdminCommand.class),
                        this.injector.createInstance(BankCommand.class),
                        this.injector.createInstance(ExchangeCommand.class)
                )

                .invalidUsageHandler(this.injector.createInstance(InvalidCommandUsageHandler.class))
                .permissionHandler(this.injector.createInstance(UnauthorizedCommandHandler.class));

        this.commandsSection.getEconomyCommands().forEach((baseCommand, command) -> {
            builder.commandEditor(baseCommand, (editor) -> editor.name(command));
        });

        builder.register();
    }
}
