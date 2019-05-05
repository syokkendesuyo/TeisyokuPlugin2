package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

import static java.lang.Math.pow;

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
                // Bukkit.broadcastMessage("Debug: " + s + " " + event.getLocation().getWorld().getName());
                if (event.getLocation().getWorld().getName().equals(s)) {
                    event.setCancelled(false);
                    return;
                } else {
                    event.setCancelled(true);
                }
            }
            Player player = getNearByPlayer(event.getLocation());
            Msg.warning(player, "このワールドではウィザーを召喚することはできません");
        }
    }

    public Player getNearByPlayer(Location loc) {
        double size = 100;
        Player target = null;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers() ) {
            if (onlinePlayer.getWorld() != loc.getWorld()) {
                continue;
            }
            Location targetLoc = onlinePlayer.getLocation();

            double x1 = loc.getX();
            double y1 = loc.getY();
            double z1 = loc.getZ();

            double x2 = targetLoc.getX();
            double y2 = targetLoc.getY();
            double z2 = targetLoc.getZ();

            double length = pow( (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) + (z2-z1)*(z2-z1), 0.5 );

            if (length < size) {
                target = onlinePlayer;
                size = length;
            }
        }
        return target;
    }
}
