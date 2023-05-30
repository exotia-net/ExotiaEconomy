package net.exotia.plugins.economy.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlaceCoinListener implements Listener {
    @EventHandler
    public void onCoinPlace(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType().equals(Material.AIR)) return;
        NBTItem item = new NBTItem(event.getItem());
        event.setCancelled(item.getBoolean("isCoin") && event.getAction().equals(Action.RIGHT_CLICK_BLOCK));
    }
}
