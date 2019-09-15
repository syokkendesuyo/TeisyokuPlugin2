package net.jp.minecraft.plugins;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
@Deprecated
public class Messages implements Listener {

    /**
     * Prefixを提供
     *
     * @return string
     */

    @Deprecated
    public static String getDenyPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "Teisyoku" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + ">> " + ChatColor.WHITE;
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
}
