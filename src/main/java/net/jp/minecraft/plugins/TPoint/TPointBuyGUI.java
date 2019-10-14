package net.jp.minecraft.plugins.TPoint;

import net.jp.minecraft.plugins.GUI.GUI_YesNo;
import net.jp.minecraft.plugins.Listener.Listener_TPoint;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Color;
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
import java.util.Objects;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class TPointBuyGUI implements Listener {

    /**
     * インスタンスへアクセスする変数
     */
    private TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

    private static String inventoryName = " TPoint - お買い物 ";
    private static int invSize = 3;
    private static HashMap<UUID, Integer> Teisyoku_TPointGUI = new HashMap<UUID, Integer>();

    static void index(Player player) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //Create Inventory
        Inventory inv = Bukkit.createInventory(player, invSize * 9, inventoryName);

        //Get Tpoint
        int point = Listener_TPoint.int_status(player);

        //TPoint Status
        String[] lore_status = {};
        ItemStack item_status = Item.customItem(ChatColor.AQUA + "" + ChatColor.BOLD + point + " TPoint", 1, Material.COD, lore_status);

        inv.setItem(0, item_status);
        int cnt = 9;
        for (; cnt < invSize * 9 - 1; cnt++) {
            if (plugin.configTPoint.getConfig().get("goods." + cnt + ".name") == null) {
                /*
                 ##  Debug  ##
                String ItemName = plugin.configTPoint.getConfig().getString("goods." + cnt + ".name");
                String test = plugin.configTPoint.getConfig().getString("test");
                Msg.warning(player,"Continue:" + cnt + " & " + ItemName+ " " + test);
                 */
                continue;
            }
            try {
                String ItemName = Color.convert(Objects.requireNonNull(plugin.configTPoint.getConfig().getString("goods." + cnt + ".name")));
                String lore0 = Color.convert(Objects.requireNonNull(plugin.configTPoint.getConfig().getString("goods." + cnt + ".lore1")));
                String lore1 = Color.convert(Objects.requireNonNull(plugin.configTPoint.getConfig().getString("goods." + cnt + ".lore2")));
                String lore2 = Color.convert(Objects.requireNonNull(plugin.configTPoint.getConfig().getString("goods." + cnt + ".lore3")));

                //TPoint Buy
                String[] lore_buy_for = {lore0, lore1, lore2};
                String material = plugin.configTPoint.getConfig().getString("goods." + cnt + ".material");
                assert material != null;
                ItemStack item_buy_for = Item.customItem(ItemName, 1, Material.getMaterial(material), lore_buy_for);
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

            if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.AIR)) {
                return;
            }

            String ItemName = ChatColor.stripColor(Color.convert(Objects.requireNonNull(plugin.configTPoint.getConfig().getString("goods." + event.getRawSlot() + ".name"))));
            GUI_YesNo.openGUI(player, "購入", "キャンセル", ItemName);
            Teisyoku_TPointGUI.put(player.getUniqueId(), event.getRawSlot());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void buy(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!Teisyoku_TPointGUI.containsKey(player.getUniqueId())) {
            return;
        }
        int goods_number = Teisyoku_TPointGUI.get(player.getUniqueId());
        if (event.getInventory().getName().equalsIgnoreCase(ChatColor.stripColor(Color.convert(Objects.requireNonNull(plugin.configTPoint.getConfig().getString("goods." + goods_number + ".name")))))) {
            event.setCancelled(true);
            if (event.getRawSlot() == 2) {
                List<String> messages = plugin.configTPoint.getConfig().getStringList("goods." + goods_number + ".messages");

                //購入をキャンセルするかどうか
                int point = plugin.configTPoint.getConfig().getInt("goods." + goods_number + ".point");
                if (!Listener_TPoint.canBuy(point, player)) {
                    Msg.warning(player, "購入がキャンセルされました");
                    return;
                }
                Listener_TPoint.subtractPoint(point, player, Bukkit.getConsoleSender());

                for (String m : messages) {
                    Msg.info(player, Color.convert(m.replaceAll("%player%", player.getName())));
                }

                List<String> commands = plugin.configTPoint.getConfig().getStringList("goods." + goods_number + ".commands");
                for (String s : commands) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Color.convert(s.replaceAll("%player%", player.getName())));
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
}
