package net.jp.minecraft.plugins.teisyokuplugin2.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TeisyokuPlugin2<br />
 * 時間に関するユーティリティを提供
 *
 * @author syokkendesuyo
 */
public class TimeUtil {

    /**
     * 日時フォーマットを取得するメソッド
     *
     * @return 日時の文字列
     */
    public static String getDateFormat() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
        return sdf.format(date.getTime());
    }
}
