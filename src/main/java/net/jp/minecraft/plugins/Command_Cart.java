package net.jp.minecraft.plugins;

import org.bukkit.*;
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
public class Command_Cart implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

        if(!(sender instanceof Player)){
            sender.sendMessage(Messages.getDenyPrefix() + "コンソールからコマンドを送信することはできません");
            return true;
        }

        if(args.length > 1){
            sender.sendMessage(Messages.getDenyPrefix() + "引数が多すぎです");
            return true;
        }

        if(args.length == 0){
            sender.sendMessage(Messages.getSuccessPrefix() +"マインカートをインベントリに追加しました");
            Player player =  (Player) sender;
            player.getInventory().addItem(new ItemStack(Material.MINECART));
            return true;
        }
        else if(args.length == 1){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
            if(offlinePlayer.isOnline()){
                if(!(sender.hasPermission("teisyoku.admin"))){
                    sender.sendMessage(Messages.getNoPermissionMessage("teisyoku.admin"));
                    return true;
                }
                else{

                    Player player =  (Player) offlinePlayer;
                    player.getInventory().addItem(new ItemStack(Material.MINECART));
                    sender.sendMessage(Messages.getSuccessPrefix() + "プレイヤー " + ChatColor.YELLOW + args[0] + ChatColor.RESET + " にマインカートを渡しました");
                    player.sendMessage(Messages.getSuccessPrefix() + "プレイヤー " + ChatColor.YELLOW + sender.getName() + ChatColor.RESET + " からマインカートを渡されました");

                    player.playSound(player.getLocation(), Sound.WOLF_BARK, 3.0F, 1.8F);
                    return true;
                }
            }
            else{
                //プレイヤーが居ないのでエラー
                sender.sendMessage(Messages.getDenyPrefix() + "プレイヤー " + ChatColor.YELLOW + args[0] + ChatColor.RESET + " はオンラインではありません");
                return true;
            }
        }
        return true;
    }
}
