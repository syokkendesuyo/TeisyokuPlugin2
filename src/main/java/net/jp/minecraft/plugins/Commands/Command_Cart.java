package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Permission;
import org.bukkit.Bukkit;
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
public class Command_Cart implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.cart")) {
            Msg.commandNotEnabled(sender, commandLabel);
            return true;
        }

        //引数がない場合
        if (args.length == 0) {
            //コンソールからの送信は弾く
            if (!(sender instanceof Player)) {
                help(sender, commandLabel);
                return true;
            }
            if (sender.hasPermission(Permission.CART.toString())) {
                giveCart(sender, (Player) sender);
                return true;
            }
            Msg.noPermissionMessage(sender, Permission.CART);
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
                    Permission.CART,
                    Permission.CART_GIVE,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!(sender.hasPermission(Permission.USER.toString()) || sender.hasPermission(Permission.CART.toString()) || sender.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(sender, Permission.CART);
            return true;
        }

        //引数1以上の想定は無いためヘルプを表示
        if (args.length > 1) {
            help(sender, commandLabel);
            return true;
        }

        //引数1の場合,
        giveCart(sender, args[0]);
        return true;
    }

    /**
     * マインカートをインベントリに追加
     *
     * @param sender 送信者
     * @param target ターゲット
     */
    private void giveCart(CommandSender sender, String target) {
        if (!sender.hasPermission(Permission.CART_GIVE.toString())) {
            Msg.noPermissionMessage(sender, Permission.CART_GIVE);
            return;
        }
        if (Bukkit.getServer().getPlayer(target) == null) {
            Msg.warning(sender, "プレイヤー " + ChatColor.YELLOW + target + ChatColor.RESET + " さんはオンラインではありません");
            return;
        }
        giveCart(sender, Bukkit.getServer().getPlayer(target));
    }

    /**
     * マインカートをインベントリに追加
     *
     * @param sender 送信者
     * @param target ターゲット
     */
    private void giveCart(CommandSender sender, Player target) {
        //アイテムを作成
        ItemStack cart = new ItemStack(Material.MINECART);

        //ターゲットプレイヤーとコマンド送信者の一致を確認
        if (sender == target) {
            target.getInventory().addItem(cart);
            Msg.success(sender, "マインカートをインベントリに追加しました");
        } else {
            if (!sender.hasPermission(Permission.CART_GIVE.toString())) {
                Msg.noPermissionMessage(sender, Permission.CART_GIVE);
                return;
            }
            target.getInventory().addItem(cart);
            Msg.success(sender, "プレイヤー " + ChatColor.YELLOW + target.getName() + ChatColor.RESET + " さんにマインカートを渡しました");
            Msg.success(target, "プレイヤー " + ChatColor.YELLOW + sender.getName() + ChatColor.RESET + " さんからマインカートを渡されました");
        }
    }

    /**
     * cartコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        String description = "マインカートをインベントリに追加";
        if (!(sender instanceof Player)) {
            description = "ヘルプを表示 (ゲーム内から使用した場合、自分にマインカートを追加)";
        }
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + "", description);
        Msg.commandFormat(sender, commandLabel + " <プレイヤー>", "指定したプレイヤーにマインカートを追加");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
