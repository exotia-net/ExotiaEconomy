package net.exotia.plugins.economy.inventory.bank.withdraw;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import net.exotia.plugins.economy.utils.ItemCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

@Getter
public class BankWithdrawConfiguration extends OkaeriConfig {
    private String title = "&lWypłać";
    private int rows = 5;

    private boolean fillEmptySlots = true;
    private ItemStack fillItem = new ItemCreator(Material.BLACK_STAINED_GLASS_PANE).title("&7").build();

    private Map<Integer, Integer> addValueSlots = Map.of(11, 1, 20, 10, 29, 100);
    private Map<Integer, Integer> removeValueSlots = Map.of(15, 1, 24, 10, 33, 100);
    
    private ItemStack addCoins =  new ItemCreator(Material.GREEN_SHULKER_BOX).title("&a&l+ {amount}").build();
    private ItemStack removeCoins = new ItemCreator(Material.RED_SHULKER_BOX).title("&c&l- {amount}").build();
    
    private ItemStack errorItem = new ItemCreator(Material.BARRIER).title("&cMinimalna kwota do wyplacenia to 1!").build();
    private ItemStack noEnoughCoins = new ItemCreator(Material.BARRIER).title("&cNie masz tyle pieniedzy!").build();

    @Comment("In ticks. 20 ticks = 1 seccond")
    private long errorDisplayTime = 20;

    private int acceptTransactionSlot = 22;
    private ItemStack acceptTransaction = new ItemCreator(Material.PAPER)
            .title("&7Kwota: &a{amount}")
            .lore("&8&l>> &7Kliknij aby wypłacić")
            .build();
    public ItemStack getButton(boolean isAdding, int count) {
        ItemStack item = (isAdding ? this.addCoins : this.removeCoins).clone();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(meta.getDisplayName().replace("{amount}", String.valueOf(count)));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack getAcceptTransaction(int amount) {
        ItemStack item = this.acceptTransaction.clone();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(meta.getDisplayName().replace("{amount}", String.valueOf(amount)));
        item.setItemMeta(meta);
        return item;
    }

    public Gui getGui() {
        Gui gui = Gui.gui()
                .title(Component.text(this.title))
                .rows(this.rows).create();
        if (this.fillEmptySlots) {
            gui.getFiller().fill(ItemBuilder.from(this.fillItem).asGuiItem());
        }
        gui.disableAllInteractions();
        return gui;
    }
}
