package net.exotia.plugins.economy.utils.items;

import net.exotia.plugins.economy.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemCreator {
    private List<String> lore = new ArrayList<>();
    private HashMap<Enchantment, Integer> enchantments = new HashMap<>();
    private final Material material;
    private boolean hideAttributes;
    private int amount;
    private String displayName;
    private UUID uuid;
    private String oraxenId = null;

    public ItemCreator(Material material) {
        this.material = material;
        this.amount = 1;
    }
    public ItemCreator(Material material, int amount) {
        this.displayName = null;
        this.material = material;
        this.amount = amount;
    }
    public ItemCreator(Material material, UUID uuid) {
        this.displayName = null;
        this.material = material;
        this.amount = 1;
        this.uuid = uuid;
    }

    public ItemCreator title(String title) {
        this.displayName = MessageUtil.serialize(title);
        return this;
    }
    public ItemCreator lore(String lore) {
        this.lore.add(MessageUtil.serialize(lore));
        return this;
    }
    public ItemCreator lore(List<String> loreLines) {
        this.lore.addAll(loreLines.stream().map(MessageUtil::serialize).toList());
        return this;
    }
    public ItemCreator lore(String[] loreLines) {
        this.lore.addAll(Arrays.stream(loreLines).map(MessageUtil::serialize).toList());
        return this;
    }
    public ItemCreator setLoreForce(List<String> lore) {
        this.lore = lore;
        return this;
    }
    public ItemCreator setEnchantmentForce(Enchantment enchantment, int level) {
        this.enchantments.clear();
        if (enchantment == null) return this;
        this.enchantments.put(enchantment, level);
        return this;
    }
    public ItemCreator enchantment(Enchantment enchantment, int level) {
        if (enchantment == null) return this;
        this.enchantments.remove(enchantment);
        this.enchantments.put(enchantment, level);
        return this;
    }
    public ItemCreator amount(int amount) {
        this.amount = amount;
        return this;
    }
    public ItemCreator hideAttributes(boolean hideAttributes) {
        this.hideAttributes = hideAttributes;
        return this;
    }
    public ItemCreator oraxenId(String oraxenId) {
        this.oraxenId = oraxenId;
        return this;
    }
    public boolean hasEnchantment(Enchantment enchantment) {
        return this.enchantments.containsKey(enchantment);
    }
    public YourItem build() {
        Material material = this.material == null ? Material.BARRIER : this.material;
        ItemStack itemStack = new ItemStack(material, this.amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (this.displayName != null && itemMeta != null) {
            itemMeta.setDisplayName(this.displayName);
        }
        if (material == Material.PLAYER_HEAD && this.uuid != null) {
            SkullMeta skullMeta = (SkullMeta)itemMeta;
            if (skullMeta != null) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(this.uuid);
                skullMeta.setOwningPlayer(offlinePlayer);
            }
        }
        if (itemMeta != null && !this.lore.isEmpty()) {
            itemMeta.setLore(this.lore);
        }
        if (itemMeta != null && this.hideAttributes) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        itemStack.setItemMeta(itemMeta);
        itemStack.addUnsafeEnchantments(this.enchantments);
        return new YourItem(this.oraxenId, itemStack);
    }
}
