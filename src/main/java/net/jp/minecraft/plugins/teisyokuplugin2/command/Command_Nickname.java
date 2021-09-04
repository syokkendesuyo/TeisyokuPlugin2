package net.jp.minecraft.plugins.teisyokuplugin2.command;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.module.PlayerDatabase;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Color;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo azuhata
 */
public class Command_Nickname implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //ニックネームのパスを指定
        // TODO: enum化
        String nicknamePath = "nickname.prefix";

        //コマンドが有効化されているかどうか検出
        // TODO: enum化
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.nickname")) {
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
                    Permission.NICKNAME,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!Permission.hasPermission(sender, Permission.USER, Permission.NICKNAME, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.NICKNAME);
            return true;
        }

        //adminコマンド
        if (args[0].equalsIgnoreCase("admin")) {
            if (args.length == 1) {
                help(sender, commandLabel);
                return true;
            }

            //パーミッションを確認
            if (!(sender.hasPermission(Permission.NICKNAME_ADMIN.toString()))) {
                Msg.noPermissionMessage(sender, Permission.NICKNAME_ADMIN);
            }

            if (args[1].equalsIgnoreCase("set")) {
                if (!(args.length == 4)) {
                    Msg.warning(sender, "引数が多すぎるかまたは少なすぎます");
                    help(sender, commandLabel);
                    return true;
                }

                //プレイヤーが存在するか確認
                OfflinePlayer player = PlayerDatabase.getPlayer(args[2]);
                if (player != null) {
                    PlayerDatabase.set(player, nicknamePath, args[3]);
                    Msg.success(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + " さんのニックネームを " + ChatColor.AQUA + args[3] + ChatColor.RESET + " に設定しました");
                    if (player.isOnline()) {
                        Msg.success((Player) player, ChatColor.YELLOW + sender.getName() + ChatColor.RESET + " さんによってニックネームを " + ChatColor.AQUA + args[3] + ChatColor.RESET + " に設定されました");
                    }
                    return true;
                } else {
                    //プレイヤーが居ない場合の処理
                    Msg.warning(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんは存在しないため操作できません");
                    return true;
                }
            }

            if (args[1].equalsIgnoreCase("remove")) {
                if (args.length != 3) {
                    Msg.success(sender, "引数が多すぎるかまたは少なすぎます");
                    help(sender, commandLabel);
                    return true;
                }

                //プレイヤーが存在するか確認
                OfflinePlayer player = PlayerDatabase.getPlayer(args[2]);
                if (player != null) {
                    PlayerDatabase.set(player, nicknamePath, "");
                    Msg.success(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんのニックネームを削除しました");
                    if (player.isOnline()) {
                        Msg.success((Player) player, ChatColor.YELLOW + sender.getName() + ChatColor.RESET + "さんによってニックネームを削除されました");
                    }
                    return true;
                } else {
                    //プレイヤーが居ない場合の処理
                    Msg.warning(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんはオンラインでない為操作できません");
                    return true;
                }
            }

            if (args[1].equalsIgnoreCase("color")) {
                if (args.length != 4) {
                    Msg.warning(sender, "引数が多すぎるかまたは少なすぎます");
                    return true;
                }
                Player player = Bukkit.getServer().getPlayer(args[3]);
                if (player == null) {
                    Msg.warning(sender, ChatColor.YELLOW + args[3] + ChatColor.RESET + "さんはオンラインでない為操作できません");
                    return true;
                }

                ChatColor color = Color.getChatColor(args[2]);
                if (args[2].equalsIgnoreCase("default") || color == null) {
                    PlayerDatabase.set(player, "nickname.color", "default");
                    Msg.success(sender, ChatColor.YELLOW + player.getName() + ChatColor.RESET + " さんのニックネームカラーを" + color + "■" + ChatColor.RESET + "に変更しました");
                    Msg.success(player, ChatColor.YELLOW + sender.getName() + ChatColor.RESET + " さんによってニックネームカラーが" + color + "■" + ChatColor.RESET + "に変更されました");
                    return true;
                }
                PlayerDatabase.set(player, "nickname.color", color.toString());
                Msg.success(sender, ChatColor.YELLOW + player.getName() + ChatColor.RESET + " さんのニックネームカラーを" + color + "■" + ChatColor.RESET + "に変更しました");
                Msg.success(player, ChatColor.YELLOW + sender.getName() + ChatColor.RESET + " さんによってニックネームカラーが" + color + "■" + ChatColor.RESET + "に変更されました");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("limits") || args[0].equalsIgnoreCase("limit") || args[0].equalsIgnoreCase("l")) {
            Msg.success(sender, "このサーバーで使用できる最大文字数" + ChatColor.DARK_GRAY + ": " + ChatColor.GOLD + plugin.configTeisyoku.getConfig().getInt("nickname.limits") + "文字");
            return true;
        }

        //下記コマンドに侵入するにはsenderがplayerである必要があります
        if (!(sender instanceof Player)) {
            Msg.success(sender, "コンソールからコマンドを送信することはできません");
            Msg.success(sender, "ただし /" + commandLabel + " admin は利用可能です");
            return true;
        }

        //player変数を用意して処理を楽に
        Player player = (Player) sender;

        //一般：set
        if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 2) {
                int maxLength = plugin.configTeisyoku.getConfig().getInt("nickname.limits");
                if (args[1].length() > maxLength) {
                    Msg.warning(sender, "ニックネームは" + maxLength + "文字以下に設定してください");
                    Msg.warning(sender, args[1] + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + ChatColor.GOLD + args[1].length() + "文字");
                    return true;
                } else {
                    PlayerDatabase.set(player, nicknamePath, args[1]);
                    Msg.success(sender, "ニックネームを " + ChatColor.AQUA + args[1] + ChatColor.RESET + " に設定しました");
                    return true;
                }
            } else {
                Msg.warning(sender, "引数が多すぎるかまたは少なすぎます");
                help(sender, commandLabel);
                return true;
            }
        }

        //一般：remove
        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length == 1) {
                PlayerDatabase.set(player, nicknamePath, "");
                Msg.success(sender, "ニックネームを削除しました");
                return true;
            } else {
                Msg.warning(sender, "引数が多すぎるかまたは少なすぎます");
                help(sender, commandLabel);
                return true;
            }
        }

        Msg.warning(sender, "引数 " + args[0] + " は存在しません");
        return true;
    }

    /**
     * nicknameコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ" + ChatColor.GRAY + " (開発協力: azuhata)");
        Msg.commandFormat(sender, commandLabel + " set <ﾆｯｸﾈｰﾑ>", "自分のﾆｯｸﾈｰﾑをｾｯﾄします");
        Msg.commandFormat(sender, commandLabel + " remove", "自分のﾆｯｸﾈｰﾑを削除します");
        Msg.commandFormat(sender, commandLabel + " <limits|limit|l>", "ニックネームの最大文字列を表示します");
        if (sender.hasPermission(Permission.NICKNAME_ADMIN.toString())) {
            Msg.commandFormat(sender, commandLabel + " admin set <ﾌﾟﾚｲﾔｰ名> <ﾆｯｸﾈｰﾑ>", "他ﾌﾟﾚｲﾔｰのﾆｯｸﾈｰﾑをｾｯﾄします");
            Msg.commandFormat(sender, commandLabel + " admin remove <ﾌﾟﾚｲﾔｰ名>", "他ﾌﾟﾚｲﾔｰのﾆｯｸﾈｰﾑを削除します");
            Msg.commandFormat(sender, commandLabel + " admin color <カラー名> <ﾌﾟﾚｲﾔｰ名>", "他ﾌﾟﾚｲﾔｰのﾆｯｸﾈｰﾑｶﾗｰを変更します");
        }
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
