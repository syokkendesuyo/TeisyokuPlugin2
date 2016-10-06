package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.API.API_Discord;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_Discord implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        int players = Bukkit.getOnlinePlayers().size();
        sendMsg(players);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        int players = Bukkit.getOnlinePlayers().size();
        sendMsg(players - 1);
    }

    private static void sendMsg(int players) {
        if (players == 0) {
            API_Discord.sendToDiscord("現在ログインしているプレイヤーは居ません");
        } else {
            API_Discord.sendToDiscord("現在のログイン人数： " + players + " 名");
        }
    }
}
