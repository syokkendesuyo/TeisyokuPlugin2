package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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
public class Listener_Horse implements Listener{
    @EventHandler
    public void HorseClick (PlayerInteractEntityEvent event){
        if(event.getRightClicked().getType().equals(EntityType.HORSE)) {
            Player player = event.getPlayer();
            UUID entityUUID = event.getRightClicked().getUniqueId();

            //棒以外は無視
            if(!(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK)){
                return;
            }
            if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護ツール")){
                HorseRegister(player,entityUUID);
                event.setCancelled(true);
                return;
            }
            if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護解除ツール")){
                HorseRemove(player,entityUUID);
                event.setCancelled(true);
                return;
            }
            if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護情報確認ツール")){
                if(isRegister(entityUUID) == true){
                    Msg.info(player, "この馬は保護されています");
                    getStatus(player,entityUUID);
                }
                else {
                    Msg.info(player, "この馬は保護されていません");
                }
                event.setCancelled(true);
                return;
            }
        }
    }

    /*
    馬に乗る時
     */
    @EventHandler
    public static void HorseRide(VehicleEnterEvent event){
        if(event.getVehicle() instanceof Horse){
            //馬に乗る際、プレイヤー以外だった場合は無視する
            //例:スケルトンホースが自然にスポーンする際にスケルトンが乗る場合、エラーを吐いてしまう
            if(!(event.getEntered() instanceof Player)){
                return;
            }

            //プレイヤーと判定し、処理を継続させる
            Player player = (Player)event.getEntered();
            UUID entityUUID = event.getVehicle().getUniqueId();
            UUID playerUUID =  player.getUniqueId();

            if(player.getInventory().getItemInMainHand().getType().equals(Material.STICK)){
                if((player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() != null)||(!(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("")))){
                    if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護ツール") || player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護解除ツール") || player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護情報確認ツール")){
                        event.setCancelled(true);
                        return;
                    }
                }
            }

            int temp = isEqual(player,playerUUID,entityUUID);
            if(temp == 1){
                Msg.success(player, "ロックされた馬に乗りました");
                return;
            }
            else if(temp == 2){
                Msg.info(player, "登録情報の無い馬です");
                return;
            }
            else {
                Msg.warning(player, "この馬はロックされています");
                event.setCancelled(true);
                getStatus(player,entityUUID);
                return;
            }
        }
    }

    /*
    馬をロックする
     */
    public static void HorseRegister (Player player , UUID uuid){
        if(isRegister(uuid) == true){
            Msg.warning(player,"この馬は既にロックされた馬です");
            getStatus(player,uuid);
            return;
        }
        try{
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
            String strDate = sdf.format(date.getTime());

            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString(), player.toString());
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".data",strDate);
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".player",player.getName().toString());
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".uuid",player.getUniqueId().toString());
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".mode","private");

            TeisyokuPlugin2.getInstance().saveHorseConfig();
            Msg.success(player, "馬をロックしました");

        }
        catch (Exception e){
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    /*
    馬のロックを解除する
     */
    public static void HorseRemove (Player player , UUID entityUUID){
        if(isEqual(player, player.getUniqueId(), entityUUID) == 2){
            Msg.warning(player,"この馬は保護されていません");
            return;
        }
        else if(isEqual(player,player.getUniqueId(),entityUUID) == 0){
            Msg.warning(player,"登録者以外馬のロックは解除できません");
            getStatus(player,entityUUID);
            return;
        }

        try{
            TeisyokuPlugin2.getInstance().HorseConfig.set(entityUUID.toString(), null);
            TeisyokuPlugin2.getInstance().saveHorseConfig();
            Msg.success(player, "馬のロックを解除しました");
        }
        catch (Exception e){
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }
    
    /*
     * ゾンビ馬に変換する
     */
    
    @EventHandler
    public void ChangeHorse(PlayerInteractAtEntityEvent event){
        if(!(event.getRightClicked().getType().equals(EntityType.HORSE))){
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item == null){
            return;
        }
        if(!(item.getType().equals(Material.PAPER))){
            return;
        }
        if(item.getItemMeta().getDisplayName() == null){
            return;
        }
        if((item.getItemMeta().getDisplayName().equals(TeisyokuPlugin2.getInstance().SkeletonTicket))||(item.getItemMeta().getDisplayName().equals(TeisyokuPlugin2.getInstance().ZombieTicket))){
            event.setCancelled(true);
            UUID entityUUID = event.getRightClicked().getUniqueId();
            if(isEqual(player,player.getUniqueId(),entityUUID) == 0){
                Msg.warning(player,"登録者以外の馬の変換はできません");
                getStatus(player,entityUUID);
                return;
            }
            Horse horse = (Horse) event.getRightClicked();
            if(item.getItemMeta().getDisplayName().equals(TeisyokuPlugin2.getInstance().ZombieTicket)){
                if(!(horse.getVariant().equals(Variant.SKELETON_HORSE))){
                    Msg.warning(player, "スケルトンホースにのみ有効です");
                    return;
                }
                if(item.getAmount() <= 1){
                    player.getInventory().removeItem(item);
                    player.updateInventory();
                }
                else {
                    item.setAmount(item.getAmount() - 1);
                }
                horse.setVariant(Variant.UNDEAD_HORSE);
                Msg.success(player, "ゾンビホースに変換しました");
                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1, 1);
            }
            else if(item.getItemMeta().getDisplayName().equals(TeisyokuPlugin2.getInstance().SkeletonTicket)){
                if(!(horse.getVariant().equals(Variant.UNDEAD_HORSE))){
                    Msg.warning(player, "ゾンビホースにのみ有効です");
                    return;
                }
                if(item.getAmount() <= 1){
                    player.getInventory().removeItem(item);
                    player.updateInventory();
                }
                else {
                    item.setAmount(item.getAmount() - 1);
                }
                horse.setVariant(Variant.SKELETON_HORSE);
                Msg.success(player, "スケルトンホースに変換しました");
                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1, 1);
            }
        }
        return;
    }

    public static int isEqual(Player player , UUID playerUUID , UUID entityUUID){
        if(playerUUID.toString().equals(TeisyokuPlugin2.getInstance().HorseConfig.get(entityUUID + ".uuid"))){
            return 1;
        }
        else{
            if(TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".uuid") == null){
                return 2;
            }
            return 0;
        }
    }

    public static void getStatus(Player player , UUID entityUUID){
        Msg.info(player,"登録者名 : " + TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".player"));
        Msg.info(player,"登録日 : " + TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".data"));
    }

    public static boolean isRegister(UUID entityUUID){
        if(TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".uuid") == null){
            return false;
        }
        else{
            return true;
        }
    }
}
