package net.jp.minecraft.plugins.api;

import net.jp.minecraft.plugins.module.Permission;
import net.jp.minecraft.plugins.util.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import static java.lang.Math.pow;

/**
 * TeisyokuPlugin2<br />
 * 便利なAPIを提供する総合クラス
 *
 * @author syokkendesuyo
 */
public class API {

    /**
     * 指定した位置から一番近いプレイヤーを返却するメソッド
     * TODO: プレイヤーが０の場合の処理を追加
     *
     * @param loc ロケーション
     * @return 一番近いプレイヤー
     */
    public static Player getNearByPlayer(Location loc) {
        double size = 100;
        Player target = null;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getWorld() != loc.getWorld()) {
                continue;
            }
            Location targetLoc = onlinePlayer.getLocation();

            double x1 = loc.getX();
            double y1 = loc.getY();
            double z1 = loc.getZ();

            double x2 = targetLoc.getX();
            double y2 = targetLoc.getY();
            double z2 = targetLoc.getZ();

            double length = pow((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1), 0.5);

            if (length < size) {
                target = onlinePlayer;
                size = length;
            }
        }
        return target;
    }

    /**
     * プレイヤー名であるか確認するメソッド
     *
     * @param playerName プレイヤー名
     * @return プレイヤー名かどうか
     */
    public static boolean isPlayerName(String playerName) {
        if (playerName == null) {
            return false;
        }
        if (playerName.length() < 3 || playerName.length() > 16) {
            return false;
        }
        return Pattern.matches("^[0-9a-zA-Z]+$", playerName);
    }

    /**
     * プレイヤーを取得するメソッド<br />
     * PlayerDatabaseにデータが存在しない場合やプレイヤー名が存在しない場合はnullを返却します。
     *
     * @param playerName プレイヤー名
     * @return オフラインプレイヤー
     */
    public static OfflinePlayer getPlayer(String playerName) {
        try {
            UUID uuid = UUIDFetcher.getUUIDOf(playerName);

            //Minecraftに登録されているUUIDか検索 (ネットワーク接続必須)
            if (uuid == null) {
                return null;
            }

            //UUIDからオフラインプレイヤーへ変換
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

            //データベースにデータが存在するか確認
            if (API_PlayerDatabase.exists(player)) {
                return null;
            }
            return player;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 渡された文字列が数字であるか確認する
     *
     * @param num 文字列
     * @return boolean
     */
    public static boolean isNumber(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException event) {
            return false;
        }
    }

    /**
     * 日時フォーマットを取得するメソッド
     *
     * @return 日時の文字列
     */
    public static String getDateFormat() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
        return sdf.format(date.getTime());
    }

    /**
     * String型からChatColor型へ変換するメソッド<br />
     * 存在しない文字列の場合、グレーが返却されます
     *
     * @param color String型のカラー
     * @return チャットカラーまたはnull
     */
    public static ChatColor getChatColor(String color) {
        for (ChatColor c : ChatColor.class.getEnumConstants()) {
            if (c.name().equalsIgnoreCase(color)) {
                return c;
            }
        }
        // TODO: デバッグ機能を追加
        return ChatColor.GRAY;
    }

    /**
     * 複数のパーミッションを同時にチェックし、1件でも保有しているパーミッションがあった場合、trueを返します。
     *
     * @param sender      コマンド
     * @param permissions パーミッション
     * @return パーミッションの状態
     */
    public static boolean hasPermission(CommandSender sender, Permission... permissions) {
        for (Permission permission : permissions) {
            if (sender.hasPermission(permission.toString())) {
                return true;
            }
        }
        return false;
    }
}
