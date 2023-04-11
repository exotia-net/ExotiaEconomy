package net.exotia.plugins.economy.commands.handler;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.schematic.Schematic;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InvalidCommandUsageHandler implements InvalidUsageHandler<CommandSender> {
    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Schematic schematic) {
        List<String> schematics = schematic.getSchematics();

        if (schematics.size() == 1) {
            MessageUtil.send(sender, "&cNie poprawne użycie komendy &8>> &7" + schematics.get(0));
            return;
        }
        MessageUtil.send(sender, "&cNie poprawne użycie komendy!");
        for (String sch : schematics) {
            MessageUtil.send(sender, "&8 >> &7" + sch);
        }
    }
}
