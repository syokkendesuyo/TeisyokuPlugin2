package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_DeathEvent implements Listener {
    @EventHandler
    public void death(PlayerDeathEvent event) {
        try {
            EntityDamageEvent.DamageCause dc = event.getEntity().getLastDamageCause().getCause();
        } catch (NullPointerException e) {
            event.setDeathMessage(null);
            Msg.warning(Bukkit.getConsoleSender(), ChatColor.YELLOW + event.getEntity().getName() + ChatColor.RESET + " さんは自殺しました", true);
        }
    }
}
