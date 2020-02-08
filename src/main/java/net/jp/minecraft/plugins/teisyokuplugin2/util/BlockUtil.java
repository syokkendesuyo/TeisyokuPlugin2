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
        String[] target = {
                "ACACIA_SIGN",
                "ACACIA_WALL_SIGN",
                "BIRCH_SIGN",
                "BIRCH_WALL_SIGN",
                "DARK_OAK_SIGN",
                "DARK_OAK_WALL_SIGN",
                "JUNGLE_SIGN",
                "JUNGLE_WALL_SIGN",
                "OAK_SIGN",
                "OAK_WALL_SIGN",
                "SPRUCE_SIGN",
                "SPRUCE_WALL_SIGN"};

        for (String s : target) {
            if (material.toString().equals(s))
                return true;
        }

        return false;
    }
}
