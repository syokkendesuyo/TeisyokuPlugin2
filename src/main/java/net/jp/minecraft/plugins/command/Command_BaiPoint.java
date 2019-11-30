package net.jp.minecraft.plugins.command;

import net.jp.minecraft.plugins.util.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_BaiPoint implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(args.length == 0){
            Msg.commandFormat(sender,commandLabel + " <数値>","？？？");
            return true;
        }
        Msg.defaultChatFormat(sender, "遅い " + ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "" +  ChatColor.BOLD + args[0] + "BP");
        return true;
    }
}
