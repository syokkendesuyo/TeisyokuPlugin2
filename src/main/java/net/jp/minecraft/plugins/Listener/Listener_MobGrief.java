package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.API.API;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;
import java.util.Objects;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_MobGrief implements Listener {
    @EventHandler
    public void creeperGrief(EntityExplodeEvent e) {
        if (!(e.getEntityType().equals(EntityType.CREEPER))) {
            return;
        }
        if (!(Objects.requireNonNull(e.getLocation().getWorld()).getName().equals("world"))) {
            return;
        }
        e.blockList().clear();
    }

    @EventHandler
    public void endermanGrief(EntityChangeBlockEvent e) {
        if (!(e.getEntityType().equals(EntityType.ENDERMAN))) {
            return;
        }
        if (!(Objects.requireNonNull(e.getBlock().getLocation().getWorld()).getName().equals("world"))) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void witherGrief(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.WITHER) {
            List<String> worlds = TeisyokuPlugin2.getInstance().configTeisyoku.getConfig().getStringList("arrow_summon_wither");
            for (String s : worlds) {
                if (Objects.requireNonNull(event.getLocation().getWorld()).getName().equals(s)) {
                    event.setCancelled(false);
                    return;
                } else {
                    event.setCancelled(true);
                }
            }
            Player player = API.getNearByPlayer(event.getLocation());
            Msg.warning(player, "このワールドではウィザーを召喚することはできません");
        }
    }
}
