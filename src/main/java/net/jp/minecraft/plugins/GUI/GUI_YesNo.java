package net.jp.minecraft.plugins.GUI;

import net.jp.minecraft.plugins.Utility.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class GUI_YesNo {

    /**
     * <p>はい/いいえを選択するGUIを表示します</p>
     *
     * @param player        プレイヤー
     * @param yes           yesの時の説明文
     * @param no            noの時の説明文
     * @param inventoryName インベントリ名
     * @return GUIを開く
     */
    public static String openGUI(Player player, String yes, String no, String inventoryName) {
        Inventory inv = Bukkit.createInventory(player, 9, inventoryName);

        String[] lore_yes = {ChatColor.WHITE + yes};
        ItemStack item_yes = Item.customItem(ChatColor.GOLD + "はい", 1, Material.LIME_WOOL, lore_yes);

        String[] lore_no = {ChatColor.WHITE + no};
        ItemStack item_no = Item.customItem(ChatColor.GOLD + "いいえ", 1, Material.RED_WOOL, lore_no);

        inv.setItem(2, item_yes);
        inv.setItem(6, item_no);

        player.openInventory(inv);

        return inventoryName;
    }
}
