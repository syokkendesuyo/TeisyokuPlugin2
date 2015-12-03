package net.jp.minecraft.plugins;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_TPoint implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

        //パーミッションの確認
        if(! sender.hasPermission("teisyoku.user")){
            sender.sendMessage(Messages.getNoPermissionMesssage("teisyoku.user"));
            return true;
        }

        //引数0の場合
        if(args.length == 0){
            HelpMessage(sender , cmd);
            if(sender.hasPermission("teisyoku.admin")){
                AdminHelpMessage(sender , cmd);
            }
            return true;
        }

        //ヘルプ
        if(args[0].equalsIgnoreCase("help")){
            if(! (args.length == 1)){
                sender.sendMessage(Messages.getDenyPrefix() + "引数が多すぎます");
                return true;
            }
            HelpMessage(sender , cmd);
            if(sender.hasPermission("teisyoku.admin")){
                AdminHelpMessage(sender , cmd);
            }
            return true;
        }

        //ステイタス
        if(args[0].equalsIgnoreCase("status")){

            return true;
        }

        //追加
        if(args[0].equalsIgnoreCase("add")){

            return true;
        }

        //削減
        if(args[0].equalsIgnoreCase("subtract")){

            return true;
        }

        //設定
        if(args[0].equalsIgnoreCase("set")){

            return true;
        }

        //ギフトコード
        if(args[0].equalsIgnoreCase("code") || args[0].equalsIgnoreCase("giftcode")){

            return true;
        }

        HelpMessage(sender , cmd);
        if(sender.hasPermission("teisyoku.admin")){
            AdminHelpMessage(sender , cmd);
        }
        return true;
    }

    public void HelpMessage(CommandSender sender , Command cmd){
        sender.sendMessage(Messages.getSuccessPrefix() + cmd.getName() + "コマンドのヘルプ(現在開発中)");
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " help", "TPointｺﾏﾝﾄﾞのﾍﾙﾌﾟ"));
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " status","所持ﾎﾟｲﾝﾄを参照"));
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " code <文字列>","ｷﾞﾌﾄｺｰﾄﾞを入力"));
    }

    public void AdminHelpMessage(CommandSender sender , Command cmd){
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " <set/add/subtract> <ﾌﾟﾚｲﾔｰ名> <数値>","ﾌﾟﾚｲﾔｰのﾎﾟｲﾝﾄ数を変更"));
    }
}
