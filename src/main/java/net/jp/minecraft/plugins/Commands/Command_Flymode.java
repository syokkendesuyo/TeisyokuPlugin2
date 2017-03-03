package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Listener.Listener_Flymode;
import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 * <p>
 * flyコマンドを実行時の処理
 */
public class Command_Flymode implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        /**
         * コンソールからのコマンドは拒否
         */

        if (!(sender instanceof Player)) {
            Msg.warning(sender, "コンソールからコマンドを送信することはできません");
        }

        //コンソール以外からの指令なのでプレイヤーと断定
        Player player = (Player) sender;


        /**
         * コマンドの利用権限を確認
         */

        //パーミッションの確認(コマンド側)
        if ( !(player.getWorld().getName().equalsIgnoreCase("flat")) && !(player.hasPermission(Permissions.getFlyCommandPermisson()))) {
            Msg.noPermissionMessage(sender, Permissions.getFlyCommandPermisson());
            return true;
        }


        /**
         * 例外処理
         */

        //引数が0だった場合
        if (args.length == 0) {
            Msg.info(sender, "利用方法： /fly <true/false> (Player)");
            return true;
        }

        //引数が1より大きかった場合
        else if (args.length > 2) {
            Msg.warning(sender, "引数が多すぎです");
            Msg.warning(sender, "利用方法： /fly <true/false> (Player)");
            return true;
        }


        /**
         * 引数が1つの場合の処理
         */
        if (args.length == 1) {
            //引数1がtrueまたはenableだった場合flyモードを開始
            if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("enable")) {
                Listener_Flymode.enable_fly(player);
                return true;
            }

            //引数1がfalseまたはdisableだった場合flyモードを終了
            else if (args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("disable")) {
                Listener_Flymode.disable_fly(player);
                return true;
            }

            //パーミッションの確認コマンド
            else if (args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("permission")) {
                Msg.checkPermission(sender, Permissions.getFlyCommandPermisson());
                Msg.checkPermission(sender, Permissions.getFlyPermisson());
                return true;
            }

            //その他の場合
            else {
                Msg.warning(sender, "引数「" + args[0] + "」は存在しません");
                Msg.warning(sender, "利用方法： /fly <true/false> (Player)");
                return true;
            }
        } else if (args.length == 2) {
            Player target_player = Bukkit.getServer().getPlayer(args[1]);
            if (!(target_player == null)) {

                //パーミッションの確認(コマンド側)
                if (!(player.hasPermission(Permissions.getFlyCommandPermisson()))) {
                    Msg.noPermissionMessage(sender, Permissions.getFlyCommandPermisson());
                    return true;
                }

                //正常に処理
                if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("enable")) {
                    Listener_Flymode.enable_fly(target_player);
                    Msg.success(player, ChatColor.YELLOW + args[1] + ChatColor.RESET + " のFlyモードを" + ChatColor.GREEN + " 有効 " + ChatColor.RESET + "にしました");
                    return true;
                }

                //引数1がfalseまたはdisableだった場合flyモードを終了
                else if (args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("disable")) {
                    Listener_Flymode.disable_fly(target_player);
                    Msg.success(player, ChatColor.YELLOW + args[1] + ChatColor.RESET + " のFlyモードを" + ChatColor.RED + " 無効 " + ChatColor.RESET + "にしました");
                    return true;
                }
                //その他の場合
                else {
                    Msg.warning(sender, "引数「" + args[0] + "」は存在しません");
                    Msg.warning(sender, "利用方法： /fly <true/false> (Player)");
                    return true;
                }
            } else {
                //プレイヤーが居ないのでエラー
                Msg.warning(sender, "プレイヤー " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " はオンラインではありません");
                return true;
            }
        }
        return true;
    }
}
