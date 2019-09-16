package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Command_Help implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.TeisyokuConfig.getBoolean("functions.help")) {
            Msg.warning(sender, "helpコマンドは有効化されていません");
            return true;
        }

        //パーミッションの確認
        if (!sender.hasPermission(Permission.HELP.toString())) {
            Msg.noPermissionMessage(sender, Permission.HELP);
            return true;
        }

        Msg.sendTeisyokuHelp(sender);
        return true;
    }
}
