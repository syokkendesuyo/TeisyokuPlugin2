package net.jp.minecraft.plugins.teisyokuplugin2.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

import static java.lang.Math.pow;

/**
 * TeisyokuPlugin2<br />
 * プレイヤー関連のユーティリティを提供
 *
 * @author syokkendesuyo
 */
public class PlayerUtil {

    /**
     * 指定した位置から一番近いプレイヤーを返却するメソッド
     * TODO: プレイヤーが０の場合の処理を追加
     *
     * @param loc ロケーション
     * @return 一番近いプレイヤー
     */
    public static Player getNearByPlayer(Location loc) {
        double size = 100;
        Player target = null;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getWorld() != loc.getWorld()) {
                continue;
            }
            Location targetLoc = onlinePlayer.getLocation();

            double x1 = loc.getX();
            double y1 = loc.getY();
            double z1 = loc.getZ();

            double x2 = targetLoc.getX();
            double y2 = targetLoc.getY();
            double z2 = targetLoc.getZ();

            double length = pow((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1), 0.5);

            if (length < size) {
                target = onlinePlayer;
                size = length;
            }
        }
        return target;
    }

    /**
     * プレイヤー名であるか確認するメソッド
     *
     * @param playerName プレイヤー名
     * @return プレイヤー名かどうか
     */
    public static boolean isPlayerName(String playerName) {
        if (playerName == null) {
            return false;
        }
        if (playerName.length() < 3 || playerName.length() > 16) {
            return false;
        }
        return Pattern.matches("^[0-9a-zA-Z]+$", playerName);
    }

    /**
     * サウンドを再生
     *
     * @param player プレイヤー
     * @param sound  サウンド
     */
    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 10.0F, 1.0F);
    }
}
