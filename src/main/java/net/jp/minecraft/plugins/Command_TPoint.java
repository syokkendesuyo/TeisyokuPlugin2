package net.jp.minecraft.plugins;

import net.jp.minecraft.plugins.Listener.Listener_TPoint;
import net.jp.minecraft.plugins.TPoint.TPointIndexGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_TPoint implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

        //パーミッションの確認

        if(! sender.hasPermission("teisyoku.user")){
            sender.sendMessage(Messages.getNoPermissionMessage("teisyoku.user"));
            return true;
        }

        //引数0の場合
        if(args.length == 0){
            if(sender instanceof Player){
                TPointIndexGUI.index((Player) sender);
                return true;
            }
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
            if(args.length == 1){
                if(!(sender instanceof Player)){
                    sender.sendMessage(Messages.getNormalPrefix() + "使い方：/tpoint status <プレイヤー>");
                    sender.sendMessage(Messages.getNormalPrefix() + "※ゲーム側からのみプレイヤーを省略できます");
                }
                else{
                    Listener_TPoint.status((Player) sender);
                }
            }
            else if(args.length == 2){
                try{
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    UUID uuid = player.getUniqueId();
                    String name = args[1];

                    Listener_TPoint.status_uuid(uuid,sender,name,(Player)player);
                    return true;
                }
                catch (Exception e){
                    sender.sendMessage(Messages.getDenyPrefix() + "不明なエラーが発生しました。Location: Command_TPoint --> status");
                    e.printStackTrace();
                    return true;}
                }
            return true;
        }

        //パーミッションの確認(コマンド側)
        if(!(sender.hasPermission("teisyoku.admin"))){
            sender.sendMessage(Messages.getNoPermissionMessage("teisyoku.admin"));
            return true;
        }

        //追加
        if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("+")){
            // /tpoint add Player int
            //          0    1     2

            if(!(args.length == 3)){
                sender.sendMessage(Messages.getDenyPrefix() + "引数が不正です");
                sender.sendMessage(Messages.getDenyPrefix() + "使用方法：/tpoint add <ﾌﾟﾚｲﾔｰ> <数値>");
                return true;
            }

            if(!(isNumber(args[2]))){
                //数字でなかったら拒否
                sender.sendMessage(Messages.getDenyPrefix() + args[2] +"は数値ではありません");
                return true;
            }

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

            if(offlinePlayer.isOnline()){
                Player onlinePlayer = (Player) offlinePlayer;
                int point = Integer.parseInt(args[2]);
                Listener_TPoint.addPoint(point, onlinePlayer);
                if(! (onlinePlayer == sender)){
                    sender.sendMessage(Messages.getSuccessPrefix() + onlinePlayer.getName() + " さんに " + point + " TPoint与えました");
                }
                return true;
            } else {
                //プレイヤーが居ないのでエラー
                sender.sendMessage(Messages.getDenyPrefix() + "プレイヤー " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " はオンラインではありません");
                return true;
            }
        }

        //削減
        if(args[0].equalsIgnoreCase("subtract") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("-")){
            if(!(args.length == 3)){
                sender.sendMessage(Messages.getDenyPrefix() + "引数が不正です");
                sender.sendMessage(Messages.getDenyPrefix() + "使用方法：/tpoint remove <ﾌﾟﾚｲﾔｰ> <数値>");
                return true;
            }

            if(!(isNumber(args[2]))){
                //数字でなかったら拒否
                sender.sendMessage(Messages.getDenyPrefix() + args[2] +"は数値ではありません");
                return true;
            }

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

            if(offlinePlayer.isOnline()){
                Player onlinePlayer = (Player) offlinePlayer;
                int point = Integer.parseInt(args[2]);
                Listener_TPoint.subtractPoint(point, onlinePlayer);
                if(! (onlinePlayer == sender)){
                    sender.sendMessage(Messages.getSuccessPrefix() + onlinePlayer.getName() + " さんから " + point + " TPoint差し引きました");
                }
                return true;
            } else {
                //プレイヤーが居ないのでエラー
                sender.sendMessage(Messages.getDenyPrefix() + "プレイヤー " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " はオンラインではありません");
                return true;
            }
        }

        //セット
        if(args[0].equalsIgnoreCase("set")){
            if(!(args.length == 3)){
                sender.sendMessage(Messages.getDenyPrefix() + "引数が不正です");
                sender.sendMessage(Messages.getDenyPrefix() + "使用方法：/tpoint set <ﾌﾟﾚｲﾔｰ> <数値>");
                return true;
            }

            if(!(isNumber(args[2]))){
                //数字でなかったら拒否
                sender.sendMessage(Messages.getDenyPrefix() + args[2] +"は数値ではありません");
                return true;
            }

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

            if(offlinePlayer.isOnline()){
                Player onlinePlayer = (Player) offlinePlayer;
                int point = Integer.parseInt(args[2]);
                Listener_TPoint.setPoint(point, onlinePlayer);
                if(! (onlinePlayer == sender)){
                    sender.sendMessage(Messages.getSuccessPrefix() + onlinePlayer.getName() + " さんによって " + point + " TPointにセットされました");
                }
                return true;
            } else {
                //プレイヤーが居ないのでエラー
                sender.sendMessage(Messages.getDenyPrefix() + "プレイヤー " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " はオンラインではありません");
                return true;
            }
        }

        //ギフトコード
        if(args[0].equalsIgnoreCase("code") || args[0].equalsIgnoreCase("giftcode")){
            sender.sendMessage(Messages.getDenyPrefix() + "現在利用できません");
            return true;
        }

        /*
        if(args[0].equalsIgnoreCase("convert") || args[0].equalsIgnoreCase("exchange")){

            if(args.length > 2){
                HelpMessage(sender , cmd);
            }

            if(!(sender instanceof Player)){
                sender.sendMessage(Messages.getDenyPrefix() + "コンソールでは引数「"+ args[0] +"」は利用できません");
                return true;
            }
            else {
                Player player = (Player) sender;
                if(player.getItemInHand().getType() == Material.AIR||player.getItemInHand().getItemMeta().getLore() == null || !(player.getItemInHand().getItemMeta().getLore().toString().equals("[何かと交換できるらしい･･･]"))){
                    sender.sendMessage(Messages.getDenyPrefix() + "このアイテムは変換できません");
                    return true;
                }
                else{
                    ItemStack item = new ItemStack(339, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("Test");
                    List<String> lore = new ArrayList<String>();
                    lore.add()
                    meta.setLore(("何かと交換できるらしい･･･"));

                    player.getInventory().removeItem(item);
                    player.updateInventory();
                    sender.sendMessage(Messages.getSuccessPrefix() + "交換しました");
                    return true;
                }
                //デバッグ用
                //String lore = player.getItemInHand().getItemMeta().getLore().toString();
                //player.sendMessage(lore);
            }
        }
        */

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
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " <set/add/remove> <ﾌﾟﾚｲﾔｰ名> <数値>","ﾌﾟﾚｲﾔｰのﾎﾟｲﾝﾄ数を変更"));
    }

    public boolean isNumber(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException event) {
            return false;
        }
    }
}
