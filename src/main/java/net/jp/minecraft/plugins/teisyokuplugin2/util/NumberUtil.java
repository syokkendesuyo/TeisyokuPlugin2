package net.jp.minecraft.plugins.teisyokuplugin2.util;

/**
 * TeisyokuPlugin2<br />
 * 数値関連のユーティリティを提供
 *
 * @author syokkendesuyo
 */
public class NumberUtil {

    /**
     * 渡された文字列が数字であるか確認する
     *
     * @param num 文字列
     * @return boolean
     */
    public static boolean isNumber(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException event) {
            return false;
        }
    }

}
