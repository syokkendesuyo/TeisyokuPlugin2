package net.jp.minecraft.plugins;

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
        if(!(player.hasPermission(Permissions.getFlyPermisson()))){
            player.sendMessage(Messages.getNoPermissionMessage(Permissions.getFlyPermisson()));
        }
        else{
            player.sendMessage(Messages.getSuccessPrefix() + "Flyモードを有効にしました");
            player.setAllowFlight(true);
        }
        return true;
    }


    //flyを無効化
    public static boolean disable_fly(Player player){
        if(!(player.hasPermission(Permissions.getFlyPermisson()))){
            player.sendMessage(Messages.getNoPermissionMessage(Permissions.getFlyPermisson()));
        }
        else{
            player.sendMessage(Messages.getSuccessPrefix() + "Flyモードを無効にしました");
            player.setAllowFlight(false);
        }
        return true;
    }
}
