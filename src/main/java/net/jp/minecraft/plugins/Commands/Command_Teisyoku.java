package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Config.GiftConfigAPI;
import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.TeisyokuMenuIndex;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_Teisyoku implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender.hasPermission(Permissions.getTeisyokuCommandPermisson()))) {
            Msg.noPermissionMessage(sender, Permissions.getTeisyokuUserPermisson());
            return true;
        }

        if (args.length == 0) {
            teisyoku(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            if (!(sender.hasPermission(Permissions.getHelpCommandPermisson()))) {
                Msg.noPermissionMessage(sender, Permissions.getTeisyokuUserPermisson());
                return true;
            }
            Msg.sendTeisyokuHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("permission") || args[0].equalsIgnoreCase("perm")) {
            Msg.checkPermission(sender, Permissions.getTeisyokuUserPermisson());
            return true;
        }

        if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("rl")) {
            if (!sender.hasPermission("teisyoku.admin")) {
                Msg.warning(sender, "パーミッションがありません");
                return true;
            }
            // TODO: CustomConfigに置き換える
            TeisyokuPlugin2.getInstance().configTeisyoku.reloadConfig();
            TeisyokuPlugin2.getInstance().configGift.reloadConfig();
            TeisyokuPlugin2.getInstance().configRailways.reloadConfig();
            TeisyokuPlugin2.getInstance().reloadLastPlayerJoinConfig();
            TeisyokuPlugin2.getInstance().reloadHorseConfig();
            TeisyokuPlugin2.getInstance().reloadTPointSettingsConfig();
            Msg.success(sender, "TeisyokuPlugin2のconfigをリロードしました。");
            return true;
        }

        if (args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("version")) {
            Msg.success(sender, "Version ： " + TeisyokuPlugin2.getInstance().getDescription().getVersion());
            return true;
        }

        if (args[0].equalsIgnoreCase("gift") || args[0].equalsIgnoreCase("giftcode")) {
            if (args.length == 2) {
                Msg.info(sender, "手続き中です...");
                GiftConfigAPI.gift(args[1], sender);
                return true;
            } else {
                Msg.warning(sender, "引数が多すぎるか、または少なすぎます");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("flag") || args[0].equalsIgnoreCase("f")) {
            if (!Permissions.hasPermission(sender, Permissions.getTeisyokuAdminPermisson())) {
                Msg.noPermissionMessage(sender, Permissions.getTeisyokuAdminPermisson());
                return true;
            }
            if (args[1].equalsIgnoreCase("ad")) {
                if (args.length == 4) {
                    Player target_player = Bukkit.getServer().getPlayer(args[2]);
                    if (!(target_player == null)) {
                        Msg.warning(sender, args[2] + " さんはオンラインではありません");
                        return true;
                    }
                    if (args[3].equalsIgnoreCase("true")) {

                    }
                    return true;
                }
                Msg.warning(sender, "引数が多すぎるか、または少なすぎます");
                return true;
            }
            Msg.warning(sender, "引数「" + args[1] + "」は存在しません");
            return true;
        }
        Msg.warning(sender, "引数 " + args[0] + " は存在しません");
        return true;
    }

    private void teisyoku(CommandSender sender) {
        if (!(sender instanceof Player)) {
            Msg.warning(sender, "定食メニューコマンドはゲーム内からのみ実行できます");
            return;
        }

        if (!Permissions.hasPermission(sender, Permissions.getTeisyokuUserPermisson())) {
            Msg.noPermissionMessage(sender, Permissions.getTeisyokuUserPermisson());
            return;
        }

        Player player = (Player) sender;
        TeisyokuMenuIndex.getMenu(player);
    }
}
