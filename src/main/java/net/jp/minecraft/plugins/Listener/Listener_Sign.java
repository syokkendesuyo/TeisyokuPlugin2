package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.API.API_Flag;
import net.jp.minecraft.plugins.API.API_Trash;
import net.jp.minecraft.plugins.Enum.Flag;
import net.jp.minecraft.plugins.TPoint.TPointIndexGUI;
import net.jp.minecraft.plugins.TeisyokuMenuIndex;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Search;
import net.jp.minecraft.plugins.Utility.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_Sign implements Listener {
    //看板右クリックでゴミ箱を表示
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        assert block != null;
        if (block.getType().equals(Material.SIGN) || block.getType().equals(Material.WALL_SIGN)) {
            Sign sign = (Sign) block.getState();

            if (Search.searchKeyword(sign.getLines(), "[trash]") || Search.searchKeyword(sign.getLines(), "[gomi]") || Search.searchKeyword(sign.getLines(), "[gomibako]")) {
                API_Trash.open(player);
            } else if (Search.searchKeyword(sign.getLines(), "[cart]")) {
                addMinecart(player);
                if (!API_Flag.get(player, Flag.TFlag.SIGN_INFO_CART.getTFlag())) {
                    return;
                }
            } else if (Search.searchKeyword(sign.getLines(), "[teisyoku]")) {
                TeisyokuMenuIndex.getMenu(player);
                return;
            } else if (Search.searchKeyword(sign.getLines(), "[tpoint]") || Search.searchKeyword(sign.getLines(), "[point]")) {
                TPointIndexGUI.index(player);
                return;
            } else if (sign.getLine(0).toLowerCase().contains("[warp]")) {
                sign.getLine(1);
                Bukkit.getServer().dispatchCommand(player, "warp " + sign.getLine(1));
                return;
            } else if ((Search.searchKeyword(sign.getLines(), "[coffee]")) && (player.getInventory().getItemInMainHand().getType().equals(Material.POTION))) {
                ItemStack item = player.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                assert meta != null;
                meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Coffee");
                meta.setLore(Collections.singletonList(ChatColor.DARK_AQUA + "TeisyokuCoffee 定価:55円"));
                item.setItemMeta(meta);
                Sounds.play(player, Sound.BLOCK_NOTE_BLOCK_CHIME);
                return;
            }
            //看板のデータを照会する
            getSignInfo(player, sign);
        }
    }


    /**
     * 看板のデータを照会する関数
     *
     * @param player ターゲット
     * @param sign   看板
     */
    private void getSignInfo(Player player, Sign sign) {

        //看板の文字更新フラグが立っていれば照会を行わない
        if (Listener_SignEdit.hasData(player)) {
            return;
        }

        //看板のデータ照会を行わない個人設定ならば処理終了
        if (!API_Flag.get(player, Flag.TFlag.SIGN_INFO.getTFlag())) {
            return;
        }

        //実行
        Msg.success(player, ChatColor.BOLD + "" + ChatColor.GRAY + " 看板データ参照 ");
        for (int cnt = 0; cnt < 4; cnt++) {
            if (!(sign.getLine(cnt).length() == 0)) {
                Msg.info(player, sign.getLine(cnt));
            }
        }
    }


    /**
     * マインカートをプレイヤーに贈与する関数
     *
     * @param player ターゲット
     */
    private void addMinecart(Player player) {

        //カート看板が個人設定で無効化されていれば処理終了
        if (!API_Flag.get(player, Flag.TFlag.SIGN_CART.getTFlag())) {
            return;
        }

        //実行
        Msg.success(player, "マインカートをインベントリに追加しました");
        ItemStack cart = new ItemStack(Material.MINECART);
        player.getInventory().addItem(cart);
    }
}
