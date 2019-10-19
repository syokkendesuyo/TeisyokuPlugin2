package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Enum.Permission;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Color;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Replace;
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
 * @author syokkendesuyo
 * TODO: APIへ移行
 */
public class Listener_SignEdit implements Listener {

    private static HashMap<UUID, String> editData = new HashMap<>();
    private static HashMap<UUID, Integer> lineData = new HashMap<>();

    public static void saveData(Player player, String editString, int line) {
        editData.put(player.getUniqueId(), editString);
        lineData.put(player.getUniqueId(), line - 1);
    }

    public static void updateSign(Location loc, Player player) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //実行コマンドのパーミッションを確認
        if (!(player.hasPermission(Permission.USER.toString()) || player.hasPermission(Permission.SIGNEDIT.toString()) || player.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(player, Permission.SIGNEDIT);
            return;
        }

        //TPointを必要とする場合、購入処理を行う
        int cost = plugin.configTeisyoku.getConfig().getInt("signedit.cost");
        if (0 < cost) {
            if (!(Listener_TPoint.canBuy(cost, player))) {
                Msg.warning(player, "看板を更新するには" + cost + "TPoint必要です");
                editData.remove(player.getUniqueId());
                lineData.remove(player.getUniqueId());
                return;
            }
            Listener_TPoint.subtractPoint(cost, player, player);
        }

        World w = loc.getWorld();
        assert w != null;
        Block a = w.getBlockAt(loc);
        if (a.getType() == Material.SIGN || a.getType() == Material.WALL_SIGN) {
            Sign sign = (Sign) a.getState();
            sign.setLine(lineData.get(player.getUniqueId()), Replace.blank(Color.convert(editData.get(player.getUniqueId()))));
            sign.update(true);
        }
        Msg.success(player, "看板を更新しました。 " + Replace.blank(Color.convert(editData.get(player.getUniqueId()))) + ChatColor.GRAY + " @ " + ChatColor.RESET + (lineData.get(player.getUniqueId()) + 1));
        editData.remove(player.getUniqueId());
        lineData.remove(player.getUniqueId());
    }

    public static boolean hasData(Player player) {
        return lineData.containsKey(player.getUniqueId());
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
        assert block != null;
        if (block.getType().equals(Material.SIGN) || block.getType().equals(Material.WALL_SIGN)) {
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
