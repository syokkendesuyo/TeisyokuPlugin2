package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import net.jp.minecraft.plugins.teisyokuplugin2.function.Fly;
import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * TeisyokuPlugin2
 * flyの実行時の処理
 *
 * @author syokkendesuyo
 */
public class Listener_Fly implements Listener {

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
        if (event.getPlayer().hasPermission(Permission.ADMIN.toString())) {
            return;
        }
        event.getPlayer().setAllowFlight(false);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerFlatWorldEnterEvent(PlayerChangedWorldEvent event) {
        // TODO: configで設定できるように変更
        if (event.getPlayer().getWorld().getName().equalsIgnoreCase("flat")) {
            Fly.setFlying(event.getPlayer(), true);
            return;
        }
        event.getPlayer().setAllowFlight(false);
    }
}
