package net.jp.minecraft.plugins.Utility;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * TeisyokuPlugin2
 * サウンドをプレイヤーに送信
 *
 * @author syokkendesuyo
 */
public class Sounds implements Listener {

    @Deprecated
    public static void sound_note(Player player) {
        player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_NOTE_PLING, 3.0F, 1.5F);
    }

    @Deprecated
    public static void sound_arrow(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 10.0F, 1.0F);
    }

    @Deprecated
    public static void sound_villager(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 10.0F, 1.0F);
    }

    @Deprecated
    public static void sound_zombie_villager(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0F, 1.0F);
    }

    public static void play(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 10.0F, 1.0F);
    }
}
