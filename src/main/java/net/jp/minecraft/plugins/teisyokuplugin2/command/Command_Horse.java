package net.jp.minecraft.plugins.teisyokuplugin2.command;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.api.API;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_Horse;
import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Item;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Command_Horse implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.horse")) {
            Msg.commandNotEnabled(sender, commandLabel);
            return true;
        }

        if (!(sender instanceof Player)) {
            //TODO: コンソールから保護数等確認可能に
            Msg.warning(sender, "/" + commandLabel + " コマンドはゲーム内でのみ有効です");
            help(sender, commandLabel);
            return true;
        }

        //senderをplayerにキャスト
        Player player = (Player) sender;

        if (args.length == 0) {
            help(sender, commandLabel);
            return true;
        }

        //ヘルプ
        if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
            help(sender, commandLabel);
            return true;
        }

        //パーミッションの確認コマンドを追加
        if (args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("perms") || args[0].equalsIgnoreCase("permission")) {
            Msg.checkPermission(sender,
                    Permission.USER,
                    Permission.HORSE,
                    Permission.HORSE_ADMIN,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!API.hasPermission(sender, Permission.USER, Permission.HORSE, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.HORSE);
            return true;
        }

        if (args[0].equals("+")) {
            String[] lore = {ChatColor.YELLOW + "使い方:", ChatColor.WHITE + "馬に向かって右クリックすると", ChatColor.WHITE + "馬をロックできます"};
            ItemStack item = Item.customItem(ChatColor.GOLD + "馬保護ツール", 1, Material.STICK, true, lore);
            player.getInventory().addItem(item);
            Msg.success(player, "馬保護ツールを交付しました");
            return true;
        }

        if (args[0].equals("-")) {
            String[] lore = {ChatColor.YELLOW + "使い方:", ChatColor.WHITE + "馬に向かって右クリックすると", ChatColor.WHITE + "馬のロックを解除できます"};
            ItemStack item = Item.customItem(ChatColor.GOLD + "馬保護解除ツール", 1, Material.STICK, true, lore);
            player.getInventory().addItem(item);
            Msg.success(player, "馬保護解除ツールを交付しました");
            return true;
        }

        if (args[0].equals("!")) {
            String[] lore = {ChatColor.YELLOW + "使い方:", ChatColor.WHITE + "馬に向かって右クリックすると", ChatColor.WHITE + "馬のロック情報を確認できます"};
            ItemStack item = Item.customItem(ChatColor.GOLD + "馬保護情報確認ツール", 1, Material.STICK, true, lore);
            player.getInventory().addItem(item);
            Msg.success(player, "馬保護情報確認ツールを交付しました");
            return true;
        }

        // TODO: 他者の保護数を確認できるように修正
        if (args[0].equals("status") || args[0].equals("s")) {
            Msg.info(player, ChatColor.GOLD + "馬の保護数" + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + Listener_Horse.getLocks(player) + "/" + Listener_Horse.getMaxLocks());
            return true;
        }
        return true;
    }

    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, commandLabel + " コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + " +", "馬保護ツールを入手");
        Msg.commandFormat(sender, commandLabel + " -", "馬保護解除ツールを入手");
        Msg.commandFormat(sender, commandLabel + " !", "馬保護情報確認ツールを入手");
        Msg.commandFormat(sender, commandLabel + " status", "現在の保護数を確認");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
