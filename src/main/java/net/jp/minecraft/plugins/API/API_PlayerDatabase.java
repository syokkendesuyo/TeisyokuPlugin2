package net.jp.minecraft.plugins.API;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class API_PlayerDatabase {

    private static File userdata = new File(TeisyokuPlugin2.getInstance().getDataFolder(), File.separator + "PlayerDatabase");

    /**
     * プレイヤーのファイルへアクセスするメソッド
     *
     * @param uuid プレイヤーのUUID
     * @return UUID
     */
    private static FileConfiguration getPlayerFile(UUID uuid) {
        File f = new File(userdata, File.separator + uuid + ".yml");
        return YamlConfiguration.loadConfiguration(f);
    }

    /**
     * ワールドデータに登録されているプレイヤー数を取得します
     *
     * @return プレイヤー数
     */
    public static int getTotalPlayers() {
        return Objects.requireNonNull(new File("world/playerdata/").list()).length;
    }

    /**
     * プレイヤーデータベースのプレイヤーデータに新規でパスを追加します
     *
     * @param player プレイヤー
     * @param path   コンフィグ内パス
     * @param data   データ
     */
    public static void set(Player player, String path, Object data) {
        try {
            File file = new File(userdata, File.separator + player.getUniqueId() + ".yml");
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(file);
            playerData.set(path, data);
            playerData.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * プレイヤーデータベースのプレイヤーデータからデータを取得します
     *
     * @param player プレイヤー
     * @return Stringデータ
     */
    public static String getString(Player player, String path) {
        FileConfiguration file = getPlayerFile(player.getUniqueId());
        try {
            return Objects.requireNonNull(file.get(path)).toString();
        } catch (NullPointerException e) {
            //TODO: デバッグ機能の実装
            return null;
        }
    }

    /**
     * プレイヤーデータベースのプレイヤーデータからデータを取得します
     *
     * @param player プレイヤー
     * @return Integerデータ
     */
    public static Integer getInt(Player player, String path) {
        FileConfiguration file = getPlayerFile(player.getUniqueId());
        try {
            return file.getInt(path);
        } catch (NullPointerException e) {
            //TODO: デバッグ機能の実装
            return null;
        }
    }

    /**
     * プレイヤーデータベースのプレイヤーデータからデータを取得します
     *
     * @param player プレイヤー
     * @return Booleanデータ
     */
    public static Boolean getBoolean(Player player, String path) {
        FileConfiguration file = getPlayerFile(player.getUniqueId());
        try {
            return file.getBoolean(path);
        } catch (NullPointerException e) {
            //TODO: デバッグ機能の実装
            return null;
        }
    }
}
