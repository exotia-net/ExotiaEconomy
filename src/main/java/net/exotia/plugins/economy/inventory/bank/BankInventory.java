package net.exotia.plugins.economy.inventory.bank;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.economy.inventory.bank.deposit.BankDepositInventory;
import net.exotia.plugins.economy.inventory.bank.withdraw.BankWithdrawInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BankInventory {
    @Inject private Injector injector;

    public void open(Player player) {
        Gui gui = Gui.gui().title(Component.text("Bank")).rows(3).create();
        gui.getFiller().fill(ItemBuilder.from(Material.AIR).asGuiItem());
        gui.disableAllInteractions();

        GuiItem deposit = ItemBuilder.from(Material.STONE).asGuiItem(event -> {
            this.injector.createInstance(BankWithdrawInventory.class).open(player);
        });

        gui.setItem(15, ItemBuilder.from(Material.GRANITE).asGuiItem(event -> {
            this.injector.createInstance(BankDepositInventory.class).open(player);
        }));
        gui.setItem(13, deposit);
        gui.open(player);
    }
}
