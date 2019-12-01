package net.jp.minecraft.plugins.teisyokuplugin2.function;

import net.jp.minecraft.plugins.teisyokuplugin2.module.PlayerDatabase;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2<br />
 * 飛行状態を更新する提供します。
 *
 * @author syokkendesuyo
 */
public class Fly {

    /**
     * 飛行モードを変更(本人から指定)
     *
     * @param player プレイヤー
     * @param bool   飛行状態
     */
    public static void setFlying(Player player, Boolean bool) {
        setFlying(player, bool, player);
    }

    /**
     * 飛行モードを変更(他者から指定)
     *
     * @param player ターゲットプレイヤー
     * @param bool   飛行状態
     * @param sender コマンド送信者
     */
    public static void setFlying(Player player, Boolean bool, CommandSender sender) {
        player.setAllowFlight(bool);
        player.setFlying(bool);
        PlayerDatabase.set(player, "fly", bool);
        if (bool) {
            Msg.success(sender, ChatColor.YELLOW + player.getName() + ChatColor.RESET + " の飛行モードを" + ChatColor.GREEN + " 有効 " + ChatColor.RESET + "にしました");
            if (!player.equals(sender)) {
                Msg.success(player, ChatColor.YELLOW + sender.getName() + ChatColor.RESET + " によって飛行モードが" + ChatColor.GREEN + " 有効 " + ChatColor.RESET + "に変更されました");
            }
            return;
        }
        Msg.success(sender, ChatColor.YELLOW + player.getName() + ChatColor.RESET + " の飛行モードを" + ChatColor.RED + " 無効 " + ChatColor.RESET + "にしました");
        if (!player.equals(sender)) {
            Msg.success(player, ChatColor.YELLOW + sender.getName() + ChatColor.RESET + " によって飛行モードが" + ChatColor.RED + " 無効 " + ChatColor.RESET + "に変更されました");
        }
    }

    /**
     * 飛行状態をトグルするメソッド
     *
     * @param player プレイヤー
     */
    public static void toggleFlying(Player player) {
        if (isFlying(player)) {
            setFlying(player, false);
            return;
        }
        setFlying(player, true);
    }

    /**
     * 飛行状態を確認するメソッド
     *
     * @param player プレイヤー
     * @return 状態
     */
    public static boolean isFlying(Player player) {
        return PlayerDatabase.getBoolean(player, "fly");
    }
}
