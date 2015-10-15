package net.jp.minecraft.plugins;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author syokkendesuyo
 */
public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoinMessage(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.VILLAGER_DEATH, 10.0F, 1.0F);
        player.sendMessage(ChatColor.AQUA + "---------------  " + ChatColor.GOLD + "ようこそ！定食サーバ へ！" + ChatColor.AQUA + "  ---------------");
        player.sendMessage("");
        player.sendMessage("- ルールやコマンドなどは確認しましたか？ http://bit.ly/teisyoku で確認！");
        player.sendMessage("-" + ChatColor.LIGHT_PURPLE + " 問題が発生したら？  掲示板へご連絡ください。");
        player.sendMessage("");
        player.sendMessage("- 直接接続アドレス:teisyoku.minecraft.jp.net");
        player.sendMessage(ChatColor.AQUA + "-----------------------------------------------------------");
        return;
    }
}
