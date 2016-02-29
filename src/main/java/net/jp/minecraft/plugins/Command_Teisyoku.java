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
            sender.sendMessage(Messages.getNoPermissionMessage(Permissions.getTeisyokuCommandPermisson()));
            return true;
        }

        if(args.length == 0){
            teisyoku(sender);
            return true;
        }
        else if(args.length == 1){
            if(args[0].equalsIgnoreCase("help")){
                if(!(sender.hasPermission(Permissions.getHelpCommandPermisson()))){
                    sender.sendMessage(Messages.getNoPermissionMessage(Permissions.getHelpCommandPermisson()));
                    return true;
                }
                Messages.HelpMessage(sender);
                return true;
            }
            else if(args[0].equalsIgnoreCase("permission") || args[0].equalsIgnoreCase("perm")){
                sender.sendMessage(Messages.getNormalPrefix() + "パーミッション(通常利用): " + Permissions.getTeisyokuPermisson());
                sender.sendMessage(Messages.getNormalPrefix() + "パーミッション(コマンド): " + Permissions.getTeisyokuCommandPermisson());
                return true;
            }
            else if(args[0].equalsIgnoreCase("reload")){
                TeisyokuPlugin2.getInstance().reloadTeisyokuConfig();
                TeisyokuPlugin2.getInstance().reloadLastPlayerJoinConfig();
                TeisyokuPlugin2.getInstance().reloadNickConfig();
                TeisyokuPlugin2.getInstance().reloadTPointConfig();
                TeisyokuPlugin2.getInstance().reloadCartConfig();
                TeisyokuPlugin2.getInstance().reloadHorseConfig();
                TeisyokuPlugin2.getInstance().reloadTPointSettingsConfig();
                sender.sendMessage(Messages.getSuccessPrefix() + "TeisyokuPlugin2のconfigをリロードしました。" );
                return true;
            }
            else if(args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("version")){
                sender.sendMessage(Messages.getNormalPrefix() + "Version ： " + TeisyokuPlugin2.getInstance().getDescription().getVersion().toString());
                return true;
            }
            sender.sendMessage(Messages.getDenyPrefix() + "引数 " + args[0].toString()  + " は存在しません");
            return true;
        }
        else{
            sender.sendMessage(Messages.getDenyPrefix() + "引数が多すぎるかまたは少なすぎます");
            return true;
        }
    }
    public void teisyoku(CommandSender sender){
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.getDenyPrefix() +"定食メニューコマンドはゲーム内からのみ実行できます");
            return;
        }

        if(!(sender.hasPermission(Permissions.getTeisyokuPermisson()))){
            sender.sendMessage(Messages.getNoPermissionMessage(Permissions.getTeisyokuPermisson()));
            return ;
        }

        Player player = (Player) sender;
        TeisyokuMenuIndex.getMenu(player);
    }
}
