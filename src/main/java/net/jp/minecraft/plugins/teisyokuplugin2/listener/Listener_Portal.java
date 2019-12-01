package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.module.TFlag;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
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
public class Listener_Portal implements Listener {
    @EventHandler
    public void onNetherGateEnterEvent(PlayerPortalEvent event) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        Player player = event.getPlayer();

        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {

            //バイパス権限を保有していた場合は機能をバイパス
            if (player.hasPermission(Permission.PORTAL_BYPASS_END.toString())) {
                return;
            }

            //ネザーポータルが有効化されているかどうか検出
            if (!plugin.configTeisyoku.getConfig().getBoolean("portal.end")) {
                event.setCancelled(true);
                if (TFlag.getTFlagStatus(player, TFlag.PORTAL_WARNING.getTFlag())) {
                    Msg.warning(player, "このサーバーではエンドポータルはご利用頂けません");
                    Msg.noPermissionMessage(player, Permission.PORTAL_BYPASS_END);
                }
            }
        }

        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {

            //バイパス権限を保有していた場合は機能をバイパス
            if (player.hasPermission(Permission.PORTAL_BYPASS_NETHER.toString())) {
                return;
            }

            //ネザーポータルが有効化されているかどうか検出
            if (!plugin.configTeisyoku.getConfig().getBoolean("portal.nether")) {
                event.setCancelled(true);
                if (TFlag.getTFlagStatus(player, TFlag.PORTAL_WARNING.getTFlag())) {
                    Msg.warning(player, "このサーバーではエンドポータルはご利用頂けません");
                    Msg.noPermissionMessage(player, Permission.PORTAL_BYPASS_NETHER);
                }
            }
        }
    }
}
