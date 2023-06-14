package net.exotia.plugins.economy.commands.handler;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.handle.PermissionHandler;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.configuration.files.MessagesConfiguration;
import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.command.CommandSender;

public class UnauthorizedCommandHandler implements PermissionHandler<CommandSender> {
    @Inject private MessagesConfiguration messages;

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, RequiredPermissions requiredPermissions) {
        String message = this.messages.getNoPermission()
                .replace("{permissions}", String.join(", ", requiredPermissions.getPermissions()));
        MessageUtil.send(sender, message);
    }
}