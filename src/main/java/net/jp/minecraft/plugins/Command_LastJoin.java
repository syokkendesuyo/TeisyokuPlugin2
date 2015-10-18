package net.jp.minecraft.plugins;

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
public class Command_LastJoin implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){


        /**
         * コマンドの利用権限を確認
         */

        //パーミッションの確認(コマンド側)
        if(!(sender.hasPermission(Permissions.getFlyCommandPermisson()))){
            sender.sendMessage(Messages.getNoPermissionMesssage(Permissions.getLastJoinCommandPermisson()));
            return true;
        }


        /**
         * 例外処理
         */

        //引数が0だった場合
        if(args.length == 0){
            sender.sendMessage(Messages.getNormalPrefix() + "利用方法： /lastjoin <プレイヤー名>");
            return true;
        }

        //引数が1より大きかった場合
        else if(args.length > 1){
            sender.sendMessage(Messages.getDenyPrefix() + "引数が多すぎです");
            sender.sendMessage(Messages.getNormalPrefix() + "利用方法：/lastjoin <プレイヤー名>");
            return true;
        }


        /**
         * 引数が1つの場合の処理
         */

        //パーミッションの確認コマンド
        if(args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("permission")){
            Messages.getCheckPermissionMessage(Permissions.getFlyCommandPermisson());
            Messages.getCheckPermissionMessage(Permissions.getFlyPermisson());
            return true;
        }

        else if(args[0].length()<3 || args[0].length() >16){
            sender.sendMessage(Messages.getDenyPrefix() + "プレイヤー名は3から16文字以内で入力してください");
            return true;
        }

        //その他の場合プレイヤー名と判定
        else{
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            UUID uuid = player.getUniqueId();
            try{
                String date = TeisyokuPlugin2.getInstance().lastJoinPlayerConfig.getString(uuid + ".date");

                sender.sendMessage(Messages.getSuccessPrefix() + ChatColor.YELLOW +  args[0].toString() + ChatColor.RESET + "の最終ログイン： " + date);
                return true;
            }
            catch(Exception e){
                sender.sendMessage(Messages.getDenyPrefix() + "データがありませんでした");
                e.printStackTrace();
                return true;
            }
        }
    }
}
