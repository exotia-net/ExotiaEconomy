package net.exotia.plugins.economy.inventory_util.providers.bank;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.exotia.plugins.economy.inventory_util.BaseItem;
import net.exotia.plugins.economy.inventory_util.InventoryConfiguration;
import net.exotia.plugins.economy.utils.ItemCreator;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;

@Getter
public class BankInventoryConfiguration extends OkaeriConfig implements InventoryConfiguration {
    private String title = "Bank";
    private List<String> pattern = List.of(
            "# # # # # # # # #",
            "# # # W # D # # #",
            "# # # # # # # # #"
    );
    public HashMap<Character, BaseItem> items = this.setupItems();

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

    private HashMap<Character, BaseItem> setupItems() {
        HashMap<Character, BaseItem> items = new HashMap<>();
        items.put('#', new BaseItem(
                new ItemCreator(Material.BLACK_STAINED_GLASS_PANE).title("&8").build(), null
        ));
        items.put('W', new BaseItem(
                new ItemCreator(Material.DIAMOND).title("&8&l>> &aWyplac pieniadze").build(), null
        ));
        items.put('D', new BaseItem(
                new ItemCreator(Material.NETHERITE_INGOT).title("&8&l>> &aWplac pieniadze").build(), null
        ));
        return items;
    }
}
