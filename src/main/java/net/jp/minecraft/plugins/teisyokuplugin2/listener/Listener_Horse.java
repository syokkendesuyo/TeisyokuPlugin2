package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo azuhata
 * TODO: コンフィグに接続する部分をAPIへ移動
 */
public class Listener_Horse implements Listener {

    /**
     * 馬をクリックしたときのイベント
     *
     * @param event イベント
     */
    @EventHandler
    public void HorseClick(PlayerInteractAtEntityEvent event) {

        //馬以外は無視する
        if (!((event.getRightClicked().getType().equals(EntityType.HORSE)) || (event.getRightClicked().getType().equals(EntityType.SKELETON_HORSE)) || (event.getRightClicked().getType().equals(EntityType.ZOMBIE_HORSE)))) {
            return;
        }
        Player player = event.getPlayer();
        UUID entityUUID = event.getRightClicked().getUniqueId();

        //棒以外は無視
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.STICK) {
            return;
        }
        String displayName = Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName();
        if (displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護ツール")) {
            net.jp.minecraft.plugins.teisyokuplugin2.function.Horse.HorseRegister(player, entityUUID);
            event.setCancelled(true);
            return;
        }
        if (displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護解除ツール")) {
            net.jp.minecraft.plugins.teisyokuplugin2.function.Horse.HorseRemove(player, entityUUID);
            event.setCancelled(true);
            return;
        }
        if (displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護情報確認ツール")) {
            if (net.jp.minecraft.plugins.teisyokuplugin2.function.Horse.isRegister(entityUUID)) {
                event.setCancelled(true);
                Msg.info(player, "この馬は保護されています");
                net.jp.minecraft.plugins.teisyokuplugin2.function.Horse.sendStatus(player, entityUUID);
            } else {
                Msg.info(player, "この馬は保護されていません");
            }
            event.setCancelled(true);
        }
    }

    /**
     * 馬に乗る時のイベント
     *
     * @param event イベント
     */
    @EventHandler
    public void HorseRide(VehicleEnterEvent event) {
        if ((event.getVehicle() instanceof Horse) || (event.getVehicle() instanceof SkeletonHorse) || (event.getVehicle() instanceof ZombieHorse)) {
            //馬に乗る際、プレイヤー以外だった場合は無視する
            //例:スケルトンホースが自然にスポーンする際にスケルトンが乗る場合、エラーを吐いてしまう
            if (!(event.getEntered() instanceof Player)) {
                return;
            }

            //プレイヤーと判定し、処理を継続させる
            Player player = (Player) event.getEntered();
            UUID entityUUID = event.getVehicle().getUniqueId();
            UUID playerUUID = player.getUniqueId();

            if (player.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
                String displayName = Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName();
                if (displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護ツール") ||
                        displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護解除ツール") ||
                        displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護情報確認ツール")) {
                    event.setCancelled(true);
                    return;
                }
            }

            //登録情報が存在するか確認
            if (!net.jp.minecraft.plugins.teisyokuplugin2.function.Horse.isRegister(entityUUID)) {
                Msg.info(player, "登録情報の無い馬です");
                return;
            }

            //ロック情報を照会
            if (net.jp.minecraft.plugins.teisyokuplugin2.function.Horse.checkLockStatus(playerUUID, entityUUID) ||
                    player.hasPermission(Permission.HORSE_BYPASS_RIDE.toString()) ||
                    player.hasPermission(Permission.HORSE_ADMIN.toString()) ||
                    player.hasPermission(Permission.ADMIN.toString())) {
                Msg.success(player, "ロックされた馬に乗りました");
                net.jp.minecraft.plugins.teisyokuplugin2.function.Horse.sendStatus(player, entityUUID);
                return;
            }

            //他者によるロック
            event.setCancelled(true);
            Msg.warning(player, "この馬はロックされています");
            net.jp.minecraft.plugins.teisyokuplugin2.function.Horse.sendStatus(player, entityUUID);
        }
    }

    /**
     * 馬が死んだときに登録を解除する
     *
     * @param event イベント
     */
    @EventHandler
    public void HorseDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Horse || event.getEntity() instanceof SkeletonHorse || event.getEntity() instanceof SkeletonHorse)) {
            return;
        }
        UUID uuid = event.getEntity().getUniqueId();
        net.jp.minecraft.plugins.teisyokuplugin2.function.Horse.HorseRemoveWildcard(uuid);
    }

    /* 解決策が出るまで保留
     * ゾンビ馬に変換する
     */
    /*@EventHandler
    public void ChangeHorse(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked().getType().equals(EntityType.SKELETON_HORSE))) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null) {
            return;
        }
        if (!(item.getType().equals(Material.PAPER))) {
            return;
        }
        if (item.getItemMeta().getDisplayName() == null) {
            return;
        }
        if ((item.getItemMeta().getDisplayName().equals(TeisyokuPlugin2.getInstance().SkeletonTicket)) || (item.getItemMeta().getDisplayName().equals(TeisyokuPlugin2.getInstance().ZombieTicket))) {
            event.setCancelled(true);
            UUID entityUUID = event.getRightClicked().getUniqueId();
            if (isEqual(player, player.getUniqueId(), entityUUID) == 0) {
                Msg.warning(player, "登録者以外の馬の変換はできません");
                getStatus(player, entityUUID);
                return;
            }

            Horse horse = (Horse) event.getRightClicked();
            if (item.getItemMeta().getDisplayName().equals(TeisyokuPlugin2.getInstance().ZombieTicket)) {
                if (!(horse.getVariant().equals(Variant.SKELETON_HORSE))) {
                    Msg.warning(player, "スケルトンホースにのみ有効です");
                    return;
                }
                if (item.getAmount() <= 1) {
                    player.getInventory().removeItem(item);
                    player.updateInventory();
                } else {
                    item.setAmount(item.getAmount() - 1);
                }
                horse.setVariant(Variant.UNDEAD_HORSE);
                Msg.success(player, "ゾンビホースに変換しました");
                Sounds.sound_zombie_villager(player);
            } else if (item.getItemMeta().getDisplayName().equals(TeisyokuPlugin2.getInstance().SkeletonTicket)) {
                if (!(horse.getVariant().equals(Variant.UNDEAD_HORSE))) {
                    Msg.warning(player, "ゾンビホースにのみ有効です");
                    return;
                }
                if (item.getAmount() <= 1) {
                    player.getInventory().removeItem(item);
                    player.updateInventory();
                } else {
                    item.setAmount(item.getAmount() - 1);
                }
                horse.setVariant(Variant.SKELETON_HORSE);
                Msg.success(player, "スケルトンホースに変換しました");
                Sounds.sound_zombie_villager(player);
            }
        }
    }*/

    /**
     * 馬に保護がされている場合にダメージを無効化するメソッド
     *
     * @param event イベント
     */
    @EventHandler
    public void HorseDamage(EntityDamageByEntityEvent event) {
        Entity damageEntity = event.getEntity();
        Entity damager = event.getDamager();

        //ダメージを与えたのがプレイヤー以外の場合処理を終了
        if (!(damager instanceof Player)) {
            return;
        }

        //ダメージャーをプレイヤーに指定
        Player player = (Player) damager;

        //エンティティーが馬以外の場合処理を終了
        if (!(damageEntity instanceof Horse)) {
            return;
        }

        //パーミッションを確認
        if (player.hasPermission(Permission.HORSE_BYPASS_DAMAGE.toString()) ||
                player.hasPermission(Permission.HORSE_ADMIN.toString()) ||
                player.hasPermission(Permission.ADMIN.toString())) {
            return;
        }

        //ツールではダメージが通らないように
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            try {
                String displayName = Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName();
                if (displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護ツール") ||
                        displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護解除ツール") ||
                        displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護情報確認ツール")) {
                    event.setCancelled(true);
                    return;
                }
            } catch (Exception e) {
                //謎のエラー回避
            }
        }

        //ダメージャーとエンティティーが一致した場合、処理を終了
        if (!net.jp.minecraft.plugins.teisyokuplugin2.function.Horse.checkLockStatus((Player) event.getDamager(), event.getEntity())) {
            return;
        }

        Msg.warning(player, "この馬は他のプレイヤーがロックしています");
        event.setCancelled(true);
    }
}
