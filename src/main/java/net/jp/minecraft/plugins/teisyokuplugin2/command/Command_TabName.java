package net.jp.minecraft.plugins.teisyokuplugin2.command;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.api.API;
import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Color;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Replace;
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
public class Command_TabName implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.tabname")) {
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
                    Permission.TABNAME,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!API.hasPermission(sender, Permission.USER, Permission.TABNAME, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.TABNAME);
            return true;
        }

        if (!(sender instanceof Player)) {
            Msg.warning(sender, "コンソールからコマンドを送信することはできません");
            return true;
        }

        if (args.length > 1) {
            help(sender, commandLabel);
            return true;
        }
        Player p = (Player) sender;
        if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("*")) {
            p.setPlayerListName(p.getName());
            Msg.success(p, "タブリストのエントリ名を通常に戻しました");
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {
            help(sender, commandLabel);
            return true;
        } else {
            p.setPlayerListName(Color.convert(Replace.blank(args[0])) + ChatColor.RESET);
            Msg.success(p, "タブリストのエントリ名を「" + Color.convert(Replace.blank(args[0])) + ChatColor.RESET + "」に更新しました");
            return true;
        }
    }

    /**
     * tabnameコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    public void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel, "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <文字列>", "タブリストを指定した文字列に更新します");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
