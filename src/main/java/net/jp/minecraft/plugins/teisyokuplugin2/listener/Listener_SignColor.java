package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.Objects;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_SignColor implements Listener {
    @EventHandler
    public void onSignChangeEvent(SignChangeEvent event) {
        event.setLine(0, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(event.getLine(0))));
        event.setLine(1, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(event.getLine(1))));
        event.setLine(2, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(event.getLine(2))));
        event.setLine(3, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(event.getLine(3))));
    }
}
