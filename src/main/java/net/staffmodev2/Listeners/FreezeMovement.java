package net.staffmodev2.Listeners;

import net.staffmodev2.Utility;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeMovement implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFreezePlayerMovement(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (Utility.freeze.contains(player.getUniqueId())) {
            Location loc = e.getFrom();
            loc.setX(loc.getBlockX()+0.5);
            loc.setY(loc.getBlockY());
            loc.setZ(loc.getBlockZ()+0.5);
            player.teleport(loc);
            e.setCancelled(true);
        }
    }
}
