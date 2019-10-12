package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.API.API;
import net.jp.minecraft.plugins.API.API_Nick;
import net.jp.minecraft.plugins.Listener.Listener_TPoint;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo azuhata
 */
public class Command_Nickname implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {


        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
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
        if (!(sender.hasPermission(Permission.USER.toString()) || sender.hasPermission(Permission.NICKNAME.toString()) || sender.hasPermission(Permission.ADMIN.toString()))) {
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

                //オンラインかどうかの確認
                Player player = API.getPlayer(args[2]);
                if (player != null) {
                    API_Nick.setNick(player, args[3]);
                    Msg.success(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + " さんのニックネームを " + ChatColor.AQUA + args[3] + ChatColor.RESET + " に設定しました");
                    Msg.success(player, ChatColor.YELLOW + sender.getName() + ChatColor.RESET + " さんによってニックネームを " + ChatColor.AQUA + args[3] + ChatColor.RESET + " に設定されました");
                    return true;
                } else {
                    //プレイヤーが居ない場合の処理
                    Msg.warning(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんはオンラインではないため操作できません");
                    return true;
                }
            }

            if (args[1].equalsIgnoreCase("remove")) {
                if (!(args.length == 3)) {
                    Msg.success(sender, "引数が多すぎるかまたは少なすぎます");
                    help(sender, commandLabel);
                    return true;
                }

                //オンラインかどうかの確認
                Player player = API.getPlayer(args[2]);
                if (player != null) {
                    API_Nick.removeNick(player);
                    Msg.success(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんのニックネームを削除しました");
                    Msg.success(player, ChatColor.YELLOW + sender.getName() + ChatColor.RESET + "さんによってニックネームを削除されました");
                    return true;
                } else {
                    //プレイヤーが居ない場合の処理
                    Msg.warning(sender, ChatColor.YELLOW + args[2] + ChatColor.RESET + "さんはオンラインでない為操作できません");
                    return true;
                }
            }

            if (args[1].equalsIgnoreCase("color")) {
                if (!(args.length == 4)) {
                    Msg.warning(sender, "引数が多すぎるかまたは少なすぎます");
                    return true;
                }
                Player player = Bukkit.getServer().getPlayer(args[3]);
                if (player == null) {
                    Msg.warning(sender, ChatColor.YELLOW + args[3] + ChatColor.RESET + "さんはオンラインでない為操作できません");
                    return true;
                }
                if (args[2].equalsIgnoreCase("default")) {
                    Listener_TPoint.setDefault(player);
                    return true;
                }
                if (args[2].equalsIgnoreCase("AQUA")) {
                    Listener_TPoint.setNickColor(player, ChatColor.AQUA);
                    return true;
                }
                if (args[2].equalsIgnoreCase("LIGHT_PURPLE")) {
                    Listener_TPoint.setNickColor(player, ChatColor.LIGHT_PURPLE);
                    return true;
                }
                if (args[2].equalsIgnoreCase("GREEN")) {
                    Listener_TPoint.setNickColor(player, ChatColor.GREEN);
                    return true;
                }
                Msg.warning(sender, args[2] + " は利用できません");

            }
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
                //TODO: ニックネームの最大文字数を設定可能にする
                if (args[1].length() > 10) {
                    Msg.warning(sender, "ニックネームは10文字以下に設定してください");
                    return true;
                } else {
                    API_Nick.setNick(player, args[1]);
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
                API_Nick.removeNick(player);
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
        Msg.success(sender, "コマンドのヘルプ" + ChatColor.GRAY + "(開発協力: azuhata)");
        Msg.commandFormat(sender, commandLabel + " set <ﾆｯｸﾈｰﾑ>", "自分のﾆｯｸﾈｰﾑをｾｯﾄします");
        Msg.commandFormat(sender, commandLabel + " remove", "自分のﾆｯｸﾈｰﾑを削除します");
        if (sender.hasPermission(Permission.NICKNAME.toString())) {
            Msg.commandFormat(sender, commandLabel + " admin set <ﾌﾟﾚｲﾔｰ名> <ﾆｯｸﾈｰﾑ>", "他ﾌﾟﾚｲﾔｰのﾆｯｸﾈｰﾑをｾｯﾄします");
            Msg.commandFormat(sender, commandLabel + " admin remove <ﾌﾟﾚｲﾔｰ名>", "他ﾌﾟﾚｲﾔｰのﾆｯｸﾈｰﾑを削除します");
            Msg.commandFormat(sender, commandLabel + " admin color <カラー名> <ﾌﾟﾚｲﾔｰ名>", "他ﾌﾟﾚｲﾔｰのﾆｯｸﾈｰﾑｶﾗｰを変更します");
        }
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
