package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_Tab implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {
        event.getPlayer().setPlayerListHeaderFooter(Color.convert(Objects.requireNonNull(TeisyokuPlugin2.getInstance().configTeisyoku.getConfig().get("title")).toString()), Color.convert(TeisyokuPlugin2.getInstance().configTeisyoku.getConfig().get("subtitle").toString()));
    }

    @EventHandler
    public void exit(PlayerQuitEvent event) {
        event.getPlayer().setPlayerListHeaderFooter("", "");
    }
}
