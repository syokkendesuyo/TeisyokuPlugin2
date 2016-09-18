package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.DetectorRail;
import org.bukkit.material.PoweredRail;
import org.bukkit.material.Rails;

import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.TeisyokuPlugin2;

import java.io.File;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo azuhata
 */
public class Listener_MineCartEvent implements Listener {
    @EventHandler
    public void onVehicleDestroyEvent (VehicleDestroyEvent event){

        if(!(event.getAttacker() instanceof Player)){
            return;
        }

        Player player = (Player) event.getAttacker();
        Vehicle vehicle = event.getVehicle();
        if(player.getGameMode() != GameMode.SURVIVAL){
            player.sendMessage(Messages.getNormalPrefix() +"サバイバルモードの場合にのみマインカートをイベントリへ回収します");
            vehicle.remove();
        }
        else {
            switch (event.getVehicle().getType()) {
                case MINECART:
                    player.sendMessage(Messages.getSuccessPrefix() +"マインカートを回収しました");
                    ItemStack cart = new ItemStack(Material.MINECART);
                    ItemMeta cartmeta = cart.getItemMeta();
                    cartmeta.setDisplayName(vehicle.getCustomName());
                    cart.setItemMeta(cartmeta);
                    //MineCartの名前を保持する
                    player.getInventory().addItem(cart);
                    vehicle.remove();
                    event.setCancelled(true);
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
                String playerUniqueId = player.getUniqueId().toString();
                File userdata = new File(TeisyokuPlugin2.getInstance().getDataFolder(), File.separator + "PlayerDatabase");
                File f = new File(userdata, File.separator + playerUniqueId + ".yml");
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
                if(playerData.getBoolean("auto_cart_remove") == true){
                    Msg.success(player, "マインカートを削除しました");
                    vehicle.remove();
                    return;
                }
                
                player.sendMessage(Messages.getSuccessPrefix() +" マインカートを回収しました");
                
                ItemStack cart = new ItemStack(Material.MINECART);
                ItemMeta cartmeta = cart.getItemMeta();
                cartmeta.setDisplayName(vehicle.getCustomName());
                cart.setItemMeta(cartmeta);
                //MineCartの名前を保持する
                player.getInventory().addItem(cart);
                
                vehicle.remove();
            }
        }
    }

    /**
     * マインカートに乗っている場合にレール下に看板があると発動
     * @param event
     */
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
                if(sign.getLine(0).equalsIgnoreCase("[alert]")||sign.getLine(0).equalsIgnoreCase("[announce]")||sign.getLine(0).equalsIgnoreCase("[a]") || sign.getLine(0).equalsIgnoreCase("[ri]") || sign.getLine(0).equalsIgnoreCase("[railwayinfo]")){
                    Player sendplayer =((Player) player);
                    
                    
                    try{
                        if(sign.getLine(1).equalsIgnoreCase("")){
                            sendplayer.sendMessage(Messages.getDenyPrefix() + "看板2行目が空白になっています");
                            return;
                        }
                        
                        if(TeisyokuPlugin2.getInstance().CartConfig.get(sign.getLine(1).toString()) == null){
                        	sendplayer.sendMessage(Messages.getDenyPrefix() + "登録名 " + ChatColor.YELLOW + sign.getLine(1) + ChatColor.RESET + " は登録されていません");
                            return;
                        }
                        else{
                            //正常処理
                            String announce = TeisyokuPlugin2.getInstance().CartConfig.getString(sign.getLine(1).toString() + ".string");
                            String announceReplace = announce.replaceAll("&","§");
                            String annaunceReplace2 = announceReplace.replaceAll("%%"," ");
                            sendplayer.sendMessage(annaunceReplace2);
                            return;
                        }
                    }
                    catch (Exception e){
                        sendplayer.sendMessage(Messages.getDenyPrefix() + "不明なエラーが発生しました");
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }
    
    /**
     * Minecartの最高速度変化させるところ
     * トロッコの真下のブロックの種類によって速度が変化する
     * 
     * アクティベーターレールは最高速度を通常Minecartに戻すことができる
     */
    
    @SuppressWarnings("deprecation")
    @EventHandler
    public void changeMincartSpeed(VehicleMoveEvent event){
        if(!(event.getVehicle() instanceof Minecart)){
            return;
        }
        Minecart cart = (Minecart) event.getVehicle();
        if(!(cart.getPassenger() instanceof Player)){
            cart.setMaxSpeed(0.4);
            return;
        }
        
        if(cart.getLocation().getBlock().getType().equals(Material.ACTIVATOR_RAIL)){
            cart.setMaxSpeed(0.4);
            return;
        }
        
        Rails rail = null;
        PoweredRail p_rail = null;
        DetectorRail d_rail = null;
        Block block = null;
        
        if((cart.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.IRON_BLOCK))||(cart.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.OBSIDIAN))){//下が鉄ブロック、黒曜石であることを確認
            block = cart.getLocation().getBlock();
            if(((block.getType().equals(Material.POWERED_RAIL))||(block.getType().equals(Material.DETECTOR_RAIL))||(block.getType().equals(Material.RAILS)))){
                if(block.getType().equals(Material.RAILS)){
                    rail = (Rails) block.getState().getData();
                    if(rail.isCurve()){
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                    else if(rail.isOnSlope()){
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                }
                else if(block.getType().equals(Material.POWERED_RAIL)){
                    p_rail = (PoweredRail) block.getState().getData();
                    if(p_rail.isOnSlope()){
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                }
                else if(block.getType().equals(Material.DETECTOR_RAIL)){
                    d_rail = (DetectorRail) block.getState().getData();
                    if(d_rail.isOnSlope()){
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                }
            }
            if(cart.getMaxSpeed() == 1.6){
                return;
            }
            cart.setMaxSpeed(1.6);
            return;
        }
        else if(cart.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.SMOOTH_BRICK)){
            if(cart.getLocation().add(0, -1, 0).getBlock().getState().getRawData() != (byte)3){
                cart.setMaxSpeed(0.4);
                return;
            }
            block = cart.getLocation().getBlock();
            if(((block.getType().equals(Material.POWERED_RAIL))||(block.getType().equals(Material.DETECTOR_RAIL))||(block.getType().equals(Material.RAILS)))){
                if(cart.getLocation().getBlock().getType().equals(Material.RAILS)){
                    rail = (Rails) cart.getLocation().getBlock().getState().getData();
                    if(rail.isCurve()){
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                    else if(rail.isOnSlope()){
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                }
                else if(block.getType().equals(Material.POWERED_RAIL)){
                    p_rail = (PoweredRail) block.getState().getData();
                    if(p_rail.isOnSlope()){
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                }
                else if(block.getType().equals(Material.DETECTOR_RAIL)){
                    d_rail = (DetectorRail) block.getState().getData();
                    if(d_rail.isOnSlope()){
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                }
            }
            if(cart.getMaxSpeed() == 1.2){
                return;
            }
            cart.setMaxSpeed(1.2);
            return;
        }
        else {
            if(cart.getMaxSpeed() == 0.4){
                return;
            }
            cart.setMaxSpeed(0.4);
            return;
        }
    }
}