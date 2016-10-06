package net.jp.minecraft.plugins.Commands;

import com.google.common.base.Joiner;
import eu.manuelgu.discordmc.MessageAPI;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_Discord implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("DiscordMC")) {
            Msg.warning(sender, "DiscordMCがロードされていない為利用できません");
            return true;
        }

        if (!(sender.hasPermission("teisyoku.admin"))) {
            Msg.warning(sender, "パーミッションがありません");
            return true;
        }

        if (args.length == 0) {
            Msg.commandFormat(sender, "dc <メッセージ>", "Discordへメッセージを送信します");
            return true;
        }

        try {
            String arg = Joiner.on(' ').join(args);
            MessageAPI.sendToDiscord(" __**[Teisyoku]**__ " + arg);
            Msg.success(sender, ChatColor.YELLOW + "送信しました" + ChatColor.GRAY + " : " + ChatColor.RESET + arg);
            return true;
        } catch (Exception e) {
            Msg.warning(sender, "不明なエラーが発生しました");
            e.printStackTrace();
            return true;
        }
    }
}
