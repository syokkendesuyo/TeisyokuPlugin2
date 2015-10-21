package net.jp.minecraft.plugins;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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
                if (sign.getLine(0).equalsIgnoreCase("[GOMI]") && player.hasPermission("teisyoku.use" + sign.getLine(1))){
                    Listener_Gomibako.openGomibako(player);
                }
                else if (sign.getLine(0).equalsIgnoreCase("[Teisyoku]") && player.hasPermission("teisyoku.use" + sign.getLine(1))){
                    TeisyokuMenuIndex.getMenu(player);
                }
            }
        }
    }
}
