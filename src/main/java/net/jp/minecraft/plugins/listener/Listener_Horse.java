package net.jp.minecraft.plugins.listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.util.Msg;
import net.jp.minecraft.plugins.enumeration.Permission;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
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
     * インスタンスへアクセスする変数
     */
    private TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

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
        if (!(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK)) {
            return;
        }
        String displayName = Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName();
        if (displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護ツール")) {
            HorseRegister(player, entityUUID);
            event.setCancelled(true);
            return;
        }
        if (displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護解除ツール")) {
            HorseRemove(player, entityUUID);
            event.setCancelled(true);
            return;
        }
        if (displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護情報確認ツール")) {
            if (isRegister(entityUUID)) {
                event.setCancelled(true);
                Msg.info(player, "この馬は保護されています");
                sendStatus(player, entityUUID);
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
            if (!isRegister(entityUUID)) {
                Msg.info(player, "登録情報の無い馬です");
                return;
            }

            //ロック情報を照会
            if (checkLockStatus(playerUUID, entityUUID) ||
                    player.hasPermission(Permission.HORSE_BYPASS_RIDE.toString()) ||
                    player.hasPermission(Permission.HORSE_ADMIN.toString()) ||
                    player.hasPermission(Permission.ADMIN.toString())) {
                Msg.success(player, "ロックされた馬に乗りました");
                sendStatus(player, entityUUID);
                return;
            }

            //他者によるロック
            event.setCancelled(true);
            Msg.warning(player, "この馬はロックされています");
            sendStatus(player, entityUUID);
        }
    }

    /**
     * 馬が死んだときに登録を解除する
     *
     * @param event イベント
     */
    @EventHandler
    private void HorseDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Horse || event.getEntity() instanceof SkeletonHorse || event.getEntity() instanceof SkeletonHorse)) {
            return;
        }
        UUID uuid = event.getEntity().getUniqueId();
        HorseRemoveWildcard(uuid);
    }

    /**
     * 馬をロックする
     *
     * @param player プレイヤー
     * @param uuid   UUID
     */
    private void HorseRegister(Player player, UUID uuid) {
        if (isRegister(uuid)) {
            Msg.warning(player, "この馬は既にロックされています");
            sendStatus(player, uuid);
            Msg.success(player, "現在のロック数" + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + getLocks(player));
            return;
        }

        int limits = getMaxLocks();

        if (limits <= 0) {
            Msg.warning(player, "保護数の上限が0以下に設定されています");
            return;
        }

        if (getLocks(player) > limits - 1) {
            Msg.warning(player, limits + "体以上の馬をロックすることはできません");
            return;
        }

        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
            String strDate = sdf.format(date.getTime());

            plugin.configHorses.getConfig().set(uuid.toString(), player.toString());
            plugin.configHorses.getConfig().set(uuid.toString() + ".data", strDate);
            plugin.configHorses.getConfig().set(uuid.toString() + ".player", player.getName());
            plugin.configHorses.getConfig().set(uuid.toString() + ".uuid", player.getUniqueId().toString());
            plugin.configHorses.getConfig().set(uuid.toString() + ".mode", "private");

            plugin.configHorses.saveConfig();
            Msg.success(player, "馬をロックしました");

        } catch (Exception e) {
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    /**
     * 馬のロックを解除する
     *
     * @param player     削除するプレイヤー名
     * @param entityUUID entityのUUID
     */
    private void HorseRemove(Player player, UUID entityUUID) {
        if (!isRegister(entityUUID)) {
            Msg.info(player, "登録情報の無い馬です");
            return;
        }

        if (!checkLockStatus(player.getUniqueId(), entityUUID)) {
            Msg.warning(player, "登録者以外馬のロックは解除できません");
            sendStatus(player, entityUUID);
            return;
        }

        try {
            plugin.configHorses.getConfig().set(entityUUID.toString(), null);
            plugin.configHorses.saveConfig();
            Msg.success(player, "馬のロックを解除しました");
        } catch (Exception e) {
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    /**
     * 馬のロックをワイルドカードで削除します
     *
     * @param entityUUID entityのUUID
     */
    private void HorseRemoveWildcard(UUID entityUUID) {
        if (!isRegister(entityUUID)) {
            return;
        }
        try {
            plugin.configHorses.getConfig().set(entityUUID.toString(), null);
            plugin.configHorses.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * 登録情報があるか確認するメソッド
     *
     * @param entityUUID エンティティーのUUID
     * @return 登録状態
     */
    private boolean isRegister(UUID entityUUID) {
        return !(plugin.configHorses.getConfig().getString(entityUUID + ".uuid") == null);
    }

    /**
     * 指定したエンティティーの登録者であるかどうかを確認するメソッド
     *
     * @param player プレイヤー
     * @param entity エンティティーのUUID
     * @return 登録者であるかどうかの結果
     */
    private boolean checkLockStatus(Player player, Entity entity) {
        return checkLockStatus(player.getUniqueId(), entity.getUniqueId());
    }

    /**
     * 指定したエンティティーの登録者であるかどうかを確認するメソッド
     *
     * @param playerUUID プレイヤーのUUID
     * @param entityUUID エンティティーのUUID
     * @return 登録者であるかどうかの結果
     */
    private boolean checkLockStatus(UUID playerUUID, UUID entityUUID) {
        return playerUUID.toString().equals(plugin.configHorses.getConfig().get(entityUUID + ".uuid"));
    }

    /**
     * ステイタス情報をターゲットへ送信するメソッド
     *
     * @param player     プレイヤー
     * @param entityUUID エンティティーのUUID
     */
    private void sendStatus(Player player, UUID entityUUID) {
        Msg.info(player, "登録者名" + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + plugin.configHorses.getConfig().getString(entityUUID + ".player"));
        Msg.info(player, "登録日" + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + plugin.configHorses.getConfig().getString(entityUUID + ".data"));
    }

    /**
     * ロック数を取得するメソッド
     *
     * @param player プレイヤー
     * @return ロック数
     */
    public static int getLocks(Player player) {
        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();
        int cnt = 0;
        ConfigurationSection cs = plugin.configHorses.getConfig().getConfigurationSection("");
        assert cs != null;
        for (String keys : cs.getKeys(false)) {
            String uuid = plugin.configHorses.getConfig().getString(keys + ".uuid");
            if (player.getUniqueId().toString().equals(uuid)) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * ロックの最大数を取得するメソッド
     *
     * @return ロック数
     */
    public static int getMaxLocks() {
        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();
        return plugin.configTeisyoku.getConfig().getInt("horse.limits");
    }

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
        if (!checkLockStatus((Player) event.getDamager(), event.getEntity())) {
            return;
        }

        Msg.warning(player, "この馬は他のプレイヤーがロックしています");
        event.setCancelled(true);
    }
}
