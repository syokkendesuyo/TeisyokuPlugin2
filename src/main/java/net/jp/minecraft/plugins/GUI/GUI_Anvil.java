package net.jp.minecraft.plugins.GUI;

import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class GUI_Anvil implements Listener {

    private static String invName = " ** Sign Editor ** ";
    public int line = 1;

    @EventHandler
    public void openSignEditor(PlayerInteractEvent event) {

        //現在開発中につき無効化
        boolean flag = false;
        if (flag == false) {
            return;
        }

        //右クリック以外は無視
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        Player player = event.getPlayer();
        //スニーキングしていない場合は無視
        if (player.isSneaking() == false) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block.getType().equals(Material.SIGN) || block.getType().equals(Material.WALL_SIGN)) {
            Inventory inventory = Bukkit.createInventory(player, InventoryType.ANVIL, invName);

            String lore0[] = {ChatColor.GOLD + "編集ライン番号：" + line};
            ItemStack item0 = Item.customItem("文字列を入力", 1, Material.NAME_TAG, (short) 0, lore0);

            inventory.setItem(0, item0);
            event.getPlayer().openInventory(inventory);
            return;
        }
        return;
    }


    public static Inventory createEditorUI() {
        Inventory anvil = Bukkit.createInventory(null, InventoryType.ANVIL, invName);
        return anvil;
    }

    public static void openEditorUI(Player player, Inventory inv) {
        player.openInventory(inv);
        return;
    }

    @EventHandler
    public void editorClickEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Msg.info(player, event.getCurrentItem().toString());
        //インベントリが同一のものか確認する
        if (event.getInventory().getName().equals(invName)) {

            //アイテムを強奪されないようにキャンセル
            event.setCancelled(true);

            //アイテムがnullなら無視
            //if(event.getCurrentItem() == null){
            //    return;
            //}

            //列番号切り替え
            if (event.getRawSlot() == 0) {
                if (line < 4) {
                    line++;
                } else {
                    line = 1;
                }
            }

            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                String str = event.getWhoClicked().getInventory().getItem(2).toString();
                Msg.info(player, "" + str);
                player.closeInventory();
            }

            Inventory inventory = event.getInventory();

            String lore0[] = {ChatColor.GOLD + "編集ライン番号 : " + line};
            ItemStack item0 = Item.customItem("文字列を入力", 1, Material.NAME_TAG, (short) 0, lore0);


            inventory.setItem(0, item0);
            player.updateInventory();

            //アイテムを強奪されないようにキャンセル
            //保障で最後にも
            event.setCancelled(true);
            return;
        }
        return;
    }
}
