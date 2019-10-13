package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Config.GiftConfigAPI;
import net.jp.minecraft.plugins.TeisyokuMenuIndex;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

import static net.jp.minecraft.plugins.Utility.Msg.commandFormat;
import static net.jp.minecraft.plugins.Utility.Msg.success;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 * TODO: リファクタリング
 */
public class Command_Teisyoku implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {
        if (!(sender.hasPermission(Permission.USER.toString()))) {
            Msg.noPermissionMessage(sender, Permission.USER);
            return true;
        }

        if (args.length == 0) {
            teisyoku(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            help(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("rl")) {
            if (!sender.hasPermission(Permission.ADMIN.toString())) {
                Msg.noPermissionMessage(sender, Permission.ADMIN);
                return true;
            }
            // TODO: CustomConfigに置き換える
            TeisyokuPlugin2.getInstance().configTeisyoku.reloadConfig();
            TeisyokuPlugin2.getInstance().configGift.reloadConfig();
            TeisyokuPlugin2.getInstance().configRailways.reloadConfig();
            TeisyokuPlugin2.getInstance().reloadLastPlayerJoinConfig();
            TeisyokuPlugin2.getInstance().reloadHorseConfig();
            TeisyokuPlugin2.getInstance().reloadTPointSettingsConfig();
            success(sender, "TeisyokuPlugin2のコンフィグをリロードしました。");
            return true;
        }

        if (args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("version")) {
            success(sender, ChatColor.GOLD + "TeisyokuPlugin2 Version" + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + TeisyokuPlugin2.getInstance().getDescription().getVersion());
            return true;
        }

        //　TODO: 外部コマンド化
        if (args[0].equalsIgnoreCase("gift") || args[0].equalsIgnoreCase("giftcode")) {
            if (!(sender.hasPermission(Permission.USER.toString()) || sender.hasPermission(Permission.GIFT.toString()) || sender.hasPermission(Permission.ADMIN.toString()))) {
                Msg.noPermissionMessage(sender, Permission.GIFT);
                return true;
            }
            if (args.length == 2) {
                Msg.info(sender, "手続き中です...");
                GiftConfigAPI.gift(args[1], sender);
                return true;
            } else {
                Msg.warning(sender, "引数が多すぎるか、または少なすぎます");
                return true;
            }
        }
        Msg.warning(sender, "引数 " + args[0] + " は存在しません");
        return true;
    }

    private void teisyoku(CommandSender sender) {
        if (!(sender instanceof Player)) {
            help(sender);
            return;
        }

        if (!(sender.hasPermission(Permission.USER.toString()) || sender.hasPermission(Permission.TEISYOKU_MENU.toString()) || sender.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(sender, Permission.TEISYOKU_MENU);
            return;
        }

        Player player = (Player) sender;
        TeisyokuMenuIndex.getMenu(player);
    }

    /**
     * ヘルプを表示
     *
     * @param sender 送信先
     */
    private void help(CommandSender sender) {
        success(sender, "TeisyokuPlugin2のヘルプ");
        commandFormat(sender, "ad", "広告をサーバー全体に送信");
        commandFormat(sender, "call", "プレイヤーサウンド付きで呼び出し");
        commandFormat(sender, "cart", "マインカートをインベントリに追加");
        commandFormat(sender, "color", "カラーコード表示");
        commandFormat(sender, "fly", "飛行モード");
        commandFormat(sender, "help", "ヘルプを開く");
        commandFormat(sender, "horse", "馬の保護");
        commandFormat(sender, "last", "最終ログイン日時等を表示");
        commandFormat(sender, "nick", "ニックネームを設定");
        commandFormat(sender, "players", "ログイン中のプレイヤーを表示");
        commandFormat(sender, "ri", "RailwaysInfoに関するコマンド");
        commandFormat(sender, "se", "看板編集コマンド");
        commandFormat(sender, "t", "便利機能GUIを開く");
        commandFormat(sender, "tflag", "個人設定コマンド");
        commandFormat(sender, "tpoint", "TPointに関するコマンド");
        commandFormat(sender, "trash", "ゴミ箱を開く");
    }
}
