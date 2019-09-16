package net.jp.minecraft.plugins.Utility;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Item {
    /**
     * <p>アイテムのカスタマイズを行う関数<br/>
     * GUIに置くアイテムのカスタマイズに重宝します<br/>
     * 注意：String loreの配列が3つ以上になるとloreにエラーが表示されます</p>
     *
     * @param displayName 表示名(必須 String)
     * @param stack       スタック数(必須 int)
     * @param material    素材名(必須 Material)
     * @param meta        メタ番号(必須 Short)
     * @param lore        詳細(任意 String ※配列)
     * @return アイテム
     */
    public static ItemStack customItem(String displayName, Integer stack, Material material, Short meta, String... lore) {
        return customItem(displayName, stack, material, meta, false, lore);
    }

    /**
     * <p>アイテムのカスタマイズを行う関数<br/>
     * GUIに置くアイテムのカスタマイズに重宝します<br/>
     * 注意：String loreの配列が3つ以上になるとloreにエラーが表示されます<br />
     * このメソッドでenchantmentをtrueにするると、実質ほぼ無意味な耐久Ⅰを交付したアイテムを取得できます</p>
     *
     * @param displayName 表示名(必須 String)
     * @param stack       スタック数(必須 int)
     * @param material    素材名(必須 Material)
     * @param meta        メタ番号(必須 Short)
     * @param enchantment エンチャント(必須 Boolean)
     * @param lore        詳細(任意 String ※配列)
     * @return アイテム
     */
    public static ItemStack customItem(String displayName, Integer stack, Material material, Short meta, Boolean enchantment, String... lore) {
        ItemStack item = new ItemStack(material, stack, meta);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(displayName);
        if (enchantment) {
            itemmeta.addEnchant(Enchantment.DURABILITY, 1, true);
        }
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
        itemmeta.setLore(Arrays.asList("Error: Item.java", "loreは3つまでしか登録できません。配列を確認してください。"));
        item.setItemMeta(itemmeta);
        return item;
    }
}
