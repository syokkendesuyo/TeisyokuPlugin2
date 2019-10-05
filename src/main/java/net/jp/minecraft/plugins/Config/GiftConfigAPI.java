package net.jp.minecraft.plugins.Config;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Color;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 * TODO: ギフトコードAPIをAPIとして提供するように変更
 */
public class GiftConfigAPI {

    /**
     * Gift.ymlをカスタムコンフィグ経由で取得
     */
    private static CustomConfig config = TeisyokuPlugin2.getInstance().configGift;

    /**
     * ギフト機能を実行するメソッド
     *
     * @param giftCode　ギフトコード
     * @param sender コマンド送信者
     */
    public static void gift(String giftCode, CommandSender sender) {

        //ギフト機能が有効化されているかどうか検出
        if (!TeisyokuPlugin2.getInstance().configTeisyoku.getConfig().getBoolean("functions.gift")) {
            Msg.warning(sender, "ギフト機能は有効化されていません");
            return;
        }

        //ギフトの生成
        config.saveDefaultConfig();

        //送信元のチェック
        if (!(sender instanceof Player)) {
            Msg.warning(sender, "コンソールからこのコマンドは実行できません");
            return;
        }

        //キャスト
        Player player = (Player) sender;

        //ギフトコードの検索
        if (config.getConfig().getString("gifts." + giftCode) == null) {
            Msg.warning(sender, "ギフト名 " + ChatColor.YELLOW + giftCode + ChatColor.RESET + " は見つかりませんでした");
            return;
        }

        //利用済かチェックする
        List<String> players = config.getConfig().getStringList("gifts." + giftCode + ".players");
        if (listMatch(players, player.getUniqueId().toString())) {
            Msg.warning(player, "ギフトコード " + ChatColor.YELLOW + giftCode + ChatColor.RESET + " は既に利用されています");
            return;
        }

        //利用回数の取得
        if (!(config.getConfig().getString("gifts." + giftCode + ".limit") == null)) {
            int limit = config.getConfig().getInt("gifts." + giftCode + ".limit");
            if (0 < limit) {
                config.getConfig().set("gifts." + giftCode + ".limit", limit - 1);
                config.saveConfig();
            } else if (limit == 0) {
                Msg.warning(sender, "ギフト名 " + ChatColor.YELLOW + giftCode + ChatColor.RESET + " は利用できません");
                Msg.warning(sender, "理由： 利用回数が制限値に達しました");
                return;
            }
        }

        //コマンドを実行
        List<String> code = config.getConfig().getStringList("gifts." + giftCode + ".commands");
        for (String s : code) {
            String ss = Color.convert(s);
            String sss = ss.replaceAll("%player%", sender.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), sss);
        }

        //既に利用したプレイヤーとして登録する
        List<String> playersList = config.getConfig().getStringList("gifts." + giftCode + ".players");
        playersList.add(player.getUniqueId().toString());
        config.getConfig().set("gifts." + giftCode + ".players", playersList);
        config.saveConfig();
    }

    /**
     * 与えられたリスト内に指定した要素が存在するか確認するメソッド。
     *
     * @param list リストデータ
     * @param match 検索文字列
     * @return 検索結果
     */
    private static boolean listMatch(List<String> list, String match) {
        for (String s : list) {
            if (s.equalsIgnoreCase(match)) {
                return true;
            }
        }
        return false;
    }
}
