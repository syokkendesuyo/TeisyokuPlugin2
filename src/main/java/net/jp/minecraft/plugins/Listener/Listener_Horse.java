package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo azuhata
 */
public class Listener_Horse implements Listener {
    @EventHandler
    public void HorseClick(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType().equals(EntityType.HORSE)) {
            Player player = event.getPlayer();
            UUID entityUUID = event.getRightClicked().getUniqueId();

            //棒以外は無視
            if (!(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK)) {
                return;
            }
            if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護ツール")) {
                HorseRegister(player, entityUUID);
                event.setCancelled(true);
                return;
            }
            if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護解除ツール")) {
                HorseRemove(player, entityUUID);
                event.setCancelled(true);
                return;
            }
            if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護情報確認ツール")) {
                if (isRegister(entityUUID)) {
                    event.setCancelled(true);
                    Msg.info(player, "この馬は保護されています");
                    getStatus(player, entityUUID);
                } else {
                    Msg.info(player, "この馬は保護されていません");
                }
                event.setCancelled(true);
            }
        }
    }

    /**
     * 馬に乗る時のイベント
     *
     * @param event
     */
    @EventHandler
    public static void HorseRide(VehicleEnterEvent event) {
        if (event.getVehicle() instanceof Horse) {
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
                if ((player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() != null) || (!(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("")))) {
                    if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護ツール") || player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護解除ツール") || player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護情報確認ツール")) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }

            int temp = isEqual(player, playerUUID, entityUUID);

            if (temp == 3) {
                Msg.success(player, "ロックされた馬に乗りました");
                getStatus(player, entityUUID);
                return;
            } else if (temp == 4) {
                Msg.info(player, "この馬はロックされていません");
                return;
            } else if (temp == 1 || player.isOp()) {
                Msg.success(player, "ロックされた馬に乗りました");
                getStatus(player, entityUUID);
                return;
            } else if (temp == 2) {
                Msg.info(player, "登録情報の無い馬です");
                return;
            }

            //tempが0の場合不一致なのでキャンセル処理
            event.setCancelled(true);
            Msg.warning(player, "この馬はロックされています");
            getStatus(player, entityUUID);
        }
    }

    /*
    馬をロックする
     */
    private static void HorseRegister(Player player, UUID uuid) {
        if (isRegister(uuid)) {
            Msg.warning(player, "この馬は既にロックされた馬です");
            getStatus(player, uuid);
            return;
        }
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
            String strDate = sdf.format(date.getTime());

            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString(), player.toString());
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".data", strDate);
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".player", player.getName());
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".uuid", player.getUniqueId().toString());
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".mode", "private");

            TeisyokuPlugin2.getInstance().saveHorseConfig();
            Msg.success(player, "馬をロックしました");

        } catch (Exception e) {
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    /*
    馬のロックを解除する
     */
    private static void HorseRemove(Player player, UUID entityUUID) {
        if (isEqual(player, player.getUniqueId(), entityUUID) == 2) {
            Msg.warning(player, "この馬は保護されていません");
            return;
        } else if (isEqual(player, player.getUniqueId(), entityUUID) == 0) {
            Msg.warning(player, "登録者以外馬のロックは解除できません");
            getStatus(player, entityUUID);
            return;
        }

        try {
            TeisyokuPlugin2.getInstance().HorseConfig.set(entityUUID.toString(), null);
            TeisyokuPlugin2.getInstance().saveHorseConfig();
            Msg.success(player, "馬のロックを解除しました");
        } catch (Exception e) {
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    /*
     * ゾンビ馬に変換する
     */
    @EventHandler
    public void ChangeHorse(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked().getType().equals(EntityType.HORSE))) {
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
    }

    /**
     * エンティティデータを比較するメソッド<br />
     * 0:不一致<br />
     * 1:一致<br />
     * 2:比較対象が無い<br />
     * 3:OP(登録あり)<br />
     * 4:OP(登録なし)<br />
     *
     * @param player
     * @param playerUUID
     * @param entityUUID
     * @return
     */
    static int isEqual(Player player, UUID playerUUID, UUID entityUUID) {
        //OPであり、馬の登録がある場合
        if (player.isOp() && isRegister(entityUUID)) {
            return 3;
        }
        //OPだが、馬の登録が無い場合
        if (player.isOp() && !isRegister(entityUUID)) {
            return 4;
        }
        //一般プレイヤーで、UUIDが一致した場合
        if (playerUUID.toString().equals(TeisyokuPlugin2.getInstance().HorseConfig.get(entityUUID + ".uuid"))) {
            return 1;
        } else {
            //そもそも登録が無い場合
            if (TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".uuid") == null) {
                return 2;
            }
            //不一致
            return 0;
        }
    }

    static void getStatus(Player player, UUID entityUUID) {
        Msg.info(player, "登録者名 : " + TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".player"));
        Msg.info(player, "登録日 : " + TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".data"));
    }

    private static boolean isRegister(UUID entityUUID) {
        if (TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".uuid") == null) {
            return false;
        } else {
            return true;
        }
    }
}
