package net.exotia.plugins.economy.inventory.bank.withdraw;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.exotia.plugins.economy.utils.ItemCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

public class BankWithdrawInventory {
    private int value;
    private Gui gui;
    public void open(Player player) {
        this.value = 0;
        this.gui = Gui.gui()
                .title(Component.text("Wypłać"))
                .rows(6)
                .create();
        this.gui.getFiller().fill(ItemBuilder.from(Material.AIR).asGuiItem());
        this.gui.disableAllInteractions();
//        this.gui.setItem(13, this.buildItem(1, true));
//        this.gui.setItem(12, this.buildItem(10, true));
//        this.gui.setItem(14, this.buildItem(100, true));
//        this.gui.setItem(3, this.buildItem(1, false));
//        this.gui.setItem(4, this.buildItem(10, false));
//        this.gui.setItem(5, this.buildItem(100, false));
        Map.of(13,1, 12,10, 14, 100).forEach((slot, count) -> this.gui.setItem(slot, this.buildItem(count,true)));
        this.gui.setItem(1, this.acceptTransition());
        this.gui.open(player);
    }
    private GuiItem buildItem(int count, boolean adding) {
        String title = (adding ? "+" : "-") + count;
        return ItemBuilder.from(new ItemCreator(Material.SUNFLOWER).title(title).build()).asGuiItem(event -> {
            int temp = adding ? this.value + count : this.value - count;
            if(temp < 1) {
                event.getWhoClicked().sendMessage("Posiadasz za mało monet w banku!");
            }else {
                this.value = temp;
                this.gui.updateItem(1, this.acceptTransition());
            }
        });
    }
    private GuiItem acceptTransition() {
        return ItemBuilder.from(new ItemCreator(Material.NAME_TAG).title(String.valueOf(this.value)).build()).asGuiItem(event -> {
            event.getWhoClicked().sendMessage(String.valueOf(this.value));
        });
    }
}
