package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.applet.resources.MsgAppletViewer_de;

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
                    Msg.warning(sender, "引数が多すぎるかまたは少なすぎます");
                    HelpMessage(sender, cmd);
                    AdminHelpMessage(sender, cmd);
                    return true;
                }

                //オンラインかどうかの確認
                Player player = Bukkit.getServer().getPlayer(args[2]);
                if( !(player == null) ){
                    //正常処理
                    Player onlinePlayer = player;
                    setConfig(player, args[3]);
                    Msg.success(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + " さんのニックネームを " + ChatColor.AQUA + args[3] + ChatColor.RESET +" に設定しました");
                    Msg.success(onlinePlayer, ChatColor.YELLOW + sender.getName() + ChatColor.RESET + " さんによってニックネームを " + ChatColor.AQUA + args[3] + ChatColor.RESET + " に設定されました");
                    return true;
                }
                else{
                    //プレイヤーが居ない場合の処理
                    Msg.warning(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんはオンラインでない為操作できません");
                    return true;
                }
            }

            if(args[1].equalsIgnoreCase("remove")){
                if(! (args.length == 3)){
                    Msg.success(sender, "引数が多すぎるかまたは少なすぎます");
                    HelpMessage(sender, cmd);
                    AdminHelpMessage(sender, cmd);
                    return true;
                }

                //オンラインかどうかの確認
                Player player = Bukkit.getServer().getPlayer(args[2]);
                if( !(player == null) ){
                    //正常処理
                    Player onlinePlayer = player;
                    removeConfig(player);
                    Msg.success(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんのニックネームを削除しました");
                    Msg.success(onlinePlayer, ChatColor.YELLOW + sender.getName() + ChatColor.RESET + "さんによってニックネームを削除されました");
                    return true;
                }
                else{
                    //プレイヤーが居ない場合の処理
                    Msg.warning(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんはオンラインでない為操作できません");
                    return true;
                }
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
            Msg.success(sender, "コンソールからコマンドを送信することはできません");
            Msg.success(sender, "ただし /"+ cmd.getName() +" admin は利用可能です");
            return true;
        }

        //player変数を用意して処理を楽に
        Player player = (Player) sender;

        //一般：set
        if(args[0].equalsIgnoreCase("set")){
            if(args.length == 2){
                if(args[1].length() > 10){
                    Msg.warning(sender, "ニックネームは10文字以下に設定してください");
                    return true;
                }
                else{
                    setConfig(player, args[1]);
                    Msg.success(sender, "ニックネームを " + ChatColor.AQUA + args[1] + ChatColor.RESET + " に設定しました");
                    return true;
                }
            }
            else{
                Msg.warning(sender, "引数が多すぎるかまたは少なすぎます");
                HelpMessage(sender, cmd);
                return true;
            }
        }

        //一般：remove
        if(args[0].equalsIgnoreCase("remove")){
            if(args.length == 1){
                removeConfig(player);
                Msg.success(sender, "ニックネームを削除しました");
                return true;
            }
            else{
                Msg.warning(sender, "引数が多すぎるかまたは少なすぎます");
                HelpMessage(sender, cmd);
                return true;
            }
        }

        Msg.warning(sender, "引数 " + args[0].toString()  + " は存在しません");
        return true;
    }

    public void HelpMessage(CommandSender sender , Command cmd){
        Msg.success(sender, "コマンドのヘルプ" + ChatColor.GRAY +"(開発協力:azuhataさん)");
        Msg.commandFormat(sender, cmd.getName() + " set <ﾆｯｸﾈｰﾑ>", "自分のﾆｯｸﾈｰﾑをｾｯﾄします");
        Msg.commandFormat(sender, cmd.getName() + " remove","自分のﾆｯｸﾈｰﾑを削除します");
    }

    public void AdminHelpMessage(CommandSender sender , Command cmd){
        Msg.commandFormat(sender, cmd.getName() + " admin set <ﾌﾟﾚｲﾔｰ名> <ﾆｯｸﾈｰﾑ>","他ﾌﾟﾚｲﾔｰのﾆｯｸﾈｰﾑをｾｯﾄします");
        Msg.commandFormat(sender, cmd.getName() + " admin remove <ﾌﾟﾚｲﾔｰ名>","他ﾌﾟﾚｲﾔｰのﾆｯｸﾈｰﾑを削除します");
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
