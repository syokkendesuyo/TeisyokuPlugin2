package net.jp.minecraft.plugins.Utility;

import net.jp.minecraft.plugins.Enum.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public final class Msg {

    /**
     * TeisyokuPlugin2の基本prefix
     *
     * @param color 　色
     * @return prefix
     */
    private static String prefix(ChatColor color) {
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
     * メッセージの先頭に空白を挿入
     *
     * @param sender 送信先
     * @param msg    メッセージ内容
     * @param num    空白数
     */
    public static void blank(final CommandSender sender, final String msg, final int num) {
        int cnt = 0;
        StringBuilder sb = new StringBuilder("");
        while (cnt < num) {
            sb.append(" ");
            cnt++;
        }
        sender.sendMessage(sb + msg);
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
     * @param msg       メッセージ
     * @param playSound サウンド
     */
    public static void adminBroadcast(final String msg, boolean playSound) {
        for (Player admin : Bukkit.getOnlinePlayers()) {
            if (admin.hasPermission(Permission.ADMIN.toString())) {
                admin.sendMessage(prefix(ChatColor.LIGHT_PURPLE) + ChatColor.RESET + msg);
                if (playSound) {
                    Sounds.play(admin, Sound.BLOCK_NOTE_BLOCK_BELL);
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
     * @param sender プレイヤー
     * @param perm   パーミッション
     */
    public static void noPermissionMessage(final CommandSender sender, final String perm) {
        Msg.warning(sender, "パーミッションがありません" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + perm);
    }

    public static void noPermissionMessage(final CommandSender sender, final Permission perm) {
        noPermissionMessage(sender, perm.toString());
    }

    /**
     * コマンドが有効化されていない場合に表示するメッセージメソッド
     *
     * @param sender       コマンド送信者
     * @param commandLabel コマンドラベル
     */
    public static void commandNotEnabled(CommandSender sender, String commandLabel) {
        Msg.warning(sender, commandLabel + "コマンドは有効化されていません");
    }

    /**
     * パーミッションを表示する時のスタイル
     *
     * @param p    プレイヤー
     * @param perm パーミッション
     */
    @Deprecated
    public static void checkPermission(final CommandSender p, final String perm) {
        ChatColor color = ChatColor.RED;
        String hasPerm = "無効";
        if (p.hasPermission(perm)) {
            color = ChatColor.GREEN;
            hasPerm = "有効";
        }
        Msg.info(p, "パーミッション" + ChatColor.DARK_GRAY + ": " + color + hasPerm + " " + perm);
    }

    /**
     * パーミッションを確認するメソッド
     *
     * @param sender 送信者
     * @param perms  パーミッション(権限)...
     */
    public static void checkPermission(final CommandSender sender, final Permission... perms) {
        Msg.info(sender, "パーミッション状態 ");
        for (Permission perm : perms) {
            ChatColor color = ChatColor.RED;
            String hasPerm = "無効";
            if (sender.hasPermission(perm.toString())) {
                color = ChatColor.GREEN;
                hasPerm = "有効";
            }
            blank(sender, color + "・" + ChatColor.RESET + perm + ChatColor.DARK_GRAY + " (" + color + hasPerm + ChatColor.DARK_GRAY + ")", 2);
        }
    }

    /**
     * コマンドヘルプのフォーマット
     *
     * @param sender      プレイヤー
     * @param command     コマンド
     * @param discription 説明
     */
    public static void commandFormat(final CommandSender sender, final String command, final String discription) {
        sender.sendMessage(ChatColor.GRAY + "   > " + ChatColor.YELLOW + "/" + command + ChatColor.DARK_GRAY + "  : " + ChatColor.RESET + discription);
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
