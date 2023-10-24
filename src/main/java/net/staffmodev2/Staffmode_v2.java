package net.staffmodev2;

import net.staffmodev2.Commands.FreezeCommand;
import net.staffmodev2.Commands.StaffmodeCommand;
import net.staffmodev2.Config.ConfigManager;
import net.staffmodev2.Listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Staffmode_v2 extends JavaPlugin {
    
    @Override
    public void onEnable() {
        ConfigManager.initialize(this);

        Utility.initialize(this);

        getServer().getPluginManager().registerEvents(new VanishListener(), this);
        getServer().getPluginManager().registerEvents(new FreezeMovement(), this);
        getServer().getPluginManager().registerEvents(new InterruptListeners(), this);
        getServer().getPluginManager().registerEvents(new PelaajatMenuListener(), this);
        getServer().getPluginManager().registerEvents(new FreezeListener(), this);
        getServer().getPluginManager().registerEvents(new onJoinListener(), this);

        getCommand("staffmode").setExecutor(new StaffmodeCommand());
        getCommand("sm").setExecutor(new StaffmodeCommand());
        getCommand("freeze").setExecutor(new FreezeCommand());

        loadConfig();

    }


    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
