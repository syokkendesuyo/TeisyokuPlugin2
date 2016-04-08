package net.jp.minecraft.plugins.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.TeisyokuPlugin2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_RailwayInfo implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(args.length == 0){
            help(cmd,sender);
            return true;
        }
        if(args[0].equalsIgnoreCase("add")||args[0].equalsIgnoreCase("+")||args[0].equalsIgnoreCase("register")||args[0].equalsIgnoreCase("set")){
            if(isplayer(sender) == false){
                sender.sendMessage(Messages.getDenyPrefix() + "コンソールから追加はできません");
                return true;
            }
            if(args.length == 3){
                if(isresigter(args[1]) == true){
                    String name = TeisyokuPlugin2.getInstance().CartConfig.getString(args[1] + ".player");
                    sender.sendMessage(Messages.getDenyPrefix() + "既に " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " は登録されています (登録者: " + ChatColor.YELLOW + name + ChatColor.RESET + " )");
                    return true;
                }
                else{
                    add(args[1],args[2],(Player)sender);
                    sender.sendMessage(Messages.getSuccessPrefix() + ChatColor.YELLOW + args[1] + ChatColor.RESET + " を登録しました");
                    return true;
                }
            }
            else{
                help(cmd,sender);
                return true;
            }
        }
        else if(args[0].equalsIgnoreCase("remove")||args[0].equalsIgnoreCase("-")||args[0].equalsIgnoreCase("delete")){
            if(args.length == 2){
                if(isresigter(args[1]) == true){
                    remove(args[1],(Player)sender);
                    return true;
                }
                else{
                    sender.sendMessage(Messages.getDenyPrefix() + ChatColor.YELLOW + args[1] + ChatColor.RESET + " は登録されていません");
                    return true;
                }
            }
            help(cmd,sender);
            return true;
        }
        else if(args[0].equalsIgnoreCase("edit")||args[0].equalsIgnoreCase("change")||args[0].equalsIgnoreCase("*")){
            if(isplayer(sender) == false){
                sender.sendMessage(Messages.getDenyPrefix() + "コンソールから編集はできません");
                return true;
                
            }
            if(isresigter(args[1]) == true){
                edit(args[1], args[2], (Player) sender);
                return true;
            }
            else{
                sender.sendMessage(Messages.getDenyPrefix() + ChatColor.YELLOW + args[1] + ChatColor.RESET + " は登録されていません");
                return true;
            }
        }
        else if(args[0].equalsIgnoreCase("perm")||args[0].equalsIgnoreCase("perms")||args[0].equalsIgnoreCase("permission")){
            Messages.getCheckPermissionMessage(Permissions.getTeisyokuUserPermisson());
            return true;
        }
        else{
            help(cmd,sender);
            return true;
        }
    }

    public static void help(Command cmd , CommandSender sender){
        sender.sendMessage(Messages.getSuccessPrefix() +  "Railway Information 機能のヘルプ");
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " + <登録名> <登録する文章>", "文章を設定します"));
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " * <登録名> <編集する文章>","文章を編集します"));
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " - <登録名>","登録を削除します"));
    }

    public static boolean isresigter(String string){
        try{
            if(TeisyokuPlugin2.getInstance().CartConfig.get(string) == null){
                return false;
            }
            else{
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isplayer(CommandSender sender){
        if(sender instanceof Player){
            return true;
        }
        else{
            return false;
        }
    }

    public static void add(String name , String string , Player player){
        try{
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
            String strDate = sdf.format(date.getTime());

            TeisyokuPlugin2.getInstance().CartConfig.set(name.toString(),name.toString());
            TeisyokuPlugin2.getInstance().CartConfig.set(name.toString() + ".data",strDate);
            TeisyokuPlugin2.getInstance().CartConfig.set(name.toString() + ".player",player.getName().toString());
            TeisyokuPlugin2.getInstance().CartConfig.set(name.toString() + ".uuid",player.getUniqueId().toString());
            TeisyokuPlugin2.getInstance().CartConfig.set(name.toString() + ".string",string);
            TeisyokuPlugin2.getInstance().CartConfig.set(name.toString() + ".mode","private");

            TeisyokuPlugin2.getInstance().saveCartConfig();

        }
        catch (Exception e){
            player.sendMessage(Messages.getDenyPrefix() + "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    public static void remove(String name , Player player){
        try{
            if(TeisyokuPlugin2.getInstance().CartConfig.getString(name + ".uuid").toString().equalsIgnoreCase(player.getUniqueId().toString()) || player.hasPermission(Permissions.getTeisyokuUserPermisson())){
                TeisyokuPlugin2.getInstance().CartConfig.set(name.toString(),null);
                TeisyokuPlugin2.getInstance().saveCartConfig();
                player.sendMessage(Messages.getSuccessPrefix() + ChatColor.YELLOW + name + ChatColor.RESET + " を削除しました");
                return;
            }
            else{
                String register = TeisyokuPlugin2.getInstance().CartConfig.getString(name + ".player");
                player.sendMessage(Messages.getDenyPrefix() + "削除できるプレイヤーは登録者のみです (登録者: " + ChatColor.YELLOW + register + ChatColor.RESET + " )");
                return;
            }
        }
        catch (Exception e){
            player.sendMessage(Messages.getDenyPrefix() + "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    public static void edit(String name , String string , Player player){
        try{
            if(TeisyokuPlugin2.getInstance().CartConfig.getString(name + ".uuid").toString().equalsIgnoreCase(player.getUniqueId().toString()) || player.hasPermission(Permissions.getTeisyokuUserPermisson())){
                add(name,string,player);
                player.sendMessage(Messages.getSuccessPrefix() + ChatColor.YELLOW + name + ChatColor.RESET + " を編集しました");
                return;
            }
            else{
                String register = TeisyokuPlugin2.getInstance().CartConfig.getString(name + ".player");
                player.sendMessage(Messages.getDenyPrefix() + "編集できるプレイヤーは登録者のみです (登録者: " + ChatColor.YELLOW + register + ChatColor.RESET + " )");
                return;
            }
        }
        catch (Exception e){
            player.sendMessage(Messages.getDenyPrefix() + "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }
}
