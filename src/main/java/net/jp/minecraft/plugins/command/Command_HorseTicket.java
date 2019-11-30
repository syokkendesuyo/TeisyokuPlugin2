package net.jp.minecraft.plugins.command;

import net.jp.minecraft.plugins.api.API;
import net.jp.minecraft.plugins.module.Permission;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.util.Item;
import net.jp.minecraft.plugins.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Command_HorseTicket implements CommandExecutor {

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.horse_ticket")) {
            Msg.commandNotEnabled(sender, commandLabel);
            return true;
        }

        if (!API.hasPermission(sender, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.ADMIN);
            return true;
        }

        if (!(args.length < 3)) {
            Msg.warning(sender, "引数が多すぎます");
            return false;
        }
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                Msg.warning(sender, "引数が少なすぎます");
                return false;
            }
            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("zombie")) {
                player.getInventory().addItem(Z_Ticket());
                Msg.success(player, "ゾンビホース変換チケットを入手しました");
                return true;

            } else if (args[0].equalsIgnoreCase("skeleton")) {
                player.getInventory().addItem(S_Ticket());
                Msg.success(player, "スケルトンホース変換チケットを入手しました");
                return true;
            }
            Msg.warning(sender, "引数「" + args[0] + "」は存在しません");
            Msg.warning(sender, "利用方法： /horseticket <zombie/skeleton> (Player)");
            return false;
        } else if (args.length == 2) {
            Player toplayer = Bukkit.getPlayer(args[1]);
            if (toplayer == null) {
                Msg.warning(sender, args[1] + " はオフラインであるか、存在しないIDです");
                return false;
            }
            if (args[0].equalsIgnoreCase("zombie")) {
                toplayer.getInventory().addItem(Z_Ticket());
                Msg.success(toplayer, "ゾンビホース変換チケットを入手しました");
                Msg.success(sender, "ゾンビホース変換チケットを " + args[1] + " に与えました");
                return true;

            } else if (args[0].equalsIgnoreCase("skeleton")) {
                toplayer.getInventory().addItem(S_Ticket());
                Msg.success(toplayer, "スケルトンホース変換チケットを入手しました");
                Msg.success(sender, "スケルトンホース変換チケットを " + args[1] + " に与えました");
                return true;
            }
            Msg.warning(sender, "引数「" + args[0] + "」は存在しません");
            Msg.warning(sender, "利用方法： /horseticket <zombie/skeleton> (Player)");
            return false;
        }
        return false;
    }

    private ItemStack Z_Ticket() {
        String[] lore = {ChatColor.GOLD + "スケルトンホースに", ChatColor.GOLD + "対して右クリックすると", ChatColor.GOLD + "ゾンビホースに変換します"};
        return Item.customItem(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "ゾンビホース変換チケット", 1, Material.PAPER, lore);
    }

    private ItemStack S_Ticket() {
        String[] lore = {ChatColor.GOLD + "ゾンビホースに対して", ChatColor.GOLD + "右クリックすると", ChatColor.GOLD + "スケルトンホースに変換します"};
        return Item.customItem(ChatColor.GRAY + "" + ChatColor.BOLD + "スケルトンホース変換チケット", 1, Material.PAPER, lore);
    }

}
