package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TPoint.TPointIndexGUI;
import net.jp.minecraft.plugins.TeisyokuMenuIndex;
import net.jp.minecraft.plugins.Utility.Msg;
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

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_Sign implements Listener {
    //看板右クリックでゴミ箱を表示
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        else{
            if(block.getType().equals(Material.SIGN_POST) || block.getType().equals(Material.WALL_SIGN)){
                Sign sign = (Sign) block.getState();
                if (sign.getLine(0).equalsIgnoreCase("[GOMI]")){
                    Listener_Gomibako.openGomibako(player);
                    return;
                }
                else if (sign.getLine(0).equalsIgnoreCase("[Teisyoku]") || sign.getLine(1).equalsIgnoreCase("[Teisyoku]")){
                    TeisyokuMenuIndex.getMenu(player);
                    return;
                }
                else if (sign.getLine(0).equalsIgnoreCase("[Cart]") || sign.getLine(1).equalsIgnoreCase("[Cart]") || sign.getLine(2).equalsIgnoreCase("[Cart]") || sign.getLine(3).equalsIgnoreCase("[Cart]")) {
                    Msg.success(player, "マインカートをインベントリに追加しました");
                    ItemStack cart = new ItemStack(Material.MINECART);
                    player.getInventory().addItem(cart);
                    return;
                }
                else if (sign.getLine(0).toString().indexOf("[Cart]") != -1 || sign.getLine(0).toString().indexOf("[cart]") != -1){
                    Msg.success(player, "マインカートをインベントリに追加しました");
                    ItemStack cart = new ItemStack(Material.MINECART);
                    player.getInventory().addItem(cart);
                    return;
                }
                else if(sign.getLine(0).equalsIgnoreCase("[Warp]")){
                    if(sign.getLine(1) != null){
                        Bukkit.getServer().dispatchCommand(player, "warp " + sign.getLine(1));
                        return;
                    }
                    else{
                        Bukkit.getServer().dispatchCommand(player, "warp");
                        return;
                    }
                }
                else if(sign.getLine(0).equalsIgnoreCase("[tpoint]") || sign.getLine(0).equalsIgnoreCase("[point]")){
                    TPointIndexGUI.index(player);
                    return;
                }
                else{
                    Msg.success(player, ChatColor.BOLD + "" + ChatColor.GRAY + " 看板データ参照 ");
                    for(int cnt=0;cnt<4;cnt++){
                        if(! (sign.getLine(cnt).length() == 0)){
                            Msg.info(player,sign.getLine(cnt).toString());
                        }
                    }
                    return;
                }
            }
        }
    }
}
