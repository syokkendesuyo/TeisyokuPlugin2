package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_WitherSpawmCancel implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCreatureSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.WITHER) {
            List<String> worlds = TeisyokuPlugin2.getInstance().TeisyokuConfig.getStringList("arrow_summon_wither");
            for (String s : worlds) {
                // ## Debug ##
                // Bukkit.broadcastMessage("Debug: " + s + " " + event.getLocation().getWorld().getName().toString());
                if (event.getLocation().getWorld().getName().equals(s)) {
                    event.setCancelled(false);
                    return;
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
