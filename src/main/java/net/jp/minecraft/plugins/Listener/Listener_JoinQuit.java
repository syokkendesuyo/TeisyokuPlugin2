package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.API.API;
import net.jp.minecraft.plugins.API.API_Flag;
import net.jp.minecraft.plugins.API.API_Fly;
import net.jp.minecraft.plugins.API.API_PlayerDatabase;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Color;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_JoinQuit implements Listener {

    @EventHandler
    public void onPlayerJoinMessage(PlayerJoinEvent event) {
        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();
        Player player = event.getPlayer();

        //ログイン時のメッセージを日本語化
        event.setJoinMessage("");
        Msg.success(Bukkit.getConsoleSender(), ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET + " さんがゲームに参加しました", true);

        //ログイン時のプレイヤーデータを保管
        API_PlayerDatabase.set(player, "name", player.getName());
        API_PlayerDatabase.set(player, "join.date", API.getDateFormat());
        API_PlayerDatabase.set(player, "join.timestamp", System.currentTimeMillis());

        //飛行モードを継続
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.fly") && API_PlayerDatabase.getBoolean(player, "fly") && API_Flag.get(player, "fly_save_state")) {
            API_Fly.setFlying(player, true);
        } else {
            API_Fly.setFlying(player, false);
        }

        List<String> ad = plugin.configTeisyoku.getConfig().getStringList("joinMessage");
        for (String s : ad) {
            Msg.info(player, Color.convert(s));
        }
        if (plugin.configTeisyoku.getConfig().getString("debug.SpawnFixed") == null) {
            plugin.configTeisyoku.getConfig().set("debug.SpawnFixed", false);
        }
        if (plugin.configTeisyoku.getConfig().getBoolean("debug.SpawnFixed")) {
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 72, 0));
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //ログイン時のメッセージを日本語化
        event.setQuitMessage("");
        Msg.success(Bukkit.getConsoleSender(), ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET + " さんがゲームから退室しました", true);

        //ログアウト時にプレイヤーデータを保管
        API_PlayerDatabase.set(player, "name", player.getName());
        API_PlayerDatabase.set(player, "quit.date", API.getDateFormat());
        API_PlayerDatabase.set(player, "quit.timestamp", System.currentTimeMillis());
    }
}
