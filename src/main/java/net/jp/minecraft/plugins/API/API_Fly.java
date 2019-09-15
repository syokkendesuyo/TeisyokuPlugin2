package net.jp.minecraft.plugins.API;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class API_Fly {

    /**
     * 飛行モードを変更
     *
     * @param player プレイヤー
     * @param bool   飛行状態
     */
    public static void setFlying(Player player, Boolean bool) {
        player.setAllowFlight(bool);
        player.setFlying(bool);
        API_PlayerDatabase.set(player, "fly", bool);
        if (bool) {
            Msg.success(player, ChatColor.YELLOW + player.getName() + ChatColor.RESET + " の飛行モードを" + ChatColor.GREEN + " 有効 " + ChatColor.RESET + "にしました");
            return;
        }
        Msg.success(player, ChatColor.YELLOW + player.getName() + ChatColor.RESET + " の飛行モードを" + ChatColor.RED + " 無効 " + ChatColor.RESET + "にしました");
    }

    /**
     * 飛行状態をトグルするメソッド
     *
     * @param player プレイヤー
     */
    public static void toggleFlying(Player player) {
        if (isFlying(player)) {
            setFlying(player, false);
            return;
        }
        setFlying(player, true);
    }

    /**
     * 飛行状態を確認するメソッド
     *
     * @param player プレイヤー
     * @return 状態
     */
    public static boolean isFlying(Player player) {
        return API_PlayerDatabase.getBoolean(player, "fly");
    }
}
