package net.jp.minecraft.plugins.Utility;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */


public class Search {

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
            if (s.toLowerCase().indexOf(keyword) != -1) {
                return true;
            }
        }
        return false;
    }
}
