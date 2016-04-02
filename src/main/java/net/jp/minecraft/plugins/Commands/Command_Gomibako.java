package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Listener.Listener_Gomibako;
import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_Gomibako implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(! (sender.hasPermission(Permissions.getGomibakoCommandPermisson()))){
            Msg.noPermissionMessage(sender,Permissions.getGomibakoCommandPermisson());
            return true;
        }

        if(args.length != 1){
            Listener_Gomibako.openGomibako(sender);
        }
        else if(args.length == 1){
            if(args[0].equalsIgnoreCase("perm")||args[0].equalsIgnoreCase("permission")){
                Msg.info(sender, "パーミッション(通常利用): " + Permissions.getGomibakoPermisson());
                Msg.info(sender, "パーミッション(コマンド): " + Permissions.getGomibakoCommandPermisson());
            }
           else{
                Msg.warning(sender,"引数「" + args[0] + "」は存在しません");
            }
        }
        return true;
    }
}
