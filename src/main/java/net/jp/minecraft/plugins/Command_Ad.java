package net.jp.minecraft.plugins;

import com.google.common.base.Joiner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 *
 * flyコマンドを実行時の処理
 */
public class Command_Ad implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){


        //引数が0だった場合
        if(args.length == 0){
            sender.sendMessage(Messages.getDenyPrefix() + "利用方法： /"+ commandLabel.toString() +" <メッセージ>");
            return true;
        }

        String arg = Joiner.on(' ').join(args);
        String argReplace = arg.replaceAll("&","§");
        Bukkit.broadcastMessage(Messages.getAdPrefix(sender.getName().toString()) + argReplace);

        for(Player player : Bukkit.getOnlinePlayers()){
            player.playSound(player.getLocation() , Sound.NOTE_PLING , 3.0F,1.5F);
        }

        return true;
    }
}
