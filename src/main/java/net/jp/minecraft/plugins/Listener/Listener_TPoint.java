package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_TPoint {

    static File cfile;
    static FileConfiguration config;
    static File df = TeisyokuPlugin2.getInstance().getDataFolder();

    /**
     * プレイヤーのポイントにポイントを追加します<br />
     * @param point
     * @param player
     */
    public static void addPoint(int point , Player player){
        cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        FileConfiguration playerData = config;
        int point_before = playerData.getInt("tpoint");
        int point_after = point_before + point;
        Msg.info(player, point_before + " " + point_after);//debug
        playerData.set("tpoint", point_after);
        save();
        Msg.success(player, point + " TPoint受け取りました");
        status(player);//ステイタスを表示
        return;
    }

    /**
     * プレイヤーのポイントを差し引きます<br />
     * @param point
     * @param player
     *
     * @return 成功・不成功の結果を返却
     */
    public static boolean subtractPoint(int point , Player player){
        cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        FileConfiguration playerData = config;
        int point_before = playerData.getInt("tpoint");
        int point_after = point_before - point;

        if(point_after < 0){
            int error = Math.abs(point_after);
            Msg.warning(player, point + " TPoint消費しようとしましたが、" + error + " TPoint足りませんでした");
            status(player);//ステイタスを表示
            return false;
        }
        else{
            playerData.set("tpoint", point_after);
            save();
            Msg.success(player, point + " TPoint消費しました");
            save();
            status(player);//ステイタスを表示
            return true;
        }
    }

    /**
     * プレイヤーのポイントをセットします<br />
     * @param point
     * @param player
     */
    public static void setPoint(int point , Player player){
        cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        FileConfiguration playerData = config;
        playerData.set("tpoint", point);
        Msg.success(player," TPointにセットしました");
        save();
        status(player);//ステイタスを表示
    }

    /**
     * Playerを渡すことで現在の保有ポイントを取得できます。<br />
     * @param player
     */
    public static void status(Player player){
        try{
            //正常に取得
            cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(cfile);
            FileConfiguration playerData = config;
            int point = playerData.getInt("tpoint");
            Msg.success(player,"現在の保有ポイント： " + point + " TPoint");
            return;
        }
        catch (Exception e){
            //エラー
            Msg.warning(player, "ポイント取得時にエラーが発生しました。管理者に以下のエラーをお知らせください。");
            Msg.warning(player, "Exception Error : Listener_TPoint.status function");
            e.printStackTrace();
            return;
        }
    }

    /**
     * uuidとplayer(sender),string(target)を渡すことで現在の保有ポイントを取得できます。<br />
     * @param uuid
     * @param sender
     * @param target
     * @param player
     */
    public static void status_uuid(UUID uuid , CommandSender sender, String target, Player player){
        try{
            //正常に取得
            cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(cfile);
            FileConfiguration playerData = config;
            if(!cfile.exists()){
                Msg.warning(player, ChatColor.YELLOW + target.toString() +ChatColor.RESET + " は見つかりませんでした。");
                return;
            }

            int point = playerData.getInt("tpoint");
            Msg.info(sender, ChatColor.YELLOW + target.toString() +ChatColor.RESET + "の保有ポイント： " + point + " TPoint");
            return;
        }
        catch (Exception e){
            //エラー
            Msg.warning(sender, "ポイント取得時にエラーが発生しました。管理者に以下のエラーをお知らせください。");
            Msg.warning(sender, "Exception Error : Listener_TPoint.status function");
            e.printStackTrace();
            return;
        }
    }

    /**
     * Playerを渡すことで現在保有しているポイントをintで返します<br />
     * @param player
     * @return 保有ポイント(int)
     */
    public static int int_status(Player player){
        try{
            //正常に取得
            cfile = new File(df, "PlayerDatabase" + File.separator + player.getUniqueId() + ".yml");
            config = YamlConfiguration.loadConfiguration(cfile);
            FileConfiguration playerData = config;
            int point = playerData.getInt("tpoint");
            return point;
        }
        catch (Exception e){
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
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}