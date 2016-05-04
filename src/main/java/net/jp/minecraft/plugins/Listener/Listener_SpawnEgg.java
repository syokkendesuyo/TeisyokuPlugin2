package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * TeisyokuPlugin2
 *
 * MOBスポナーにエッグを右クリックした時キャンセルする処理
 *
 * @auther syokkendesuyo
 */
public class Listener_SpawnEgg implements Listener {
    @EventHandler
    public void SpawnEgg(PlayerInteractEvent event){

        //ClickBlock
        Block block = event.getClickedBlock();

        //イベントの戻り値がnullだった場合の処理
        if(!event.hasBlock()){
            return;
        }

        //プレイヤー変数
        Player player = event.getPlayer();

        //メインハンドの持ち物
        ItemStack item_main = event.getPlayer().getInventory().getItemInMainHand();

        //オフハンドの持ち物
        ItemStack item_off = event.getPlayer().getInventory().getItemInOffHand();


        //1.Egg以外の手持ちで右クリックは無視
        if(!(item_main.getType().toString().equals(Material.MONSTER_EGG.toString()) || item_off.getType().toString().equals(Material.MONSTER_EGG.toString()))){
            return;
        }

        //2.ターゲットがスポナー以外なら無視
        if(!(block.getType().equals(Material.MOB_SPAWNER))){
            return;
        }

        //teisyoku.adminを保有していた場合は利用が可能
        if(player.hasPermission("teisyoku.admin")){
            Msg.success(player, "MOBスポナーを操作しました");
            return;
        }

        //3.目的に到着するのでキャンセル
        Msg.warning(player,"MOBスポナーにモンスターエッグをクリックすることはできません");
        event.setCancelled(true);
        return;
    }
}
