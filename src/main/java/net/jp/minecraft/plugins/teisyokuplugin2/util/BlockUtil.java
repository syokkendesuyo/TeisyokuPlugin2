package net.jp.minecraft.plugins.teisyokuplugin2.util;

import org.bukkit.Material;

public class BlockUtil {

    /**
     * 看板かどうかを判定するメソッド
     *
     * @param material 検査するmaterial
     * @return 看板か否か(trueは看板)
     */
    public static boolean isSign(Material material) {

        //新しい看板が追加されたらここに追加する
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
}
