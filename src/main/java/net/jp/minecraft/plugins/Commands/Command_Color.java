package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_Color implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        Msg.success(sender, "カラーコード一覧");
        Msg.info(sender, "§11 §22 §33 §44 §55 §66 §77 §88 §99 §aa §bb §cc §dd §ee §ff");
        Msg.info(sender, "l§l:太字§r  m:§m打消し線§r  n:§n下線§r  o:§o斜体§r  r:リセット");
        return true;
    }
}
