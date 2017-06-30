package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_TPoint {

    private static File cfile;
    private static FileConfiguration config;
    private static File df = TeisyokuPlugin2.getInstance().getDataFolder();

    /**
     * プレイヤーにポイントをポイントを追加します<br />
     *
     * @param point        ポイント
     * @param targetPlayer ターゲット
     * @param sender       コマンド送信者
     */
    public static Boolean addPoint(int point, Player targetPlayer, CommandSender sender) {
        try {
            cfile = new File(df, "PlayerDatabase" + File.separator + targetPlayer.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(cfile);
            FileConfiguration playerData = config;
            int point_before = playerData.getInt("tpoint");
            int point_after = point_before + point;
            playerData.set("tpoint", point_after);
            save();
            Msg.success(targetPlayer, "" + ChatColor.AQUA + ChatColor.BOLD + point + " TPoint" + ChatColor.RESET + " 受け取りました");
            Msg.success(sender, targetPlayer.getName() + " さんに " + point + " TPoint与えました");
            sendPersonalStatus(targetPlayer);//ステイタスを表示
            return true;
        } catch (Exception e) {
            Msg.warning(targetPlayer, "不明なエラーが発生しました。 Location: Listener_TPoint >> addPoint");
            Msg.warning(sender, "不明なエラーが発生しました。 Location: Listener_TPoint >> addPoint");
            return false;
        }
    }

    /**
     * プレイヤーのポイントを差し引きます<br />
     *
     * @param point        ポイント
     * @param targetPlayer ターゲット
     * @param sender       コマンド送信者
     * @return 成功・不成功の結果を返却
     */
    public static boolean subtractPoint(int point, Player targetPlayer, CommandSender sender) {
        try {
            cfile = new File(df, "PlayerDatabase" + File.separator + targetPlayer.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(cfile);
            FileConfiguration playerData = config;
            int point_before = playerData.getInt("tpoint");
            int point_after = point_before - point;

            if (point_after < 0) {
                int error = Math.abs(point_after);
                Msg.warning(targetPlayer, point + " TPoint消費しようとしましたが、" + error + " TPoint足りませんでした");
                Msg.warning(sender, ChatColor.YELLOW + targetPlayer.getName() + ChatColor.RESET + " さんから " + point + " TPoint消費しようとしましたが、" + error + " TPoint足りませんでした");
                sendPersonalStatus(targetPlayer);//ステイタスを表示
                return false;
            } else {
                playerData.set("tpoint", point_after);
                save();
                Msg.success(targetPlayer, point + " TPoint消費しました");
                Msg.success(sender, targetPlayer.getName() + " さんから " + point + " TPoint差し引きました");
                sendPersonalStatus(targetPlayer);//ステイタスを表示
                return true;
            }
        } catch (Exception e) {
            Msg.warning(targetPlayer, "不明なエラーが発生しました。 Location: Listener_TPoint >> subtractPoint");
            Msg.warning(sender, "不明なエラーが発生しました。 Location: Listener_TPoint >> subtractPoint");
            return false;
        }
    }

    /**
     * プレイヤーが十分なTPointを持っているか確認します<br />
     *
     * @param point  ポイント
     * @param player プレイヤー
     * @return 購入可能かのBoolean
     */
    public static boolean canBuy(int point, Player player) {
        cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        FileConfiguration playerData = config;
        int point_before = playerData.getInt("tpoint");
        int point_after = point_before - point;

        if (point_after < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * プレイヤーのポイントをセットします<br />
     *
     * @param point  ポイント
     * @param player ターゲット
     */
    public static void setPoint(int point, Player player) {
        cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        FileConfiguration playerData = config;
        playerData.set("tpoint", point);
        save();
    }

    /**
     * プレイヤーのTPointステイタスを表示します<br />
     *
     * @param player ターゲット
     */
    public static void sendPersonalStatus(Player player) {
        try {
            //正常に取得
            cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(cfile);
            FileConfiguration playerData = config;
            int point = playerData.getInt("tpoint");
            int syokken = point / 1000;
            Msg.info(player, "現在の保有ポイント： " + ChatColor.AQUA + ChatColor.BOLD + point + " TPoint");
            Msg.info(player, "食券換算 : " + ChatColor.GREEN + ChatColor.BOLD + syokken + "枚" + ChatColor.RESET + "分");
        } catch (Exception e) {
            //エラー
            Msg.warning(player, "ポイント取得時にエラーが発生しました。管理者に以下のエラーをお知らせください。");
            Msg.warning(player, "Exception Error : Listener_TPoint.status function");
            e.printStackTrace();
        }
    }

    /**
     * オフラインプレイヤーのTPointステイタスを表示します<br />
     *
     * @param sender コマンド送信者
     * @param target ターゲット
     */
    public static void getOfflinePlayerStatus(CommandSender sender, OfflinePlayer target) {
        try {
            if (!target.hasPlayedBefore()) {
                Msg.warning(sender, "ログイン履歴のないプレイヤーです");
                return;
            }

            //正常に取得
            cfile = new File(df, "PlayerDatabase" + File.separator + target.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(cfile);
            FileConfiguration playerData = config;
            if (!cfile.exists()) {
                Msg.warning(sender, ChatColor.YELLOW + target.getName() + ChatColor.RESET + " は見つかりませんでした。");
                return;
            }

            int point = playerData.getInt("tpoint");
            int syokken = point / 1000;
            Msg.info(sender, ChatColor.YELLOW + target.getName() + ChatColor.RESET + " さんの保有ポイント： " + ChatColor.AQUA + ChatColor.BOLD + point + " TPoint ");
            Msg.info(sender, "食券換算 : " + ChatColor.GREEN + ChatColor.BOLD + syokken + "枚" + ChatColor.RESET + "分");
        } catch (Exception e) {
            //エラー
            Msg.warning(sender, "ポイント取得時にエラーが発生しました。管理者に以下のエラーをお知らせください。");
            Msg.warning(sender, "Exception Error : Listener_TPoint.status function");
            e.printStackTrace();
        }
    }

    /**
     * Playerを渡すことで現在保有しているポイントをintで返します<br />
     *
     * @param player プレイヤー
     * @return 保有ポイント(int)
     */
    public static int int_status(Player player) {
        try {
            //正常に取得
            cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(cfile);
            FileConfiguration playerData = config;
            return playerData.getInt("tpoint");
        } catch (Exception e) {
            //エラー
            Msg.warning(player, "ポイント取得時にエラーが発生しました。管理者に以下のエラーをお知らせください。");
            Msg.warning(player, "Exception Error : Listener_TPoint.int_status function");
            e.printStackTrace();
            return -1;
        }
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void load(Player p) {
        cfile = new File(df, "PlayerDatabase" + File.separator + p.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public static void save() {
        try {
            config.save(cfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void colorDefault(Player player) {
        cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        FileConfiguration playerData = config;
        playerData.set("nick_color", "default");
        save();
    }

    public static void colorAqua(Player player) {
        cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        FileConfiguration playerData = config;
        playerData.set("nick_color", "aqua");
        save();
    }

    public static void colorPink(Player player) {
        cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        FileConfiguration playerData = config;
        playerData.set("nick_color", "pink");
        save();
    }

    public static void colorGreen(Player player) {
        cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        FileConfiguration playerData = config;
        playerData.set("nick_color", "green");
        save();
    }
}