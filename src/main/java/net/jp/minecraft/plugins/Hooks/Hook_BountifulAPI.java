package net.jp.minecraft.plugins.Hooks;

import com.connorlinfoot.bountifulapi.BountifulAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Hook_BountifulAPI {

    /**
     * BountifulAPIのsendTabTitleを利用してTabリストに対しパケットを送信します<br />
     * 読み込まれていない場合は処理がされません。<br />
     * <br />
     * リンク<br />
     * <a href="https://www.spigotmc.org/resources/bountifulapi-1-8-1-12.1394/">Spigot-BountifulAPI</a><br />
     * <a href="https://github.com/ConnorLinfoot/BountifulAPI">GitHub-BountifulAPI</a><br />
     *
     * @param player プレイヤー
     * @param header ヘッダー
     * @param footer フッター
     * @see com.connorlinfoot.bountifulapi.BountifulAPI
     */
    public static void sendTabTitle(Player player, String header, String footer) {
        if (Bukkit.getServer().getPluginManager().getPlugin("BountifulAPI") != null) {
            BountifulAPI.sendTabTitle(player, header, footer);
        }
    }
}
