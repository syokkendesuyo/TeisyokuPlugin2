package net.jp.minecraft.plugins;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_WitherSpawmCancel implements Listener {
    @EventHandler
    public void onCreatureSpawn(EntitySpawnEvent event){
        if (event.getEntityType() == EntityType.WITHER){
            List<String> worlds = TeisyokuPlugin2.getInstance().TeisyokuConfig.getStringList("arrow_summon_wither");
            for(String s : worlds){
                if (!(event.getLocation().getWorld().getName().toString().equals(s))){
                    event.setCancelled(true);
                }
            }
        }
    }
}
