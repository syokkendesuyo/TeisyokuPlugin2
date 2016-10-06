package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_RailwayInfo implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 0) {
            help(cmd, sender);
            return true;
        }
        if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("+") || args[0].equalsIgnoreCase("register") || args[0].equalsIgnoreCase("set")) {
            if (!isplayer(sender)) {
                sender.sendMessage(Messages.getDenyPrefix() + "コンソールから追加はできません");
                return true;
            }
            if (args.length == 3) {
                if (isresigter(args[1])) {
                    String name = TeisyokuPlugin2.getInstance().CartConfig.getString(args[1] + ".player");
                    sender.sendMessage(Messages.getDenyPrefix() + "既に " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " は登録されています (登録者: " + ChatColor.YELLOW + name + ChatColor.RESET + " )");
                    return true;
                }
                if (args[1].contains("uuid") || args[1].contains("string")) {
                    Msg.warning(sender, ChatColor.YELLOW + args[1] + ChatColor.RESET + " はプラグインの仕様上、登録することができません。");
                    return true;
                } else {
                    add(args[1], args[2], (Player) sender);
                    sender.sendMessage(Messages.getSuccessPrefix() + ChatColor.YELLOW + args[1] + ChatColor.RESET + " を登録しました");
                    return true;
                }
            } else {
                help(cmd, sender);
                return true;
            }
        } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("-") || args[0].equalsIgnoreCase("delete")) {
            if (args.length == 2) {
                if (isresigter(args[1])) {
                    remove(args[1], (Player) sender);
                    return true;
                } else {
                    sender.sendMessage(Messages.getDenyPrefix() + ChatColor.YELLOW + args[1] + ChatColor.RESET + " は登録されていません");
                    return true;
                }
            }
            help(cmd, sender);
            return true;
        } else if (args[0].equalsIgnoreCase("removeall") || args[0].equalsIgnoreCase("-all") || args[0].equalsIgnoreCase("-//")) {
            removeAll((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("!")) {
            getAll((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("change") || args[0].equalsIgnoreCase("*")) {
            if (!(args.length == 3)) {
                help(cmd, sender);
                return true;
            }
            if (!isplayer(sender)) {
                sender.sendMessage(Messages.getDenyPrefix() + "コンソールから編集はできません");
                return true;

            }
            if (isresigter(args[1])) {
                edit(args[1], args[2], (Player) sender);
                return true;
            } else {
                sender.sendMessage(Messages.getDenyPrefix() + ChatColor.YELLOW + args[1] + ChatColor.RESET + " は登録されていません");
                return true;
            }
        } else if (args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("perms") || args[0].equalsIgnoreCase("permission")) {
            Messages.getCheckPermissionMessage(Permissions.getTeisyokuUserPermisson());
            return true;
        } else {
            help(cmd, sender);
            return true;
        }
    }

    private void help(Command cmd, CommandSender sender) {
        sender.sendMessage(Messages.getSuccessPrefix() + "Railway Information 機能のヘルプ");
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " + <登録名> <登録する文章>", "文章を設定します"));
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " * <登録名> <編集する文章>", "文章を編集します"));
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " ! <登録名>", "登録情報をすべて出力します"));
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " - <登録名>", "登録を削除します"));
        sender.sendMessage(Messages.getCommandFormat(cmd.getName() + " -// <登録名>", "登録を全て削除します"));

    }

    private boolean isresigter(String string) {
        try {
            if (TeisyokuPlugin2.getInstance().CartConfig.get(string) == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isplayer(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        }
        return false;
    }

    private static void add(String name, String string, Player player) {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
            String strDate = sdf.format(date.getTime());

            TeisyokuPlugin2.getInstance().CartConfig.set(name, name);
            TeisyokuPlugin2.getInstance().CartConfig.set(name + ".data", strDate);
            TeisyokuPlugin2.getInstance().CartConfig.set(name + ".player", player.getName());
            TeisyokuPlugin2.getInstance().CartConfig.set(name + ".uuid", player.getUniqueId().toString());
            TeisyokuPlugin2.getInstance().CartConfig.set(name + ".string", string);
            TeisyokuPlugin2.getInstance().CartConfig.set(name + ".mode", "private");

            TeisyokuPlugin2.getInstance().saveCartConfig();

        } catch (Exception e) {
            player.sendMessage(Messages.getDenyPrefix() + "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    private static void remove(String name, Player player) {
        try {
            if (TeisyokuPlugin2.getInstance().CartConfig.getString(name + ".uuid").equalsIgnoreCase(player.getUniqueId().toString()) || player.hasPermission(Permissions.getTeisyokuUserPermisson())) {
                TeisyokuPlugin2.getInstance().CartConfig.set(name, null);
                TeisyokuPlugin2.getInstance().saveCartConfig();
                player.sendMessage(Messages.getSuccessPrefix() + ChatColor.YELLOW + name + ChatColor.RESET + " を削除しました");
            } else {
                String register = TeisyokuPlugin2.getInstance().CartConfig.getString(name + ".player");
                player.sendMessage(Messages.getDenyPrefix() + "削除できるプレイヤーは登録者のみです (登録者: " + ChatColor.YELLOW + register + ChatColor.RESET + " )");
            }
        } catch (Exception e) {
            player.sendMessage(Messages.getDenyPrefix() + "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    private static void removeAll(Player player) {
        try {
            //key:パス
            //value:データ値
            Configuration config = TeisyokuPlugin2.getInstance().CartConfig;
            Msg.info(player, "削除を開始しました...");
            for (String key : config.getKeys(true)) {
                if (key.endsWith(".uuid")) {
                    String path = key.replaceAll(".uuid", "");
                    String uuid = config.getString(path + ".uuid");
                    if (!(uuid.equals(player.getUniqueId().toString()))) {
                        continue;
                    }
                    remove(path, player);
                }
            }
            Msg.info(player, "削除が終了しました");
        } catch (Exception e) {
            player.sendMessage(Messages.getDenyPrefix() + "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    private static void edit(String name, String string, Player player) {
        try {
            if (TeisyokuPlugin2.getInstance().CartConfig.getString(name + ".uuid").equalsIgnoreCase(player.getUniqueId().toString()) || player.hasPermission(Permissions.getTeisyokuUserPermisson())) {
                add(name, string, player);
                player.sendMessage(Messages.getSuccessPrefix() + ChatColor.YELLOW + name + ChatColor.RESET + " を編集しました");
            } else {
                String register = TeisyokuPlugin2.getInstance().CartConfig.getString(name + ".player");
                player.sendMessage(Messages.getDenyPrefix() + "編集できるプレイヤーは登録者のみです (登録者: " + ChatColor.YELLOW + register + ChatColor.RESET + " )");
            }
        } catch (Exception e) {
            player.sendMessage(Messages.getDenyPrefix() + "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    private static void getAll(Player player) {
        try {
            //key:パス
            //value:データ値
            Configuration config = TeisyokuPlugin2.getInstance().CartConfig;
            Msg.info(player, "--- 登録リスト ---");
            for (String key : config.getKeys(true)) {
                if (key.endsWith(".uuid")) {
                    String path = key.replaceAll(".uuid", "");
                    String str = config.getString(path + ".string");
                    String uuid = config.getString(path + ".uuid");
                    if (!(uuid.equals(player.getUniqueId().toString()))) {
                        continue;
                    }
                    Msg.info(player, ChatColor.YELLOW + path + ChatColor.RESET + ChatColor.GRAY + " >> " + ChatColor.RESET + str);
                }
            }
            Msg.info(player, "-------------------");
        } catch (Exception e) {
            player.sendMessage(Messages.getDenyPrefix() + "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }
}
