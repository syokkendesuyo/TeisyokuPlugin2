package net.jp.minecraft.plugins.API;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class API_Nickname {

    /**
     * ニックネームを取得するメソッド
     * @param player プレイヤー
     * @return ニックネーム
     */
    public static String getNick(Player player){
        return API_PlayerDatabase.getString(player, "nick");
    }

    /**
     * ニックネームをセットするメソッド
     * @param player プレイヤー
     * @param nick ニックネーム
     * @return 実行結果
     */
    public static Boolean setNick(Player player, String nick){
        try{
            File userdata = new File(TeisyokuPlugin2.getInstance().getDataFolder(), File.separator + "PlayerDatabase");
            File f = new File(userdata, File.separator + player.getUniqueId() + ".yml");
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
            playerData.set("nick", nick);
            playerData.save(f);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ニックネームをセットするメソッド
     * @param player プレイヤー
     * @return 実行結果
     */
    public static Boolean removeNick(Player player){
        try{
            File userdata = new File(TeisyokuPlugin2.getInstance().getDataFolder(), File.separator + "PlayerDatabase");
            File f = new File(userdata, File.separator + player.getUniqueId() + ".yml");
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
            playerData.set("nick", "");
            playerData.save(f);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}