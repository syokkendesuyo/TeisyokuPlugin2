package net.jp.minecraft.plugins.Utility;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */


public class Search {

    /**
     *
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
