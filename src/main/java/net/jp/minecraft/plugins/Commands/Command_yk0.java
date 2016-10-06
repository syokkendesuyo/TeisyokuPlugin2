package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_yk0 implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player)) {
            Msg.warning(sender, "このコマンドはゲーム内からのみ有効です");
            return true;
        }

        if (args.length == 0) {
            Msg.warning(sender, "???");
            return true;
        }

        if (!(args[0].equalsIgnoreCase("on"))) {
            Msg.warning(sender, "???");
            return true;
        }

        int random = new Random().nextInt(Bukkit.getServer().getOnlinePlayers().size());
        Player p = (Player) Bukkit.getServer().getOnlinePlayers().toArray()[random];
        for (Player player : Bukkit.getOnlinePlayers()) {
            Msg.info(player, "/yk0 on " + ChatColor.DARK_GRAY + ">> " + ChatColor.YELLOW + p.getName());
        }
        Msg.info(p, "「おーん」って言いましょう(強制)");
        Sounds.sound_note(p);
        return true;
    }
}
