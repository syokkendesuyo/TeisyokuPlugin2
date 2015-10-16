package net.jp.minecraft.plugins;

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
public class Command_fly implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

        /**
         * コンソールからのコマンドは拒否
         */

        if(! (sender  instanceof Player)){
            sender.sendMessage(Messages.getDenyPrefix() + "コンソールからコマンドを送信することはできません");
        }

        //コンソール以外からの指令なのでプレイヤーと断定
        Player player = (Player)sender;


        /**
         * コマンドの利用権限を確認
         */

        //パーミッションの確認(コマンド側)
        if(!(player.hasPermission(Permissions.getFlyCommandPermisson()))){
            player.sendMessage(Messages.getNoPermissionMesssage(Permissions.getFlyCommandPermisson()));
            return true;
        }


        /**
         * 例外処理
         */

        //引数が0だった場合
        if(args.length == 0){
            player.sendMessage(Messages.getNormalPrefix() + "利用方法： /fly <true/false>");
            return true;
        }

        //引数が1より大きかった場合
        else if(args.length > 1){
            player.sendMessage(Messages.getDenyPrefix() + "引数が多すぎです");
            player.sendMessage(Messages.getNormalPrefix() + "利用方法： /fly <true/false>");
            return true;
        }


        /**
         * 引数が1つの場合の処理
         */

        //引数1がtrueまたはenableだった場合flyモードを開始
        if(args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("enable")){
            Listener_fly.enable_fly(player);
            return true;
        }

        //引数1がfalseまたはdisableだった場合flyモードを終了
        else if(args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("disable")){
            Listener_fly.disable_fly(player);
            return true;
        }

        //パーミッションの確認コマンド
        else if(args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("permission")){
            Messages.getPermission(Permissions.getFlyCommandPermisson());
            Messages.getPermission(Permissions.getFlyPermisson());
            return true;
        }

        //その他の場合
        else{
            player.sendMessage(Messages.getDenyPrefix() + "引数「" + args[0] + "」は存在しません");
            player.sendMessage(Messages.getNormalPrefix() + "利用方法： /fly <true/false>");
            return true;
        }
    }
}
