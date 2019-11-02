package net.jp.minecraft.plugins.Commands;

import com.google.common.base.Joiner;
import net.jp.minecraft.plugins.API.API;
import net.jp.minecraft.plugins.Enum.Permission;
import net.jp.minecraft.plugins.Enum.TFlag;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Color;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * TeisyokuPlugin2
 * callコマンド
 *
 * @author syokkendesuyo
 */
public class Command_Call implements CommandExecutor {
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.call")) {
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

        //パーミッションの確認コマンド
        if (args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("perms") || args[0].equalsIgnoreCase("permission")) {
            Msg.checkPermission(sender,
                    Permission.USER,
                    Permission.CALL,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!API.hasPermission(sender, Permission.USER, Permission.CALL, Permission.ADMIN)) {
            Msg.noPermissionMessage(sender, Permission.CALL);
            return true;
        }

        //引数が1だった場合
        if (args.length == 1) {
            help(sender, commandLabel);
            return true;
        }

        //プレイヤーの変数を作成
        Player player = Bukkit.getServer().getPlayer(args[0]);

        //プレイヤー名を一時保存
        String playerName = args[0];

        //プレイヤーがオンラインであればメッセージを送信
        if (!(player == null)) {

            //プレイヤー名を削除
            args[0] = "";

            //引数を全て接続
            String arg = Joiner.on(' ').join(args);

            //メッセージを送信
            Msg.success(sender, ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " さんにメッセージを送信しました" + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + Color.convert(arg));
            Msg.success(player, ChatColor.YELLOW + sender.getName() + ChatColor.GRAY + " さんからメッセージ" + ChatColor.DARK_GRAY + " : " + ChatColor.RESET + Color.convert(arg));

            //サウンドを再生
            if (TFlag.getTFlagStatus(player, TFlag.CALL_SOUNDS.getTFlag())) {
                Sounds.play(player, Sound.BLOCK_NOTE_BLOCK_CHIME);
            }

            //コンソールなどには音を鳴らせないので送信先がプレイヤーかどうか確認する
            if (sender instanceof Player) {
                Player receiver = (Player) sender;
                if (TFlag.getTFlagStatus(receiver, TFlag.CALL_SOUNDS.getTFlag())) {
                    Sounds.play(receiver, Sound.ENTITY_ARROW_SHOOT);
                }
            }
            return true;
        } else {
            Msg.warning(sender, ChatColor.YELLOW + playerName + ChatColor.RESET + " さんは現在オフラインです");
            return true;
        }
    }

    /**
     * callコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + " <プレイヤー> <メッセージ>", "音付きで個人メッセージを送信");
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
