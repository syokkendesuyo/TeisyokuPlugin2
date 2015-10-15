package net.jp.minecraft.plugins;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class TeisyokuCommand implements CommandExecutor{
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
        }
        else if (args.length == 2){
            if(args[0].equalsIgnoreCase("give") && sender.isOp() && sender instanceof Player){
                if(args[1].equalsIgnoreCase("43")){
                    Player player = (Player)sender;
                    sender.sendMessage(Messages.getSuccessPrefix() +"43をインベントリへ投入");
                    player.getInventory().addItem(new ItemStack(Material.DOUBLE_STEP, 1));
                }
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
