package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import net.jp.minecraft.plugins.teisyokuplugin2.function.SignEdit;
import net.jp.minecraft.plugins.teisyokuplugin2.util.BlockUtil;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_SignEdit implements Listener {

    @EventHandler
    public void onClickEvent(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!(SignEdit.isEditMode(player))) {
            return;
        }
        assert block != null;
        if (BlockUtil.isSign(block.getType())) {
            Location loc = block.getLocation();
            SignEdit.updateSign(loc, event.getPlayer());
            return;
        }
        Msg.warning(player, "更新できませんでした。");
        Msg.warning(player, "再度コマンドを実行し看板に向かって右クリックしてください。");
        SignEdit.removeData(player);
    }
}
