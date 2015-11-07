package net.jp.minecraft.plugins;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_EntityDamage implements Listener {

    @EventHandler
    public void EntityDamage(EntityDamageByEntityEvent event){
        Entity damageEntity = event.getEntity();
        Entity damager = event.getDamager();

        //ダメージを与えたのがプレイヤー以外ならさよなら
        if(!(damager instanceof Player)){
            return;
        }

        //ダメージを与えたのをプレイヤー変数に変換
        Player player = (Player) damager;

        //ダメージを受けたのが村人か馬じゃなかったらさよなら
        if(!(damageEntity instanceof Villager || damageEntity instanceof Horse)){
            return;
        }

        if(player.hasPermission("teisyoku.admin")){
            return;
        }

        if(player.getItemInHand().getType() == Material.DIAMOND_SWORD){
            return;
        }

        player.sendMessage(Messages.getDenyPrefix() + "ダイヤの剣以外では村人と馬は殺害できません");
        event.setCancelled(true);

    }
}