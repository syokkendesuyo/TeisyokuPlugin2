package net.jp.minecraft.plugins.API;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class API_Flag {

    /**
     * プレイヤーのデータベースを参照しフラグを更新します。
     *
     * @param sender  コマンド実行者
     * @param player  対象プレイヤー
     * @param args    コマンド引数
     * @param flagMsg メッセージ
     */
    public static void set(CommandSender sender, Player player, String[] args, String flagMsg) {
        //オンライン時のみ更新可能
        //TODO: オフライン状態でも変更可能にする
        if (!player.isOnline()) {
            Msg.warning(sender, "プレイヤー" + ChatColor.YELLOW + sender + ChatColor.RESET + "はオンラインではありません。");
            return;
        }

        //引数に不正な文字列があった場合は処理を終了する
        if (!(args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false"))) {
            Msg.warning(sender, "引数「" + args[1] + "」は利用できません。trueまたはfalseを指定して下さい。");
            return;
        }

        //引数をBoolean型にキャスト
        Boolean value = Boolean.valueOf(args[1]);

        //ChatColorを設定
        ChatColor color = ChatColor.RED;
        if (value) {
            color = ChatColor.GREEN;
        }

        //設定を保存
        API_PlayerDatabase.set(player, "flags." + args[0], value);
        Msg.success(sender, flagMsg + "： " + color + value);
    }

    /**
     * フラグ状態を取得します。<br />
     * 設定が存在しない場合は有効状態を示します。
     *
     * @param player プレイヤー
     * @param flag   フラグ名
     * @return フラグ状態
     */
    @Deprecated
    public static Boolean get(Player player, String flag) {
        String flagData = API_PlayerDatabase.getString(player, "flags." + flag);
        if (!(flagData.equals("true") || flagData.equals("false"))) {
            return true;
        }
        return API_PlayerDatabase.getBoolean(player, "flags." + flag);
    }
}
