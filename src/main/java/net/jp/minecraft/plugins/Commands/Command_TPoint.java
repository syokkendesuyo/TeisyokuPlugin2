package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Listener.Listener_TPoint;
import net.jp.minecraft.plugins.TPoint.TPointIndexGUI;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.UUIDFetcher;
import org.bukkit.*;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_TPoint implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        //パーミッションの確認
        if (!sender.hasPermission("teisyoku.user")) {
            Msg.noPermissionMessage(sender, "teisyoku.user");
            return true;
        }

        //引数0の場合
        if (args.length == 0) {
            if (sender instanceof Player) {
                TPointIndexGUI.index((Player) sender);
                return true;
            }
            HelpMessage(sender, cmd);
            if (sender.hasPermission("teisyoku.admin")) {
                AdminHelpMessage(sender, cmd);
            }
            return true;
        }

        //ヘルプ
        if (args[0].equalsIgnoreCase("help")) {
            if (!(args.length == 1)) {
                Msg.warning(sender, "引数が多すぎます");
                return true;
            }
            HelpMessage(sender, cmd);
            if (sender.hasPermission("teisyoku.admin")) {
                AdminHelpMessage(sender, cmd);
            }
            return true;
        }

        //ステイタス
        if (args[0].equalsIgnoreCase("status")) {
            if (args.length == 1) {
                if (!(sender instanceof Player)) {
                    Msg.info(sender, "使い方：/tpoint status <プレイヤー>");
                } else {
                    Listener_TPoint.sendPersonalStatus((Player) sender);
                }
            } else if (args.length == 2) {
                try {
                    UUID uuid = UUIDFetcher.getUUIDOf(args[1]);
                    OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

                    if (uuid == null) {
                        Msg.warning(sender, ChatColor.YELLOW + args[1] + ChatColor.RESET + "というプレイヤーは存在しません");
                        return true;
                    }

                    Listener_TPoint.getOfflinePlayerStatus(sender, player);
                    return true;
                } catch (Exception e) {
                    Msg.warning(sender, "不明なエラーが発生しました。Location: Command_TPoint --> status");
                    e.printStackTrace();
                    return true;
                }
            }
            return true;
        }

        //パーミッションの確認(コマンド側)
        if (!(sender.hasPermission("teisyoku.admin"))) {
            Msg.noPermissionMessage(sender, "teisyoku.admin");
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

            if (!(isNumber(args[2]))) {
                //数字でなかったら拒否
                Msg.warning(sender, args[2] + "は数値ではありません");
                if (sender instanceof CommandBlock) {
                    return false;
                } else {
                    return true;
                }
            }

            Player player = Bukkit.getServer().getPlayer(args[1]);
            if (!(player == null)) {
                int point = Integer.parseInt(args[2]);
                if (!Listener_TPoint.addPoint(point, player, sender) && sender instanceof CommandBlock) {
                    return false;
                }
                return true;
            } else {
                //プレイヤーが居ないのでエラー
                Msg.warning(sender, "プレイヤー " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " はオンラインではありません");
                return true;
            }
        }

        //削減
        if (args[0].equalsIgnoreCase("subtract") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("-")) {
            if (!(args.length == 3)) {
                Msg.warning(sender, "引数が不正です");
                Msg.warning(sender, "使用方法：/tpoint remove <ﾌﾟﾚｲﾔｰ> <数値>");
                return true;
            }

            if (!(isNumber(args[2]))) {
                //数字でなかったら拒否
                Msg.warning(sender, args[2] + "は数値ではありません");
                if (sender instanceof BlockCommandSender) {
                    Location loc = ((BlockCommandSender) sender).getBlock().getLocation();
                    World world = loc.getWorld();
                    long y = loc.getBlockY() + 1;
                    loc.setY(y);
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
                Msg.warning(sender, "引数が不正です");
                Msg.warning(sender, "使用方法：/tpoint set <ﾌﾟﾚｲﾔｰ> <数値>");
                return true;
            }

            if (!(isNumber(args[2]))) {
                //数字でなかったら拒否
                Msg.warning(sender, args[2] + "は数値ではありません");
                return true;
            }

            try{
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
            } catch (Exception e){
                Msg.warning(sender, "不明なエラーが発生しました。Location: Command_TPoint --> set");
            }
        }

        //ギフトコード
        if (args[0].equalsIgnoreCase("code") || args[0].equalsIgnoreCase("giftcode")) {
            Msg.warning(sender, "現在利用できません");
            return true;
        }

        HelpMessage(sender, cmd);
        if (sender.hasPermission("teisyoku.admin")) {
            AdminHelpMessage(sender, cmd);
        }
        return true;
    }

    /**
     * TPointコマンドのヘルプ
     *
     * @param sender 送信相手
     * @param cmd    コマンド
     */
    private void HelpMessage(CommandSender sender, Command cmd) {
        Msg.success(sender, cmd.getName() + "コマンドのヘルプ");
        Msg.commandFormat(sender, cmd.getName() + " help", "TPointｺﾏﾝﾄﾞのﾍﾙﾌﾟ");
        Msg.commandFormat(sender, cmd.getName() + " status", "所持ﾎﾟｲﾝﾄを参照");
        Msg.commandFormat(sender, cmd.getName() + " code <文字列>", "ｷﾞﾌﾄｺｰﾄﾞを入力");
    }

    /**
     * TPointの管理者コマンドヘルプ
     *
     * @param sender 送信相手
     * @param cmd
     */
    private void AdminHelpMessage(CommandSender sender, Command cmd) {
        Msg.commandFormat(sender, cmd.getName() + " <set/add/remove> <ﾌﾟﾚｲﾔｰ名> <数値>", "ﾌﾟﾚｲﾔｰのﾎﾟｲﾝﾄ数を変更");
    }

    /**
     * 渡された文字列が数字であるか確認する
     *
     * @param num
     * @return boolean
     */
    private boolean isNumber(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException event) {
            return false;
        }
    }
}
