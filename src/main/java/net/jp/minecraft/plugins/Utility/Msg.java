package net.jp.minecraft.plugins.Utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public final class Msg{

    //基本のPrefix
    public final static String prefix = "[Teisyoku] " + ChatColor.DARK_GRAY + ">" + ChatColor.GRAY + ">" + " ";
    public final static String broadcast = "[Teisyoku|Broadcast] " + ChatColor.DARK_GRAY + ">" + ChatColor.GRAY + ">" + " ";

    /**
     *Successのメッセージをプレイヤーに送信
     * @param p プレイヤー
     * @param msg メッセージ
     * @param broadcast
     */
    public static void success(final CommandSender p , final String msg , boolean broadcast){
        if(broadcast == true){
            Bukkit.broadcastMessage(ChatColor.GREEN + prefix + ChatColor.RESET + msg);
        }else{
            p.sendMessage(ChatColor.GREEN + prefix + ChatColor.RESET + msg);
        }
    }

    /**
     *Successのメッセージをプレイヤーに送信
     * @param p プレイヤー
     * @param msg メッセージ
     */
    public static void success(final CommandSender p , final String msg){
        success(p,msg,false);
    }

    /**
     *Warningのメッセージをプレイヤーに送信
     * @param p プレイヤー
     * @param msg メッセージ
     * @param broadcast
     */
    public static void warning(final CommandSender p , final String msg , boolean broadcast){
        if(broadcast == true){
            Bukkit.broadcastMessage(ChatColor.RED + prefix + ChatColor.RESET + msg);
        }else{
            p.sendMessage(ChatColor.RED + prefix + ChatColor.RESET + msg);
        }
    }

    /**
     *Warningのメッセージをプレイヤーに送信
     * @param p プレイヤー
     * @param msg メッセージ
     */
    public static void warning(final CommandSender p , final String msg){
        warning(p,msg,false);
    }

    /**
     *Infoのメッセージをプレイヤーに送信
     * @param p プレイヤー
     * @param msg メッセージ
     * @param broadcast
     */
    public static void info(final CommandSender p , final String msg , boolean broadcast){
        if(broadcast == true){
            Bukkit.broadcastMessage(ChatColor.YELLOW + prefix + ChatColor.RESET + msg);
        }else{
            p.sendMessage(ChatColor.YELLOW + prefix + ChatColor.RESET + msg);
        }
    }

    /**
     *Infoのメッセージをプレイヤーに送信
     * @param p プレイヤー
     * @param msg メッセージ
     */
    public static void info(final CommandSender p , final String msg){
        info(p,msg,false);
    }

    /**
     *Adminにのみブロードキャストメッセージを送信
     * @param msg メッセージ
     * @param sound
     */
    public static void adminBroadcast(final String msg , boolean sound){
        for(Player admin : Bukkit.getOnlinePlayers()){
            if(admin.hasPermission("teisyoku.admin")){
                admin.sendMessage(ChatColor.LIGHT_PURPLE + broadcast + ChatColor.RESET + msg);
                if(sound == true){
                    admin.playSound(admin.getLocation() , Sound.NOTE_PLING , 3.0F,1.5F);
                }
            }
        }
    }

    /**
     *Adminにのみブロードキャストメッセージを送信
     * @param msg メッセージ
     */
    public static void opBroadcast(final String msg) {
        adminBroadcast(msg , true);
    }

    /**
     *パーミッションエラーを返す時のスタイル
     * @param p プレイヤー
     * @param perm パーミッション
     */
    public static void noPermissionMessage(final CommandSender p , final String perm){
        Msg.warning(p , "パーミッションがありません" + getPermissionNode(perm));
    }

    /**
     *パーミッションを表示する時のスタイル
     * @param p プレイヤー
     * @param perm パーミッション
     */
    public static void checkPermission(final CommandSender p , final String perm){
        Msg.info(p , "パーミッション：" + getPermissionNode(perm));
    }

    /**
     *パーミッションノードを灰色で表示
     * @param permission パーミッション
     * @return パーミッション専用スタイル
     */
    public static String getPermissionNode(final String permission){
        String string = ChatColor.GRAY + permission + ChatColor.RESET;
        return string;
    }

    /**
     * コマンドヘルプのフォーマット
     * @param command コマンドラベル
     * @param discription コマンドの説明
     */
    public static void commandFormat(final CommandSender p , final String command , final String discription){
        String string = ChatColor.GRAY + "   > "+ ChatColor.YELLOW + "/" + command + ChatColor.DARK_GRAY  + "  : "+ ChatColor.RESET + discription;
        p.sendMessage(string);
    }
}
