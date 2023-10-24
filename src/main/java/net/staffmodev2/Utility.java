package net.staffmodev2;

import net.staffmodev2.Config.ConfigManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.material.Redstone;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import static org.bukkit.Bukkit.getServer;

public class Utility {

    private static Staffmode_v2 plugin = null;


    public static ArrayList<UUID> invisible =  new ArrayList<UUID>();
    public static ArrayList<UUID> freeze = new ArrayList<UUID>();
    public static HashMap<UUID, ItemStack[]> armor = new HashMap<UUID, ItemStack[]>();
    public static HashMap<UUID, ItemStack[]> items = new HashMap<UUID, ItemStack[]>();
    public static HashMap<UUID, Location> ogLoc = new HashMap<UUID, Location>();
    public static ArrayList<UUID> staffmode_users = new ArrayList<>();

    public static void initialize(Staffmode_v2 instance) {
        plugin = instance;
    }

    public static void storeAndClearInventory(Player player) {
        UUID uuid = player.getUniqueId();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack[] contents = playerInventory.getContents();
        ItemStack[] armorContents = playerInventory.getArmorContents();
        Location loc = player.getLocation();

        ConfigManager.getPlayers().set("Players." + uuid + ".Inventory.armor", armorContents);
        ConfigManager.getPlayers().set("Players." + uuid + ".Inventory.inv", contents);
        ConfigManager.getPlayers().set("Players." + uuid + ".Location", loc);
        ConfigManager.savePlayers();


        player.getInventory().clear();

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }
    public static void restoreInventory(Player player){
        UUID uuid = player.getUniqueId();
        ItemStack[] contents = (ItemStack[]) ConfigManager.getPlayers().get("Players." + uuid + ".Inventory.inv");
        ItemStack[] armorContents = (ItemStack[]) ConfigManager.getPlayers().get("Players." + uuid + ".Inventory.armor");
        Location loc = (Location) ConfigManager.getPlayers().get("Players." + uuid + ".Location");

        ConfigManager.getPlayers().set("Players." + uuid, null);
        ConfigManager.savePlayers();

        player.teleport(loc);
        if(contents != null){
            player.getInventory().setContents(contents);
        }
        else{
            player.getInventory().clear();
        }

        if(armorContents != null){
            player.getInventory().setArmorContents(armorContents);
        }
        else{
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
        }
    }

    public static void enableStaffMode(Player player) {
        storeAndClearInventory(player);
        UUID uuid = player.getUniqueId();

        List<String> loresList = new ArrayList<>();

        staffmode_users.add(uuid);
        player.sendTitle(ChatColor.AQUA  + "" + ChatColor.BOLD + "STAFFMODE", ChatColor.GREEN + "Enabled", 10, 30, 10);

        PlayerInventory inv = player.getInventory();
        ItemStack vanish = new ItemStack(Material.LIME_DYE);
        ItemStack freeze = new ItemStack(Material.PACKED_ICE);
        ItemStack players = new ItemStack(Material.RECOVERY_COMPASS);
        ItemStack raksaKyrpa = new ItemStack(Material.GOLDEN_HELMET);
        ArmorMeta kyrpaMeta = (ArmorMeta) raksaKyrpa.getItemMeta();
        kyrpaMeta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.SNOUT));
        kyrpaMeta.setDisplayName("§aRaksa Kypärä!");
        loresList.add("§cTurvallisuus ensin!");
        kyrpaMeta.setLore(loresList);
        raksaKyrpa.setItemMeta(kyrpaMeta);
        ItemMeta playersMeta = players.getItemMeta();
        ItemMeta freezeMeta = freeze.getItemMeta();
        ItemMeta vanishMeta = vanish.getItemMeta();
        playersMeta.setDisplayName(ChatColor.GOLD + "Pelaajat");
        players.setItemMeta(playersMeta);
        freezeMeta.setDisplayName(ChatColor.BLUE + "FREEZE");
        freeze.setItemMeta(freezeMeta);
        if (invisible.contains(player.getUniqueId())) {
            vanish.setType(Material.LIME_DYE);
            vanishMeta.setDisplayName(ChatColor.GREEN + "Vanish on päällä");
            vanish.setItemMeta(vanishMeta);
        } else if (!(invisible.contains(player.getUniqueId()))) {
            vanish.setType(Material.GRAY_DYE);
            vanishMeta.setDisplayName(ChatColor.GRAY + "Vanish ei ole päällä");
            vanish.setItemMeta(vanishMeta);
        }
        inv.setItem(0, vanish);
        inv.setItem(1, freeze);
        inv.setItem(8, players);
        inv.setHelmet(raksaKyrpa);


        player.setInvulnerable(true);
        player.setAllowFlight(true);

    }

    public static void disableStaffMode(Player player){
            UUID uuid = player.getUniqueId();

            player.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD + "STAFFMODE", ChatColor.RED + "Disabled", 10, 30, 10);
            restoreInventory(player);
            player.setInvulnerable(false);
            player.setAllowFlight(false);
            player.setFlying(false);
            disableVanish(player);
            staffmode_users.remove(uuid);
    }

    public static void enableFreeze(Player player, Player target) {
        freeze.add(target.getUniqueId());
        target.sendTitle(ChatColor.RED + "Sinut on jäädytetty!", "", 10, 30, 10);
        player.sendMessage(ChatColor.AQUA + "Jäädytit pelaajan " + target.getName());
    }

    public static void disableFreeze(Player player, Player target) {
        freeze.remove(target.getUniqueId());
        target.sendTitle(ChatColor.RED + "Sinut on sulatettu!", "", 10, 30, 10);
        player.sendMessage(ChatColor.AQUA + "Sulatit pelaajan " + target.getName());
    }

    public static void enableVanish(Player player) {
        for (Player other : getServer().getOnlinePlayers()) {
            if(other.hasPermission("Staff.showvanished")) {
                continue;
            }
            other.hidePlayer(player);
        }
        player.sendMessage(ChatColor.RED + "Olet nyt näkymätön muille pelaajille!");
        invisible.add(player.getUniqueId());
    }

    public static void disableVanish(Player player) {
            for (Player other : getServer().getOnlinePlayers()) {
                other.showPlayer(player);
                player.sendMessage(ChatColor.RED + "Et ole enään näkymätön!");
                invisible.remove(player.getUniqueId());
            }
    }

    public static Entity getNearestEntityInSight(Player player, int range) {
        ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight( (Set<Material>) null, range);
        ArrayList<Location> sight = new ArrayList<Location>();
        for (int i = 0;i<sightBlock.size();i++)
            sight.add(sightBlock.get(i).getLocation());
        for (int i = 0;i<sight.size();i++) {
            for (int k = 0;k<entities.size();k++) {
                if (Math.abs(entities.get(k).getLocation().getX()-sight.get(i).getX())<1.3) {
                    if (Math.abs(entities.get(k).getLocation().getY()-sight.get(i).getY())<1.5) {
                        if (Math.abs(entities.get(k).getLocation().getZ()-sight.get(i).getZ())<1.3) {
                            return entities.get(k);
                        }
                    }
                }
            }
        }
        return null; //Return null/nothing if no entity was found
    }
}
