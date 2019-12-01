package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.function.Fly;
import net.jp.minecraft.plugins.teisyokuplugin2.module.PlayerDatabase;
import net.jp.minecraft.plugins.teisyokuplugin2.module.TFlag;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Color;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import net.jp.minecraft.plugins.teisyokuplugin2.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class Listener_JoinQuit implements Listener {

    @EventHandler
    public void onPlayerJoinMessage(PlayerJoinEvent event) {
        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();
        Player player = event.getPlayer();

        //古い設定等に対応
        updateNewFunctions(player);

        //ログイン時のメッセージを日本語化
        event.setJoinMessage("");
        Msg.success(Bukkit.getConsoleSender(), ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET + " さんがゲームに参加しました", true);

        //ログイン時のプレイヤーデータを保管
        PlayerDatabase.set(player, "id", player.getName());
        PlayerDatabase.set(player, "join.date", TimeUtil.getDateFormat());
        PlayerDatabase.set(player, "join.timestamp", System.currentTimeMillis());

        //飛行モードが有効化されているか確認
        if (plugin.configTeisyoku.getConfig().getBoolean("functions.fly")) {
            //飛行モードを継承するかどうか確認
            if (TFlag.getTFlagStatus(player, TFlag.FLY_SAVE_STATE.getTFlag())) {
                //ゲームモードがデフォルトで飛行の場合は無視
                if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
                    Msg.info(player, "ゲームモードがサバイバル時にのみ飛行モードが継承されます");
                } else {
                    //設定が更新される場合に通知を表示
                    if (player.isFlying() != PlayerDatabase.getBoolean(player, "fly")) {
                        Fly.setFlying(player, PlayerDatabase.getBoolean(player, "fly"));
                    }
                }
            }
        } else {
            Fly.setFlying(player, false);
        }

        List<String> ad = plugin.configTeisyoku.getConfig().getStringList("joinMessage");
        for (String s : ad) {
            Msg.info(player, Color.convert(s));
        }
        if (plugin.configTeisyoku.getConfig().getString("debug.SpawnFixed") == null) {
            plugin.configTeisyoku.getConfig().set("debug.SpawnFixed", false);
        }
        if (plugin.configTeisyoku.getConfig().getBoolean("debug.SpawnFixed")) {
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 72, 0));
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //ログイン時のメッセージを日本語化
        event.setQuitMessage("");
        Msg.success(Bukkit.getConsoleSender(), ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET + " さんがゲームから退室しました", true);

        //ログアウト時にプレイヤーデータを保管
        PlayerDatabase.set(player, "id", player.getName());
        PlayerDatabase.set(player, "quit.date", TimeUtil.getDateFormat());
        PlayerDatabase.set(player, "quit.timestamp", System.currentTimeMillis());
    }

    /**
     * 古い設定等に対応するためのメソッド
     *
     * @param player プレイヤー
     */
    private void updateNewFunctions(Player player) {

        boolean isUpdatePlayerDatabase = false;

        //古い個人設定を削除
        PlayerDatabase.set(player, "auto_cart_remove", null);
        PlayerDatabase.set(player, "flag.auto_cart_remove", null);

        //nameからidへパスが変更された件に対応
        //ログイン時にidを更新するため古いパスのみ削除
        String oldNamePathData = PlayerDatabase.getString(player, "name");
        if (!oldNamePathData.isEmpty()) {
            PlayerDatabase.set(player, "name", null);
            isUpdatePlayerDatabase = true;
        }

        //nickからnickname.prefixへパスが変更された件に対応
        String oldNickPathData = PlayerDatabase.getString(player, "nick");
        if (!oldNickPathData.isEmpty()) {
            PlayerDatabase.set(player, "nick", null);
            PlayerDatabase.set(player, "nickname.prefix", oldNickPathData);
            isUpdatePlayerDatabase = true;
        }

        //nick_colorからnickname.colorへパスが変更された件に対応
        String oldNickColorPathData = PlayerDatabase.getString(player, "nick_color");
        if (!oldNickColorPathData.isEmpty()) {
            PlayerDatabase.set(player, "nick_color", null);
            PlayerDatabase.set(player, "nickname.color", oldNickColorPathData);
            isUpdatePlayerDatabase = true;
        }

        //PlayerDatabaseを更新した場合、管理者へ通知する
        if (isUpdatePlayerDatabase) {
            Msg.adminBroadcast(ChatColor.YELLOW + player.getName() + ChatColor.RESET + " さんのデータベースを更新しました", false);
        }
    }
}
