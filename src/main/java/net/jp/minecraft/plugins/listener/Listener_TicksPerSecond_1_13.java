package net.jp.minecraft.plugins.listener;

import net.minecraft.server.v1_13_R2.MinecraftServer;

import java.math.BigDecimal;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_TicksPerSecond_1_13 {

    public static double getTps(int num) {
        double[] tps = MinecraftServer.getServer().recentTps;
        return tps[num - 1];
    }

    public static String doubleToString(double num) {
        BigDecimal bd = new BigDecimal(num);
        BigDecimal bd2 = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
        return bd2.toString();
    }
}
