package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Hooks.Hook_BountifulAPI;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_Tab implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Hook_BountifulAPI.sendTabTitle(event.getPlayer(), color(TeisyokuPlugin2.getInstance().TeisyokuConfig.get("title").toString()), color(TeisyokuPlugin2.getInstance().TeisyokuConfig.get("subtitle").toString()));
    }

    @EventHandler
    public void exit(PlayerQuitEvent event) {
        Hook_BountifulAPI.sendTabTitle(event.getPlayer(), "", "");
    }

    private static String color(String str) {
        return str.replaceAll("&", "§");
    }
}
