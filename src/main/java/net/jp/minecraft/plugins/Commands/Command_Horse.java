package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */


public class Command_Horse implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Please excute this /ait command on a game!");
            sender.sendMessage("/ait コマンドはゲーム内で実行してください。");
            return true;
        }

        //senderをplayerにキャスト
        Player player = (Player) sender;

        if(args.length == 0){
            help(sender,label);
            return true;
        }
        if(args[0].equalsIgnoreCase("help")){
            help(sender,label);
            return true;
        }
        if(args[0].equals("+")){
            ItemStack item = new ItemStack(Material.STICK);
            ItemMeta itemmeta = item.getItemMeta();
            itemmeta.setDisplayName(ChatColor.GOLD + "馬保護ツール");
            itemmeta.setLore(Arrays.asList(ChatColor.YELLOW + "使い方:", ChatColor.WHITE + "馬に向かって右クリックすると", ChatColor.WHITE + "馬をロックできます"));
            itemmeta.addEnchant(Enchantment.DURABILITY , 1, true);
            item.setItemMeta(itemmeta);
            player.getInventory().addItem(item);
            Msg.success(player, "馬保護ツールを交付しました");
            return true;
        }
        if (args[0].equals("-")){
            ItemStack item = new ItemStack(Material.STICK);
            ItemMeta itemmeta = item.getItemMeta();
            itemmeta.setDisplayName(ChatColor.GOLD + "馬保護解除ツール");
            itemmeta.setLore(Arrays.asList(ChatColor.YELLOW + "使い方:", ChatColor.WHITE + "馬に向かって右クリックすると", ChatColor.WHITE + "馬のロックを解除できます"));
            itemmeta.addEnchant(Enchantment.DURABILITY , 1, true);
            item.setItemMeta(itemmeta);
            player.getInventory().addItem(item);
            Msg.success(player, "馬保護解除ツールを交付しました");
            return true;
        }
        if(args[0].equals("?")){
            ItemStack item = new ItemStack(Material.STICK);
            ItemMeta itemmeta = item.getItemMeta();
            itemmeta.setDisplayName(ChatColor.GOLD + "馬保護情報確認ツール");
            itemmeta.setLore(Arrays.asList(ChatColor.YELLOW + "使い方:", ChatColor.WHITE + "馬に向かって右クリックすると", ChatColor.WHITE + "馬のロック情報を確認できます"));
            itemmeta.addEnchant(Enchantment.DURABILITY , 1, true);
            item.setItemMeta(itemmeta);
            player.getInventory().addItem(item);
            Msg.success(player, "馬保護情報確認ツールを交付しました");
            return true;
        }
        return true;
    }

    public void help(CommandSender sender , String commandLabel){
        Msg.success(sender , commandLabel.toString() + " コマンドのヘルプ");
        Msg.commandFormat(sender , commandLabel.toString() + " help", "このヘルプ");
        Msg.commandFormat(sender , commandLabel.toString() + " +", "馬保護ツールを入手");
        Msg.commandFormat(sender , commandLabel.toString() + " -", "馬保護解除ツールを入手");
        Msg.commandFormat(sender , commandLabel.toString() + " ?", "馬保護情報確認ツールを入手");
    }
}
