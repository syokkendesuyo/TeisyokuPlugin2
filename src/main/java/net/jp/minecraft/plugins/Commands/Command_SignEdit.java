package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.API.API;
import net.jp.minecraft.plugins.Enum.Permission;
import net.jp.minecraft.plugins.Listener.Listener_SignEdit;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Color;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Replace;
import org.bukkit.ChatColor;
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
public class Command_SignEdit implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.signedit")) {
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
                    Permission.SIGNEDIT,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!API.hasPermission(sender, Permission.USER, Permission.SIGNEDIT, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.SIGNEDIT);
            return true;
        }

        if (!(sender instanceof Player)) {
            Msg.warning(sender, "プレイヤーのみ利用できるコマンドです");
            help(sender, commandLabel);
            return true;
        }

        if (!(args.length == 2)) {
            help(sender, commandLabel);
            return true;
        }

        int line;
        try {
            line = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Msg.warning(sender, "行番号は数値で入力してください");
            return true;
        }
        if (line >= 1 && line <= 4) {
            Listener_SignEdit.saveData((Player) sender, args[0], line);
            Msg.success(sender, "更新したい看板を右クリックしてください。 " + Replace.blank(Color.convert(args[0])) + ChatColor.GRAY + " @ " + ChatColor.RESET + line);
            return true;
        }
        Msg.warning(sender, "行番号は1から4までの数値で入力してください");

        return true;
    }

    /**
     * signコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel, "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <更新する文字列> <行番号>", "指定した行の看板を更新します");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
