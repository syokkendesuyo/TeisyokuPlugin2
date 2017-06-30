package net.jp.minecraft.plugins.GUI;

import net.jp.minecraft.plugins.Listener.Listener_Gomibako;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class GUI_ClickEvent implements Listener {
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();


        //ゴミ箱
        if (event.getInventory().getName().equalsIgnoreCase(" ゴミ箱 ")) {
            if (event.getRawSlot() == 0 || event.getRawSlot() == 1) {
                event.setCancelled(true);
            }
            if (event.getRawSlot() == 1) {
                player.closeInventory();
                Msg.success(player, "ゴミを処分しました");
            }
        }


        //定食サーバメニュー
        else if (event.getInventory().getName().equalsIgnoreCase(" 定食サーバメニュー ")) {
            if (event.getRawSlot() == 0 || event.getRawSlot() == 1) {
                event.setCancelled(true);
            }
            if (event.getRawSlot() == 10) {
                Bukkit.getServer().dispatchCommand(player, "warp spawn");
                player.closeInventory();
            } else if (event.getRawSlot() == 12) {
                Bukkit.getServer().dispatchCommand(player, "warp material");
                player.closeInventory();
            } else if (event.getRawSlot() == 14) {
                Bukkit.getServer().dispatchCommand(player, "warp nether");
                player.closeInventory();
            } else if (event.getRawSlot() == 16) {
                player.kickPlayer(ChatColor.YELLOW + " サーバから切断しました。 ");
            } else if (event.getRawSlot() == 28) {
                Listener_Gomibako.openGomibako(player);
            } else if (event.getRawSlot() == 30) {
                Bukkit.getServer().dispatchCommand(player, "lock");
                player.closeInventory();
            } else if (event.getRawSlot() == 32) {
                Bukkit.getServer().dispatchCommand(player, "cremove");
                player.closeInventory();
            } else if (event.getRawSlot() == 34) {
                Bukkit.getServer().dispatchCommand(player, "cpersist");
                player.closeInventory();
            } else if (event.getRawSlot() == 2) {
                GUI_PlayersList.getPlayersList(player);
            } else if (event.getRawSlot() == 4) {
                GUI_YesNo.openGUI(player, "自殺を処理を行います", "自殺をやっぱり辞める！", "自殺を行いますか？");
            } else if (event.getRawSlot() == 6) {
                Msg.info(player, "投票サイト:" + ChatColor.AQUA + " http://bit.ly/vote_mc");
                player.closeInventory();
            }
            event.setCancelled(true);
        }


        //プレイヤー一覧
        else if (event.getInventory().getName().equalsIgnoreCase(" プレイヤー一覧 ")) {
            if (event.getRawSlot() > 0) {
                event.setCancelled(true);
            }
            if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                return;
            }
            if (event.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (event.getCurrentItem() == null) {
                return;
            }
            if (!event.getCurrentItem().hasItemMeta()) {
                //デフォルトのアイテム名が適応されている場合はキャンセル
                return;
            }
            if (!(event.getCurrentItem().getAmount() == 0)) {
                String name = event.getCurrentItem().getItemMeta().getDisplayName();
                Player p = Bukkit.getServer().getPlayer(name);
                try {
                    String world = p.getLocation().getWorld().getName();
                    int x = (int) p.getLocation().getX();
                    int y = (int) p.getLocation().getY();
                    int z = (int) p.getLocation().getZ();
                    if (player.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                        player.teleport(p.getLocation());
                        Msg.success(player, ChatColor.YELLOW + p.getName() + ChatColor.RESET + " へテレポートしました");
                    }
                    Msg.success(player, ChatColor.YELLOW + p.getName() + ChatColor.RESET + " の現在地" + ChatColor.DARK_GRAY + " ： " + ChatColor.WHITE + world + " " + x + " , " + y + " , " + z);
                } catch (NullPointerException e) {
                    Msg.info(player, "現在地を取得できませんでした。プレイヤーがオフラインの可能性があります。");
                }
                event.setCancelled(true);
            }
            event.setCancelled(true);
        }
    }
}
