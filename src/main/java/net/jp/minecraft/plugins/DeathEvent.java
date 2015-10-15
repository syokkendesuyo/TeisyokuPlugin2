package net.jp.minecraft.plugins;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class DeathEvent implements Listener {
    @EventHandler
    public void death(PlayerDeathEvent event){
        try{
            @SuppressWarnings("all")
            EntityDamageEvent.DamageCause dc = event.getEntity().getLastDamageCause().getCause();
        }
        catch(NullPointerException e){
            event.setDeathMessage(null);
            Bukkit.broadcastMessage(Messages.getSuccessPrefix() + event.getEntity().getName().toString() + " さんは自殺しました");
        }
    }
}
