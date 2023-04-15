package net.exotia.plugins.economy.inventory.bank.withdraw;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.exotia.plugins.economy.utils.ItemCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BankWithdrawInventory {
    private int value;
    public void open(Player player) {
        this.value = 10;
        Gui gui = Gui.gui()
                .title(Component.text("Wypłać"))
                .rows(3)
                .create();
        gui.getFiller().fill(ItemBuilder.from(Material.AIR).asGuiItem());
        gui.disableAllInteractions();

        gui.setItem(13, this.buildItem(1, true));
        gui.setItem(12, this.buildItem(10, true));
        gui.setItem(14, this.buildItem(100, true));
        gui.setItem(3, this.buildItem(1, false));
        gui.setItem(4, this.buildItem(10, false));
        gui.setItem(5, this.buildItem(100, false));
        gui.open(player);
    }
    private GuiItem buildItem(int count, boolean adding) {
        return ItemBuilder.from(new ItemCreator(Material.SUNFLOWER).title("+" + count).build()).asGuiItem(event -> {
//            if(this.value < 1) {
//                event.getWhoClicked().sendMessage("Posiadasz za mało monet w banku!");
//            }else {
//                if(adding) this.value += count;
//                else this.value -= count;
//            }
            if(adding) this.value += count;
            else this.value -= count;
            event.getWhoClicked().sendMessage(String.valueOf(this.value))
            ;
        });
    }
}
