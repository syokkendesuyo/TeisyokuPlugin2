package net.jp.minecraft.plugins.teisyokuplugin2.util;

import org.bukkit.Material;
import org.bukkit.block.data.Rail;

public class BlockUtil {
    /**
     * 指定のmaterialがmaterial配列のなかにあるかどうか検査するメソッド
     * @param material 検査するmaterial
     * @param targets 対象のmaterialの配列
     * @return 検査するmaterialが対象のmaterialだったかどうか
     */
    public static boolean scanMaterial(Material material, Material[] targets){
        for (Material m : targets) {
            if (material.equals(m))
                return true;
        }
        return false;
    }

    /**
     * レールが直線かどうかを判定するメソッド
     *
     * @param rail 検査するrail
     * @return 直線か否か(trueは直線)
     */
    public static boolean isRailStraight(Rail rail) {
        return rail.getShape().equals(Rail.Shape.EAST_WEST) || rail.getShape().equals(Rail.Shape.NORTH_SOUTH);
    }
}
