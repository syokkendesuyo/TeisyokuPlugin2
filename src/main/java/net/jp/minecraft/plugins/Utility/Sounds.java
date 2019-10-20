package net.jp.minecraft.plugins.Utility;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2
 * サウンドをプレイヤーに送信
 *
 * @author syokkendesuyo
 */
public class Sounds {

    /**
     * サウンドを再生
     *
     * @param player プレイヤー
     * @param sound  サウンド
     */
    public static void play(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 10.0F, 1.0F);
    }
}
