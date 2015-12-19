package net.jp.minecraft.plugins;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_TPoint implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event){

        Player player = event.getPlayer();

        TeisyokuPlugin2.getInstance().TPointConfig.set(event.getPlayer().getUniqueId().toString() + ".Name",event.getPlayer().getName().toString());

        //はじめてログインしたときの設定
        if(!event.getPlayer().hasPlayedBefore()) {
            addPoint(1000, player);
        }
        TeisyokuPlugin2.getInstance().saveTPointConfig();
    }

    /**
     * プレイヤーのポイントにポイントを追加します<br />
     * @param point
     * @param player
     */
    public static void addPoint(int point , Player player){
        //演算
        int point_before = TeisyokuPlugin2.getInstance().TPointConfig.getInt(player.getPlayer().getUniqueId().toString() + ".Point");
        int point_after = point_before + point;
        TeisyokuPlugin2.getInstance().TPointConfig.set(player.getUniqueId().toString() + ".Point", point_after);
        player.sendMessage(Messages.getSuccessPrefix() + point + " TPoint受け取りました");
        TeisyokuPlugin2.getInstance().saveTPointConfig();
        status(player);//ステイタスを表示
        return;
    }

    /**
     * プレイヤーのポイントを差し引きます<br />
     * @param point
     * @param player
     */
    public static void subtractPoint(int point , Player player){
        //演算
        int point_before = TeisyokuPlugin2.getInstance().TPointConfig.getInt(player.getPlayer().getUniqueId().toString() + ".Point");
        int point_after = point_before - point;

        if(point_after < 0){
            int error = Math.abs(point_after);
            player.sendMessage(Messages.getDenyPrefix() + point + " TPoint消費しようとしましたが、" + error + " TPointが足りませんでした");
            status(player);//ステイタスを表示
            return;
        }
        else{
            player.sendMessage(Messages.getSuccessPrefix() + point + " TPoint消費しました");
            TeisyokuPlugin2.getInstance().TPointConfig.set(player.getUniqueId().toString() + ".Point", point_after);
            TeisyokuPlugin2.getInstance().saveTPointConfig();
            status(player);//ステイタスを表示
            return;
        }
    }

    /**
     * プレイヤーのポイントをセットします<br />
     * @param point
     * @param player
     */
    public static void setPoint(int point , Player player){
        TeisyokuPlugin2.getInstance().TPointConfig.set(player.getUniqueId().toString() + ".Point", point);
        player.sendMessage(Messages.getSuccessPrefix() + point + " TPointにセットしました");
        TeisyokuPlugin2.getInstance().saveTPointConfig();
        status(player);//ステイタスを表示
    }

    /**
     * Playerを渡すことで現在の保有ポイントを取得できます。<br />
     * @param player
     */
    public static void status(Player player){
        try{
            //正常に取得
            int point = TeisyokuPlugin2.getInstance().TPointConfig.getInt(player.getPlayer().getUniqueId().toString() + ".Point");
            player.sendMessage(Messages.getNormalPrefix() + "現在の保有ポイント： " + point + " TPoint");
            return;
        }
        catch (Exception e){
            //エラー
            player.sendMessage(Messages.getDenyPrefix() + "ポイント取得時にエラーが発生しました。管理者に以下のエラーをお知らせください。");
            player.sendMessage(Messages.getDenyPrefix() + "Exception Error : Listener_TPoint.status function");
            return;
        }
    }

    /**
     * uuidとplayer(sender),string(target)を渡すことで現在の保有ポイントを取得できます。<br />
     * @param uuid
     * @param sender
     * @param target
     */
    public static void status_uuid(UUID uuid , CommandSender sender, String target){
        try{
            //正常に取得

            if(TeisyokuPlugin2.getInstance().TPointConfig.get(uuid.toString() + ".Point") == null){
                sender.sendMessage(Messages.getDenyPrefix() + ChatColor.YELLOW + target.toString() +ChatColor.RESET + "は見つかりませんでした。");
                return;
            }

            int point = TeisyokuPlugin2.getInstance().TPointConfig.getInt(uuid.toString() + ".Point");
            sender.sendMessage(Messages.getNormalPrefix() + ChatColor.YELLOW + target.toString() +ChatColor.RESET + "の保有ポイント： " + point + " TPoint");
            return;
        }
        catch (Exception e){
            //エラー
            sender.sendMessage(Messages.getDenyPrefix() + "ポイント取得時にエラーが発生しました。管理者に以下のエラーをお知らせください。");
            sender.sendMessage(Messages.getDenyPrefix() + "Exception Error : Listener_TPoint.status function");
            return;
        }
    }

}
