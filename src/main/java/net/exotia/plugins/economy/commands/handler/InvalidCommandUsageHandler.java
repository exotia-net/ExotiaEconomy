package net.exotia.plugins.economy.commands.handler;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.schematic.Schematic;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InvalidCommandUsageHandler implements InvalidUsageHandler<CommandSender> {
    @Inject private MessagesConfiguration messages;

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Schematic schematic) {
        List<String> schematics = schematic.getSchematics();

        if (schematics.size() == 1) {
            MessageUtil.send(sender, this.messages.getInvalidCommandUsage().replace("{command}", schematics.get(0)));
            return;
        }
        MessageUtil.send(sender, this.messages.getInvalidCommandHeader());
        for (String sch : schematics) {
            MessageUtil.send(sender, this.messages.getCommandTemplate().replace("{schematic}", sch));
        }
    }
}
