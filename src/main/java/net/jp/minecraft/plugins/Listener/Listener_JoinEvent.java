package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.API.API_Discord;
import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Sounds;
import org.bukkit.Bukkit;
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

    public String ver1_8_8_R01 = "1.8.8-R0.1-SNAPSHOT";
    public String ver1_9_2_R01 = "1.9.2-R0.1-SNAPSHOT";

    @EventHandler
    public void onPlayerJoinMessage(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String version = Bukkit.getBukkitVersion();
        if (version.equals(ver1_8_8_R01)) {
            Sounds.sound_villager(player);
        }
        else if(version.equals(ver1_9_2_R01)){
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 10.0F, 1.0F);
        }
        List<String> ad = TeisyokuPlugin2.getInstance().TeisyokuConfig.getStringList("joinMessage");
        for (String s : ad){
            player.sendMessage(Messages.getNormalPrefix() + color(s));
        }
        return;
    }

    @EventHandler
    public void firstJoin(PlayerJoinEvent event) {
        if(!event.getPlayer().hasPlayedBefore()) {
            Player player = event.getPlayer();
            API_Discord.sendToDiscord(" * * * * * * * * * * * * * * * * * * * * ");
            API_Discord.sendToDiscord(player.getName() + "さんは新規参加者です");
            API_Discord.sendToDiscord(" * * * * * * * * * * * * * * * * * * * * ");
        }
    }

    public static String color(String str){
        return str.replaceAll("&","§");
    }
}
