package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.API.API_FlyMode;
import net.jp.minecraft.plugins.Utility.Permission;
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
public class Listener_Flymode implements Listener {

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
            API_FlyMode.enableFly(event.getPlayer());
            return;
        }
        event.getPlayer().setAllowFlight(false);
    }
}
