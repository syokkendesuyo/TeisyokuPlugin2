package net.jp.minecraft.plugins.API;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class API_FlyMode {

    /**
     * 飛行モードを有効にする
     *
     * @param player プレイヤー
     * @return boolean
     */
    public static boolean enableFly(Player player) {
        Msg.success(player, ChatColor.YELLOW + player.getName() + ChatColor.RESET + " の飛行モードを" + ChatColor.GREEN + " 有効 " + ChatColor.RESET + "にしました");
        player.setAllowFlight(true);
        player.setFlying(true);
        API_PlayerDatabase.set(player, "fly", true);
        return true;
    }

    /**
     * 飛行モードを無効にする
     *
     * @param player プレイヤー
     * @return boolean
     */
    public static boolean disableFly(Player player) {
        Msg.success(player, ChatColor.YELLOW + player.getName() + ChatColor.RESET + " の飛行モードを" + ChatColor.RED + " 無効 " + ChatColor.RESET + "にしました");
        player.setAllowFlight(false);
        player.setFlying(false);
        API_PlayerDatabase.set(player, "fly", false);
        return true;
    }

}
