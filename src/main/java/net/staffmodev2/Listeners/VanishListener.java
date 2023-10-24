package net.staffmodev2.Listeners;

import net.staffmodev2.Utility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class VanishListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (Utility.staffmode_users.contains(player.getUniqueId())) {
            Action action = e.getAction();
            ItemStack item = e.getItem();

            if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
                return;

            try {

                if (item.getType() == Material.GRAY_DYE && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Vanish ei ole päällä")) {
                    item.setType(Material.LIME_DYE);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.GREEN + "Vanish on päällä");
                    item.setItemMeta(itemMeta);
                    Utility.enableVanish(player);
                }
                else if (item.getType() == Material.LIME_DYE && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Vanish on päällä")) {
                    item.setType(Material.GRAY_DYE);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.GRAY + "Vanish ei ole päällä");
                    item.setItemMeta(itemMeta);
                    Utility.disableVanish(player);
                }
            }catch (NullPointerException exception) {
                return;
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        for (UUID uuidOfP : Utility.invisible) {
            Player p = Bukkit.getPlayer(uuidOfP);

            player.hidePlayer(p);

        }
    }
}
