package net.jp.minecraft.plugins;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

/**
 * @author syokkendesuyo
 */
public class Listener_JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoinMessage(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.VILLAGER_DEATH, 10.0F, 1.0F);
        List<String> ad = TeisyokuPlugin2.getInstance().TeisyokuConfig.getStringList("joinMessage");
        for (String s : ad){
            player.sendMessage(Messages.getNormalPrefix() + color(s));
        }
        return;
    }

    public static String color(String str){
        return str.replaceAll("&","§");
    }
}
