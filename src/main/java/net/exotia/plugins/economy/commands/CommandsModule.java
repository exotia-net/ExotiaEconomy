package net.exotia.plugins.economy.commands;

import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.litecommands.bukkit.tools.BukkitPlayerArgument;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import net.exotia.plugins.economy.commands.execute.admin.EconomyAdminCommand;
import net.exotia.plugins.economy.commands.handler.InvalidCommandUsageHandler;
import net.exotia.plugins.economy.commands.handler.UnauthorizedCommandHandler;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandsModule {
    @Inject private Plugin plugin;
    @Inject private Injector injector;

    @PostConstruct
    public void onConstruct() {
        LiteBukkitFactory.builder(this.plugin.getServer(), this.plugin.getName())
                .argument(Player.class, new BukkitPlayerArgument<>(this.plugin.getServer(), MessageUtil.implementColors("&8&l>> &cTen gracz jest offline!")))
                .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>(MessageUtil.implementColors("&8&l>> &cTa komenda jest tylko dla gracza!")))

                .commandInstance(this.injector.createInstance(EconomyAdminCommand.class))

                .invalidUsageHandler(new InvalidCommandUsageHandler())
                .permissionHandler(new UnauthorizedCommandHandler())
                .register();
    }
}
