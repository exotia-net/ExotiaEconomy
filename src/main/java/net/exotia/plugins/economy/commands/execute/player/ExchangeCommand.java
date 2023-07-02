package net.exotia.plugins.economy.commands.execute.player;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.inventory.InventoryOpener;
import net.exotia.plugins.economy.inventory.providers.exchange.ExchangeInventory;
import org.bukkit.entity.Player;

@Route(name = "skup")
public class ExchangeCommand {
    @Inject private InventoryOpener inventoryOpener;

    @Execute()
    public void exchange(Player player) {
        this.inventoryOpener.open(player, ExchangeInventory.class);
    }
}
