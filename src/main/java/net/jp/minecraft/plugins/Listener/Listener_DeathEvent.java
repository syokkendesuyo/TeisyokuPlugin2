package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_DeathEvent implements Listener {
    @EventHandler
    public void death(PlayerDeathEvent event) {
        try {
            EntityDamageEvent.DamageCause dc = Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause();
        } catch (NullPointerException e) {
            event.setDeathMessage(null);
            Msg.warning(Bukkit.getConsoleSender(), ChatColor.YELLOW + event.getEntity().getName() + ChatColor.RESET + " さんは自殺しました", true);
        }
    }
}
