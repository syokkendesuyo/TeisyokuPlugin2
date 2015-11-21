package net.jp.minecraft.plugins;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_SignColor implements Listener {
    @EventHandler
    public void onSignChangeEvent(SignChangeEvent event)
    {
        if (!event.getPlayer().hasPermission("teisyoku.user")){
            return;
        }

        event.setLine(0, ChatColor.translateAlternateColorCodes('&', event.getLine(0)));
        event.setLine(1, ChatColor.translateAlternateColorCodes('&', event.getLine(1)));
        event.setLine(2, ChatColor.translateAlternateColorCodes('&', event.getLine(2)));
        event.setLine(3, ChatColor.translateAlternateColorCodes('&', event.getLine(3)));

        return;
    }
}
