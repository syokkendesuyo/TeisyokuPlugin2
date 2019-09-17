package net.jp.minecraft.plugins.TPoint;

import net.jp.minecraft.plugins.GUI.GUI_YesNo;
import net.jp.minecraft.plugins.Listener.Listener_TPoint;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class TPointBuyGUI implements Listener {

    private static String inventoryName = " TPoint - お買い物 ";
    private static int invSize = 3;
    private static HashMap<UUID, Integer> Teisyoku_TPointGUI = new HashMap<UUID, Integer>();

    static void index(Player player) {
        //Create Inventory
        Inventory inv = Bukkit.createInventory(player, invSize * 9, inventoryName);

        //Get Tpoint
        int point = Listener_TPoint.int_status(player);

        //TPoint Status
        String[] lore_status = {};
        ItemStack item_status = Item.customItem(ChatColor.AQUA + "" + ChatColor.BOLD + point + " TPoint", 1, Material.COD, (short) 0, lore_status);

        inv.setItem(0, item_status);
        int cnt = 9;
        for (; cnt < invSize * 9 - 1; cnt++) {
            if (TeisyokuPlugin2.getInstance().TPointSettingsConfig.get("goods." + cnt + ".name") == null) {
                /*
                 ##  Debug  ##
                String ItemName = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getString("goods." + cnt + ".name");
                String test = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getString("test");
                Msg.warning(player,"Continue:" + cnt + " & " + ItemName+ " " + test);
                 */
                continue;
            }
            try {
                String ItemName = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getString("goods." + cnt + ".name");
                String lore0 = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getString("goods." + cnt + ".lore1");
                String lore1 = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getString("goods." + cnt + ".lore2");
                String lore2 = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getString("goods." + cnt + ".lore3");

                //TPoint Buy
                String[] lore_buy_for = {lore0, lore1, lore2};
                String material = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getString("goods." + cnt + ".material");
                int meta = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getInt("goods." + cnt + ".meta");
                ItemStack item_buy_for = Item.customItem(ItemName, 1, Material.getMaterial(material), (short) meta, lore_buy_for);
                inv.setItem(cnt, item_buy_for);
            } catch (Exception e) {
                Msg.warning(player, "不明なエラーが発生しました");
                e.printStackTrace();
            }
        }

        //Open this GUI
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getName().equalsIgnoreCase(inventoryName)) {
            //Cancel click event
            event.setCancelled(true);

            if (!(event.getRawSlot() >= 9 && event.getRawSlot() <= 28)) {
                return;
            }

            if (event.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }

            String ItemName = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getString("goods." + event.getRawSlot() + ".name");
            GUI_YesNo.openGUI(player, "購入", "キャンセル", ItemName);
            Teisyoku_TPointGUI.put(player.getUniqueId(), event.getRawSlot());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void buy(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!Teisyoku_TPointGUI.containsKey(player.getUniqueId())) {
            return;
        }
        int goods_number = Teisyoku_TPointGUI.get(player.getUniqueId());
        if (event.getInventory().getName().equalsIgnoreCase(TeisyokuPlugin2.getInstance().TPointSettingsConfig.getString("goods." + goods_number + ".name"))) {
            event.setCancelled(true);
            if (event.getRawSlot() == 2) {
                List<String> messages = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getStringList("goods." + goods_number + ".messages");
                for (String m : messages) {
                    String mm = color(m);
                    String mmm = mm.replaceAll("%player%", player.getName());
                    Msg.info(player, mmm);
                }

                //購入をキャンセルするかどうか
                int point = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getInt("goods." + goods_number + ".point");
                if (!Listener_TPoint.canBuy(point, player)) {
                    Msg.warning(player, "購入がキャンセルされました");
                    return;
                }

                List<String> commands = TeisyokuPlugin2.getInstance().TPointSettingsConfig.getStringList("goods." + goods_number + ".commands");
                for (String s : commands) {
                    String ss = color(s);
                    String sss = ss.replaceAll("%player%", player.getName());
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), sss);
                }
                player.closeInventory();
                Teisyoku_TPointGUI.remove(player.getUniqueId());
            } else if (event.getRawSlot() == 6) {
                Msg.warning(player, "購入をキャンセルしました");
                player.closeInventory();
                Teisyoku_TPointGUI.remove(player.getUniqueId());
            }
        }
    }

    private static String color(String str) {
        return str.replaceAll("&", "§");
    }
}
