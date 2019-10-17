package net.jp.minecraft.plugins.API;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

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
     * @return フラグ状態
     */
    public static Boolean update(CommandSender sender, Player player, String[] args, String flagMsg) {
        //オンライン時のみ更新可能
        //TODO: オフライン状態でも変更可能にする
        if (!player.isOnline()) {
            Msg.warning(sender, "プレイヤー" + ChatColor.YELLOW + sender + ChatColor.RESET + "はオンラインではありません。");
            return false;
        }

        //引数に不正な文字列があった場合は処理を終了する
        if (!(args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false"))) {
            Msg.warning(sender, "引数「" + args[1] + "」は利用できません。trueまたはfalseを指定して下さい。");
            return false;
        }

        //引数をBoolean型にキャスト
        Boolean value = Boolean.valueOf(args[1]);

        //ChatColorを設定
        ChatColor color = ChatColor.RED;
        if (value) {
            color = ChatColor.GREEN;
        }

        //ファイル生成
        String playerUniqueId = player.getUniqueId().toString();
        File userdata = new File(TeisyokuPlugin2.getInstance().getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + playerUniqueId + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        //古いフラグを削除する
        // TODO: Listener_JoinQuitへ移動
        playerData.set("auto_cart_remove", null);
        playerData.set("flag.auto_cart_remove", null);

        //設定を保存
        playerData.set("flags." + args[0], value);

        //保存
        try {
            playerData.save(f);
            Msg.success(sender, flagMsg + "： " + color + value);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * フラグ状態を取得します。<br />
     * 設定が存在しない場合は有効状態を示します。
     *
     * @param player プレイヤー
     * @param flag   フラグ名
     * @return フラグ状態
     *
     * TODO: PlayerDatabaseへ移行
     */
    @Deprecated
    public static Boolean get(Player player, String flag) {
        String playerUniqueId = player.getUniqueId().toString();
        File userdata = new File(TeisyokuPlugin2.getInstance().getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + playerUniqueId + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (playerData.getString("flags." + flag) == null) {
            return true;
        }
        return playerData.getBoolean("flags." + flag);
    }
}
