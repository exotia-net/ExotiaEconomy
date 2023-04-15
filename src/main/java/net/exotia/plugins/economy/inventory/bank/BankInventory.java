package net.exotia.plugins.economy.inventory.bank;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.exotia.plugins.economy.inventory.bank.withdraw.BankWithdrawInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BankInventory {
    public void open(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("Bank"))
                .rows(3)
                .create();
        gui.getFiller().fill(ItemBuilder.from(Material.AIR).asGuiItem());
        gui.disableAllInteractions();
        GuiItem deposit = ItemBuilder.from(Material.STONE).asGuiItem(event -> {
            new BankWithdrawInventory().open(player);
        });
        gui.setItem(13, deposit);
        gui.open(player);
    }
}
