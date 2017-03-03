package net.jp.minecraft.plugins;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Messages implements Listener {

    /**
     * Prefixを提供
     *
     * @return string
     */
    public static String getSuccessPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.GREEN + "Teisyoku" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + ">" + ChatColor.GRAY + "> " + ChatColor.WHITE;
    }

    public static String getDenyPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Teisyoku" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + ">" + ChatColor.GRAY + "> " + ChatColor.WHITE;
    }

    public static String getNormalPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.AQUA + "Teisyoku" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + ">" + ChatColor.GRAY + "> " + ChatColor.WHITE;
    }

    public static String getYellowPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.YELLOW + "Teisyoku" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + ">" + ChatColor.GRAY + "> " + ChatColor.WHITE;
    }

    public static String getAdPrefix(String name) {
        return ChatColor.GRAY + "[" + ChatColor.GOLD + "お知らせ" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + ">" + ChatColor.GRAY + "> " + ChatColor.WHITE;
    }

    public static String getCallPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.GOLD + "お呼び出し" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + ">" + ChatColor.GRAY + "> " + ChatColor.WHITE;
    }

    public static String getCommandFormat(String command, String discription) {
        return ChatColor.GRAY + "   > " + ChatColor.YELLOW + "/" + command + ChatColor.DARK_GRAY + "  : " + ChatColor.RESET + discription;
    }

    public static String getReset() {
        return "" + ChatColor.RESET;
    }

    public static String getArray() {
        return ChatColor.GRAY + "   > " + ChatColor.RESET;
    }


    /**
     * ヘルプメッセーいを提供
     *
     * @param sender コマンド送信者
     */
    public static void HelpMessage(CommandSender sender) {
        if (!(sender.hasPermission(Permissions.getHelpPermisson()))) {
            sender.sendMessage(Messages.getNoPermissionMessage(Permissions.getHelpPermisson()));
            return;
        }
        String name = TeisyokuPlugin2.getInstance().getDescription().getName();
        sender.sendMessage(getSuccessPrefix() + name + "のヘルプ");
        sender.sendMessage(getCommandFormat("help", "当サーバのヘルプをご覧頂けます"));
        sender.sendMessage(getCommandFormat("t", "当サーバ専用のメニューを表示します"));
        sender.sendMessage(getCommandFormat("teisyoku", "当サーバ専用のメニューを表示します"));
        sender.sendMessage(getCommandFormat("tpoint", "Tポイントデータの参照をします"));
        sender.sendMessage(getCommandFormat("player", "プレイヤー一覧を表示します"));
        sender.sendMessage(getCommandFormat("ad", "全体チャットに音付きでお知らせを送信します"));
        sender.sendMessage(getCommandFormat("call", "プレイヤーを呼び出せます"));
        sender.sendMessage(getCommandFormat("last", "プレイヤーの最終ログイン・ログアウトを表示します"));
        sender.sendMessage(getCommandFormat("gomi", "ゴミ箱を展開します"));
        sender.sendMessage(getCommandFormat("nick", "ニックネームを設定します"));
        sender.sendMessage(getCommandFormat("ri", "RailwayInformationのコマンド(Teisyoku完全ｵﾘｼﾞﾅﾙ)"));
        sender.sendMessage(getCommandFormat("carthelp", "鉄道関連のヘルプを表示します"));
    }


    /**
     * パーミッション関連
     */
    //パーミッションを灰色で表示する
    public static String getPermissionNode(String permission) {
        return ChatColor.GRAY + " (" + permission + ")" + ChatColor.RESET;
    }

    //パーミッションが無い時のメッセージ
    public static String getNoPermissionMessage(String permission) {
        String permissionFormat = getPermissionNode(permission);
        return getDenyPrefix() + "パーミッションがありません" + permissionFormat;
    }

    //パーミッションの確認コマンド
    public static String getCheckPermissionMessage(String permission) {
        String permissionFormat = permission;
        return getNormalPrefix() + "パーミッション：" + permissionFormat;
    }

}
