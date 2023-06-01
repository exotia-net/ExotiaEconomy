package net.exotia.plugins.economy.inventory.bank.deposit;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.StorageGui;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.exotia.plugins.economy.utils.ItemCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public class BankDepositConfiguration extends OkaeriConfig {
    private String title = "&lWPLAC PIENIADZE...";
    private int rows = 4;
    private ItemStack borderItem = new ItemCreator(Material.BLACK_STAINED_GLASS_PANE).title("&7").build();

    private int depositButtonSlot = 31;
    private ItemStack depositButton = new ItemCreator(Material.GREEN_SHULKER_BOX)
            .title("&7Kwota do wplacenia: &a{amount}")
            .lore("&8&l>> &7Kliknij aby wpłacić")
            .build();

    public StorageGui getGui() {
        StorageGui gui = Gui.storage()
                .title(Component.text(this.title))
                .rows(this.rows).create();
        gui.getFiller().fillBorder(ItemBuilder.from(this.borderItem).asGuiItem(event -> event.setCancelled(true)));
//        gui.disableAllInteractions();
        return gui;
    }
    public ItemStack getDepositButton(int amount) {
        ItemStack item = this.depositButton.clone();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(meta.getDisplayName().replace("{amount}", String.valueOf(amount)));
        item.setItemMeta(meta);
        return item;
    }
}
