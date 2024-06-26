package net.exotia.plugins.economy.inventory.providers.bank.withdraw;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import net.exotia.plugins.economy.inventory.BaseItem;
import net.exotia.plugins.economy.inventory.InventoryConfiguration;
import net.exotia.plugins.economy.utils.items.ItemCreator;
import net.exotia.plugins.economy.utils.items.YourItem;
import org.bukkit.Material;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class BankWithdrawInventoryConfiguration extends OkaeriConfig implements InventoryConfiguration {
    private String title = "Wyplac pieniadze";
    private List<String> pattern = List.of(
            "# # # # # # # # #",
            "# # x # # # x # #",
            "# # x # A # x # #",
            "# # x # # # x # #",
            "# # # # # # # # #"
    );
    public HashMap<Character, BaseItem> items = this.setupItems();

    private Map<Integer, CoinConfigEntity> addValueSlots = Map.of(
            11, new CoinConfigEntity(new ItemCreator(Material.GREEN_SHULKER_BOX).title("&a&l+ {amount}").build(), 1),
            20, new CoinConfigEntity(new ItemCreator(Material.GREEN_SHULKER_BOX).title("&a&l+ {amount}").build(), 10),
            29, new CoinConfigEntity(new ItemCreator(Material.GREEN_SHULKER_BOX).title("&a&l+ {amount}").build(), 100));
    private Map<Integer, CoinConfigEntity> removeValueSlots = Map.of(
            15, new CoinConfigEntity(new ItemCreator(Material.RED_SHULKER_BOX).title("&c&l- {amount}").build(), 1),
            24, new CoinConfigEntity(new ItemCreator(Material.RED_SHULKER_BOX).title("&c&l- {amount}").build(), 10),
            33, new CoinConfigEntity(new ItemCreator(Material.RED_SHULKER_BOX).title("&c&l- {amount}").build(), 100));

    private YourItem errorItem = new ItemCreator(Material.BARRIER).title("&cMinimalna kwota do wyplacenia to 1!").build();
    private YourItem noEnoughCoins = new ItemCreator(Material.BARRIER).title("&cNie masz tyle pieniedzy!").build();

    @Comment("In ticks. 20 ticks = 1 seccond")
    private long errorDisplayTime = 20;

    private YourItem acceptWithdrawItem = new ItemCreator(Material.PAPER).title("&7Kwota: &a{amount}").lore("&8&l>> &7Kliknij aby wypłacić").build();
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
        return items;
    }
}
