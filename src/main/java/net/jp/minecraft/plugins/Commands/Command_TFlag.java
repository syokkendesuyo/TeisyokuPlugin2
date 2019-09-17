package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.API.API_Flag;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Command_TFlag implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        //コンソールからのコマンドを拒否
        if (!(sender instanceof Player)) {
            Msg.warning(sender, "コンソールからコマンドを送信することはできません");
            // TODO: フラグ設定をコンソールまたは管理者が設定可能にする
            return true;
        }

        //引数が多い場合
        if (args.length > 2) {
            help(sender, commandLabel);
            return true;
        }

        //引数が少ない場合
        if (args.length == 0) {
            help(sender, commandLabel);
            return true;
        } else if (args.length == 1) {
            help(sender, commandLabel);
            return true;
        }

        //正常実行
        else if (args.length == 2) {
            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("call_sounds")) {
                API_Flag.update(sender, player, args, "callコマンドの呼び出し音設定");
                return true;
            } else if (args[0].equalsIgnoreCase("cart_auto_collect")) {
                API_Flag.update(sender, player, args, "マインカートを自動でインベントリに保存");
                return true;
            } else if (args[0].equalsIgnoreCase("fly_save_state")) {
                API_Flag.update(sender, player, args, "飛行モードの状態をログイン時に継承する設定");
                return true;
            } else if (args[0].equalsIgnoreCase("sign_info")) {
                API_Flag.update(sender, player, args, "看板データの照会機能利用設定");
                return true;
            } else if (args[0].equalsIgnoreCase("sign_info_cart")) {
                API_Flag.update(sender, player, args, "[Cart]看板の看板データの照会機能利用設定");
                return true;
            } else if (args[0].equalsIgnoreCase("sign_cart")) {
                API_Flag.update(sender, player, args, "[Cart]看板の利用設定");
                return true;
            } else {
                Msg.warning(player, "引数「" + args[0] + "」は利用できません。");
            }
        }
        return true;
    }

    //ヘルプ関数
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + " <フラグ名> <true/false>", "フラグ名を指定してフラグを設定");
    }
}
