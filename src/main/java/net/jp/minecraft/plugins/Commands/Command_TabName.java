package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */


public class Command_TabName implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            Msg.warning(sender, "コンソールからコマンドを送信することはできません");
            return true;
        }
        if (sender.hasPermission("teisyoku.admin")) {
            if (args.length > 1) {
                Msg.warning(sender, "引数が多すぎます");
                help(sender, commandLabel);
                return true;
            }
            if (args.length == 0) {
                help(sender, commandLabel);
                return true;
            } else {
                Player p = (Player) sender;
                if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("*")) {
                    p.setPlayerListName(p.getName());
                    Msg.success(p, "TabListのエントリ名を通常に戻しました");
                    return true;
                } else if (args[0].equalsIgnoreCase("help")) {
                    help(sender, commandLabel);
                    return true;
                } else {
                    String string = args[0];
                    String strReplace = string.replaceAll("&", "§");
                    String strReplace2 = strReplace.replaceAll("%%", " ");
                    p.setPlayerListName(strReplace2 + ChatColor.RESET);
                    Msg.success(p, "TabListのエントリ名を「" + strReplace2 + ChatColor.RESET + "」に更新しました");
                    return true;
                }
            }
        } else {
            Msg.noPermissionMessage(sender, "teisyoku.admin");
            return true;
        }
    }

    //ヘルプ関数
    public void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + " <文字列>", "TabListを指定した文字列に更新します");
    }
}
