package net.jp.minecraft.plugins;

import org.bukkit.command.CommandSender;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
@Deprecated
public class Permissions {

    //管理者の定義
    public static String getTeisyokuAdminPermisson() {
        return "teisyoku.admin";
    }

    //一般プレイヤーの定義
    public static String getTeisyokuUserPermisson() {
        return "teisyoku.user";
    }

    //ヘルプパーミッション
    public static String getHelpCommandPermisson() {
        String permission = "teisyoku.user";
        return permission;
    }

    //定食メニューパーミッション
    public static String getTeisyokuCommandPermisson() {
        String permisson = "teisyoku.user";
        return permisson;
    }

    /**
     * パーミッションの有無を確認します
     *
     * @param sender プレイヤー/コンソール
     * @param perm   パーミッション
     * @return 結果
     */
    public static boolean hasPermission(CommandSender sender, String perm) {
        return sender.hasPermission(perm);
    }
}