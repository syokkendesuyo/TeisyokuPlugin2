package net.jp.minecraft.plugins.API;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static java.lang.Math.pow;

/**
 * TeisyokuPlugin2<br />
 * <hr />
 * 便利なAPIを提供する総合クラス<br />
 *
 * @author syokkendesuyo
 */
public class API {

    /**
     * 指定した位置から一番近いプレイヤーを
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
}
