package net.exotia.plugins.economy.commands.handler;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.handle.PermissionHandler;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.command.CommandSender;

public class UnauthorizedCommandHandler implements PermissionHandler<CommandSender> {
    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, RequiredPermissions requiredPermissions) {
        MessageUtil.send(sender, "&cNie masz permisji do tej komendy! &7(" + String.join(", ", requiredPermissions.getPermissions()) + ")");
    }
}