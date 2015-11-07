package net.jp.minecraft.plugins;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_WitherSpawmCancel implements Listener {
    @EventHandler
    public void onCreatureSpawn(EntitySpawnEvent event){
        if (event.getEntityType() == EntityType.WITHER){
            if(!(event.getLocation().getWorld().getName().equals("world_the_end"))){
                event.setCancelled(true);
            }
        }
    }
}
