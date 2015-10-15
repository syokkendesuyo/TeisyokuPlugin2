package net.jp.minecraft.plugins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 *
 * flyコマンドを実行時の処理
 */
public class FlyCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(! (sender  instanceof Player)){
            sender.sendMessage(Messages.getDenyPrefix() + "コンソールからコマンドを送信することはできません");
        }

        Player player = (Player)sender;

        if(!(player.hasPermission(Permissions.getFlyCommandPermisson()))){
            player.sendMessage(Messages.getNoPermissionMesssage(Permissions.getFlyCommandPermisson()));
            return true;
        }

        if(args.length == 0){
            player.sendMessage(Messages.getNormalPrefix() + "利用方法： /fly <true/false>");
            return true;
        }
        else if(args.length > 1){
            player.sendMessage(Messages.getDenyPrefix() + "引数が多すぎです");
            player.sendMessage(Messages.getNormalPrefix() + "利用方法： /fly <true/false>");
            return true;
        }


        if(!(player.hasPermission(Permissions.getFlyPermisson()))){
            player.sendMessage(Messages.getNoPermissionMesssage(Permissions.getFlyPermisson()));
            return true;
        }


        if(args[0].equalsIgnoreCase("true")){
            player.sendMessage(Messages.getSuccessPrefix() + "Flyモードを有効にしました");
            player.setAllowFlight(true);
            return true;
        }
        else if(args[0].equalsIgnoreCase("false")){
            player.sendMessage(Messages.getSuccessPrefix() + "Flyモードを無効にしました");
            player.setAllowFlight(false);
            return true;
        }
        else if(args[0].equalsIgnoreCase("permission") || args[0].equalsIgnoreCase("perm")){
            player.sendMessage(Messages.getNormalPrefix() + "パーミッション: " + Permissions.getFlyCommandPermisson());
            return true;
        }
        else{
            player.sendMessage(Messages.getDenyPrefix() + "引数「" + args[0] + "」は存在しません");
            player.sendMessage(Messages.getNormalPrefix() + "利用方法： /fly <true/false>");
            return true;
        }
    }
}
