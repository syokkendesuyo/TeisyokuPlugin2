package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.API.API_PlayerDatabase;
import net.jp.minecraft.plugins.GUI.GUI_PlayersList;
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
public class Command_Players implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.players")) {
            Msg.commandNotEnabled(sender, commandLabel);
            return true;
        }

        //引数が0だった場合
        if (args.length == 0) {
            showPlayersList(sender, commandLabel);
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
                    Permission.PLAYERS,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!(sender.hasPermission(Permission.USER.toString()) || sender.hasPermission(Permission.PLAYERS.toString()) || sender.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(sender, Permission.PLAYERS);
            return true;
        }

        if (args.length == 1) {
            if (!(args[0].equalsIgnoreCase("total"))) {
                showPlayersList(sender, commandLabel);
                return true;
            }
            Msg.info(sender, "総計 " + API_PlayerDatabase.getTotalPlayers() + " 名のプレイヤーがこのサーバで遊びました");
            return true;
        }
        showPlayersList(sender, commandLabel);
        return true;
    }

    /**
     * プレイヤーリストを表示<br />
     * 送信者がコンソールの場合、ヘルプを表示します<br />
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void showPlayersList(CommandSender sender, String commandLabel) {
        if (sender instanceof Player) {
            GUI_PlayersList.getPlayersList((Player) sender);
            return;
        }
        help(sender, commandLabel);
    }

    /**
     * playersコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        if (sender instanceof Player) {
            Msg.commandFormat(sender, commandLabel, "オンラインのプレイヤーリストを表示");
        } else {
            Msg.commandFormat(sender, commandLabel, "ヘルプを表示");
        }
        Msg.commandFormat(sender, commandLabel + " total", "ログインしたプレイヤー数を表示");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
