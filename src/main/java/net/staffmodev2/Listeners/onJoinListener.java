package net.staffmodev2.Listeners;

import net.staffmodev2.Config.ConfigManager;
import net.staffmodev2.Utility;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class onJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        ConfigurationSection uuidSection = ConfigManager.getPlayers().getConfigurationSection("Players");

        if (uuidSection.contains(String.valueOf(uuid))) {
            Utility.disableStaffMode(player);


        }


    }
}
