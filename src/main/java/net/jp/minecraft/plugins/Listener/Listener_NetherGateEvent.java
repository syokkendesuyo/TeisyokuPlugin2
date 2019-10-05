package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_NetherGateEvent implements Listener {
    @EventHandler
    public void onNetherGateEnterEvent(PlayerPortalEvent event) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        Player player = event.getPlayer();

        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {

            //バイパス権限を保有していた場合は機能をバイパス
            if (player.hasPermission(Permission.PORTAL_BYPASS_NETHER.toString())) {
                return;
            }

            //ネザーポータルが有効化されているかどうか検出
            if (!plugin.configTeisyoku.getConfig().getBoolean("functions.portal.nether")) {
                event.setCancelled(true);
                //TODO: メッセージを非表示にできるフラグを追加
                Msg.warning(player, "当サーバではネザーポータルをご利用頂けません");
                Msg.noPermissionMessage(player, Permission.PORTAL_BYPASS_NETHER);
            }
        }
    }
}
