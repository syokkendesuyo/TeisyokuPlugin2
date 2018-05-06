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
    @Deprecated
    public static String getSuccessPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + ChatColor.BOLD +  "Teisyoku" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + ">> " + ChatColor.WHITE;
    }

    @Deprecated
    public static String getDenyPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "Teisyoku" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + ">> " + ChatColor.WHITE;
    }

    @Deprecated
    public static String getNormalPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + ChatColor.BOLD + "Teisyoku" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + ">> " + ChatColor.WHITE;
    }

    @Deprecated
    public static String getYellowPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + "Teisyoku" + ChatColor.DARK_GRAY + "] "+ ChatColor.GRAY + ">> " + ChatColor.WHITE;
    }

    @Deprecated
    public static String getAdPrefix(String name) {
        return ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + "お知らせ" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + ">> " + ChatColor.WHITE;
    }

    @Deprecated
    public static String getCallPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + "お呼び出し" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + ">> " + ChatColor.WHITE;
    }

    @Deprecated
    public static String getCommandFormat(String command, String discription) {
        return ChatColor.DARK_GRAY + "   > " + ChatColor.YELLOW + "/" + command + ChatColor.DARK_GRAY + "  : " + ChatColor.RESET + discription;
    }

    @Deprecated
    public static String getReset() {
        return "" + ChatColor.RESET;
    }

    @Deprecated
    public static String getArray() {
        return ChatColor.GRAY + "   > " + ChatColor.RESET;
    }


    /**
     * ヘルプメッセーいを提供
     *
     * @param sender コマンド送信者
     */
    @Deprecated
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
    @Deprecated
    public static String getPermissionNode(String permission) {
        return ChatColor.GRAY + " (" + permission + ")" + ChatColor.RESET;
    }

    //パーミッションが無い時のメッセージ
    @Deprecated
    public static String getNoPermissionMessage(String permission) {
        String permissionFormat = getPermissionNode(permission);
        return getDenyPrefix() + "パーミッションがありません" + permissionFormat;
    }

    //パーミッションの確認コマンド
    @Deprecated
    public static String getCheckPermissionMessage(String permission) {
        String permissionFormat = permission;
        return getNormalPrefix() + "パーミッション：" + permissionFormat;
    }

}
