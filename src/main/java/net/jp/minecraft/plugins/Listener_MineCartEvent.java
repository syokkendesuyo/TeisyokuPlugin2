package net.jp.minecraft.plugins;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_MineCartEvent implements Listener {
    @EventHandler
    public void onVehicleDestroyEvent (VehicleDestroyEvent event){
        Player player = (Player) event.getAttacker();
        Vehicle vehicle = event.getVehicle();
        if(player.getGameMode() != GameMode.SURVIVAL){
            player.sendMessage(Messages.getNormalPrefix() +"サバイバルモードの場合にのみマインカートをイベントリへ回収します");
            vehicle.remove();
        }
        else{
            switch (event.getVehicle().getType()) {
                case MINECART:
                    player.sendMessage(Messages.getSuccessPrefix() +"マインカートを回収しました");
                    player.getInventory().addItem(new ItemStack(Material.MINECART));
                    vehicle.remove();
                    break;
                case MINECART_CHEST:
                case MINECART_FURNACE:
                case MINECART_HOPPER:
                case MINECART_TNT:
                    player.sendMessage(Messages.getDenyPrefix() +"通常のマインカート以外は回収しない設定になっています");
                    break;
                default:
                    break;
            }
        }
    }

    @EventHandler
    public void onVehicleExitEvent(VehicleExitEvent event) {
        if(event.getExited() instanceof Player && event.getVehicle() instanceof RideableMinecart){
            Vehicle vehicle = event.getVehicle();
            Player player = (Player) event.getExited();
            if(player.getGameMode() != GameMode.SURVIVAL){
                player.sendMessage(Messages.getNormalPrefix() +" サバイバルモードの場合にのみマインカートをイベントリへ回収します");
                vehicle.remove();
            }
            else{
                player.sendMessage(Messages.getSuccessPrefix() +" マインカートを回収しました");
                player.getInventory().addItem(new ItemStack(Material.MINECART));
                vehicle.remove();
            }
        }
    }

    @EventHandler
    public void onVehicleDestroy(VehicleDestroyEvent event) {
        if (event.getVehicle() instanceof RideableMinecart) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerVehicleMoveEvent(VehicleMoveEvent event){
        if(event.getVehicle() instanceof Minecart){
            if(event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
            Vehicle minecart = event.getVehicle();
            Entity player = minecart.getPassenger();
            if(player instanceof Player){
                if(! (minecart.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.WALL_SIGN))){
                    return;
                }
                Sign sign = (Sign) minecart.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getState();
                if(sign.getLine(0).equalsIgnoreCase("[test]")){
                    Player sendPlayer = ((Player) player);
                    sendPlayer.sendMessage(sign.getLine(1).toString());
                }
            }
        }
    }
}