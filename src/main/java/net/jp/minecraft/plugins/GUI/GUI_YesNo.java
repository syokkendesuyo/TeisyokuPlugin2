package net.jp.minecraft.plugins.GUI;

import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.TeisyokuItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class GUI_YesNo implements Listener {

    /**
     * <p>はい/いいえを選択するGUIを表示します</p>
     *
     * @param player        プレイヤー
     * @param yes           yesの時の説明文
     * @param no            noの時の説明文
     * @param inventoryName インベントリ名
     * @return
     */
    public static String openGUI(Player player, String yes, String no, String inventoryName) {
        Inventory inv = Bukkit.createInventory(player, 9, inventoryName);

        String lore_yes[] = {ChatColor.WHITE + yes};
        ItemStack item_yes = TeisyokuItem.custom_item(ChatColor.GOLD + "はい", 1, Material.WOOL, (short) 5, lore_yes);

        String lore_no[] = {ChatColor.WHITE + no};
        ItemStack item_no = TeisyokuItem.custom_item(ChatColor.GOLD + "いいえ", 1, Material.WOOL, (short) 14, lore_no);

        inv.setItem(2, item_yes);
        inv.setItem(6, item_no);
        player.openInventory(inv);

        return inventoryName;
    }

    @EventHandler
    public static void getKill(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getName().equalsIgnoreCase("自殺しますか？")) {
            if (event.getRawSlot() == 2) {
                //実行
                player.setHealth(0);
                player.closeInventory();
                Msg.success(player, "自殺しました");
            } else if (event.getRawSlot() == 6) {
                //拒否
                Msg.success(player, "自殺をやっぱり辞めました！");
                player.closeInventory();
            }
        }
    }
}
