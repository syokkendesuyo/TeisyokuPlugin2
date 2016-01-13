package net.jp.minecraft.plugins;

import com.google.common.base.Joiner;
import net.jp.minecraft.plugins.Utility.Msg;
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
 * Adコマンドを実行時の処理
 */
public class Command_Ad implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

        //引数が0だった場合
        if(args.length == 0){
            help(sender,commandLabel);
            return true;
        }

        //パーミッションの確認コマンドを追加
        if(args[0].equalsIgnoreCase("help")){
            help(sender,commandLabel);
            return true;
        }

        //パーミッションの確認コマンドを追加
        if(args[0].equalsIgnoreCase("perm")||args[0].equalsIgnoreCase("perms")||args[0].equalsIgnoreCase("permission")){
            Msg.checkPermission((Player)sender , Permissions.getTeisyokuUserPermisson());
            return true;
        }

        //コマンドの引数を結合する
        String arg = Joiner.on(' ').join(args);

        //カラーコードを適用
        String argReplace = arg.replaceAll("&","§");

        //ブロードキャストでメッセージを送信
        Bukkit.broadcastMessage(Messages.getAdPrefix(sender.getName().toString()) + argReplace);

        //オンラインプレイヤー全員に音を鳴らす
        for(Player player : Bukkit.getOnlinePlayers()){
            player.playSound(player.getLocation() , Sound.NOTE_PLING , 3.0F,1.5F);
        }
        return true;
    }

    /**
     *Adコマンドのヘルプ
     * @param sender 送信者
     * @param commandLabel コマンドラベル
     */
    public void help(CommandSender sender , String commandLabel){
        Msg.success((Player)sender , "コマンドのヘルプ");
        Msg.commandFormat((Player)sender , commandLabel.toString() + " <メッセージ>", "広告を表示");
    }
}
