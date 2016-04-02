package net.jp.minecraft.plugins;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo azuhata
 */
public class Listener_Chat implements Listener{
    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent event)
    {
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
                Name = ChatColor.AQUA + "" + NickName + " " + ChatColor.RESET + sender;
                player.setDisplayName(Name);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
