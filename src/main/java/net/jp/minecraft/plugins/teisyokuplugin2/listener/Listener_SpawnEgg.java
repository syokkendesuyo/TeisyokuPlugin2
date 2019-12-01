package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * TeisyokuPlugin2
 * <p>
 * MOBスポナーにエッグを右クリックした時キャンセルする処理
 *
 * @author syokkendesuyo
 */
public class Listener_SpawnEgg implements Listener {
    @EventHandler
    public void SpawnEgg(PlayerInteractEvent event) {

        //ClickBlock
        Block block = event.getClickedBlock();

        //イベントの戻り値がnullだった場合の処理
        if (!event.hasBlock()) {
            return;
        }

        //プレイヤー変数
        Player player = event.getPlayer();

        //メインハンドの持ち物
        ItemStack item_main = event.getPlayer().getInventory().getItemInMainHand();

        //オフハンドの持ち物
        ItemStack item_off = event.getPlayer().getInventory().getItemInOffHand();


        //1.スポーンエッグ以外がアクティブな場合は無視 (api-version: 1.13 以降のみに対応)
        if (!(item_main.getType().toString().contains("_SPAWN_EGG") || item_off.getType().toString().contains("_SPAWN_EGG"))) {
            return;
        }

        //2.ターゲットがスポナー以外なら無視
        assert block != null;
        if (!(block.getType().equals(Material.SPAWNER))) {
            return;
        }

        //teisyoku.adminを保有していた場合は利用が可能
        if (player.hasPermission("teisyoku.admin")) {
            Msg.success(player, "MOBスポナーを操作しました");
            return;
        }

        //3.目的に到着するのでキャンセル
        Msg.warning(player, "MOBスポナーにモンスターエッグをクリックすることはできません");
        event.setCancelled(true);
    }
}
