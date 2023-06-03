package net.exotia.plugins.economy.inventory;

import java.util.HashMap;

public interface InventoryConfiguration {
    String[] getPattern();
    String getTitle();
    HashMap<Character, BaseItem> getItems();
}
