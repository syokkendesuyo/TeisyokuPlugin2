package net.jp.minecraft.plugins;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_LastJoin implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event){

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
        String strDate = sdf.format(date.getTime());

        //プレイヤー名
        TeisyokuPlugin2.getInstance().lastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".Name",event.getPlayer().getName().toString());

        //ログイン時の日時
        TeisyokuPlugin2.getInstance().lastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".JoinDate", strDate.toString());
        TeisyokuPlugin2.getInstance().lastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".JoinTimestamp",System.currentTimeMillis());

        event.getPlayer().sendMessage(Messages.getSuccessPrefix() + "ログインデータを書き込みました。");
        TeisyokuPlugin2.getInstance().saveLastPlayerJoinConfig();
    }

    @EventHandler
    public void quit(PlayerQuitEvent event){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
        String strDate = sdf.format(date.getTime());

        //プレイヤー名
        TeisyokuPlugin2.getInstance().lastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".Name",event.getPlayer().getName().toString());

        //ログアウト時の日時
        TeisyokuPlugin2.getInstance().lastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".QuitDate", strDate.toString());
        TeisyokuPlugin2.getInstance().lastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".QuitTimestamp",System.currentTimeMillis());

        TeisyokuPlugin2.getInstance().saveLastPlayerJoinConfig();
    }
}
