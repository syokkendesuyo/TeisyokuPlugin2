package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_Horse implements Listener{
    @EventHandler
    public void HorseClick (PlayerInteractEntityEvent event){
        if(event.getRightClicked().getType().equals(EntityType.HORSE)) {
            //String str = event.getRightClicked().getUniqueId().toString();
            //Msg.success(event.getPlayer(), "ID: " + str);
        }
    }
}
