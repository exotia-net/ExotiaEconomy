package net.exotia.plugins.economy.inventory.exchange;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.exotia.plugins.economy.configuration.objects.ExchangeItem;
import net.exotia.plugins.economy.utils.ItemCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

@Getter
public class ExchangeInventoryConfiguration extends OkaeriConfig {
    private String title = "&lSKUP...";
    private int rows = 5;
    private ItemStack borderItem = new ItemCreator(Material.BLACK_STAINED_GLASS_PANE).title("&7").build();

    private ItemStack nextPageItem = new ItemCreator(Material.ARROW).title("&8&l>> &aNastepna strona").build();
    private int nextPageSlot = 42;
    private ItemStack previousPageItem = new ItemCreator(Material.ARROW).title("&8&l>> &cPoprzednia strona").build();
    private int previousPageSlot = 38;

    private ItemStack sellDifferentItem = new ItemCreator(Material.CHEST).title("&8&l>> &a&lSPRZEDAJ WIELE &8&l<<").build();
    private int sellDifferentPageSlot = 40;

    private ItemStack itemView = new ItemCreator(Material.BEDROCK).title("&7{item_name}")
            .lore(Arrays.asList("", "&8&l>> &7Cena sprzedazy: &a{item_price}", "&8&l>> &7Minimalna ilosc: &a{item_count}", ""))
            .build();

    private HashMap<Material, ExchangeItem> items = this.setupItems();

    private HashMap<Material, ExchangeItem> setupItems() {
        HashMap<Material, ExchangeItem> items = new HashMap<>();
        items.put(Material.BEDROCK, new ExchangeItem(8, 12));
        return items;
    }
    public ItemStack getItem(Material material, ExchangeItem exchangeItem) {
        ItemStack itemStack = this.itemView.clone();
        itemStack.setType(material);
        itemStack.setAmount(exchangeItem.getCount());
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(meta.getDisplayName().replace("{item_name}", material.name()));
        meta.setLore(meta.getLore().stream().map(line -> line
                .replace("{item_price}", String.valueOf(exchangeItem.getPrice()))
                .replace("{item_count}", String.valueOf(exchangeItem.getCount()))
        ).collect(Collectors.toList()));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public PaginatedGui getGui() {
        PaginatedGui gui = Gui.paginated()
                .title(Component.text(this.title))
                .rows(this.rows).create();
        gui.getFiller().fillBorder(ItemBuilder.from(this.borderItem).asGuiItem(event -> event.setCancelled(true)));
        gui.setItem(this.nextPageSlot, new GuiItem(this.nextPageItem, event -> gui.next()));
        gui.setItem(this.previousPageSlot, new GuiItem(this.previousPageItem, event -> gui.previous()));
        gui.disableAllInteractions();
        return gui;
    }
}
