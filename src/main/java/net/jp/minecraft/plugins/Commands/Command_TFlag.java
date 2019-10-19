package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.API.API_Flag;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Enum.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Command_TFlag implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.tflag")) {
            Msg.commandNotEnabled(sender, commandLabel);
            return true;
        }

        //引数が0だった場合
        if (args.length == 0) {
            help(sender, commandLabel);
            return true;
        }

        //ヘルプ
        if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
            help(sender, commandLabel);
            return true;
        }

        //パーミッションの確認コマンドを追加
        if (args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("perms") || args[0].equalsIgnoreCase("permission")) {
            Msg.checkPermission(sender,
                    Permission.USER,
                    Permission.TFLAG,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!(sender.hasPermission(Permission.USER.toString()) || sender.hasPermission(Permission.TFLAG.toString()) || sender.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(sender, Permission.TFLAG);
            return true;
        }

        //コンソールからのコマンドを拒否
        if (!(sender instanceof Player)) {
            Msg.warning(sender, "コンソールからコマンドを送信することはできません");
            // TODO: フラグ設定をコンソールまたは管理者が設定可能にする
            return true;
        }

        if (args.length == 1 || args.length > 2) {
            help(sender, commandLabel);
            return true;
        }

        Player player = (Player) sender;
        if (args[0].equalsIgnoreCase("call_sounds")) {
            API_Flag.set(sender, player, args, "callコマンドの呼び出し音設定");
            return true;
        } else if (args[0].equalsIgnoreCase("cart_auto_collect")) {
            API_Flag.set(sender, player, args, "マインカートを自動でインベントリに保存");
            return true;
        } else if (args[0].equalsIgnoreCase("fly_save_state")) {
            API_Flag.set(sender, player, args, "飛行モードの状態をログイン時に継承する設定");
            return true;
        } else if (args[0].equalsIgnoreCase("sign_info")) {
            API_Flag.set(sender, player, args, "看板データの照会機能利用設定");
            return true;
        } else if (args[0].equalsIgnoreCase("sign_info_cart")) {
            API_Flag.set(sender, player, args, "[Cart]看板の看板データの照会機能利用設定");
            return true;
        } else if (args[0].equalsIgnoreCase("sign_cart")) {
            API_Flag.set(sender, player, args, "[Cart]看板の利用設定");
            return true;
        } else {
            Msg.warning(player, "「" + args[0] + "」は利用できません。");
        }
        return true;
    }

    /**
     * tflagコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel, "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <フラグ名> <true/false>", "フラグ名を指定してフラグを設定");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
