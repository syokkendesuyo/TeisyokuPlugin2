package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import net.jp.minecraft.plugins.teisyokuplugin2.module.PlayerDatabase;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo azuhata
 */
public class Listener_Chat implements Listener {

    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        try {
            String NickName = PlayerDatabase.getString(player, "nickname.prefix");

            String sender = event.getPlayer().getName();

            if (NickName == null || NickName.equals("")) {
                player.setDisplayName(sender);
            } else {
                String color = PlayerDatabase.getString(player, "nickname.color");
                if (color.equalsIgnoreCase("default") || color.equalsIgnoreCase("") || color.isEmpty()) {
                    player.setDisplayName(ChatColor.GRAY + "" + NickName + " " + ChatColor.RESET + sender);
                    return;
                }
                player.setDisplayName(color + "" + NickName + " " + ChatColor.RESET + sender);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void ChatReplace(AsyncPlayerChatEvent event) {
        String msg = event.getMessage().replaceAll("Penguin", ChatColor.AQUA + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Penguin" + ChatColor.RESET);
        msg = msg.replaceAll("penguin", ChatColor.AQUA + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Penguin" + ChatColor.RESET);
        msg = msg.replaceAll("rorikon", ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Rorikon" + ChatColor.RESET);
        msg = msg.replaceAll("Rorikon", ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Rorikon" + ChatColor.RESET);
        event.setMessage(msg);
    }

    @EventHandler
    public void ChatBP1(AsyncPlayerChatEvent event) {
        String msg = event.getMessage();
        String regex = "\\d{1,20}BP";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(msg);

        if (m.find()) {
            String match = m.group();
            String result = msg.replaceAll(msg, match);

            msg = msg.replaceAll(result, ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + result + ChatColor.RESET);
            event.setMessage(msg);
        }
    }

    @EventHandler
    public void ChatBP2(AsyncPlayerChatEvent event) {
        String msg = event.getMessage();
        String regex = "[-]\\d{1,20}BP";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(msg);

        if (m.find()) {
            String match = m.group();
            String result = msg.replaceAll(msg, match);

            msg = msg.replaceAll(result, ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + result + ChatColor.RESET);
            event.setMessage(msg);
        }
    }
}
