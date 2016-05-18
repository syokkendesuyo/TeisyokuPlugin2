package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.GUI.GUI_Anvil;
import net.jp.minecraft.plugins.Utility.TeisyokuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_SignEdit implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

        if(!(sender instanceof Player)){

        }

        return true;
    }
}
