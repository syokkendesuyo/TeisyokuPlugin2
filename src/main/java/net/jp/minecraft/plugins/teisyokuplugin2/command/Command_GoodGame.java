package net.jp.minecraft.plugins.teisyokuplugin2.command;

import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_GoodGame implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Msg.defaultChatFormat(sender, ChatColor.LIGHT_PURPLE + "/gg" + ChatColor.RESET + ChatColor.ITALIC + " GoodGame!");
        return true;
    }
}
