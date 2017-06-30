package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_SignEdit implements Listener {

    private static HashMap<UUID, String> editData = new HashMap<>();
    private static HashMap<UUID, Integer> lineData = new HashMap<>();

    public static boolean saveData(Player player, String editString, int line) {
        editData.put(player.getUniqueId(), editString);
        lineData.put(player.getUniqueId(), line - 1);
        return true;
    }

    public static void updateSign(Location loc, Player player) {
        if (!Permissions.hasPermission(player, "teisyoku.sign")) {
            Msg.noPermissionMessage(player, "teisyoku.sign");
            return;
        }

        if (!(Listener_TPoint.canBuy(5, player))) {
            Msg.warning(player, "看板を更新するには5TPoint必要です");
            editData.remove(player.getUniqueId());
            lineData.remove(player.getUniqueId());
            return;
        }
        Listener_TPoint.subtractPoint(5, player, player);
        World w = loc.getWorld();
        Block a = w.getBlockAt(loc);
        if (a.getType() == Material.SIGN_POST || a.getType() == Material.WALL_SIGN) {
            Sign sign = (Sign) a.getState();
            sign.setLine(lineData.get(player.getUniqueId()), blank(color(editData.get(player.getUniqueId()))));
            sign.update(true);
        }
        Msg.success(player, "看板を更新しました。 " + blank(color(editData.get(player.getUniqueId()))) + ChatColor.GRAY + " @ " + ChatColor.RESET + lineData.get(player.getUniqueId()) + 1);
        editData.remove(player.getUniqueId());
        lineData.remove(player.getUniqueId());
    }

    public static boolean hasData(Player player) {
        return lineData.containsKey(player.getUniqueId());
    }

    public static String color(String str) {
        return str.replace("&", "§");
    }
    public static String blank(String str) {
        return str.replace("%%", " ");
    }

    @EventHandler
    public void onClickEvent(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!(Listener_SignEdit.hasData(player))) {
            return;
        }
        if (block.getType().equals(Material.SIGN_POST) || block.getType().equals(Material.WALL_SIGN)) {
            Location loc = block.getLocation();
            updateSign(loc, event.getPlayer());
            return;
        }
        Msg.warning(player, "更新できませんでした。");
        Msg.warning(player, "再度コマンドを実行し看板に向かって右クリックしてください。");
        editData.remove(player.getUniqueId());
        lineData.remove(player.getUniqueId());
    }
}
