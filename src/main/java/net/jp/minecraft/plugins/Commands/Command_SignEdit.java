package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Listener.Listener_SignEdit;
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
public class Command_SignEdit implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

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
            Msg.success(sender, "更新したい看板を右クリックしてください。 " + blank(color(args[0])) + ChatColor.GRAY + " @ " + ChatColor.RESET + line);
            return true;
        }
        Msg.warning(sender, "行番号は1から4までの数値で入力してください");

        return true;
    }

    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "Sign コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + " <更新する文字列> <行番号>", "指定した行の看板を更新します");
    }

    public static String color(String str) {
        return str.replace("&", "§");
    }
    public static String blank(String str) {
        return str.replace("%%", " ");
    }
}
