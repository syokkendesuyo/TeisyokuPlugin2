package net.jp.minecraft.plugins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class GUI_PlayersList {
    public static void getPlayersList(Player player) {

        if(! (player.hasPermission(Permissions.getPlayersPermisson()))){
            player.sendMessage(Messages.getNoPermissionMessage(Permissions.getPlayersPermisson()));
            return;
        }

        // Make sure the inv has enough slots
        int size = Bukkit.getOnlinePlayers().size();
        int size2=1;

        if(size % 9 == 0){
            //割り切れた時
            size2 = size/9;
        }
        else{
            //割り切れなかった時
            size2=(int) (size/9) +1;
        }

        if(size2>6){
            return;
        }
        Inventory inv = Bukkit.createInventory(player, size2*9 ," プレイヤー一覧 ");
        // Add all the skulls
        for (Player p : Bukkit.getOnlinePlayers()) {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
            meta.setDisplayName(p.getName().toString());
            meta.setOwner(p.getName());
            meta.setLore(Arrays.asList(new String[]{ChatColor.GRAY + "UUID:" + p.getUniqueId().toString(), ChatColor.GRAY + "クリックで現在地を表示します"}));
            skull.setItemMeta(meta);

            inv.addItem(skull);
        }
        player.openInventory(inv);
    }
}
