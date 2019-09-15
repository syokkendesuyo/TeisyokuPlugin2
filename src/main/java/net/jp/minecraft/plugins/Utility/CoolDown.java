package net.jp.minecraft.plugins.Utility;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class CoolDown {

    //HashMapの初期値を最大ログイン人数にする
    private static int n = Bukkit.getMaxPlayers();

    //HashMapを生成、＜プレイヤーのUUID,クールダウン終了の時刻を格納＞
    private static Map<UUID, Calendar> map = new HashMap<UUID, Calendar>(n * 4 / 3);

    public static boolean cooldown(Player player) {
        //現在時刻を取得
        Date nowDate = new Date();

        //現在時刻のカレンダー
        Calendar now = Calendar.getInstance();
        now.setTime(nowDate);

        //終了時刻
        Calendar check = get(player);

        if (check.before(now)) {
            return true;
        } else {
            long difference = check.getTime().getTime() - now.getTime().getTime();
            Msg.warning(player, "コマンドを実行できませんでした： 残り " + difference / 1000 + " 秒");
            return false;
        }
    }

    public static Calendar get(Player player) {
        return map.get(player.getUniqueId());
    }

    public static boolean is(Player player) {
        return map.containsKey(player.getUniqueId());
    }

    public static void put(Player player, Calendar end) {
        end.add(Calendar.SECOND, getCoolDownTime());
        map.put(player.getUniqueId(), end);
    }

    //クールダウンの初期値を設定
    //TODO: configから変更できるように
    private static int getCoolDownTime() {
        return 60;
    }
}
