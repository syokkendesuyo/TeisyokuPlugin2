package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.API.API_Trash;
import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Command_Trash implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.TeisyokuConfig.getBoolean("functions.trash")) {
            Msg.warning(sender, "trash,gomi,gomibakoコマンドは有効化されていません");
            return true;
        }

        //引数がない場合
        if (args.length == 0) {
            //コンソールからの送信は弾く
            if (!(sender instanceof Player)) {
                help(sender, commandLabel);
                return true;
            }
            if (sender.hasPermission(Permission.TRASH.toString())) {
                API_Trash.open(sender);
                return true;
            }
            Msg.noPermissionMessage(sender, Permission.TRASH);
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
                    Permission.TRASH,
                    Permission.ADMIN
            );
            return true;
        }
        Msg.warning(sender, "引数「" + args[0] + "」は存在しません");
        return true;
    }

    /**
     * gomi,gomibako,trashコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + "", "ゴミ箱を開く");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
