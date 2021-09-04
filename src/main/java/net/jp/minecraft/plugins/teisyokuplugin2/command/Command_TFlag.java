package net.jp.minecraft.plugins.teisyokuplugin2.command;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.module.TFlag;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.ChatColor;
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
                    Permission.TFLAG_SEARCH,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!Permission.hasPermission(sender, Permission.USER, Permission.TFLAG, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.TFLAG);
            return true;
        }

        //ステイタスを確認
        if (args[0].equalsIgnoreCase("status") || args[0].equalsIgnoreCase("show") || args[0].equalsIgnoreCase("list")) {
            if (args.length > 1) {
                if (!sender.getName().equals(args[1]) && !sender.hasPermission(Permission.TFLAG_SEARCH.toString())) {
                    Msg.noPermissionMessage(sender, Permission.TFLAG_SEARCH);
                    return true;
                }
                TFlag.showTFlagStatus(sender, args[1]);
                return true;
            } else {
                if (sender instanceof Player) {
                    TFlag.showTFlagStatus(sender, sender.getName());
                    return true;
                }
                help(sender, commandLabel);
                return true;
            }
        }

        //コンソールからのコマンドを拒否
        if (!(sender instanceof Player)) {
            Msg.warning(sender, "コンソールからコマンドを送信することはできません");
            // TODO: フラグ設定をコンソールまたは管理者が設定可能にする
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1 || args.length > 2) {
            help(player, commandLabel);
            return true;
        }

        //フラグが存在するか確認
        if (!TFlag.contains(args[0])) {
            Msg.warning(player, "フラグ名 " + ChatColor.YELLOW + args[0] + ChatColor.RESET + " は存在しません");
            return true;
        }
        TFlag tFlag = TFlag.getTFlag(args[0]);
        TFlag.setTFlagStatus(sender, player, tFlag, args[1]);
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
        if (sender instanceof Player) {
            Msg.commandFormat(sender, commandLabel + " <status|show|list> (プレイヤー名)", "現在のフラグ状態を表示");
        } else {
            Msg.commandFormat(sender, commandLabel + " <status|show|list> <プレイヤー名>", "現在のフラグ状態を表示");
        }
        Msg.commandFormat(sender, commandLabel + " <フラグ名> <true/false>", "フラグ名を指定してフラグを設定");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
