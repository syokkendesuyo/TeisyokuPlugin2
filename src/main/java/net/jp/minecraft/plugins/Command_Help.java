package net.jp.minecraft.plugins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_Help implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

        if(!(sender.hasPermission(Permissions.getHelpCommandPermisson()))){
            sender.sendMessage(Messages.getNoPermissionMesssage(Permissions.getHelpCommandPermisson()));
            return true;
        }

        Messages.HelpMessage(sender);
        return true;
    }
}
