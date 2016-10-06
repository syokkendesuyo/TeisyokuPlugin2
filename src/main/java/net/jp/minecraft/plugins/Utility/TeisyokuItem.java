package net.jp.minecraft.plugins.Utility;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class TeisyokuItem {
    /**
     * <p>アイテムのカスタマイズを行う関数<br/>
     * GUIに置くアイテムのカスタマイズに重宝します<br/>
     * 注意：String loreの配列が3つ以上になるとloreにエラーが表示されます</p>
     *
     * @param display_name 表示名(必須 String)
     * @param stack        スタック数(必須 int)
     * @param material     素材名(必須 Material)
     * @param meta         メタ番号(必須 Short)
     * @param lore         詳細(任意 String ※配列)
     * @return アイテム
     */
    public static ItemStack custom_item(String display_name, Integer stack, Material material, Short meta, String... lore) {
        org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(material, stack, meta);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(display_name);
        if (lore.length == 0) {
            item.setItemMeta(itemmeta);
            return item;
        }
        if (lore.length == 1) {
            itemmeta.setLore(Arrays.asList(ChatColor.WHITE + lore[0]));
            item.setItemMeta(itemmeta);
            return item;
        }
        if (lore.length == 2) {
            itemmeta.setLore(Arrays.asList(ChatColor.WHITE + lore[0], ChatColor.WHITE + lore[1]));
            item.setItemMeta(itemmeta);
            return item;
        }
        if (lore.length == 3) {
            itemmeta.setLore(Arrays.asList(ChatColor.WHITE + lore[0], ChatColor.WHITE + lore[1], ChatColor.WHITE + lore[2]));
            item.setItemMeta(itemmeta);
            return item;
        }
        itemmeta.setLore(Arrays.asList("Error:TeisyokuItem.java", "loreは3つまでしか登録できません。配列を確認してください。"));
        item.setItemMeta(itemmeta);
        return item;
    }
}
