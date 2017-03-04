package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.API.API_Discord;
import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import org.bukkit.Bukkit;
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
    public void onPlayerJoinMessage(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        List<String> ad = TeisyokuPlugin2.getInstance().TeisyokuConfig.getStringList("joinMessage");
        for (String s : ad) {
            player.sendMessage(Messages.getNormalPrefix() + color(s));
        }
    }

    @EventHandler
    public void firstJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            Player player = event.getPlayer();
            API_Discord.sendToDiscord(" * * * * * * * * * * * * * * * * * * * * ");
            API_Discord.sendToDiscord(player.getName() + "さんは新規参加者です");
            API_Discord.sendToDiscord(" * * * * * * * * * * * * * * * * * * * * ");
        }
    }

    private static String color(String str) {
        return str.replaceAll("&", "§");
    }
}
