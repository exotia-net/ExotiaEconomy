package net.exotia.plugins.economy.inventory.bank.withdraw;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import eu.okaeri.injector.annotation.Inject;
import net.exotia.bridge.api.user.ApiUser;
import net.exotia.bridge.api.user.ApiUserService;
import net.exotia.plugins.economy.utils.ItemCreator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.Buildable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class BankWithdrawInventory {
    @Inject private Plugin plugin;
    private int value;
    private Gui gui;
    @Inject private ApiUserService userService;

    public void open(Player player) {
        this.value = 0;
        this.gui = Gui.gui()
                .title(Component.text("Wypłać"))
                .rows(5)
                .create();
        this.gui.getFiller().fill(ItemBuilder.from(Material.AIR).asGuiItem());
        this.gui.disableAllInteractions();
        Map.of(11,1, 20,10, 29, 100).forEach((slot, count) -> this.gui.setItem(slot, this.buildItem(count,true)));
        Map.of(15, 1, 24, 10, 33, 100).forEach((slot, count) -> this.gui.setItem(slot, this.buildItem(count, false)));
        this.gui.setItem(22, this.acceptTransition());
        this.gui.open(player);
    }
    private GuiItem buildItem(int count, boolean adding) {
        String title = (adding ? "+" : "-") + count;
        return ItemBuilder.from(new ItemCreator(Material.SUNFLOWER).title(title).build()).asGuiItem(event -> {
            int temp = adding ? this.value + count : this.value - count;
            if(temp < 1) {
//                GuiItem barrier = new GuiItem(Material.BARRIER);
                event.getWhoClicked().sendMessage("Posiadasz za mało monet w banku!");
//                this.gui.updateItem(event.getSlot(), barrier);
//                Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
////                    this.buildItem(count, adding);
//                    this.gui.updateItem(event.getSlot(), this.gui.getGuiItem(event.getSlot()));
//                    event.getWhoClicked().sendMessage("kilkniosda");
//                    this.gui.update();
//                },20);
            }else {
                this.value = temp;
                this.gui.updateItem(22, this.acceptTransition());
            }
        });
    }
    private GuiItem acceptTransition() {
        return ItemBuilder.from(new ItemCreator(Material.NAME_TAG).title(String.valueOf(this.value)).build()).asGuiItem(event -> {
            this.calculateCoinsToWithdraw(value, (Player) event.getWhoClicked());
        });
    }

    private void calculateCoinsToWithdraw(int value, Player player) {
        int[] denominations = {100, 10, 1};
        int[] coinsUsed = new int[denominations.length];
        int remainder = value;

        for (int i = 0; i < denominations.length; i++) {
            coinsUsed[i] = remainder / denominations[i];
            remainder = remainder % denominations[i];
        }

        for (int i = 0; i < denominations.length; i++) {
            player.sendMessage(coinsUsed[i] + " x " + denominations[i]);
        }
    }

    public GuiItem test(Player player) {
        ApiUser user = this.userService.getUser(player.getUniqueId());
        player.sendMessage("Twoje saldo: " + user.getBalance());
        return this.acceptTransition();
    }
}
