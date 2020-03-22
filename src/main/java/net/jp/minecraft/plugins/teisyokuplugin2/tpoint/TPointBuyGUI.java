package net.jp.minecraft.plugins.teisyokuplugin2.tpoint;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.gui.GUI_YesNo;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_TPoint;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Color;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Item;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
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
 * TODO: リファクタリング
 */
public class TPointBuyGUI implements Listener {

    private static String inventoryName = " TPoint - お買い物 ";
    private static int invSize = 3;
    private static HashMap<UUID, Integer> Teisyoku_TPointGUI = new HashMap<UUID, Integer>();

    /**
     * インスタンスへアクセスする変数
     */
    private TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

    static void index(Player player) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //Create Inventory
        Inventory inv = Bukkit.createInventory(player, invSize * 9, inventoryName);

        //Get Tpoint
        int point = Listener_TPoint.int_status(player);

        ItemStack[] items = new ItemStack[invSize * 9];

        //TPoint Status
        String[] loreStatus = {};
        items[0] = Item.customItem(ChatColor.AQUA + "" + ChatColor.BOLD + point + " TPoint", 1, Material.COD, loreStatus);
        items[1] = Item.customItem(ChatColor.RED + "" + ChatColor.BOLD + "戻る", 1, Material.OAK_SIGN, loreStatus);

        int cnt = 3;
        for (; cnt < invSize * 9; cnt++) {
            if (plugin.configTPoint.getConfig().get("goods." + cnt + ".name") == null) {
                continue;
            }
            try {
                //表示素材
                String material = plugin.configTPoint.getConfig().getString("goods." + cnt + ".material");

                //表示素材のnullチェック
                if (material == null) {
                    throw new NullPointerException("TPoint.ymlに登録されたクッズ番号" + cnt + "の素材名(material)が見つかりません");
                }

                //素材が存在するか確認
                if (Material.getMaterial(material) == null) {
                    throw new NullPointerException("TPoint.ymlに登録されたクッズ番号" + cnt + "の素材名「" + material + "」は無効なアイテム名です");
                }

                //アイテム名
                String itemName = plugin.configTPoint.getConfig().getString("goods." + cnt + ".name");

                //表示惣菜のnullチェック
                if (itemName == null) {
                    throw new NullPointerException("TPoint.ymlに登録されたクッズ番号" + cnt + "のアイテム名(name)が見つかりません");
                }

                //説明文
                String[] lore = new String[3];
                lore[0] = Color.convert(Objects.requireNonNull(plugin.configTPoint.getConfig().getString("goods." + cnt + ".lore1")));
                lore[1] = Color.convert(Objects.requireNonNull(plugin.configTPoint.getConfig().getString("goods." + cnt + ".lore2")));
                lore[2] = Color.convert(Objects.requireNonNull(plugin.configTPoint.getConfig().getString("goods." + cnt + ".lore3")));

                ItemStack item = Item.customItem(Color.convert(itemName), 1, Material.getMaterial(material), lore);

                items[cnt] = item;
            } catch (Exception e) {
                Msg.warning(player, "不明なエラーが発生しました");
                Bukkit.getLogger().warning("TPoint.ymlに登録されたクッズ番号" + cnt + "でエラーが発生しました");
                e.printStackTrace();
            }
        }
        player.openInventory(net.jp.minecraft.plugins.teisyokuplugin2.util.Inventory.create(player, invSize, inventoryName, items));
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equalsIgnoreCase(inventoryName)) {
            //Cancel click event
            event.setCancelled(true);

            if (!(event.getRawSlot() >= 0 && event.getRawSlot() <= invSize * 9)) {
                return;
            }

            if (event.getCurrentItem() == null) {
                return;
            }

            if (event.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }

            //スロット0は予約済みなので週力
            if (event.getRawSlot() == 0) {
                return;
            }

            //スロット1は戻るボタンなのでGUIを呼び出す
            if (event.getRawSlot() == 1) {
                TPointIndexGUI.index(player);
                return;
            }

            int point = plugin.configTPoint.getConfig().getInt("goods." + event.getRawSlot() + ".point");
            if (!Listener_TPoint.canBuy(point, player)) {
                Msg.warning(player, "TPointが不足しています");
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
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.stripColor(Color.convert(Objects.requireNonNull(plugin.configTPoint.getConfig().getString("goods." + goods_number + ".name")))))) {
            event.setCancelled(true);
            if (event.getRawSlot() == 2) {
                List<String> messages = plugin.configTPoint.getConfig().getStringList("goods." + goods_number + ".messages");

                //購入をキャンセルするかどうか
                int point = plugin.configTPoint.getConfig().getInt("goods." + goods_number + ".point");
                if (!Listener_TPoint.canBuy(point, player)) {
                    Msg.warning(player, "TPointが不足しています");
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
