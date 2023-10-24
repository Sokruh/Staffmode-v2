package net.staffmodev2.Listeners;

import net.staffmodev2.Utility;
import org.bukkit.GameEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.world.GenericGameEvent;

public class InterruptListeners implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if (Utility.staffmode_users.contains(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        if (Utility.staffmode_users.contains(player.getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (e.getPlayer() instanceof Player){
            Player player = e.getPlayer();
            if (Utility.staffmode_users.contains(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (Utility.staffmode_users.contains(player.getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (Utility.staffmode_users.contains(player.getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (Utility.staffmode_users.contains(player.getUniqueId())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (Utility.staffmode_users.contains(player.getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHandChange(PlayerSwapHandItemsEvent e)  {
        Player player = e.getPlayer();
        if (Utility.staffmode_users.contains(player.getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onStep(GenericGameEvent e) {
        if (!(e.getEvent().equals(GameEvent.STEP))) {
            return;
        }
        if (e.getEntity().getType().equals(EntityType.PLAYER)) {
            Player player = (Player) e.getEntity();
            if (Utility.staffmode_users.contains(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onGroundHit(GenericGameEvent e) {
        if (!(e.getEvent().equals(GameEvent.HIT_GROUND))) {
            return;
        }
        try {
            if (e.getEntity().getType().equals(EntityType.PLAYER)) {
                Player player = (Player) e.getEntity();
                if (Utility.staffmode_users.contains(player.getUniqueId())) {
                    e.setCancelled(true);
                }
            }
        }catch (Exception exception) {
            return;
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSculkStep(GenericGameEvent e) {
        try {
            GameEvent gameEvent = e.getEvent();
            if (!(e.getEntity().getType().equals(EntityType.PLAYER))) return;
            Player player = (Player) e.getEntity();
            if (!(Utility.staffmode_users.contains(player.getUniqueId()))) return;
            if (!(gameEvent.equals(GameEvent.SCULK_SENSOR_TENDRILS_CLICKING))) return;
            e.setCancelled(true);
        }catch (Exception exception) {
            return;
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onShriek(GenericGameEvent e) {
        try {
            GameEvent gameEvent = e.getEvent();
            if (!(e.getEntity().getType().equals(EntityType.PLAYER))) return;
            Player player = (Player) e.getEntity();
            if (!(gameEvent.equals(GameEvent.SHRIEK))) return;
            if (Utility.staffmode_users.contains(player.getUniqueId())) e.setCancelled(true);
        }catch (Exception exception) {
            return;
        }
    }
}
