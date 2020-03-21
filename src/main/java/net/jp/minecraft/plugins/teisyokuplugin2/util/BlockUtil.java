package net.jp.minecraft.plugins.teisyokuplugin2.util;

import org.bukkit.Material;

import org.bukkit.block.data.Rail;

public class BlockUtil {

    /**
     * 看板かどうかを判定するメソッド
     *
     * @param material 検査するmaterial
     * @return 看板か否か(trueは看板)
     */
    public static boolean isSign(Material material) {

        // 新しい看板が追加されたらここに追加する
        Material[] target = {
                Material.ACACIA_SIGN,
                Material.ACACIA_WALL_SIGN,
                Material.BIRCH_SIGN,
                Material.BIRCH_WALL_SIGN,
                Material.DARK_OAK_SIGN,
                Material.DARK_OAK_WALL_SIGN,
                Material.JUNGLE_SIGN,
                Material.JUNGLE_WALL_SIGN,
                Material.OAK_SIGN,
                Material.OAK_WALL_SIGN,
                Material.SPRUCE_SIGN,
                Material.SPRUCE_WALL_SIGN};

        for (Material m : target) {
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
