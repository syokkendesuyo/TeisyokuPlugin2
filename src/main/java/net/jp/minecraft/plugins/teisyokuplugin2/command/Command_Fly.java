package net.jp.minecraft.plugins.teisyokuplugin2.command;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.function.Fly;
import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 * flyコマンド
 *
 * @author syokkendesuyo
 */
public class Command_Fly implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.fly")) {
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
            if (sender.hasPermission(Permission.FLY_ME.toString())) {
                Fly.toggleFlying((Player) sender);
                return true;
            }
            Msg.noPermissionMessage(sender, Permission.FLY_ME);
            return true;
        }

        //ヘルプ
        if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
            help(sender, commandLabel);
            return true;
        }

        //パーミッションの確認コマンド
        if (args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("perms") || args[0].equalsIgnoreCase("permission")) {
            Msg.checkPermission(sender,
                    Permission.USER,
                    Permission.FLY_ME,
                    Permission.FLY_OTHERS,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!Permission.hasPermission(sender, Permission.USER, Permission.FLY_OTHERS, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.FLY_ME);
            Msg.noPermissionMessage(sender, Permission.FLY_OTHERS);
            return true;
        }

        //引数が1だった場合
        if (args.length == 1) {
            Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
            if (!(targetPlayer == null)) {
                String status = ChatColor.RED + "無効";
                if (Fly.isFlying(targetPlayer)) {
                    status = ChatColor.GREEN + "有効";
                }
                Msg.warning(sender, "プレイヤー " + ChatColor.YELLOW + args[0] + ChatColor.RESET + " の飛行状態" + ChatColor.DARK_GRAY + ": " + status);
                return true;
            } else {
                //プレイヤーが見つからない場合
                Msg.warning(sender, "プレイヤー " + ChatColor.YELLOW + args[0] + ChatColor.RESET + " はオンラインではありません");
                return true;
            }
        }

        //引数が2だった場合
        if (args.length == 2) {
            Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
            if (targetPlayer != null) {
                if (targetPlayer.equals(sender)) {
                    if (!sender.hasPermission(Permission.FLY_ME.toString())) {
                        Msg.noPermissionMessage(sender, Permission.FLY_ME);
                        return true;
                    }
                    Fly.setFlying(targetPlayer, Boolean.valueOf(args[1]), sender);
                    return true;
                }
                if (!sender.hasPermission(Permission.FLY_OTHERS.toString())) {
                    Msg.noPermissionMessage(sender, Permission.FLY_OTHERS);
                    return true;
                }
                Fly.setFlying(targetPlayer, Boolean.valueOf(args[1]), sender);
                return true;
            } else {
                //プレイヤーが居ないのでエラー
                Msg.warning(sender, "プレイヤー " + ChatColor.YELLOW + args[0] + ChatColor.RESET + " はオンラインではありません");
                return true;
            }
        }
        return true;
    }

    /**
     * flyコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        String description = "飛行モードをトグルする";
        if (!(sender instanceof Player)) {
            description = "ヘルプを表示 (ゲーム内から使用した場合、飛行モードをトグル)";
        }
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + "", description);
        Msg.commandFormat(sender, commandLabel + " <プレイヤー>", "指定したプレイヤーの飛行状態を確認");
        Msg.commandFormat(sender, commandLabel + " <プレイヤー> <true|false>", "指定したプレイヤーの飛行状態を変更");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
