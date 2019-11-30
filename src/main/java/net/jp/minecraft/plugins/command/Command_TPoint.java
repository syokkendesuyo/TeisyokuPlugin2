package net.jp.minecraft.plugins.command;

import net.jp.minecraft.plugins.api.API;
import net.jp.minecraft.plugins.module.Permission;
import net.jp.minecraft.plugins.listener.Listener_TPoint;
import net.jp.minecraft.plugins.tpoint.TPointIndexGUI;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.util.Msg;
import net.jp.minecraft.plugins.util.UUIDFetcher;
import org.bukkit.*;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 * TODO: 重複部分をメソッド化
 */
public class Command_TPoint implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.tpoint")) {
            Msg.commandNotEnabled(sender, commandLabel);
            return true;
        }

        //引数が0だった場合
        if (args.length == 0) {
            if (sender instanceof Player) {
                TPointIndexGUI.index((Player) sender);
                return true;
            }
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
                    Permission.TPOINT,
                    Permission.TPOINT_ADMIN,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!API.hasPermission(sender, Permission.USER, Permission.TPOINT, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.TPOINT);
            return true;
        }

        //ステイタス
        if (args[0].equalsIgnoreCase("status")) {
            if (args.length == 1) {
                if (!(sender instanceof Player)) {
                    help(sender, commandLabel);
                } else {
                    Listener_TPoint.sendPersonalStatus((Player) sender);
                }
            } else if (args.length == 2) {
                try {
                    UUID uuid = UUIDFetcher.getUUIDOf(args[1]);
                    OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

                    Listener_TPoint.getOfflinePlayerStatus(sender, player);
                    return true;
                } catch (IllegalArgumentException e) {
                    Msg.warning(sender, "プレイヤー " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " は存在しません");
                    return true;
                } catch (Exception e) {
                    Msg.warning(sender, "不明なエラーが発生しました");
                }
            }
            return true;
        }

        //パーミッションの確認(コマンド側)
        if (!(sender.hasPermission(Permission.TPOINT_ADMIN.toString()))) {
            Msg.noPermissionMessage(sender, Permission.TPOINT_ADMIN);
            return true;
        }

        //追加
        if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("+")) {
            // /tpoint add Player int
            //          0    1     2

            if (!(args.length == 3)) {
                Msg.warning(sender, "引数が不正です");
                Msg.warning(sender, "使用方法：/tpoint add <ﾌﾟﾚｲﾔｰ> <数値>");
                return true;
            }

            if (!(API.isNumber(args[2]))) {
                //数字でなかったら拒否
                Msg.warning(sender, args[2] + "は数値ではありません");
                return !(sender instanceof CommandBlock);
            }

            Player player = Bukkit.getServer().getPlayer(args[1]);
            if (!(player == null)) {
                int point = Integer.parseInt(args[2]);
                return Listener_TPoint.addPoint(point, player, sender) || !(sender instanceof CommandBlock);
            } else {
                //プレイヤーが居ないのでエラー
                Msg.warning(sender, "プレイヤー " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " はオンラインではありません");
                return true;
            }
        }

        //削減
        if (args[0].equalsIgnoreCase("subtract") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("-")) {
            if (!(args.length == 3)) {
                help(sender, commandLabel);
                return true;
            }

            if (!(API.isNumber(args[2]))) {
                //数字でなかったら拒否
                Msg.warning(sender, args[2] + "は数値ではありません");
                if (sender instanceof BlockCommandSender) {
                    Location loc = ((BlockCommandSender) sender).getBlock().getLocation();
                    World world = loc.getWorld();
                    long y = loc.getBlockY() + 1;
                    loc.setY(y);
                    assert world != null;
                    world.getBlockAt(loc).setType(Material.LAPIS_BLOCK);
                    return false;
                }
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[1]);
            //プレイヤーが居るか
            if (!(player == null)) {
                int point = Integer.parseInt(args[2]);
                //ポイントが無かった場合
                if (!Listener_TPoint.subtractPoint(point, player, sender)) {
                    if (sender instanceof BlockCommandSender) {
                        Location loc = ((BlockCommandSender) sender).getBlock().getLocation();
                        World world = loc.getWorld();
                        long y = loc.getBlockY() + 2;
                        loc.setY(y);
                        assert world != null;
                        world.getBlockAt(loc).setType(Material.LAPIS_BLOCK);
                        return false;
                    }
                    return true;
                }
                //ポイントを持っていた場合
                else {
                    if (sender instanceof BlockCommandSender) {
                        Location loc = ((BlockCommandSender) sender).getBlock().getLocation();
                        World world = loc.getWorld();
                        long y = loc.getBlockY() + 2;
                        loc.setY(y);
                        assert world != null;
                        world.getBlockAt(loc).setType(Material.REDSTONE_BLOCK);
                        return true;
                    }
                }
                return true;
            } else {
                //プレイヤーが居ないのでエラー
                Msg.warning(sender, "プレイヤー " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " はオンラインではありません");
                return true;
            }
        }

        //セット
        if (args[0].equalsIgnoreCase("set")) {
            if (!(args.length == 3)) {
                help(sender, commandLabel);
                return true;
            }

            if (!(API.isNumber(args[2]))) {
                //数字でなかったら拒否
                Msg.warning(sender, args[2] + "は数値ではありません");
                return true;
            }

            try {
                UUID uuid = UUIDFetcher.getUUIDOf(args[1]);
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                if (player.isOnline()) {
                    Player onlinePlayer = (Player) player;
                    int point = Integer.parseInt(args[2]);
                    Listener_TPoint.setPoint(point, onlinePlayer);
                    Msg.success(onlinePlayer, ChatColor.YELLOW + sender.getName() + ChatColor.RESET + " さんによって " + point + " TPointにセットされました");
                    if (!(onlinePlayer == sender)) {
                        Msg.success(sender, ChatColor.YELLOW + player.getName() + ChatColor.RESET + " さんを " + point + " TPointにセットしました");
                    }
                    Listener_TPoint.sendPersonalStatus(onlinePlayer);
                    return true;
                } else {
                    //プレイヤーが居ないのでエラー
                    Msg.warning(sender, "プレイヤー " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " はオンラインではありません");
                    return true;
                }
            } catch (IllegalArgumentException e) {
                Msg.warning(sender, "プレイヤー " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " は存在しません");
                return true;
            } catch (Exception e) {
                Msg.warning(sender, "不明なエラーが発生しました");
            }
        }

        help(sender, commandLabel);
        return true;
    }

    /**
     * TPointコマンドのヘルプ
     *
     * @param sender       送信s者
     * @param commandLabel コマンド
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        if (sender instanceof Player) {
            Msg.commandFormat(sender, commandLabel, "ヘルプを表示");
        } else {
            Msg.commandFormat(sender, commandLabel, "TPointGUIを開く");
        }
        Msg.commandFormat(sender, commandLabel + " status", "所持ポイントを参照");
        if (sender.hasPermission(Permission.TPOINT_ADMIN.toString())) {
            Msg.commandFormat(sender, commandLabel + " <set|add|remove> <プレイヤー名> <数値>", "ポイントを変更");
        }
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
