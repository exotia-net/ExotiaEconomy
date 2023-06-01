package net.exotia.plugins.economy.inventory_util.providers.bank.withdraw;

import eu.okaeri.configs.OkaeriConfig;
import net.exotia.plugins.economy.inventory_util.BaseItem;
import net.exotia.plugins.economy.inventory_util.InventoryConfiguration;
import net.exotia.plugins.economy.utils.ItemCreator;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;

public class BankWithdrawInventoryConfiguration extends OkaeriConfig implements InventoryConfiguration {
    private String title = "Wyplac pieniadze";
    private List<String> pattern = List.of(
            "# # # # # # # # #",
            "# # 1 # # # 1 # #",
            "# # 2 # A # 2 # #",
            "# # 3 # # # 3 # #",
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
        items.put('A', new BaseItem(
                new ItemCreator(Material.PAPER).title("&7Kwota: &a{amount}").lore("&8&l>> &7Kliknij aby wypłacić").build(), null
        ));
        return items;
    }
}
