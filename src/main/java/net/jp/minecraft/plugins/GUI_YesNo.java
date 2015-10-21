package net.jp.minecraft.plugins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class GUI_YesNo implements Listener {
    public static String openGUI(Player player , String yes , String no , String inventoryName){
        Inventory inv = Bukkit.createInventory(player, 9, inventoryName);
        ItemStack item0 = new ItemStack(Material.WOOL, 1, (short) 5);
        ItemMeta itemmeta0 = item0.getItemMeta();
        itemmeta0.setDisplayName(ChatColor.GOLD  + "はい");
        itemmeta0.setLore(Arrays.asList(ChatColor.WHITE + yes.toString()));
        item0.setItemMeta(itemmeta0);

        ItemStack item1 = new ItemStack(Material.WOOL, 1, (short) 14);
        ItemMeta itemmeta1 = item1.getItemMeta();
        itemmeta1.setDisplayName(ChatColor.GOLD  + "いいえ");
        itemmeta1.setLore(Arrays.asList(ChatColor.WHITE + no.toString() ));
        item1.setItemMeta(itemmeta1);

        inv.setItem(2, item0);
        inv.setItem(6, item1);
        player.openInventory(inv);

        return inventoryName;
    }

    @EventHandler
    public static void getKill(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getName().equalsIgnoreCase("自殺を行いますか？")){
            if(event.getRawSlot() == 2){
                //実行
                player.setHealth(0);
                player.closeInventory();
                player.sendMessage(Messages.getSuccessPrefix() + "自殺しました");
                return;
            }
            else if(event.getRawSlot() == 6){
                //拒否
                player.sendMessage(Messages.getDenyPrefix() + "自殺をやっぱり辞めました！");
                player.closeInventory();
                return;
            }
        }
        return;
    }
}
