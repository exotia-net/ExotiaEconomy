package net.exotia.plugins.economy.inventory_util;

import java.util.HashMap;
import java.util.List;

public interface InventoryConfiguration {
    String[] getPattern();
    String getTitle();
    HashMap<Character, BaseItem> getItems();
}
