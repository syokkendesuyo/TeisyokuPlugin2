package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Item;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_EntityDamage implements Listener {

    /**
     * 馬または村人にダメージを与える時の制御
     *
     * @param event イベント
     */
    @EventHandler
    public void EntityDamage(EntityDamageByEntityEvent event) {
        Entity damageEntity = event.getEntity();
        Entity damager = event.getDamager();

        //ダメージを与えたのがプレイヤー以外ならさよなら
        if (!(damager instanceof Player)) {
            return;
        }

        //ダメージを与えたのをプレイヤー変数に変換
        Player player = (Player) damager;

        //村人の場合の処理
        // TODO: 村人の保護機能をconfigで変更可能にする
        if (damageEntity instanceof Villager) {
            // 権限チェック
            if (Permission.hasPermission(player, Permission.ADMIN) || Permission.hasPermission(player, Permission.VILLAGER_BYPASS_DAMAGE)) {
                return;
            }
            // ダイヤモンドの剣の場合は許可
            if (((Player) damager).getInventory().getItemInMainHand().getType().equals(new ItemStack(Material.DIAMOND_SWORD).getType())) {
                return;
            }
            // イベントをキャンセル
            event.setCancelled(true);
            Msg.warning(player, "ダイヤの剣以外では村人にダメージを与えることはできません");
        }

        //馬の場合の処理
        if (damageEntity instanceof Horse) {
            // 権限チェック
            if (Permission.hasPermission(player, Permission.ADMIN) || Permission.hasPermission(player, Permission.HORSE_ADMIN) || Permission.hasPermission(player, Permission.HORSE_BYPASS_DAMAGE)) {
                return;
            }
            // ダイヤモンドの剣の場合は許可
            if (((Player) damager).getInventory().getItemInMainHand().getType().equals(new ItemStack(Material.DIAMOND_SWORD).getType())) {
                return;
            }
            // イベントをキャンセル
            event.setCancelled(true);
            Msg.warning(player, "ダイヤの剣以外では馬にダメージを与えることはできません");
        }
    }
}