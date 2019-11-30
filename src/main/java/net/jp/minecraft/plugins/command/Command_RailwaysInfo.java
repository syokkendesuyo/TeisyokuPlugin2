package net.jp.minecraft.plugins.command;

import net.jp.minecraft.plugins.api.API;
import net.jp.minecraft.plugins.module.Permission;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.util.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 * TODO: configに接続する部分をAPI化
 */
public class Command_RailwaysInfo implements CommandExecutor {

    /**
     * インスタンスへアクセスする変数
     */
    private TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.railways")) {
            Msg.commandNotEnabled(sender, commandLabel);
            return true;
        }

        //引数が0だった場合
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
                    Permission.RAILWAYS,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!API.hasPermission(sender, Permission.USER, Permission.RAILWAYS, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.RAILWAYS);
            return true;
        }

        if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("+") || args[0].equalsIgnoreCase("register") || args[0].equalsIgnoreCase("set")) {
            if (!(sender instanceof Player)) {
                Msg.warning(sender, "プレイヤーからのみこのコマンドを利用できます");
                return true;
            }
            if (args.length == 3) {
                if (isRegister(args[1])) {
                    String name = plugin.configRailways.getConfig().getString(args[1] + ".player");
                    Msg.warning(sender, "既に " + ChatColor.YELLOW + args[1] + ChatColor.RESET + " は登録されています (登録者: " + ChatColor.YELLOW + name + ChatColor.RESET + " )");
                    return true;
                }
                if (args[1].contains("uuid") || args[1].contains("string")) {
                    Msg.warning(sender, ChatColor.YELLOW + args[1] + ChatColor.RESET + " はプラグインの仕様上、登録することができません。");
                    return true;
                } else {
                    boolean status = add(args[1], args[2], (Player) sender);
                    if (status) {
                        Msg.success(sender, ChatColor.YELLOW + args[1] + ChatColor.RESET + " を登録しました");
                    }
                    return true;
                }
            } else {
                help(sender, commandLabel);
                return true;
            }
        } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("-") || args[0].equalsIgnoreCase("delete")) {
            if (args.length == 2) {
                if (isRegister(args[1])) {
                    remove(args[1], (Player) sender);
                    return true;
                } else {
                    Msg.warning(sender, ChatColor.YELLOW + args[1] + ChatColor.RESET + " は登録されていません");
                    return true;
                }
            }
            help(sender, commandLabel);
            return true;
        } else if (args[0].equalsIgnoreCase("removeall") || args[0].equalsIgnoreCase("-all") || args[0].equalsIgnoreCase("-//")) {
            removeAll((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("!")) {
            getAll((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("change") || args[0].equalsIgnoreCase("*")) {
            if (!(args.length == 3)) {
                help(sender, commandLabel);
                return true;
            }
            if (!(sender instanceof Player)) {
                Msg.warning(sender, "プレイヤーからのみこのコマンドを利用できます");
                return true;

            }
            if (isRegister(args[1])) {
                edit(args[1], args[2], (Player) sender);
                return true;
            } else {
                Msg.warning(sender, ChatColor.YELLOW + args[1] + ChatColor.RESET + " は登録されていません");
                return true;
            }
        } else {
            help(sender, commandLabel);
            return true;
        }
    }

    /**
     * riコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "Railways Information 機能のヘルプ");
        Msg.commandFormat(sender, commandLabel + " + <登録名> <登録する文章>", "文章を設定します");
        Msg.commandFormat(sender, commandLabel + " * <登録名> <編集する文章>", "文章を編集します");
        Msg.commandFormat(sender, commandLabel + " ! <登録名>", "登録情報をすべて出力します");
        Msg.commandFormat(sender, commandLabel + " - <登録名>", "登録を削除します");
        Msg.commandFormat(sender, commandLabel + " -// <登録名>", "登録を全て削除します");
    }

    private boolean isRegister(String string) {
        try {
            return plugin.configRailways.getConfig().get(string) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean add(String name, String string, Player player) {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
            String strDate = sdf.format(date.getTime());

            plugin.configRailways.getConfig().set(name, name);
            plugin.configRailways.getConfig().set(name + ".data", strDate);
            plugin.configRailways.getConfig().set(name + ".player", player.getName());
            plugin.configRailways.getConfig().set(name + ".uuid", player.getUniqueId().toString());
            plugin.configRailways.getConfig().set(name + ".string", string);
            plugin.configRailways.getConfig().set(name + ".mode", "private");

            plugin.configRailways.saveConfig();
            return true;
        } catch (Exception e) {
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
            return false;
        }
    }

    private void remove(String name, Player player) {
        try {
            if (Objects.requireNonNull(plugin.configRailways.getConfig().getString(name + ".uuid")).equalsIgnoreCase(player.getUniqueId().toString())) {
                plugin.configRailways.getConfig().set(name, null);
                plugin.configRailways.saveConfig();
                Msg.success(player, ChatColor.YELLOW + name + ChatColor.RESET + " を削除しました");
            } else {
                String register = plugin.configRailways.getConfig().getString(name + ".player");
                Msg.warning(player, "削除できるプレイヤーは登録者のみです (登録者: " + ChatColor.YELLOW + register + ChatColor.RESET + " )");
            }
        } catch (Exception e) {
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    private void removeAll(Player player) {
        try {
            //key:パス
            //value:データ値
            Msg.info(player, "削除を開始しました...");
            for (String key : plugin.configRailways.getConfig().getKeys(true)) {
                if (key.endsWith(".uuid")) {
                    String path = key.replaceAll(".uuid", "");
                    String uuid = plugin.configRailways.getConfig().getString(path + ".uuid");
                    assert uuid != null;
                    if (!(uuid.equals(player.getUniqueId().toString()))) {
                        continue;
                    }
                    remove(path, player);
                }
            }
            Msg.info(player, "削除が終了しました");
        } catch (Exception e) {
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    private void edit(String name, String string, Player player) {
        try {
            if (Objects.requireNonNull(plugin.configRailways.getConfig().getString(name + ".uuid")).equalsIgnoreCase(player.getUniqueId().toString())) {
                boolean status = add(name, string, player);
                if (status) {
                    Msg.warning(player, ChatColor.YELLOW + name + ChatColor.RESET + " を編集しました");
                }
            } else {
                String register = plugin.configRailways.getConfig().getString(name + ".player");
                Msg.warning(player, "編集できるプレイヤーは登録者のみです (登録者: " + ChatColor.YELLOW + register + ChatColor.RESET + " )");
            }
        } catch (Exception e) {
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    private void getAll(Player player) {
        try {
            //key:パス
            //value:データ値
            Msg.success(player, ChatColor.YELLOW + player.getName() + ChatColor.RESET + "さんの登録リスト");
            for (String key : plugin.configRailways.getConfig().getKeys(true)) {
                if (key.endsWith(".uuid")) {
                    String path = key.replaceAll(".uuid", "");
                    String str = plugin.configRailways.getConfig().getString(path + ".string");
                    String uuid = plugin.configRailways.getConfig().getString(path + ".uuid");
                    assert uuid != null;
                    if (!(uuid.equals(player.getUniqueId().toString()))) {
                        continue;
                    }
                    Msg.info(player, " " + ChatColor.LIGHT_PURPLE + path + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.RESET + str);
                }
            }
        } catch (Exception e) {
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }
}
