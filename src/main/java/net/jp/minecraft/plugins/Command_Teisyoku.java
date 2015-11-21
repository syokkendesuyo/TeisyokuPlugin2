package net.jp.minecraft.plugins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.security.jca.GetInstance;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_Teisyoku implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(! (sender.hasPermission(Permissions.getTeisyokuCommandPermisson()))){
            sender.sendMessage(Messages.getNoPermissionMesssage(Permissions.getTeisyokuCommandPermisson()));
            return true;
        }

        if(args.length == 0){
            teisyoku(sender);
        }
        else if(args.length == 1){
            if(args[0].equalsIgnoreCase("help")){
                if(!(sender.hasPermission(Permissions.getHelpCommandPermisson()))){
                    sender.sendMessage(Messages.getNoPermissionMesssage(Permissions.getHelpCommandPermisson()));
                    return true;
                }
                Messages.HelpMessage(sender);
            }
            else if(args[0].equalsIgnoreCase("permission") || args[0].equalsIgnoreCase("perm")){
                sender.sendMessage(Messages.getNormalPrefix() + "パーミッション(通常利用): " + Permissions.getTeisyokuPermisson());
                sender.sendMessage(Messages.getNormalPrefix() + "パーミッション(コマンド): " + Permissions.getTeisyokuCommandPermisson());
            }
            else if(args[0].equalsIgnoreCase("reload")){
                TeisyokuPlugin2.getInstance().reloadLastPlayerJoinConfig();
                TeisyokuPlugin2.getInstance().reloadNickConfig();
                sender.sendMessage(Messages.getSuccessPrefix() + "TeisyokuPlugin2のconfigをリロードしました。" );
            }
            else if(args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("version")){
                sender.sendMessage(Messages.getNormalPrefix() + "Version ： " + TeisyokuPlugin2.getInstance().getDescription().getVersion().toString());
            }
        }
        else{
            teisyoku(sender);
        }
        return true;
    }
    public void teisyoku(CommandSender sender){
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.getDenyPrefix() +"定食メニューコマンドはゲーム内からのみ実行できます");
            return;
        }

        if(!(sender.hasPermission(Permissions.getTeisyokuPermisson()))){
            sender.sendMessage(Messages.getNoPermissionMesssage(Permissions.getTeisyokuPermisson()));
            return ;
        }

        Player player = (Player) sender;
        TeisyokuMenuIndex.getMenu(player);
    }
}
