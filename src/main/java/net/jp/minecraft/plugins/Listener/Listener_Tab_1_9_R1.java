package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_9_R1.PlayerConnection;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_Tab_1_9_R1 implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + color(TeisyokuPlugin2.getInstance().TeisyokuConfig.get("title").toString()) + "\"}");
        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + color(TeisyokuPlugin2.getInstance().TeisyokuConfig.get("subtitle").toString()) + "\"}");

        sendTabTitle(player, chatTitle, chatSubTitle);

    }

    @EventHandler
    public void exit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("");
        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("");

        sendTabTitle(player, chatTitle, chatSubTitle);

    }

    private static void sendTabTitle(Player player, IChatBaseComponent header, IChatBaseComponent footer) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(header);
        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, footer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.sendPacket(headerPacket);
        }
    }

    private static String color(String str) {
        return str.replaceAll("&", "§");
    }
}
