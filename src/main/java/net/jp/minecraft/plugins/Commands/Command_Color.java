package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.API.API;
import net.jp.minecraft.plugins.Enum.Permission;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Command_Color implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.color")) {
            Msg.commandNotEnabled(sender, commandLabel);
            return true;
        }

        if (args.length > 0) {
            //ヘルプ
            if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                help(sender, commandLabel);
                return true;
            }

            //パーミッションの確認コマンドを追加
            if (args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("perms") || args[0].equalsIgnoreCase("permission")) {
                Msg.checkPermission(sender, Permission.COLOR);
                return true;
            }
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!API.hasPermission(sender, Permission.USER, Permission.COLOR, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.COLOR);
            return true;
        }

        Msg.success(sender, "カラーコード一覧");
        Msg.info(sender, "§00 §11 §22 §33 §44 §55 §66 §77 §88 §99 §aa §bb §cc §dd §ee §ff");
        Msg.info(sender, "l§l:太字§r  m:§m打消し線§r  n:§n下線§r  o:§o斜体§r  r:リセット");
        return true;
    }

    /**
     * cartコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + "", "カラーコードを表示");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
