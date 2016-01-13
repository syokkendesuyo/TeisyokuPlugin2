package net.jp.minecraft.plugins;

import com.google.common.base.Joiner;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 *
 * Callコマンドを実行時の処理
 */
public class Command_Call implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

        //引数が0だった場合
        if(args.length == 0){
            help(sender,commandLabel);
            return true;
        }

        //パーミッションの確認コマンド
        if(args[0].equalsIgnoreCase("perm")||args[0].equalsIgnoreCase("perms")||args[0].equalsIgnoreCase("permission")){
            Msg.checkPermission((Player)sender , Permissions.getTeisyokuUserPermisson());
            return true;
        }

        //引数が1だった場合
        if(args.length == 1){
            Msg.warning((Player)sender , "メッセージがありません！");
            help(sender, commandLabel);
            return true;
        }

        //プレイヤーの変数を作成
        OfflinePlayer player =  Bukkit.getOfflinePlayer(args[0]);

        //プレイヤーがオンラインであればメッセージを送信
        if(player.isOnline()){
            String arg = Joiner.on(' ').join(args);
            String noneName = arg.replaceAll(args[0].toString(),"");
            String argReplace = noneName.replaceAll("&","§");

            Player player2 = (Player) player;
            sender.sendMessage(Messages.getCallPrefix() + ChatColor.YELLOW + player2.getName().toString() + ChatColor.GRAY + " さんにメッセージを送信しました" + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + argReplace);
            player2.sendMessage(Messages.getCallPrefix() + ChatColor.YELLOW + sender.getName().toString() + ChatColor.GRAY + " さんからメッセージ" + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + argReplace);

            player2.playSound(player2.getLocation(), Sound.NOTE_PLING, 3.0F, 1.8F);

            //コンソールなどには音を鳴らせないので送信先がプレイヤーかどうか確認する
            if(sender instanceof Player){
                Player player1 = (Player) sender;
                player1.playSound(player1.getLocation(), Sound.SHOOT_ARROW, 3.0F, 1.8F);
            }
            return true;
        }
        else{
            sender.sendMessage(Messages.getDenyPrefix() + ChatColor.YELLOW + args[0] + ChatColor.RESET + " さんは現在オフラインです");
            return true;
        }
    }

    //ヘルプ関数
    public void help(CommandSender sender , String commandLabel){
        Msg.success((Player) sender, "コマンドのヘルプ");
        Msg.commandFormat((Player)sender , commandLabel.toString() + " <プレイヤー> <メッセージ>", "音付きで個人メッセージを送信");
    }
}
