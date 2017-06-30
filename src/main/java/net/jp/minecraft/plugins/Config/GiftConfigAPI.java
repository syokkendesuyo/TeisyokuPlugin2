package net.jp.minecraft.plugins.Config;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class GiftConfigAPI {
    private static void create() {
        Connection_TeisyokuConfig.createConfig("Gift.yml");
    }

    public static void gift(String gift_code, CommandSender sender) {

        //コマンドが有効化されているかどうか検出
        if (!TeisyokuPlugin2.getInstance().TeisyokuConfig.getBoolean("commands.gift")) {
            Msg.warning(sender, "ギフトコマンドは有効化されていません");
            return;
        }

        //ギフトの生成
        create();

        //送信元のチェック
        if (!(sender instanceof Player)) {
            Msg.warning(sender, "コンソールからこのコマンドは実行できません");
            return;
        }

        //キャスト
        Player player = (Player) sender;

        //ギフトコードの検索
        if (Connection_TeisyokuConfig.getString("gifts." + gift_code) == null) {
            Msg.warning(sender, "ギフト名 " + ChatColor.YELLOW + gift_code + ChatColor.RESET + " は見つかりませんでした");
            return;
        }

        //利用済かチェックする
        List<String> players = Connection_TeisyokuConfig.getStringList("gifts." + gift_code + ".players");
        if (Connection_TeisyokuConfig.listMatch(players, player.getUniqueId().toString())) {
            Msg.warning(player, "ギフトコード " + ChatColor.YELLOW + gift_code + ChatColor.RESET + " は既に利用されています");
            return;
        }

        //利用回数の取得
        if (!(Connection_TeisyokuConfig.getString("gifts." + gift_code + ".limit") == null)) {
            int limit = Connection_TeisyokuConfig.getInt("gifts." + gift_code + ".limit");
            if (0 < limit) {
                Connection_TeisyokuConfig.setConfig("gifts." + gift_code + ".limit", limit - 1);
                Connection_TeisyokuConfig.saveConfig();
            } else if (limit == 0) {
                Msg.warning(sender, "ギフト名 " + ChatColor.YELLOW + gift_code + ChatColor.RESET + " は利用できません");
                Msg.warning(sender, "理由：利用回数が制限値に達しました");
                return;
            }
        }

        //コマンドを実行
        List<String> code = Connection_TeisyokuConfig.getStringList("gifts." + gift_code + ".commands");
        for (String s : code) {
            String ss = color(s);
            String sss = ss.replaceAll("%player%", sender.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), sss);
        }

        //既に利用したプレイヤーとして登録する
        Connection_TeisyokuConfig.addArrayList("gifts." + gift_code + ".players", player.getUniqueId().toString());
        Connection_TeisyokuConfig.saveConfig();
    }

    private static String color(String str) {
        return str.replaceAll("&", "§");
    }
}
