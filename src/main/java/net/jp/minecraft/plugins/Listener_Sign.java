package net.jp.minecraft.plugins;

import org.bukkit.Bukkit;
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
                }
                else if (sign.getLine(0).equalsIgnoreCase("[Teisyoku]") || sign.getLine(1).equalsIgnoreCase("[Teisyoku]")){
                    TeisyokuMenuIndex.getMenu(player);
                }
                else if (sign.getLine(0).equalsIgnoreCase("[Cart]") || sign.getLine(1).equalsIgnoreCase("[Cart]") || sign.getLine(2).equalsIgnoreCase("[Cart]") || sign.getLine(3).equalsIgnoreCase("[Cart]")) {
                    player.sendMessage(Messages.getSuccessPrefix() +"マインカートをインベントリに追加しました");
                    player.getInventory().addItem(new ItemStack(Material.MINECART));
                }
                else if(sign.getLine(0).equalsIgnoreCase("[Warp]")){
                    if(sign.getLine(1) != null){
                        Bukkit.getServer().dispatchCommand(player, "warp " + sign.getLine(1));
                    }
                    else{
                        Bukkit.getServer().dispatchCommand(player, "warp");
                    }

                }
            }
        }
    }
}
