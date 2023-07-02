package net.exotia.plugins.economy.commands.handler;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.schematic.Schematic;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.utils.MessageUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InvalidCommandUsageHandler implements InvalidUsageHandler<CommandSender> {
    @Inject private MessagesConfiguration messages;
    @Inject private BukkitAudiences bukkitAudiences;

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Schematic schematic) {
        List<String> schematics = schematic.getSchematics();

        if (schematics.size() == 1) {
            this.bukkitAudiences.sender(sender).sendMessage(
                    MessageUtil.deserialize(this.messages.getInvalidCommandUsage().replace("{command}", schematics.get(0)))
            );
            return;
        }
        this.bukkitAudiences.sender(sender).sendMessage(MessageUtil.deserialize(this.messages.getInvalidCommandHeader()));
        for (String sch : schematics) {
            this.bukkitAudiences.sender(sender).sendMessage(MessageUtil.deserialize(
                    this.messages.getCommandTemplate().replace("{schematic}", sch)
            ));
        }
    }
}
