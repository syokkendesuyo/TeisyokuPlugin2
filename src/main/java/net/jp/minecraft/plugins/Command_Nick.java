package net.jp.minecraft.plugins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo azuhata
 */
public class Command_Nick implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

        //パーミッションのチェックを行います
        if(! (sender.hasPermission("teisyoku.user"))){
            sender.sendMessage(Messages.getNoPermissionMessage("teisyoku.user"));
            return true;
        }

        if(args.length == 0){
            HelpMessage(sender, cmd);
            if(sender.hasPermission("teisyoku.admin")){
                AdminHelpMessage(sender, cmd);
            }
            return true;
        }


        //adminコマンド
        if(args[0].equalsIgnoreCase("admin")){
            if(args.length == 1){
                HelpMessage(sender, cmd);
                AdminHelpMessage(sender, cmd);
                return true;
            }

            if(! (sender.hasPermission("teisyoku.admin"))){
                sender.sendMessage(Messages.getNoPermissionMessage("teisyoku.admin"));
                return true;
            }

            if(args[1].equalsIgnoreCase("set")){
                if(! (args.length == 4)){
                    sender.sendMessage(Messages.getDenyPrefix() + "引数が多すぎるかまたは少なすぎます");
                    HelpMessage(sender, cmd);
                    AdminHelpMessage(sender, cmd);
                    return true;
                }

                //オンラインかどうかの確認
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[2]);
                if(! offlinePlayer.isOnline()){
                    sender.sendMessage(Messages.getDenyPrefix() + ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんはオンラインでない為操作できません");
                    return true;
                }

                //正常処理
                Player onlinePlayer = (Player) offlinePlayer;
                setConfig((Player) offlinePlayer, args[3]);
                sender.sendMessage(Messages.getSuccessPrefix() + ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんのニックネームを " + ChatColor.AQUA + args[3] + ChatColor.RESET +" に設定しました");
                onlinePlayer.sendMessage(Messages.getSuccessPrefix() + ChatColor.YELLOW + sender.getName() + ChatColor.RESET + "さんによってニックネームを " + ChatColor.AQUA + args[3] + ChatColor.RESET + " に設定されました");
                return true;
            }

            if(args[1].equalsIgnoreCase("remove")){
                if(! (args.length == 3)){
                    sender.sendMessage(Messages.getDenyPrefix() + "引数が多すぎるかまたは少なすぎます");
                    HelpMessage(sender, cmd);
                    AdminHelpMessage(sender, cmd);
                    return true;
                }

                //オンラインかどうかの確認
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[2]);
                if(! offlinePlayer.isOnline()){
                    sender.sendMessage(Messages.getDenyPrefix() + ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんはオンラインでない為操作できません");
                    return true;
                }

                //正常処理
                Player onlinePlayer = (Player) offlinePlayer;
                removeConfig((Player) offlinePlayer);
                sender.sendMessage(Messages.getSuccessPrefix() + ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんのニックネームを削除しました");
                onlinePlayer.sendMessage(Messages.getSuccessPrefix() + ChatColor.YELLOW + sender.getName() + ChatColor.RESET + "さんによってニックネームを削除されました");
                return true;
            }
            return true;
        }

        //ヘルプ
        if(args[0].equalsIgnoreCase("help")){
            HelpMessage(sender, cmd);
            if(sender.hasPermission("teisyoku.admin")){
                AdminHelpMessage(sender, cmd);
            }
            return true;
        }

        //下記コマンドに侵入するにはsenderがplayerである必要があります
        if(!(sender instanceof Player)){
            sender.sendMessage(Messages.getDenyPrefix() + "コンソールからコマンドを送信することはできません");
            sender.sendMessage(Messages.getDenyPrefix() + "ただし /"+ cmd.getName() +" admin は利用可能です");
            return true;
        }

        //player変数を用意して処理を楽に
        Player player = (Player) sender;

        //一般：set
        if(args[0].equalsIgnoreCase("set")){
            if(args.length == 2){
                if(args[1].length() > 10){
                    sender.sendMessage(Messages.getDenyPrefix() + "ニックネームは10文字以下に設定してください");
                    return true;
                }
                else{
                    setConfig(player, args[1]);
                    sender.sendMessage(Messages.getSuccessPrefix() + "ニックネームを " + ChatColor.AQUA + args[1] + ChatColor.RESET + " に設定しました");
                    return true;
                }
            }
            else{
                sender.sendMessage(Messages.getDenyPrefix() + "引数が多すぎるかまたは少なすぎます");
                HelpMessage(sender, cmd);
                return true;
            }
        }

        //一般：remove
        if(args[0].equalsIgnoreCase("remove")){
            if(args.length == 1){
                removeConfig(player);
                sender.sendMessage(Messages.getSuccessPrefix() + "ニックネームを削除しました");
                return true;
            }
            else{
                sender.sendMessage(Messages.getDenyPrefix() + "引数が多すぎるかまたは少なすぎます");
                HelpMessage(sender, cmd);
                return true;
            }
        }

        sender.sendMessage(Messages.getDenyPrefix() + "引数 " + args[0].toString()  + " は存在しません");
        return true;
    }

    public void HelpMessage(CommandSender sender , Command cmd){
        sender.sendMessage(Messages.getSuccessPrefix() + cmd.getName() + "コマンドのヘルプ" + ChatColor.GRAY +"(開発協力:azuhataさん)");
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " set <ﾆｯｸﾈｰﾑ>", "自分のﾆｯｸﾈｰﾑをｾｯﾄします"));
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " remove","自分のﾆｯｸﾈｰﾑを削除します"));
    }

    public void AdminHelpMessage(CommandSender sender , Command cmd){
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " admin set <ﾌﾟﾚｲﾔｰ名> <ﾆｯｸﾈｰﾑ>","他ﾌﾟﾚｲﾔｰのﾆｯｸﾈｰﾑをｾｯﾄします"));
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " admin remove <ﾌﾟﾚｲﾔｰ名>","他ﾌﾟﾚｲﾔｰのﾆｯｸﾈｰﾑを削除します"));
    }

    private void setConfig(Player player, String NickName) {
        TeisyokuPlugin2.getInstance().NickConfig.set(player.getPlayer().getUniqueId().toString() + ".id", player.getName().toString());
        TeisyokuPlugin2.getInstance().NickConfig.set(player.getPlayer().getUniqueId().toString() + ".nick", NickName.toString());
        TeisyokuPlugin2.getInstance().saveNickConfig();
    }

    private void removeConfig(Player player) {
        TeisyokuPlugin2.getInstance().NickConfig.set(player.getPlayer().getUniqueId().toString(), null);
        TeisyokuPlugin2.getInstance().saveNickConfig();
    }
}
