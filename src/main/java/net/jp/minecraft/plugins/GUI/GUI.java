package net.jp.minecraft.plugins.GUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class GUI {
    /**
     * CreateInventoryをちょっと使いやすくしたメソッド
     * @param player
     * @param inv_name
     * @param size
     * @param items
     * @return
     */
    public static Inventory create(Player player, int size, String inv_name, ItemStack... items){
        //Define inv
        Inventory inv = Bukkit.createInventory(player, size * 9, inv_name);

        //Set items
        for (int cnt = 0; cnt < items.length; cnt++){
            inv.setItem(cnt, items[cnt]);
        }
        return inv;
    }
}
