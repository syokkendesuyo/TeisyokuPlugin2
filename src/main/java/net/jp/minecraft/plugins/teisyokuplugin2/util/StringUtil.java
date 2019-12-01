package net.jp.minecraft.plugins.teisyokuplugin2.util;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */


public class StringUtil {

    /**
     * String配列に対して検索をするメソッド
     * 部分一致でtrueを返します
     *
     * @param list    検索する文字列型の配列
     * @param keyword 検索する文字列
     * @return boolean
     */
    public static boolean searchKeyword(final String[] list, final String keyword) {
        for (String s : list) {
            if (s.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * コマンドなどで空白を入れたい場合置換メソッド<br />
     * 渡された文字列に&&が存在した場合、半角スペースに変換します
     *
     * @param string 文字列
     * @return 置換後
     */
    public static String replaceToBlank(String string) {
        return string.replaceAll("%%", " ");
    }
}
