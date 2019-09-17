package net.jp.minecraft.plugins.GUI;

import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Objects;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class GUI_PlayersList {
    public static void getPlayersList(Player player) {

        //実行コマンドのパーミッションを確認
        if (!(player.hasPermission(Permission.USER.toString()) || player.hasPermission(Permission.PLAYERS.toString()) || player.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(player, Permission.PLAYERS);
            return;
        }

        // Make sure the inv has enough slots
        int size = Bukkit.getOnlinePlayers().size();
        int size2;

        if (size % 9 == 0) {
            //割り切れた時
            size2 = size / 9;
        } else {
            //割り切れなかった時
            size2 = (size / 9) + 1;
        }

        if (size2 > 6) {
            return;
        }
        Inventory inv = Bukkit.createInventory(player, size2 * 9, " プレイヤー一覧 ");
        // Add all the skulls
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!(player.isOp()) && p.getGameMode() == GameMode.SPECTATOR) {
                continue;
            }
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
            assert meta != null : "ディスプレイ名が見つかりません";
            meta.setDisplayName(p.getName());
            meta.setOwningPlayer(p);

            String world = Objects.requireNonNull(p.getLocation().getWorld()).getName();
            int x = (int) p.getLocation().getX();
            int y = (int) p.getLocation().getY();
            int z = (int) p.getLocation().getZ();

            meta.setLore(Arrays.asList(ChatColor.YELLOW + "現在地 ： " + ChatColor.WHITE + world + " " + x + " , " + y + " , " + z, ChatColor.GRAY + "クリックで現在地をチャットに送信", ChatColor.GRAY + "UUID:" + p.getUniqueId().toString()));
            skull.setItemMeta(meta);

            inv.addItem(skull);
        }
        player.openInventory(inv);
    }
}
