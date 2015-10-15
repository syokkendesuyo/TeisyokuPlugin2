package net.jp.minecraft.plugins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class PlayersCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender.hasPermission(Permissions.getPlayersCommandPermisson()))){
            sender.sendMessage(Messages.getNoPermissionMesssage(Permissions.getPlayersCommandPermisson()));
            return true;
        }

        if(! (sender  instanceof Player)){
            sender.sendMessage(Messages.getDenyPrefix() + "コンソールからコマンドを送信することはできません");
        }

        Player player = (Player)sender;

        PlayersList.getPlayersList(player);
        return true;
    }
}
