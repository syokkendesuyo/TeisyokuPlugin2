package net.jp.minecraft.plugins.Commands;

import com.google.common.base.Joiner;
import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
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
 * Adコマンドを実行時の処理
 */
public class Command_Ad implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

        //コマンドが有効化されているかどうか検出
        if(TeisyokuPlugin2.getInstance().TeisyokuConfig.get("commands.ad") == false){
            Msg.warning(sender,"「ad」コマンドは有効化されていません");
            return true;
        }

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
            Msg.checkPermission(sender , Permissions.getTeisyokuUserPermisson());
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
        Msg.success(sender , "コマンドのヘルプ");
        Msg.commandFormat(sender , commandLabel.toString() + " <メッセージ>", "広告を表示");
    }
}
