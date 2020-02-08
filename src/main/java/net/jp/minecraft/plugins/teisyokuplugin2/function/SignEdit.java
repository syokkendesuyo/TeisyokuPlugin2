package net.jp.minecraft.plugins.teisyokuplugin2.function;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_TPoint;
import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Color;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import net.jp.minecraft.plugins.teisyokuplugin2.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * TeisyokuPlugin2<br />
 * 看板編集機能に関するクラス
 *
 * @author syokkendesuyo
 */
public class SignEdit {
    private static HashMap<UUID, String> editData = new HashMap<>();
    private static HashMap<UUID, Integer> lineData = new HashMap<>();

    /**
     * 看板の更新データを登録するメソッド
     *
     * @param player     登録対象のプレイヤー
     * @param editString 登録する文字列
     * @param line       看板のライン番号
     */
    public static void saveData(Player player, String editString, int line) {
        editData.put(player.getUniqueId(), editString);
        lineData.put(player.getUniqueId(), line - 1);
    }

    /**
     * 看板の更新データ情報を削除するメソッド
     *
     * @param player 　削除対象のプレイヤー
     */
    public static void removeData(Player player) {
        editData.remove(player.getUniqueId());
        lineData.remove(player.getUniqueId());
    }

    /**
     * 看板のアップデートを実行するメソッド
     *
     * @param loc    看板の位置
     * @param player プレイヤー
     */
    public static void updateSign(Location loc, Player player) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //実行コマンドのパーミッションを確認
        if (!(player.hasPermission(Permission.USER.toString()) || player.hasPermission(Permission.SIGNEDIT.toString()) || player.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(player, Permission.SIGNEDIT);
            return;
        }

        //TPointを必要とする場合、購入処理を行う
        int cost = plugin.configTeisyoku.getConfig().getInt("signedit.cost");
        if (0 < cost) {
            if (!(Listener_TPoint.canBuy(cost, player))) {
                Msg.warning(player, "看板を更新するには" + cost + "TPoint必要です");
                removeData(player);
                return;
            }
            Listener_TPoint.subtractPoint(cost, player, player);
        }

        World w = loc.getWorld();
        assert w != null;
        Block a = w.getBlockAt(loc);
        if (a.getType().toString().contains("SIGN")) {
            Sign sign = (Sign) a.getState();
            sign.setLine(lineData.get(player.getUniqueId()), StringUtil.replaceToBlank(Color.convert(editData.get(player.getUniqueId()))));
            sign.update(true);
        }
        Msg.success(player, "看板を更新しました。 " + StringUtil.replaceToBlank(Color.convert(editData.get(player.getUniqueId()))) + ChatColor.GRAY + " @ " + ChatColor.RESET + (lineData.get(player.getUniqueId()) + 1));
        removeData(player);
    }

    /**
     * 編集中かどうかを確認するメソッド<br />
     * 編集中の場合、ハッシュマップにデータが登録されているためtrueが返却されます。
     *
     * @param player プレイヤー
     * @return 編集モードの状態
     */
    public static boolean isEditMode(Player player) {
        return lineData.containsKey(player.getUniqueId());
    }
}
