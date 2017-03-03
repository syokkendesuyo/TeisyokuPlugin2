package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_SkeletonHorse implements Listener {
    @EventHandler
    public void spawnEvent (EntitySpawnEvent event) {

        if (event.getEntityType().equals(EntityType.SKELETON_HORSE)) {
            event.setCancelled(true);
        }
    }
}
