package net.jp.minecraft.plugins.command;

import net.jp.minecraft.plugins.api.API;
import net.jp.minecraft.plugins.enumeration.Permission;
import net.jp.minecraft.plugins.listener.Listener_TicksPerSecond_1_13;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.util.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Command_TPS implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.tps")) {
            Msg.commandNotEnabled(sender, commandLabel);
            return true;
        }

        //引数が0だった場合
        if (args.length == 0) {
            sendStatus(sender);
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
                    Permission.TPS,
                    Permission.ADMIN
            );
            return true;
        }
        sendStatus(sender);
        return true;
    }

    private void sendStatus(@Nonnull CommandSender sender) {
        if (!API.hasPermission(sender, Permission.USER, Permission.TPS, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.TPS);
            return;
        }
        String s1 = tps(1);
        String s2 = tps(2);
        String s3 = tps(3);

        double d1 = Double.parseDouble(s1);
        double d2 = Double.parseDouble(s2);
        double d3 = Double.parseDouble(s3);

        double dAll = d1 + d2 + d3;

        String ds1 = doubleToString(d1);
        String ds2 = doubleToString(d2);
        String ds3 = doubleToString(d3);

        Msg.success(sender, ChatColor.GOLD + "現在のラグ状況" + ChatColor.DARK_GRAY + ": " + statusColor(d1) + ds1 + ChatColor.RESET + "% , " + statusColor(d2) + ds2 + ChatColor.RESET + "% , " + statusColor(d3) + ds3 + ChatColor.RESET + "%");
        Msg.success(sender, ChatColor.GOLD + "診断結果" + ChatColor.DARK_GRAY + ": " + status(dAll));
        Msg.success(sender, "近況1分、3分、5分のデータで、/tps のデータを元に算出");
    }

    private ChatColor statusColor(double num) {
        if (num >= 18) {
            return ChatColor.GREEN;
        } else if (num >= 12) {
            return ChatColor.YELLOW;
        } else {
            return ChatColor.RED;
        }
    }

    private String status(double num) {
        if (num >= 18 * 3) {
            return ChatColor.GREEN + "良好";
        } else if (num >= 12 * 3) {
            return ChatColor.YELLOW + "異常";
        } else {
            return ChatColor.RED + "危険";
        }
    }

    private static String doubleToString(double num) {
        num = 100 - num * 5;
        BigDecimal bd = new BigDecimal(num);
        BigDecimal bd2 = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
        return bd2.toString();
    }

    private static String tps(int num) {
        return Listener_TicksPerSecond_1_13.doubleToString(Listener_TicksPerSecond_1_13.getTps(num));
    }

    /**
     * tpsコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel, "TPSデータを元にラグ状態を表示");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
