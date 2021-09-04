package net.jp.minecraft.plugins.teisyokuplugin2.command;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.module.PlayerDatabase;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import net.jp.minecraft.plugins.teisyokuplugin2.util.PlayerUtil;
import net.jp.minecraft.plugins.teisyokuplugin2.util.TimeUtil;
import net.jp.minecraft.plugins.teisyokuplugin2.util.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Command_PlayerData implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.player_data")) {
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
                    Permission.PLAYER_DATA,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!Permission.hasPermission(sender, Permission.USER, Permission.PLAYER_DATA, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.PLAYER_DATA);
            return true;
        }

        //プレイヤー名であるか確認
        if (!PlayerUtil.isPlayerName(args[0])) {
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

                if (PlayerDatabase.getString(player, "join.date").isEmpty() || PlayerDatabase.getString(player, "quit.date").isEmpty()) {
                    Msg.warning(sender, ChatColor.YELLOW + args[0] + ChatColor.RESET + "のデータは見つかりませんでした");
                    return true;
                }
                String joinDate = PlayerDatabase.getString(player, "join.date");
                String quitDate = PlayerDatabase.getString(player, "quit.date");

                SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.getDateFormat());
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
        Msg.commandFormat(sender, commandLabel + " <プレイヤー名>", "プレイヤーデータを表示");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
