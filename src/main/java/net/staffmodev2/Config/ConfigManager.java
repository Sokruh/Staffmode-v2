package net.staffmodev2.Config;

import net.staffmodev2.Staffmode_v2;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static Staffmode_v2 plugin = null;

    public static FileConfiguration playerscfg;
    public static File playersfile;

    public static void initialize(Staffmode_v2 instance) {
        plugin = instance;
        setup();
    }

    public static void setup() {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        playersfile = new File(plugin.getDataFolder(), "players.yml");
        if (!playersfile.exists()) {
            try {
                playersfile.createNewFile();
                Bukkit.getLogger().info("§aplayers.yml luotu!");
            }catch (IOException e) {
                Bukkit.getServer().getLogger().warning("§cplayers.yml ei voitu luoda!");
            }
        }

        playerscfg = YamlConfiguration.loadConfiguration(playersfile);
    }

    public static FileConfiguration getPlayers() {
        return playerscfg;
    }

    public static void savePlayers() {
        try {
            playerscfg.save(playersfile);
        }catch (IOException e) {
            Bukkit.getServer().getLogger().warning("§cEi voitu tallentaa players.yml");
        }
    }

    public static void reloadPlayers() {
        playerscfg = YamlConfiguration.loadConfiguration(playersfile);
        Bukkit.getServer().getLogger().info("§aplayers.yml reloaded");
    }
}
