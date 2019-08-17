package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

        event.setJoinMessage("");
        Msg.success(Bukkit.getConsoleSender(), ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET + " さんがゲームに参加しました", true);

        List<String> ad = TeisyokuPlugin2.getInstance().TeisyokuConfig.getStringList("joinMessage");
        for (String s : ad) {
            Msg.info(player, color(s));
        }
        if (TeisyokuPlugin2.getInstance().TeisyokuConfig.getString("debug.SpawnFixed") == null) {
            TeisyokuPlugin2.getInstance().TeisyokuConfig.set("debug.SpawnFixed", false);
        }
        if (TeisyokuPlugin2.getInstance().TeisyokuConfig.getBoolean("debug.SpawnFixed")) {
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 72, 0));
        }
    }
    private static String color(String str) {
        return str.replaceAll("&", "§");
    }
}
