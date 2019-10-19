package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.API.API;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Enum.Permission;
import net.jp.minecraft.plugins.Utility.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 * TODO: コマンド名が現在の利用趣旨と差異がある
 */
public class Command_Last implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.last")) {
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
                    Permission.LAST,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!(sender.hasPermission(Permission.USER.toString()) || sender.hasPermission(Permission.LAST.toString()) || sender.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(sender, Permission.LAST);
            return true;
        }

        //プレイヤー名であるか確認
        if (!API.isPlayerName(args[0])) {
            Msg.warning(sender, ChatColor.YELLOW + args[0] + ChatColor.RESET + "はプレイヤー名ではありません");
            return true;
        }

        //引数1からUUIDを取得
        else {
            try {
                UUID uuid = UUIDFetcher.getUUIDOf(args[0]);

                if (uuid == null) {
                    Msg.warning(sender, ChatColor.YELLOW + args[0] + ChatColor.RESET + "は存在しないプレイヤーです");
                    return true;
                }

                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

                if (TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.get(uuid + ".JoinDate") == null) {
                    Msg.warning(sender, ChatColor.YELLOW + args[0] + ChatColor.RESET + "のデータは見つかりませんでした");
                    return true;
                }
                String joinDate = TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.getString(uuid + ".JoinDate");
                String quitDate = TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.getString(uuid + ".QuitDate");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
                String firstPlayed = sdf.format(player.getFirstPlayed());

                Msg.info(sender, ChatColor.YELLOW + args[0] + ChatColor.RESET + "さんのデータ");
                Msg.success(sender, ChatColor.RESET + "最初のログイン " + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + firstPlayed);
                Msg.success(sender, ChatColor.RESET + "最終ログイン   " + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + joinDate);
                Msg.success(sender, ChatColor.RESET + "最終ログアウト " + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + quitDate);
                return true;
            } catch (Exception e) {
                Msg.warning(sender, "不明なエラーが発生しました");
                return true;
            }
        }
    }

    /**
     * lastコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + " <プレイヤー名>", "プレイヤーの最終ログイン日時等を取得します");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
