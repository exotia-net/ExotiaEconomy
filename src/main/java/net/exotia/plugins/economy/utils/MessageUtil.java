package net.exotia.plugins.economy.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MessageUtil {
    public static Component deserialize(String message) {
        return MiniMessage.miniMessage().deserialize(parse(message));
    }
    public static String serialize(String message) {
        return LegacyComponentSerializer.legacySection().serialize(deserialize(parse(message)));
    }
    public static String serialize(Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    public static ItemStack colorize(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(serialize(meta.getDisplayName()));
        if (meta.getLore() != null) {
            meta.setLore(meta.getLore().stream().map(MessageUtil::serialize).toList());
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static String parse(String message) {
        AtomicReference<String> atomicReference = new AtomicReference<>(message);
        Map<Character, String> map = new HashMap<>();
        map.put('0', "black");
        map.put('1', "dark_blue");
        map.put('2', "dark_green");
        map.put('3', "dark_aqua");
        map.put('4', "dark_red");
        map.put('5', "dark_purple");
        map.put('6', "gold");
        map.put('7', "gray");
        map.put('8', "dark_gray");
        map.put('9', "blue");
        map.put('a', "green");
        map.put('b', "aqua");
        map.put('c', "red");
        map.put('d', "light_purple");
        map.put('e', "yellow");
        map.put('f', "white");
        map.put('l', "bold");
        map.put('o', "italic");
        map.put('n', "underlined");
        map.put('m', "strikethrough");
        map.put('k', "obfuscated");

        map.forEach((key, value) -> {
            atomicReference.set(atomicReference.get()
                    .replace("§"+key, String.format("<%s>", value))
                    .replace("&"+key, String.format("<%s>", value))
            );
        });

        return atomicReference.get();
    }
}
