package net.jp.minecraft.plugins.Config;

import net.jp.minecraft.plugins.API.API_PlayerDatabase;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class migrationPlayerDatabase {


    public static void migration() {

        //インスタンスを取得
        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //データ移行機能が有効かどうか確認
        if (!plugin.configTeisyoku.getConfig().getBoolean("migration")) {
            return;
        }

        File file = new File(plugin.getDataFolder() + "/LastJoinPlayersData.yml");
        if (file.exists()) {
            Msg.info(Bukkit.getConsoleSender(), "データ移行開始: " + file.toString());

            //旧コンフィグ
            FileConfiguration configOld = YamlConfiguration.loadConfiguration(file);

            //登録キーを全件走査
            for (String key : Objects.requireNonNull(configOld.getConfigurationSection("")).getKeys(false)) {
                try {
                    update(key, "Name", "id");
                    update(key, "JoinDate", "join.date");
                    update(key, "JoinTimestamp", "join.timestamp");
                    update(key, "QuitDate", "quit.date");
                    update(key, "QuitTimestamp", "quit.timestamp");
                    Msg.info(Bukkit.getConsoleSender(), "データ移行完了: " + key);
                } catch (Exception e) {
                    Msg.info(Bukkit.getConsoleSender(), "データ移行失敗: " + key);
                    e.printStackTrace();
                }
            }
        }
        // TODO: migrationをfalseに変更しデータ移行機能を無効化
    }

    /**
     * 旧コンフィグから新コンフィグへパス情報を移管するメソッド
     *
     * @param uuid    　UUID (String型)
     * @param pathOld 旧パス
     * @param pathNew 新パス
     */
    private static void update(String uuid, String pathOld, String pathNew) {
        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();
        File file = new File(plugin.getDataFolder() + "/LastJoinPlayersData.yml");
        FileConfiguration configOld = YamlConfiguration.loadConfiguration(file);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        //データが存在しない場合は移管しない
        if (configOld.get(uuid + "." + pathOld) == null) {
            Msg.info(Bukkit.getConsoleSender(), "データが存在しません");
            return;
        }
        if (API_PlayerDatabase.getString(offlinePlayer, pathNew).isEmpty()) {
            API_PlayerDatabase.set(offlinePlayer, pathNew, configOld.get(uuid + "." + pathOld));
        }
    }
}
