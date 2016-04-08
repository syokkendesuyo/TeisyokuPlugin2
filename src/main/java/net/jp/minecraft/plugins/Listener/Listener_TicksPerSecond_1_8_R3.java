package net.jp.minecraft.plugins.Listener;

import net.minecraft.server.v1_9_R1.MinecraftServer;
import org.bukkit.ChatColor;

import java.math.BigDecimal;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_TicksPerSecond_1_8_R3 {

    public static String getTpsString(){
        StringBuilder sb = new StringBuilder( ChatColor.GOLD + "TPS from last 1m, 5m, 15m: " );
        for ( double tps : MinecraftServer.getServer().recentTps )
        {
            sb.append( tps );
            sb.append( ", " );
        }
        return sb.substring( 0, sb.length() - 2);
    }

    public static double getTps(int num){
        double tps[] = MinecraftServer.getServer().recentTps;
        return tps[num-1];
    }

    public static String doubleToString(double num){
        BigDecimal bd = new BigDecimal(num);
        BigDecimal bd2 = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
        return bd2.toString();
    }
}
