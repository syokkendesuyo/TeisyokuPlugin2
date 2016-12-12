package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TPoint.TPointIndexGUI;
import net.jp.minecraft.plugins.TeisyokuMenuIndex;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Search;
import net.jp.minecraft.plugins.Utility.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
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
        if (block.getType().equals(Material.SIGN_POST) || block.getType().equals(Material.WALL_SIGN)) {
            Sign sign = (Sign) block.getState();

            if (Search.searchKeyword(sign.getLines(), "[gomi]")) {
                Listener_Gomibako.openGomibako(player);
            } else if (Search.searchKeyword(sign.getLines(), "[cart]")) {
                Msg.success(player, "マインカートをインベントリに追加しました");
                ItemStack cart = new ItemStack(Material.MINECART);
                player.getInventory().addItem(cart);
            } else if (Search.searchKeyword(sign.getLines(), "[teisyoku]")) {
                TeisyokuMenuIndex.getMenu(player);
            } else if (Search.searchKeyword(sign.getLines(), "[tpoint]") || Search.searchKeyword(sign.getLines(), "[point]")) {
                TPointIndexGUI.index(player);
            } else if (sign.getLine(0).toLowerCase().indexOf("[warp]") != -1) {
                if (sign.getLine(1) != null) {
                    Bukkit.getServer().dispatchCommand(player, "warp " + sign.getLine(1));
                } else {
                    Bukkit.getServer().dispatchCommand(player, "warp");
                }
            } else if ((Search.searchKeyword(sign.getLines(), "[coffee]")) && (player.getInventory().getItemInMainHand().getType().equals(Material.POTION))) {
                ItemStack item = player.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Coffee");
                meta.setLore(Arrays.asList(ChatColor.DARK_AQUA + "TeisyokuCoffee 定価:55円"));
                item.setItemMeta(meta);
                Sounds.sound_note(player);
            } else {
                if (Listener_SignEdit.hasData(player)) {
                    return;
                }
                Msg.success(player, ChatColor.BOLD + "" + ChatColor.GRAY + " 看板データ参照 ");
                for (int cnt = 0; cnt < 4; cnt++) {
                    if (!(sign.getLine(cnt).length() == 0)) {
                        Msg.info(player, sign.getLine(cnt));
                    }
                }
            }
        }
    }
}
