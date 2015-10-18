package net.jp.minecraft.plugins;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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

        TeisyokuPlugin2.getInstance().lastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".name",event.getPlayer().getName().toString());
        TeisyokuPlugin2.getInstance().lastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".rawDate",date.toString());
        TeisyokuPlugin2.getInstance().lastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".date", strDate.toString());
        TeisyokuPlugin2.getInstance().lastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".timestamp",System.currentTimeMillis());

        event.getPlayer().sendMessage(Messages.getSuccessPrefix() + "ログインデータを書き込みました。");
        TeisyokuPlugin2.getInstance().saveLastPlayerJoinConfig();

    }
}
