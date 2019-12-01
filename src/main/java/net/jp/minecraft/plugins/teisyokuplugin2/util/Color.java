package net.jp.minecraft.plugins.teisyokuplugin2.util;

import org.bukkit.ChatColor;

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

    /**
     * String型からChatColor型へ変換するメソッド<br />
     * 存在しない文字列の場合、グレーが返却されます
     *
     * @param color String型のカラー
     * @return チャットカラーまたはnull
     */
    public static ChatColor getChatColor(String color) {
        for (ChatColor c : ChatColor.class.getEnumConstants()) {
            if (c.name().equalsIgnoreCase(color)) {
                return c;
            }
        }
        // TODO: デバッグ機能を追加
        return ChatColor.GRAY;
    }
}
