package net.jp.minecraft.plugins;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * @author syokkendesuyo
 */
public class NetherGateEvent implements Listener {
    @EventHandler
    public void onNetherGateEnterEvent(PlayerPortalEvent event){
        Player player = event.getPlayer();
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            event.setCancelled(true);
            player.sendMessage(Messages.getDenyPrefix() + "当サーバではポータルを使って移動できません");
        }
    }
}
