package net.exotia.plugins.economy.commands.execute.player;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.inventory.exchange.ExchangeInventory;
import org.bukkit.entity.Player;

@Route(name = "skup")
public class ExchangeCommand {
    @Inject private Injector injector;

    @Execute()
    public void exchange(Player player) {
        this.injector.createInstance(ExchangeInventory.class).open(player);
    }
}