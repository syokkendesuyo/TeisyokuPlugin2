package net.jp.minecraft.plugins.Commands;

import com.google.common.base.Joiner;
import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Sounds;
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
 * Callコマンドを実行時の処理
 */
public class Command_Call implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        //引数が0だった場合
        if (args.length == 0) {
            help(sender, commandLabel);
            return true;
        }

        //パーミッションの確認コマンド
        if (args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("perms") || args[0].equalsIgnoreCase("permission")) {
            Msg.checkPermission(sender, Permissions.getTeisyokuUserPermisson());
            return true;
        }

        //引数が1だった場合
        if (args.length == 1) {
            Msg.warning(sender, "メッセージがありません！");
            help(sender, commandLabel);
            return true;
        }

        //プレイヤーの変数を作成
        Player player = Bukkit.getServer().getPlayer(args[0]);

        //プレイヤーがオンラインであればメッセージを送信
        if (!(player == null)) {
            String arg = Joiner.on(' ').join(args);
            String noneName = arg.replaceAll(args[0], "");
            String argReplace = noneName.replaceAll("&", "§");

            Msg.success(sender, ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " さんにメッセージを送信しました" + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + argReplace);
            Msg.success(player, ChatColor.YELLOW + sender.getName() + ChatColor.GRAY + " さんからメッセージ" + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + argReplace);

            Sounds.sound_note(player);

            //コンソールなどには音を鳴らせないので送信先がプレイヤーかどうか確認する
            if (sender instanceof Player) {
                Player player1 = (Player) sender;
                Sounds.sound_arrow(player1);
            }
            return true;
        } else {
            Msg.warning(sender, ChatColor.YELLOW + args[0] + ChatColor.RESET + " さんは現在オフラインです");
            return true;
        }
    }

    //ヘルプ関数
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + " <プレイヤー> <メッセージ>", "音付きで個人メッセージを送信");
    }
}
