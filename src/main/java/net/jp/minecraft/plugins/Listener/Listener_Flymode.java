package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 *
 * flyの実行時の処理
 */
public class Listener_Flymode {

    //flyを有効化
    public static boolean enable_fly(Player player){
        Msg.success(player, ChatColor.YELLOW + player.getName().toString() + ChatColor.RESET + " のFlyモードを" + ChatColor.GREEN + " 有効 " + ChatColor.RESET + "にしました");
        player.setAllowFlight(true);
        return true;
    }


    //flyを無効化
    public static boolean disable_fly(Player player){
        Msg.success(player, ChatColor.YELLOW + player.getName().toString() + ChatColor.RESET + " のFlyモードを" + ChatColor.RED + " 無効 " + ChatColor.RESET + "にしました");
        player.setAllowFlight(false);
        return true;
    }
}
