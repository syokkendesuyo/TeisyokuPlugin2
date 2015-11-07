package net.jp.minecraft.plugins;

import com.google.common.base.Joiner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 *
 * flyコマンドを実行時の処理
 */
public class Command_Call implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){


        //引数が0だった場合
        if(args.length == 0){
            sender.sendMessage(Messages.getDenyPrefix() + "利用方法： /"+ commandLabel.toString() +" <プレイヤー> <メッセージ>");
            return true;
        }

        //引数が1だった場合
        if(args.length == 1){
            sender.sendMessage(Messages.getDenyPrefix() + "メッセージがありません！");
            sender.sendMessage(Messages.getDenyPrefix() + "利用方法： /"+ commandLabel.toString() +" <プレイヤー> <メッセージ>");
            return true;
        }

        OfflinePlayer player =  Bukkit.getOfflinePlayer(args[0]);

        if(player.isOnline()){

            String arg = Joiner.on(' ').join(args);
            String noneName = arg.replaceAll(args[0].toString(),"");
            String argReplace = noneName.replaceAll("&","§");

            Player player2 = (Player) player;
            sender.sendMessage(Messages.getCallPrefix() + ChatColor.YELLOW + player2.getName().toString() + ChatColor.GRAY + " さんにメッセージを送信しました" + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + argReplace);
            player2.sendMessage(Messages.getCallPrefix() + ChatColor.YELLOW + sender.getName().toString() + ChatColor.GRAY + " さんからメッセージ" + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + argReplace);

            player2.playSound(player2.getLocation(), Sound.NOTE_PLING, 3.0F, 1.8F);

            if(sender instanceof Player){
                Player player1 = (Player) sender;
                player1.playSound(player1.getLocation(), Sound.SHOOT_ARROW, 3.0F, 1.8F);
            }
            return true;
        }
        else{
            sender.sendMessage(Messages.getDenyPrefix() + ChatColor.YELLOW + args[0] + ChatColor.RESET + " さんは現在オフラインです");
            return true;
        }
    }
}
