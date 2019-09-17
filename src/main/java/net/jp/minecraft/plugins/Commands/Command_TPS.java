package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Listener.Listener_TicksPerSecond_1_13;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_TPS implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 3) {
            debug(sender, args[0], args[1], args[2]);
            return true;
        }
        String s1 = tps1();
        String s2 = tps2();
        String s3 = tps3();

        double d1 = Double.parseDouble(s1);
        double d2 = Double.parseDouble(s2);
        double d3 = Double.parseDouble(s3);

        double dAll = d1 + d2 + d3;

        String ds1 = doubleToString(d1);
        String ds2 = doubleToString(d2);
        String ds3 = doubleToString(d3);


        Msg.success(sender, ChatColor.GOLD + "現在のラグ状況" + ChatColor.DARK_GRAY + " ： " + color(d1) + ds1 + ChatColor.RESET + "% , " + color(d2) + ds2 + ChatColor.RESET + "% , " + color(d3) + ds3 + ChatColor.RESET + "%");
        Msg.success(sender, ChatColor.GOLD + "診断結果" + ChatColor.DARK_GRAY + " ： " + status(dAll));
        Msg.success(sender, "近況1分、3分、5分のデータで、/tps のデータを元に算出");
        return true;
    }

    private ChatColor color(double num) {
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

    private void debug(CommandSender sender, String s1, String s2, String s3) {
        try {
            double d1 = Double.parseDouble(s1);
            double d2 = Double.parseDouble(s2);
            double d3 = Double.parseDouble(s3);

            double dAll = d1 + d2 + d3;

            String ds1 = doubleToString(d1);
            String ds2 = doubleToString(d2);
            String ds3 = doubleToString(d3);


            Msg.success(sender, ChatColor.GOLD + "現在のラグ状況" + ChatColor.DARK_GRAY + " ： " + color(d1) + ds1 + ChatColor.RESET + "% , " + color(d2) + ds2 + ChatColor.RESET + "% , " + color(d3) + ds3 + ChatColor.RESET + "%");
            Msg.success(sender, ChatColor.GOLD + "診断結果" + ChatColor.DARK_GRAY + " ： " + status(dAll));
            Msg.success(sender, ChatColor.GRAY + "※デバッグ中" + "  引数 ：" + s1 + " , " + s2 + " , " + s3);
        } catch (Exception e) {
            Msg.warning(sender, "引数エラー");
            Msg.warning(sender, "デバッグを行う場合は /status <double> <double> <double> である必要があります");
        }
    }

    private static String tps1() {
        return Listener_TicksPerSecond_1_13.doubleToString(Listener_TicksPerSecond_1_13.getTps(1));
    }

    private static String tps2() {
        return Listener_TicksPerSecond_1_13.doubleToString(Listener_TicksPerSecond_1_13.getTps(2));
    }

    private static String tps3() {
        return Listener_TicksPerSecond_1_13.doubleToString(Listener_TicksPerSecond_1_13.getTps(3));
    }
}
