package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * @author syokkendesuyo
 */
public class Listener_NetherGateEvent implements Listener {
    @EventHandler
    public void onNetherGateEnterEvent(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            event.setCancelled(true);
            Msg.warning(player, "当サーバではネザーポータルをご利用頂けません");
        }
    }
}
