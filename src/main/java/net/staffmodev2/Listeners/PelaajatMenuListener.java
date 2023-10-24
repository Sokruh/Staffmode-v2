package net.staffmodev2.Listeners;

import net.staffmodev2.Utility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class PelaajatMenuListener implements Listener {

    private ArrayList<Player> players = new ArrayList<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack hand = e.getItem();

        if (!(Utility.staffmode_users.contains(player.getUniqueId()))) {
            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_AIR | e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            try {
                if (hand.getType() == Material.RECOVERY_COMPASS && hand.hasItemMeta() && hand.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Pelaajat")) {
                    Inventory inv = Bukkit.createInventory(null, 4 * 9, "§6§lPelaajat");

                    for (Player ps : Bukkit.getOnlinePlayers()) {
                        players.add(ps);
                        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                        SkullMeta meta = (SkullMeta) item.getItemMeta();
                        meta.setDisplayName("§r" + ps.getName());
                        meta.setOwner(ps.getName());
                        item.setItemMeta(meta);
                        inv.addItem(item);
                    }
                    e.getPlayer().openInventory(inv);
                    players.removeAll(players);
                }
            }catch (NullPointerException exception) {
                return;
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (Utility.staffmode_users.contains(p.getUniqueId())) {

            if (e.getView().getTitle().equals("§6§lPelaajat")) {
                e.setCancelled(true);
                if (e.getSlot() == e.getRawSlot()) {
                    if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
                        String pname = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                        Player pp = Bukkit.getPlayerExact(pname);

                        if (pp != null) {
                            p.teleport(pp);
                            p.sendMessage(ChatColor.RED + "Sinut teleportattiin pelaajan " + pp.getName());
                        }
                    }
                }
            }
        }
    }
}
