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
 * @auther syokkendesuyo
 */
public class API_Flag {

    /**
     * プレイヤーのデータベースを参照しフラグを更新します。
     *
     * @param sender  コマンド実行者
     * @param player  対象プレイヤー
     * @param args    コマンド引数
     * @param flagMsg メッセージ
     * @return
     */
    public static Boolean updateFlagBoolean(CommandSender sender, Player player, String[] args, String flagMsg) {

        //オンライン時のみ更新可能
        if (!player.isOnline()) {
            Msg.warning(sender, "プレイヤー" + ChatColor.YELLOW + sender + ChatColor.RESET + "はオンラインではありません。");
            return false;
        }

        //ファイル生成
        String playerUniqueId = player.getUniqueId().toString();
        File userdata = new File(TeisyokuPlugin2.getInstance().getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + playerUniqueId + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        //古いフラグを削除する
        playerData.set("auto_cart_remove", null);
        playerData.set("flag.auto_cart_remove", null);

        if (args[1].equalsIgnoreCase("true")) {
            try {
                playerData.set("flags." + args[0], true);
                Msg.success(sender, flagMsg + "： " + ChatColor.GREEN + args[1]);
                playerData.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else if (args[1].equalsIgnoreCase("false")) {
            try {
                playerData.set("flags." + args[0], false);
                Msg.success(sender, flagMsg + "： " + ChatColor.RED + args[1]);
                playerData.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        Msg.warning(sender, "引数「" + args[1] + "」は利用できません。trueまたはfalseを指定して下さい。");
        return false;
    }

    /**
     * フラグ状態を取得します。<br />
     * 設定が存在しない場合は有効状態を示します。
     *
     * @param player プレイヤー
     * @param flag   フラグ名
     * @return 状態
     */
    public static Boolean getBoolean(Player player, String flag) {
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
