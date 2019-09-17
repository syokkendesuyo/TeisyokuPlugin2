package net.jp.minecraft.plugins.Utility;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Replace {

    /**
     * コマンドなどで空白を入れたい場合置換メソッド<br />
     * 渡された文字列に&&が存在した場合、半角スペースに変換します
     *
     * @param string 文字列
     * @return 置換後
     */
    public static String blank(String string) {
        return string.replace("%%", " ");
    }
}
