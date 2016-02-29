package net.jp.minecraft.plugins.TPoint;

import net.jp.minecraft.plugins.Listener_TPoint;
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
public class TPointIndexGUI implements Listener {

    public static String inventoryName = " TPoint Menu ";
    public static int invSize = 3;

    public static void index(Player player){
        //Create Inventory
        Inventory inv = Bukkit.createInventory(player, invSize*9, inventoryName);

        //Get Tpoint
        int point = Listener_TPoint.int_status(player);

        //TPoint Status
        String lore_status[] = {};
        ItemStack item_status = TeisyokuItem.custom_item(ChatColor.AQUA + "" + ChatColor.BOLD + point + " TPoint", 1, Material.COOKED_FISH, (short) 0, lore_status);

        //TPoint Buy
        String lore_buy[] = {"TPointを使ってお買い物をします"};
        ItemStack item_buy = TeisyokuItem.custom_item(ChatColor.BOLD + "購入", 1, Material.BOOK_AND_QUILL, (short) 0, lore_buy);

        inv.setItem(0, item_status);
        inv.setItem(12, item_buy);

        //Open this GUI
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getName().equalsIgnoreCase(inventoryName)) {
            //Cancel click event
            event.setCancelled(true);

            //Call Functions
            if (event.getRawSlot() == 12) {
                TPointBuyGUI.index(player);
                return;
            }
        }
    }
}
