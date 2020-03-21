package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.config.CustomConfig;
import net.jp.minecraft.plugins.teisyokuplugin2.module.TFlag;
import net.jp.minecraft.plugins.teisyokuplugin2.util.BlockUtil;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Color;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import net.jp.minecraft.plugins.teisyokuplugin2.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Rail;
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

import java.util.List;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo azuhata
 * TODO: 重複部分をメソッド化
 */
public class Listener_Minecart implements Listener {

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
                if (TFlag.getTFlagStatus(player, TFlag.CART_AUTO_COLLECT.getTFlag())) {
                    Msg.success(player, "マインカートを回収しました");
                    ItemStack cart = new ItemStack(Material.MINECART);
                    ItemMeta cartmeta = cart.getItemMeta();
                    assert cartmeta != null;
                    cartmeta.setDisplayName(vehicle.getCustomName());
                    cart.setItemMeta(cartmeta);
                    //Minecartの名前を保持する
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

        //Railways.ymlを取得
        CustomConfig config = TeisyokuPlugin2.getInstance().configRailways;

        //処理をマインカートによるものに限定
        if (event.getVehicle() instanceof Minecart) {
            if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
            Vehicle minecart = event.getVehicle();

            //.getPassenger()が非推奨化のため対応
            //マインカートに複数のエンティティーが乗る可能性があります
            List<Entity> entities = minecart.getPassengers();
            Player player;
            for (Entity entity : entities) {
                if (!(entity instanceof Player)) {
                    return;
                }
                player = (Player) entity;

                Material material = minecart.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType();
                if (!BlockUtil.isSign(material)) {
                    return;
                }
                Sign sign = (Sign) minecart.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getState();
                //TODO: [ri]などをconfigから変更できるようにする
                if (sign.getLine(0).equalsIgnoreCase("[ri]") || sign.getLine(0).equalsIgnoreCase("[railwaysinfo]")) {
                    try {
                        if (sign.getLine(1).equalsIgnoreCase("")) {
                            Msg.warning(player, "看板2行目が空白になっています");
                            return;
                        }

                        if (config.getConfig().get(sign.getLine(1)) == null) {
                            Msg.warning(player, "登録名 " + ChatColor.YELLOW + sign.getLine(1) + ChatColor.RESET + " は登録されていません");
                        } else {
                            //正常処理
                            String announce = config.getConfig().getString(sign.getLine(1) + ".string");
                            assert announce != null;
                            player.sendMessage(Color.convert(StringUtil.replaceToBlank(announce)));
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
    @EventHandler
    public void changeMinecartSpeed(VehicleMoveEvent event) {
        if (!(event.getVehicle() instanceof Minecart)) {
            return;
        }
        Minecart cart = (Minecart) event.getVehicle();

        List<Entity> entities = cart.getPassengers();
        for (Entity entity : entities) {
            if (!(entity instanceof Player)) {
                cart.setMaxSpeed(0.4);
            }
        }

        Block space;
        space = cart.getLocation().getBlock();

        if (space.getType().equals(Material.ACTIVATOR_RAIL)) {
            cart.setMaxSpeed(0.4);
            return;
        }

        if(!(space.getType().equals(Material.RAIL) || space.getType().equals(Material.POWERED_RAIL) || space.getType().equals(Material.DETECTOR_RAIL))){
            cart.setMaxSpeed(0.4);
            return;
        }

        Rail rail;
        rail = (Rail) space.getBlockData();

        if(!(BlockUtil.isRailStraight(rail))){
            cart.setMaxSpeed(0.4);
            return;
        }

        Block block;
        block = cart.getLocation().add(0, -1, 0).getBlock();

        if ((block.getType().equals(Material.IRON_BLOCK)) || (block.getType().equals(Material.OBSIDIAN))) {//下が鉄ブロック、黒曜石であることを確認
            if (cart.getMaxSpeed() == 1.6) {
                return;
            }
            cart.setMaxSpeed(1.6);
        } else if (block.getType().equals(Material.CHISELED_STONE_BRICKS)) {
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