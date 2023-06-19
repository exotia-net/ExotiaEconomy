package net.exotia.plugins.economy.inventory.providers.exchange;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.exotia.plugins.economy.configuration.objects.ExchangeCategory;
import net.exotia.plugins.economy.inventory.BaseItem;
import net.exotia.plugins.economy.inventory.InventoryConfiguration;
import net.exotia.plugins.economy.utils.items.ItemCreator;
import net.exotia.plugins.economy.utils.items.YourItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

@Getter
public class ExchangeInventoryConfiguration extends OkaeriConfig implements InventoryConfiguration {
    private String title = "Skup";
    private List<String> pattern = List.of(
            "# # # # # # # # #",
            "# x x x x x x x #",
            "# # # # S # # # #"
    );
    public HashMap<Character, BaseItem> items = this.setupItems();

    private YourItem categoryView = new ItemCreator(Material.BEDROCK).title("&7{name}").lore(List.of("", " &8&l>> &7Kliknij aby przejsc")).build();
    private YourItem sellManyItem = new ItemCreator(Material.GOLD_BLOCK).title("&6&lSPRZEDAJ WIELE").build();

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

    public ItemStack getCategoryItem(ExchangeCategory exchangeCategory) {
        ItemStack itemStack = this.categoryView.getItem().clone();
        itemStack.setType(exchangeCategory.getIcon());
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(meta.getDisplayName().replace("{name}", exchangeCategory.getName()));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private HashMap<Character, BaseItem> setupItems() {
        HashMap<Character, BaseItem> items = new HashMap<>();
        items.put('#', new BaseItem(
                new ItemCreator(Material.BLACK_STAINED_GLASS_PANE).title("&8").build(), null
        ));
        return items;
    }
}
