package net.jp.minecraft.plugins;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
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
                Bukkit.broadcastMessage("Data: " + s + " " + event.getLocation().getWorld().getName().toString());
                if (!(event.getLocation().getWorld().getName().toString().equals(s))){
                    event.setCancelled(true);
                }
            }
        }
    }
}
