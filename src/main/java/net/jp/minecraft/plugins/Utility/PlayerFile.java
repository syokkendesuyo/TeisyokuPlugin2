package net.jp.minecraft.plugins.Utility;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class PlayerFile implements Listener{

    public static File userdata = new File(TeisyokuPlugin2.getInstance().getDataFolder(), File.separator + "PlayerDatabase");

    /**
     * プレイヤーがログインすると発動する
     * もしプレイヤーの個別ファイルが存在しなければ作成する
     *
     * @param event
     */
    @EventHandler
    public void CreatePlayerFile(PlayerJoinEvent event){
        String playerUniqueId = event.getPlayer().getUniqueId().toString();
        File f = new File(userdata, File.separator + playerUniqueId + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        try {
            if(playerData.get("tpoint") == null){
                playerData.createSection("tpoint");
                playerData.set("tpoint", 0);
            }

            if(playerData.get("nick") == null){
                playerData.createSection("nick");
                playerData.set("nick", "");
            }

            if(playerData.get("nick_color") == null){
                playerData.createSection("nick_color");
                playerData.set("nick_color", "default");
            }

            if(playerData.get("auto_cart_remove") == null){
                playerData.createSection("auto_cart_remove");
                playerData.set("auto_cart_remove", false);
            }

            playerData.save(f);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * プレイヤーのファイルへアクセスするメソッド
     * @param uuid プレイヤーのUUID
     * @return
     */
    public static FileConfiguration getPlayerFile(UUID uuid){
        File f = new File(userdata, File.separator + uuid + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        return(playerData);
    }
}
