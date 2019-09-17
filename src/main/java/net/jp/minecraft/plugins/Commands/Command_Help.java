package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Command_Help implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.TeisyokuConfig.getBoolean("functions.help")) {
            Msg.commandNotEnabled(sender, commandLabel);
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
