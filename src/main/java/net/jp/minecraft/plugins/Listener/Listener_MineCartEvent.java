package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.API.API_Flag;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Color;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Replace;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.DetectorRail;
import org.bukkit.material.PoweredRail;
import org.bukkit.material.Rails;

import java.util.List;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo azuhata
 */
public class Listener_MineCartEvent implements Listener {

    @EventHandler
    public void onVehicleDestroyEvent(VehicleDestroyEvent event) {

        if (!(event.getAttacker() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getAttacker();
        Vehicle vehicle = event.getVehicle();
        if (player.getGameMode() != GameMode.SURVIVAL) {
            Msg.warning(player, "サバイバルモードの場合にのみマインカートをイベントリへ回収します");
            vehicle.remove();
        } else {
            switch (event.getVehicle().getType()) {
                case MINECART:
                    Msg.success(player, "マインカートを回収しました");
                    ItemStack cart = new ItemStack(Material.MINECART);
                    ItemMeta cartmeta = cart.getItemMeta();
                    assert cartmeta != null;
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
                    Msg.warning(player, "通常のマインカート以外は回収しない設定になっています");
                    break;
                default:
                    break;
            }
        }
    }

    @EventHandler
    public void onVehicleExitEvent(VehicleExitEvent event) {
        if (event.getExited() instanceof Player && event.getVehicle() instanceof RideableMinecart) {
            Vehicle vehicle = event.getVehicle();
            Player player = (Player) event.getExited();
            if (player.getGameMode() != GameMode.SURVIVAL) {
                Msg.warning(player, "サバイバルモードの場合にのみマインカートをイベントリへ回収します");
                vehicle.remove();
            } else {
                if (API_Flag.get(player, "cart_auto_collect")) {
                    Msg.success(player, "マインカートを回収しました");
                    ItemStack cart = new ItemStack(Material.MINECART);
                    ItemMeta cartmeta = cart.getItemMeta();
                    assert cartmeta != null;
                    cartmeta.setDisplayName(vehicle.getCustomName());
                    cart.setItemMeta(cartmeta);
                    //MineCartの名前を保持する
                    player.getInventory().addItem(cart);
                    vehicle.remove();
                    return;
                }
                vehicle.remove();
            }
        }
    }

    /**
     * マインカートに乗っている場合にレール下に看板があると発動<br />
     */
    @EventHandler
    public void onPlayerVehicleMoveEvent(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
            Vehicle minecart = event.getVehicle();

            //.getPassenger()が非推奨化のため対応
            //マインカートに複数のエンティティーが乗る可能性があります
            List<Entity> entities = minecart.getPassengers();
            Player player = null;
            for (Entity entity : entities) {
                if (!(entity instanceof Player)) {
                    return;
                }
                //TODO:
                player = (Player) entity;

                Material material = minecart.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType();
                if (!(material.equals(Material.SIGN) || material.equals(Material.WALL_SIGN))) {
                    return;
                }
                Sign sign = (Sign) minecart.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getState();
                if (sign.getLine(0).equalsIgnoreCase("[alert]") || sign.getLine(0).equalsIgnoreCase("[announce]") || sign.getLine(0).equalsIgnoreCase("[a]") || sign.getLine(0).equalsIgnoreCase("[ri]") || sign.getLine(0).equalsIgnoreCase("[railwayinfo]")) {
                    try {
                        if (sign.getLine(1).equalsIgnoreCase("")) {
                            Msg.warning(player, "看板2行目が空白になっています");
                            return;
                        }

                        if (TeisyokuPlugin2.getInstance().CartConfig.get(sign.getLine(1)) == null) {
                            Msg.warning(player, "登録名 " + ChatColor.YELLOW + sign.getLine(1) + ChatColor.RESET + " は登録されていません");
                        } else {
                            //正常処理
                            String announce = TeisyokuPlugin2.getInstance().CartConfig.getString(sign.getLine(1) + ".string");
                            assert announce != null;
                            player.sendMessage(Color.convert(Replace.blank(announce)));
                        }
                    } catch (Exception e) {
                        Msg.warning(player, "不明なエラーが発生しました");
                        e.printStackTrace();
                    }
                }
            }


        }
    }

    /**
     * Minecartの最高速度変化させるところ<br />
     * トロッコの真下のブロックの種類によって速度が変化する<br />
     * アクティベーターレールは最高速度を通常Minecartに戻すことができる<br />
     */
    @SuppressWarnings("deprecation")
    @EventHandler
    public void changeMincartSpeed(VehicleMoveEvent event) {
        if (!(event.getVehicle() instanceof Minecart)) {
            return;
        }
        Minecart cart = (Minecart) event.getVehicle();
        if (!(cart.getPassenger() instanceof Player)) {
            cart.setMaxSpeed(0.4);
            return;
        }

        if (cart.getLocation().getBlock().getType().equals(Material.ACTIVATOR_RAIL)) {
            cart.setMaxSpeed(0.4);
            return;
        }

        Rails rail;
        PoweredRail p_rail;
        DetectorRail d_rail;
        Block block;

        if ((cart.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.IRON_BLOCK)) || (cart.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.OBSIDIAN))) {//下が鉄ブロック、黒曜石であることを確認
            block = cart.getLocation().getBlock();
            if (((block.getType().equals(Material.POWERED_RAIL)) || (block.getType().equals(Material.DETECTOR_RAIL)) || (block.getType().equals(Material.RAIL)))) {
                if (block.getType().equals(Material.RAIL)) {
                    rail = (Rails) block.getState().getData();
                    if (rail.isCurve()) {
                        cart.setMaxSpeed(0.4);
                        return;
                    } else if (rail.isOnSlope()) {
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                } else if (block.getType().equals(Material.POWERED_RAIL)) {
                    p_rail = (PoweredRail) block.getState().getData();
                    if (p_rail.isOnSlope()) {
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                } else if (block.getType().equals(Material.DETECTOR_RAIL)) {
                    d_rail = (DetectorRail) block.getState().getData();
                    if (d_rail.isOnSlope()) {
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                }
            }
            if (cart.getMaxSpeed() == 1.6) {
                return;
            }
            cart.setMaxSpeed(1.6);
        } else if (cart.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.CHISELED_STONE_BRICKS)) {
            if (cart.getLocation().add(0, -1, 0).getBlock().getState().getRawData() != (byte) 3) {
                cart.setMaxSpeed(0.4);
                return;
            }
            block = cart.getLocation().getBlock();
            if (((block.getType().equals(Material.POWERED_RAIL)) || (block.getType().equals(Material.DETECTOR_RAIL)) || (block.getType().equals(Material.RAIL)))) {
                if (cart.getLocation().getBlock().getType().equals(Material.RAIL)) {
                    rail = (Rails) cart.getLocation().getBlock().getState().getData();
                    if (rail.isCurve()) {
                        cart.setMaxSpeed(0.4);
                        return;
                    } else if (rail.isOnSlope()) {
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                } else if (block.getType().equals(Material.POWERED_RAIL)) {
                    p_rail = (PoweredRail) block.getState().getData();
                    if (p_rail.isOnSlope()) {
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                } else if (block.getType().equals(Material.DETECTOR_RAIL)) {
                    d_rail = (DetectorRail) block.getState().getData();
                    if (d_rail.isOnSlope()) {
                        cart.setMaxSpeed(0.4);
                        return;
                    }
                }
            }
            if (cart.getMaxSpeed() == 1.2) {
                return;
            }
            cart.setMaxSpeed(1.2);
        } else {
            if (cart.getMaxSpeed() == 0.4) {
                return;
            }
            cart.setMaxSpeed(0.4);
        }
    }
}