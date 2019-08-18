package net.jp.minecraft.plugins;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
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

    public static String getHelpPermisson() {
        String permission = "teisyoku.user";
        return permission;
    }

    //飛行パーミッション
    public static String getFlyCommandPermisson() {
        String permission = "teisyoku.admin";
        return permission;
    }

    public static String getFlyPermisson() {
        String permission = "teisyoku.admin";
        return permission;
    }

    //定食メニューパーミッション
    public static String getTeisyokuCommandPermisson() {
        String permisson = "teisyoku.user";
        return permisson;
    }

    public static String getTeisyokuPermisson() {
        String permisson = "teisyoku.user";
        return permisson;
    }

    //プレイヤー一覧パーミッション
    public static String getPlayersCommandPermisson() {
        String permisson = "teisyoku.user";
        return permisson;
    }

    public static String getPlayersPermisson() {
        String permisson = "teisyoku.user";
        return permisson;
    }

    //ゴミ箱パーミッション
    public static String getGomibakoCommandPermisson() {
        String string = "teisyoku.user";
        return string;
    }

    public static String getGomibakoPermisson() {
        String string = "teisyoku.user";
        return string;
    }

    //ログイン履歴確認コマンド
    public static String getLastCommandPermisson() {
        String string = "teisyoku.user";
        return string;
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