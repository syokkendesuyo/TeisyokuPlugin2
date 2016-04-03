package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo azuhata
 */
public class Listener_Chat implements Listener{

    static File cfile;
    static FileConfiguration config;
    static File df = TeisyokuPlugin2.getInstance().getDataFolder();

    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent event) {

        cfile = new File(df, "PlayerDatabase" + File.separator + event.getPlayer().getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        FileConfiguration playerData = config;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        try {
            String NickName = TeisyokuPlugin2.getInstance().NickConfig.getString(uuid + ".nick");
            String sender = event.getPlayer().getName().toString();

            String Name;
            if(NickName == null){
                Name = sender;
                player.setDisplayName(Name);
            }else{
                String color = playerData.getString("nick_color");
                if(color.equalsIgnoreCase("aqua")){
                    Name = ChatColor.AQUA + "" + NickName + " " + ChatColor.RESET + sender;
                    player.setDisplayName(Name);
                    return;
                }
                if(color.equalsIgnoreCase("pink")){
                    Name = ChatColor.LIGHT_PURPLE + "" + NickName + " " + ChatColor.RESET + sender;
                    player.setDisplayName(Name);
                    return;
                }
                else{
                    Name = ChatColor.GRAY + "" + NickName + " " + ChatColor.RESET + sender;
                    player.setDisplayName(Name);
                    return;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
