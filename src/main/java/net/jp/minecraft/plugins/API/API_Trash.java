package net.jp.minecraft.plugins.API;

import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class API_Trash implements Listener {
    public static void open(CommandSender sender) {
        if (!(sender instanceof Player)) {
            Msg.warning(sender, "ゴミ箱コマンドはゲーム内からのみ実行できます");
        }

        assert sender instanceof Player;
        Player player = (Player) sender;

        if (!(player.hasPermission(Permission.TRASH.toString()))) {
            Msg.noPermissionMessage(sender, Permission.TRASH);
            return;
        }

        ItemStack item0 = new ItemStack(Material.BOOK);
        ItemMeta itemmeta0 = item0.getItemMeta();
        assert itemmeta0 != null;
        itemmeta0.setDisplayName(ChatColor.GOLD + "ゴミ箱の使い方");
        itemmeta0.setLore(Arrays.asList(ChatColor.WHITE + "ゴミ箱に不要なアイテムを収納し、", ChatColor.WHITE + "画面を閉じると処分が完了します。"));
        item0.setItemMeta(itemmeta0);

        ItemStack item1 = new ItemStack(Material.RED_BED);
        ItemMeta itemmeta1 = item1.getItemMeta();
        assert itemmeta1 != null;
        itemmeta1.setDisplayName(ChatColor.GOLD + "画面を閉じる");
        itemmeta1.setLore(Collections.singletonList(ChatColor.GRAY + "定食サーバーオリジナルプラグイン"));
        item1.setItemMeta(itemmeta1);

        Inventory inv = Bukkit.createInventory(player, 36, " ゴミ箱 ");
        inv.setItem(0, item0);
        inv.setItem(1, item1);
        player.openInventory(inv);
    }
}
