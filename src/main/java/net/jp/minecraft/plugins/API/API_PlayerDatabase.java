package net.jp.minecraft.plugins.API;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.PlayerFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class API_PlayerDatabase {

    private static File userdata = new File(TeisyokuPlugin2.getInstance().getDataFolder(), File.separator + "PlayerDatabase");

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
        FileConfiguration file = PlayerFile.getPlayerFile(player.getUniqueId());
        return Objects.requireNonNull(file.get(path)).toString();
    }

    /**
     * プレイヤーデータベースのプレイヤーデータからデータを取得します
     *
     * @param player プレイヤー
     * @return Integerデータ
     */
    public static Integer getInt(Player player, String path) {
        FileConfiguration file = PlayerFile.getPlayerFile(player.getUniqueId());
        return file.getInt(path);
    }

    /**
     * プレイヤーデータベースのプレイヤーデータからデータを取得します
     *
     * @param player プレイヤー
     * @return Booleanデータ
     */
    public static Boolean getBoolean(Player player, String path) {
        FileConfiguration file = PlayerFile.getPlayerFile(player.getUniqueId());
        return file.getBoolean(path);
    }
}
