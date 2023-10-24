package net.staffmodev2.Listeners;

import net.staffmodev2.Utility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FreezeListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (Utility.staffmode_users.contains(player.getUniqueId())) {
            Action action = e.getAction();
            ItemStack item = e.getItem();

            if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
                return;
            }
            try {
                if (item.getType() == Material.PACKED_ICE && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(ChatColor.BLUE + "FREEZE")) {
                    if (player.hasLineOfSight(Utility.getNearestEntityInSight(player, 20))) {
                        Player target = (Player) Utility.getNearestEntityInSight(player, 20);
                        if (!(Utility.freeze.contains(target.getUniqueId()))) {
                            Utility.enableFreeze(player, target);
                        } else {
                            Utility.disableFreeze(player, target);
                        }
                    } else {
                        return;
                    }
                }
            } catch (Exception exception) {
                return;
            }
        }
    }
}
