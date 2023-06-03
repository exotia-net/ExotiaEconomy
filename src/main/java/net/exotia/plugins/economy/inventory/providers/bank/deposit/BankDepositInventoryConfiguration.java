package net.exotia.plugins.economy.inventory.providers.bank.deposit;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.exotia.plugins.economy.inventory.BaseItem;
import net.exotia.plugins.economy.inventory.InventoryConfiguration;
import net.exotia.plugins.economy.utils.ArrayUtils;
import net.exotia.plugins.economy.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class BankDepositInventoryConfiguration extends OkaeriConfig implements InventoryConfiguration {
    private String title = "Wplac pieniadze";
    private List<String> pattern = List.of(
            "# # # # # # # # #",
            "# x x x x x x x #",
            "# x x x x x x x #",
            "# # # # C # # # #"
    );
    public HashMap<Character, BaseItem> items = this.setupItems();

    private ItemStack confirmDepositItem = new ItemCreator(Material.PAPER)
            .title("&8&l>> &aWplac pieniadze")
            .lore(Arrays.asList("", "&8&l>> &7Kliknij aby wypłacić")).build();

    @Override
    public String[] getPattern() {
        return this.pattern.toArray(new String[]{});
    }
    public int getEmptySlots() {
        AtomicInteger counter = new AtomicInteger();
        this.pattern.forEach(row -> {
            counter.addAndGet(ArrayUtils.countOccurrences(row, 'x'));
        });
        return counter.get();
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
        return items;
    }
}
