package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 * <p>
 * flyの実行時の処理
 */
public class Listener_Flymode implements Listener{

    //flyを有効化
    public static boolean enable_fly(Player player) {
        Msg.success(player, ChatColor.YELLOW + player.getName() + ChatColor.RESET + " のFlyモードを" + ChatColor.GREEN + " 有効 " + ChatColor.RESET + "にしました");
        player.setAllowFlight(true);
        return true;
    }

    //flyを無効化
    public static boolean disable_fly(Player player) {
        Msg.success(player, ChatColor.YELLOW + player.getName() + ChatColor.RESET + " のFlyモードを" + ChatColor.RED + " 無効 " + ChatColor.RESET + "にしました");
        player.setAllowFlight(false);
        return true;
    }

    @EventHandler
    public void onPlayerChangedWorldEvent (PlayerChangedWorldEvent event) {
        if ( event.getPlayer().hasPermission(Permissions.getTeisyokuAdminPermisson())) {
            return;
        }
        event.getPlayer().setAllowFlight(false);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerFlatWorldEnterEvent (PlayerChangedWorldEvent event)  {
        if (event.getPlayer().getWorld().getName().equalsIgnoreCase("flat")) {
            enable_fly(event.getPlayer());
            return;
        }
        event.getPlayer().setAllowFlight(false);
    }
}
