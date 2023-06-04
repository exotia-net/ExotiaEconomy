package net.exotia.plugins.economy.inventory.providers.exchange.category;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.exotia.plugins.economy.configuration.objects.ExchangeCategory;
import net.exotia.plugins.economy.configuration.objects.ExchangeItem;
import net.exotia.plugins.economy.inventory.BaseItem;
import net.exotia.plugins.economy.inventory.InventoryConfiguration;
import net.exotia.plugins.economy.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ExchangeCategoryInventoryConfiguration extends OkaeriConfig implements InventoryConfiguration {
    private String title = "Skup";
    private List<String> pattern = List.of(
            "# # # # # # # # #",
            "# x x x x x x x #",
            "# x x x x x x x #",
            "# # < # R # > # #"
    );
    public HashMap<Character, BaseItem> items = this.setupItems();

    private ItemStack itemView = new ItemCreator(Material.BEDROCK).title("&7{name}").lore(
            List.of("", " &8&l>> &7Cena &a{price} x {amount}", " &8&l>> &7Kliknij aby sprzedac", "")
    ).build();

    private ItemStack nextPageItem = new ItemCreator(Material.ARROW).title("&8&l>> &aNastepna strona").build();
    private ItemStack previousPageItem = new ItemCreator(Material.ARROW).title("&8&l>> &cPoprzednia strona").build();

    @Override
    public String[] getPattern() {
        return this.pattern.toArray(new String[]{});
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public HashMap<Character, BaseItem> getItems() {
        return this.items;
    }

    public ItemStack getItem(ExchangeItem exchangeItem) {
        ItemStack itemStack = this.itemView.clone();
        itemStack.setType(exchangeItem.getMaterial());
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(meta.getDisplayName().replace("{name}", exchangeItem.getMaterial().name()));
        meta.setLore(meta.getLore().stream().map(line -> line
                .replace("{price}", String.valueOf(exchangeItem.getPrice()))
                .replace("{amount}", String.valueOf(exchangeItem.getCount()))
        ).collect(Collectors.toList()));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private HashMap<Character, BaseItem> setupItems() {
        HashMap<Character, BaseItem> items = new HashMap<>();
        items.put('#', new BaseItem(
                new ItemCreator(Material.BLACK_STAINED_GLASS_PANE).title("&8").build(), null
        ));
        items.put('R', new BaseItem(
                new ItemCreator(Material.RED_SHULKER_BOX).title("&c&lPOWROT").build(), null
        ));
        return items;
    }
}
