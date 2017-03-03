package net.jp.minecraft.plugins.Utility;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Sounds implements Listener {
    private static String ver1_8_8_R01 = "1.8.8-R0.1-SNAPSHOT";
    private static String ver1_11_2_R01 = "1.11.2-R0.1-SNAPSHOT";

    public static void sound_note(Player player) {
        String version = Bukkit.getBukkitVersion();
        if (version.equals(ver1_8_8_R01)) {
            return;
        } else if (version.equals(ver1_11_2_R01)) {
            player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_NOTE_PLING, 3.0F, 1.5F);
        }
    }

    public static void sound_arrow(Player player) {
        String version = Bukkit.getBukkitVersion();
        if (version.equals(ver1_8_8_R01)) {
            return;
        } else if (version.equals(ver1_11_2_R01)) {
            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 10.0F, 1.0F);
        }
    }

    public static void sound_villager(Player player) {
        String version = Bukkit.getBukkitVersion();
        if (version.equals(ver1_8_8_R01)) {
            return;
        } else if (version.equals(ver1_11_2_R01)) {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 10.0F, 1.0F);
        }
    }

    public static void sound_zombie_villager(Player player) {
        String version = Bukkit.getBukkitVersion();
        if (version.equals(ver1_8_8_R01)) {
            return;
        } else if (version.equals(ver1_11_2_R01)) {
            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0F, 1.0F);
        }
    }
}
