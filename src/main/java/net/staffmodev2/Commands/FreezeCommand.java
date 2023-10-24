package net.staffmodev2.Commands;

import net.staffmodev2.BukkitSerialization;
import net.staffmodev2.Utility;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

             if(command.getName().equalsIgnoreCase("freeze")) {
                 if (player.hasPermission("Staff.freeze")) {
                     if (strings.length == 1) {
                         Player target = Bukkit.getServer().getPlayer(strings[0]);

                         if (Utility.freeze.contains(target.getUniqueId())) {
                             Utility.disableFreeze(player, target);

                         } else {
                             Utility.enableFreeze(player, target);


                        }

                     }
                 }
             }
        }

        return false;
    }
}
