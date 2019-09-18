package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_EntityDamage implements Listener {

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

        //ダメージを受けたのが村人か馬じゃなかったらさよなら
        if (!(damageEntity instanceof Villager || damageEntity instanceof Horse)) {
            return;
        }

        if (player.hasPermission("teisyoku.admin")) {
            return;
        }

        if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_SWORD) {
            return;
        }

        Msg.warning(player, "ダイヤの剣以外では村人と馬は殺害できません");
        event.setCancelled(true);

    }

    @EventHandler
    public void HorseDamage(EntityDamageByEntityEvent event) {
        Entity damageEntity = event.getEntity();
        Entity damager = event.getDamager();

        //ダメージを与えたのがプレイヤー以外ならさよなら
        if (!(damager instanceof Player)) {
            return;
        }

        //ダメージを与えたのをプレイヤー変数に変換
        Player player = (Player) damager;

        //ダメージを受けたのが村人か馬じゃなかったらさよなら
        if (!(damageEntity instanceof Horse)) {
            return;
        }

        if (player.hasPermission("teisyoku.admin")) {
            return;
        }

        //ツールではダメージが通らないように
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            try {
                String displayName = Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName();
                if (displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護ツール") || displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護解除ツール") || displayName.equalsIgnoreCase(ChatColor.GOLD + "馬保護情報確認ツール")) {
                    event.setCancelled(true);
                    return;
                }
            } catch (Exception e) {
                //謎のエラー回避
            }
        }

        UUID playerUUID = event.getDamager().getUniqueId();
        UUID entityUUID = event.getEntity().getUniqueId();

        int temp = Listener_Horse.isEqual(player, playerUUID, entityUUID);
        if (temp == 1) {
            Msg.warning(player, "ロックされている為ダメージを与えることはできません");
            event.setCancelled(true);
        } else {
            Msg.warning(player, "ロックされている為ダメージを与えることはできません");
            Listener_Horse.getStatus(player, entityUUID);
            event.setCancelled(true);
        }
    }
}