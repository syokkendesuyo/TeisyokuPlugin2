package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_Last implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){


        /**
         * コマンドの利用権限を確認
         */

        //パーミッションの確認(コマンド側)
        if(!(sender.hasPermission(Permissions.getLastCommandPermisson()))){
            sender.sendMessage(Messages.getNoPermissionMessage(Permissions.getLastCommandPermisson()));
            return true;
        }


        /**
         * 例外処理
         */

        //引数が0だった場合
        if(args.length == 0){
            Msg.info(sender, "利用方法： /last <プレイヤー名>");
            return true;
        }

        //引数が1より大きかった場合
        else if(args.length > 1){
            Msg.warning(sender, "引数が多すぎです");
            Msg.warning(sender, "利用方法：/last <プレイヤー名>");
            return true;
        }


        /**
         * 引数が1つの場合の処理
         */

        //パーミッションの確認コマンド
        if(args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("permission")){
            Messages.getCheckPermissionMessage(Permissions.getLastCommandPermisson());
            return true;
        }

        else if(args[0].length()<3 || args[0].length() >16){
            Msg.warning(sender, "プレイヤー名は3から16文字で入力してください");
            return true;
        }

        //その他の場合プレイヤー名と判定
        else{
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            UUID uuid = player.getUniqueId();
            try{
                if(TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.get(uuid + ".JoinDate") == null){
                    Msg.warning(sender, ChatColor.YELLOW +  args[0].toString() + ChatColor.RESET  + "のデータはありませんでした");
                    return true;
                }

                String joinDate = TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.getString(uuid + ".JoinDate");
                String quitDate = TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.getString(uuid + ".QuitDate");

                Msg.success(sender, ChatColor.YELLOW +  args[0].toString() + ChatColor.RESET + "の最終ログイン  " + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + joinDate);
                Msg.success(sender, ChatColor.YELLOW +  args[0].toString() + ChatColor.RESET + "の最終ログアウト" + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + quitDate);
                return true;
            }
            catch(Exception e){
                Msg.warning(sender, "不明なエラーが発生しました");
                e.printStackTrace();
                return true;
            }
        }
    }
}
