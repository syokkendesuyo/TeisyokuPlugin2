package net.jp.minecraft.plugins.Utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public final class Msg {

    //基本のPrefix
    private static  String prefix (ChatColor color){
        return ChatColor.DARK_GRAY + "[" + color + ChatColor.BOLD + "Teisyoku" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + ">>" + " ";
    }

    /**
     * Successのメッセージをプレイヤーに送信
     *
     * @param p         プレイヤー
     * @param msg       メッセージ
     * @param broadcast ブロードキャスト
     */
    public static void success(final CommandSender p, final String msg, boolean broadcast) {
        if (broadcast) {
            Bukkit.broadcastMessage(prefix(ChatColor.GREEN) + ChatColor.RESET + msg);
        } else {
            p.sendMessage(prefix(ChatColor.GREEN) + ChatColor.RESET + msg);
        }
    }

    /**
     * Successのメッセージをプレイヤーに送信
     *
     * @param p   プレイヤー
     * @param msg メッセージ
     */
    public static void success(final CommandSender p, final String msg) {
        success(p, msg, false);
    }

    /**
     * Warningのメッセージをプレイヤーに送信
     *
     * @param p         プレイヤー
     * @param msg       メッセージ
     * @param broadcast ブロードキャスト
     */
    public static void warning(final CommandSender p, final String msg, boolean broadcast) {
        if (broadcast) {
            Bukkit.broadcastMessage(prefix(ChatColor.RED) + ChatColor.RESET + msg);
        } else {
            p.sendMessage(prefix(ChatColor.RED) + ChatColor.RESET + msg);
        }
    }

    /**
     * Warningのメッセージをプレイヤーに送信
     *
     * @param p   プレイヤー
     * @param msg メッセージ
     */
    public static void warning(final CommandSender p, final String msg) {
        warning(p, msg, false);
    }

    /**
     * Infoのメッセージをプレイヤーに送信
     *
     * @param p         プレイヤー
     * @param msg       メッセージ
     * @param broadcast プロードキャスト
     */
    public static void info(final CommandSender p, final String msg, boolean broadcast) {
        if (broadcast) {
            Bukkit.broadcastMessage(prefix(ChatColor.YELLOW) + ChatColor.RESET + msg);
        } else {
            p.sendMessage(prefix(ChatColor.YELLOW) + ChatColor.RESET + msg);
        }
    }

    /**
     * Infoのメッセージをプレイヤーに送信
     *
     * @param p   プレイヤー
     * @param msg メッセージ
     */
    public static void info(final CommandSender p, final String msg) {
        info(p, msg, false);
    }

    /**
     * Adminにのみブロードキャストメッセージを送信
     *
     * @param msg   メッセージ
     * @param sound サウンド
     */
    public static void adminBroadcast(final String msg, boolean sound) {
        for (Player admin : Bukkit.getOnlinePlayers()) {
            if (admin.hasPermission("teisyoku.admin")) {
                admin.sendMessage(prefix(ChatColor.LIGHT_PURPLE) + ChatColor.RESET + msg);
                if (sound) {
                    Sounds.sound_note(admin);
                }
            }
        }
    }

    /**
     * Adminにのみブロードキャストメッセージを送信
     *
     * @param msg メッセージ
     */
    public static void opBroadcast(final String msg) {
        adminBroadcast(msg, true);
    }

    /**
     * パーミッションエラーを返す時のスタイル
     *
     * @param p    プレイヤー
     * @param perm パーミッション
     */
    public static void noPermissionMessage(final CommandSender p, final String perm) {
        Msg.warning(p, "パーミッションがありません " + ChatColor.RED + perm);
    }

    /**
     * パーミッションを表示する時のスタイル
     *
     * @param p    プレイヤー
     * @param perm パーミッション
     */
    public static void checkPermission(final CommandSender p, final String perm) {
        ChatColor color = ChatColor.RED;
        String hasPerm = "使用不可";
        if (p.hasPermission(perm)) {
            color = ChatColor.GREEN;
            hasPerm = "使用可能";
        }
        Msg.info(p, "パーミッション" + ChatColor.DARK_GRAY + ": " + color + hasPerm + " " + perm);
    }

    /**
     * コマンドヘルプのフォーマット
     *
     * @param p           プレイヤー
     * @param command     コマンド
     * @param discription 説明
     */
    public static void commandFormat(final CommandSender p, final String command, final String discription) {
        String string = ChatColor.GRAY + "   > " + ChatColor.YELLOW + "/" + command + ChatColor.DARK_GRAY + "  : " + ChatColor.RESET + discription;
        p.sendMessage(string);
    }

    /**
     * コマンドヘルプのフォーマット
     *
     * @param p           プレイヤー
     * @param discription 説明
     */
    public static void defaultChatFormat(final CommandSender p, final String discription) {
        String string = p.getName() + ChatColor.GREEN + ": " + ChatColor.RESET + discription;
        Bukkit.broadcastMessage(string);
    }
}
