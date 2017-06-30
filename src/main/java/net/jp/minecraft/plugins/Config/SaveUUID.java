package net.jp.minecraft.plugins.Config;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class SaveUUID implements Listener{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Connection_UUIDConfig.createConfig("UUIDs.yml");

        if (Connection_UUIDConfig.getString("enable") == null) {
            Connection_UUIDConfig.setConfig("enable", "true");
        }

        if (!Connection_UUIDConfig.getString("enable").equals("true")) {
            return;
        }

        Connection_UUIDConfig.addArrayList("list", event.getPlayer().getUniqueId().toString().replace("-", ""));
    }
}
