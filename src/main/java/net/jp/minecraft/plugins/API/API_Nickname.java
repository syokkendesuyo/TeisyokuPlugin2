package net.jp.minecraft.plugins.API;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.PlayerFile;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class API_Nickname {

    /**
     * ニックネームを取得するメソッド
     * @param player プレイヤー
     * @return
     */
    public static String getNick(Player player){
        FileConfiguration file = PlayerFile.getPlayerFile(player.getUniqueId());
        return file.get("nick").toString();
    }

    /**
     * ニックネームをセットするメソッド
     * @param player プレイヤー
     * @param nick ニックネーム
     * @return
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
     * @return
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
