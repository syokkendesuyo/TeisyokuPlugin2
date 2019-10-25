package net.jp.minecraft.plugins.Utility;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Inventory {
    /**
     * CreateInventoryをちょっと使いやすくしたメソッド
     *
     * @param player  プレイヤー
     * @param invName インベントリ名
     * @param size    生成するインベントリの行数
     * @param items   設定するアイテム
     * @return インベントリ
     */
    public static org.bukkit.inventory.Inventory create(Player player, int size, String invName, ItemStack... items) {
        //Define inv
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(player, size * 9, invName);

        //Set items
        for (int cnt = 0; cnt < items.length; cnt++) {
            inv.setItem(cnt, items[cnt]);
        }
        return inv;
    }
}
