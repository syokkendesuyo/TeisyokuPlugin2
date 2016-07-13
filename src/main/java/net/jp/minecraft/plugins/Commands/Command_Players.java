package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.API.API_Players;
import net.jp.minecraft.plugins.GUI.GUI_PlayersList;
import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_Players implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender.hasPermission(Permissions.getPlayersCommandPermisson()))){
            Msg.noPermissionMessage(sender, Permissions.getPlayersCommandPermisson());
            return true;
        }

        if(! (sender  instanceof Player)){
            Msg.warning(sender, "コンソールからコマンドを送信することはできません");
            return true;
        }

        Player player = (Player)sender;

        if(args.length == 0){
            GUI_PlayersList.getPlayersList(player);
            return true;
        }

        if(args.length == 1){
            if(!(args[0].equalsIgnoreCase("total"))){
                GUI_PlayersList.getPlayersList(player);
                return true;
            }
            Msg.info(sender, "総計 " + API_Players.getTotalPlayers() + " 名のプレイヤーがこのサーバで遊びました");
            return true;
        }

        GUI_PlayersList.getPlayersList(player);

        return true;
    }
}
