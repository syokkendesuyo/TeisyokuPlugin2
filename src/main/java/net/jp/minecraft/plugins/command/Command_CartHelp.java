package net.jp.minecraft.plugins.command;

import net.jp.minecraft.plugins.util.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class Command_CartHelp implements CommandExecutor {

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, String[] args) {

        if (args.length != 0) {
            Msg.warning(sender, "引数が多すぎです");
            return true;
        }
        Msg.info(sender, ChatColor.AQUA + "---------------[MinecartHelp]---------------" + ChatColor.GRAY + "(開発:azuhata)", false);
        Msg.info(sender, "線路の下のブロックによって速度を変更します", false);
        Msg.info(sender, "アクティベーターレール : 最高速度を通常Minecartの最高速度に再設定します", false);
        Msg.info(sender, "鉄ブロック 黒曜石 : 32m/s", false);
        Msg.info(sender, "模様入り石レンガ : 24m/s", false);
        Msg.info(sender, "通常のトロッコ : 8m/s", false);
        Msg.info(sender, "注意点 : 通常のトロッコ以上の速度を出す時、カーブや斜面の前に必ずアクティベーターレールを設置して下さい", false);
        Msg.info(sender, ChatColor.AQUA + "--------------------------------------------", false);
        return true;
    }
}