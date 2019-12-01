package net.jp.minecraft.plugins.teisyokuplugin2.module;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.util.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * TeisyokuPlugin2<br />
 * プレイヤーのデータベースへ接続する機能を提供します。
 *
 * @author syokkendesuyo
 */
public class PlayerDatabase {

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
     * ワールドデータに登録されているプレイヤー数を取得します。
     *
     * @return プレイヤー数
     */
    public static int getTotalPlayers() {
        return Objects.requireNonNull(new File("world/playerdata/").list()).length;
    }

    /**
     * プレイヤーデータベースのプレイヤーデータに新規でパスを追加します。
     *
     * @param player プレイヤー
     * @param path   コンフィグ内パス
     * @param data   データ
     */
    public static void set(OfflinePlayer player, String path, Object data) {
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
     * プレイヤーデータベースのプレイヤーデータからデータを取得します。<br />
     * パスが存在しない場合、文字列""を返却します。
     *
     * @param player プレイヤー
     * @return Stringデータ
     */
    public static String getString(OfflinePlayer player, String path) {
        FileConfiguration file = getPlayerFile(player.getUniqueId());
        try {
            return Objects.requireNonNull(file.get(path)).toString();
        } catch (NullPointerException e) {
            //TODO: デバッグ機能の実装
            return "";
        }
    }

    /**
     * プレイヤーデータベースのプレイヤーデータからデータを取得します。<br />
     * パスが存在しない場合、-1を返却します。
     *
     * @param player プレイヤー
     * @return Integerデータ
     */
    public static Integer getInt(OfflinePlayer player, String path) {
        FileConfiguration file = getPlayerFile(player.getUniqueId());
        try {
            return file.getInt(path);
        } catch (NullPointerException e) {
            //TODO: デバッグ機能の実装
            return -1;
        }
    }

    /**
     * プレイヤーデータベースのプレイヤーデータからデータを取得します。<br />
     * パスが存在しない場合、falseを返却します。
     *
     * @param player プレイヤー
     * @return Booleanデータ
     */
    public static Boolean getBoolean(OfflinePlayer player, String path) {
        FileConfiguration file = getPlayerFile(player.getUniqueId());
        try {
            return file.getBoolean(path);
        } catch (NullPointerException e) {
            //TODO: デバッグ機能の実装
            return false;
        }
    }

    /**
     * プレイヤーデータベースが存在するかどうか確認します。<br />
     * データベースファイルが存在してもidがファイル内に登録されていない場合falseを返します。
     *
     * @param player プレイヤー
     * @return 存在の有無
     */
    public static Boolean exists(OfflinePlayer player) {
        try {
            return PlayerDatabase.getString(player, "id").isEmpty();
        } catch (NullPointerException e) {
            //TODO: デバッグ機能の実装
            return false;
        }
    }

    /**
     * プレイヤーを取得するメソッド<br />
     * PlayerDatabaseにデータが存在しない場合やプレイヤー名が存在しない場合はnullを返却します。
     *
     * @param playerName プレイヤー名
     * @return オフラインプレイヤー
     */
    public static OfflinePlayer getPlayer(String playerName) {
        try {
            UUID uuid = UUIDFetcher.getUUIDOf(playerName);

            //Minecraftに登録されているUUIDか検索 (ネットワーク接続必須)
            if (uuid == null) {
                return null;
            }

            //UUIDからオフラインプレイヤーへ変換
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

            //データベースにデータが存在するか確認
            if (exists(player)) {
                return null;
            }
            return player;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
