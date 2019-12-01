package net.jp.minecraft.plugins.teisyokuplugin2.function;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * TeisyokuPlugin2<br />
 * 馬の保護に関するクラス
 *
 * @author syokkendesuyo
 */
public class Horse {

    /**
     * インスタンスへアクセスする変数
     */
    public static TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

    /**
     * ロック数を取得するメソッド
     *
     * @param player プレイヤー
     * @return ロック数
     */
    public static int getLocks(Player player) {
        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();
        int cnt = 0;
        ConfigurationSection cs = plugin.configHorses.getConfig().getConfigurationSection("");
        assert cs != null;
        for (String keys : cs.getKeys(false)) {
            String uuid = plugin.configHorses.getConfig().getString(keys + ".uuid");
            if (player.getUniqueId().toString().equals(uuid)) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * ロックの最大数を取得するメソッド
     *
     * @return ロック数
     */
    public static int getMaxLocks() {
        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();
        return plugin.configTeisyoku.getConfig().getInt("horse.limits");
    }

    /**
     * 馬をロックする
     *
     * @param player プレイヤー
     * @param uuid   UUID
     */
    public static void HorseRegister(Player player, UUID uuid) {
        if (isRegister(uuid)) {
            Msg.warning(player, "この馬は既にロックされています");
            sendStatus(player, uuid);
            Msg.success(player, "現在のロック数" + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + getLocks(player));
            return;
        }

        int limits = getMaxLocks();

        if (limits <= 0) {
            Msg.warning(player, "保護数の上限が0以下に設定されています");
            return;
        }

        if (getLocks(player) > limits - 1) {
            Msg.warning(player, limits + "体以上の馬をロックすることはできません");
            return;
        }

        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
            String strDate = sdf.format(date.getTime());

            plugin.configHorses.getConfig().set(uuid.toString(), player.toString());
            plugin.configHorses.getConfig().set(uuid.toString() + ".data", strDate);
            plugin.configHorses.getConfig().set(uuid.toString() + ".player", player.getName());
            plugin.configHorses.getConfig().set(uuid.toString() + ".uuid", player.getUniqueId().toString());
            plugin.configHorses.getConfig().set(uuid.toString() + ".mode", "private");

            plugin.configHorses.saveConfig();
            Msg.success(player, "馬をロックしました");

        } catch (Exception e) {
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    /**
     * 馬のロックを解除する
     *
     * @param player     削除するプレイヤー名
     * @param entityUUID entityのUUID
     */
    public static void HorseRemove(Player player, UUID entityUUID) {
        if (!isRegister(entityUUID)) {
            Msg.info(player, "登録情報の無い馬です");
            return;
        }

        if (!checkLockStatus(player.getUniqueId(), entityUUID)) {
            Msg.warning(player, "登録者以外馬のロックは解除できません");
            sendStatus(player, entityUUID);
            return;
        }

        try {
            plugin.configHorses.getConfig().set(entityUUID.toString(), null);
            plugin.configHorses.saveConfig();
            Msg.success(player, "馬のロックを解除しました");
        } catch (Exception e) {
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    /**
     * 馬のロックをワイルドカードで削除します
     *
     * @param entityUUID entityのUUID
     */
    public static void HorseRemoveWildcard(UUID entityUUID) {
        if (!isRegister(entityUUID)) {
            return;
        }
        try {
            plugin.configHorses.getConfig().set(entityUUID.toString(), null);
            plugin.configHorses.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登録情報があるか確認するメソッド
     *
     * @param entityUUID エンティティーのUUID
     * @return 登録状態
     */
    public static boolean isRegister(UUID entityUUID) {
        return !(plugin.configHorses.getConfig().getString(entityUUID + ".uuid") == null);
    }

    /**
     * 指定したエンティティーの登録者であるかどうかを確認するメソッド
     *
     * @param player プレイヤー
     * @param entity エンティティーのUUID
     * @return 登録者であるかどうかの結果
     */
    public static boolean checkLockStatus(Player player, Entity entity) {
        return checkLockStatus(player.getUniqueId(), entity.getUniqueId());
    }

    /**
     * 指定したエンティティーの登録者であるかどうかを確認するメソッド
     *
     * @param playerUUID プレイヤーのUUID
     * @param entityUUID エンティティーのUUID
     * @return 登録者であるかどうかの結果
     */
    public static boolean checkLockStatus(UUID playerUUID, UUID entityUUID) {
        return playerUUID.toString().equals(plugin.configHorses.getConfig().get(entityUUID + ".uuid"));
    }

    /**
     * ステイタス情報をターゲットへ送信するメソッド
     *
     * @param player     プレイヤー
     * @param entityUUID エンティティーのUUID
     */
    public static void sendStatus(Player player, UUID entityUUID) {
        Msg.info(player, "登録者名" + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + plugin.configHorses.getConfig().getString(entityUUID + ".player"));
        Msg.info(player, "登録日" + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + plugin.configHorses.getConfig().getString(entityUUID + ".data"));
    }
}
