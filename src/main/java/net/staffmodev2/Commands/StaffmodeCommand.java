package net.staffmodev2.Commands;

import net.staffmodev2.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class StaffmodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            UUID uuid = player.getUniqueId();
            if (command.getName().equalsIgnoreCase("sm") || command.getName().equalsIgnoreCase("staffmode")) {
                if (!(player.hasPermission("Staff.staffmode"))) return false;
                if (!(Utility.staffmode_users.contains(uuid))) {
                    Utility.enableStaffMode(player);
                } else {
                    Utility.disableStaffMode(player);
                }
            }

        }

        return false;
    }
}
