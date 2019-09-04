package net.jp.minecraft.plugins.Utility;

public class Color {

    /**
     * チャットカラーに変換するメソッド
     *
     * @param string &で表現されたカラーデータ
     * @return チャットカラー変換後の文字列
     */
    public static String convert(String string) {
        return string.replaceAll("&", "§");
    }
}
