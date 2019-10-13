package net.jp.minecraft.plugins.TPoint;

import net.jp.minecraft.plugins.GUI.GUI;
import net.jp.minecraft.plugins.Listener.Listener_TPoint;
import net.jp.minecraft.plugins.Utility.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class TPointIndexGUI implements Listener {

    private static String inventoryName = " TPoint Menu ";

    public static void index(Player player) {
        //Get Tpoint
        int point = Listener_TPoint.int_status(player);

        //TPoint Status
        String[] lore_status = {};
        ItemStack item_status = Item.customItem(ChatColor.AQUA + "" + ChatColor.BOLD + point + " TPoint", 1, Material.COD, lore_status);

        //TPoint Buy
        String[] lore_buy = {"TPointを使ってお買い物をします"};
        ItemStack item_buy = Item.customItem(ChatColor.BOLD + "購入", 1, Material.WRITABLE_BOOK, lore_buy);

        Inventory inv = GUI.create(player, 3, inventoryName, item_status, item_buy);

        //Open this GUI
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getName().equalsIgnoreCase(inventoryName)) {
            //Cancel click event
            event.setCancelled(true);

            if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                return;
            }

            if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.AIR)) {
                return;
            }

            //Call Functions
            String str = Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName();
            str = ChatColor.stripColor(str);
            if (str.equals("購入")) {
                TPointBuyGUI.index(player);
            }
        }
    }
}
