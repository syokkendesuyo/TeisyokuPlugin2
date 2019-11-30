package net.jp.minecraft.plugins.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class TeisyokuMenuIndex {
    public static void getMenu(Player player) {
        ItemStack item0 = new ItemStack(Material.BOOK);
        ItemMeta itemmeta0 = item0.getItemMeta();
        itemmeta0.setDisplayName(ChatColor.GOLD + "使い方");
        itemmeta0.setLore(Arrays.asList(ChatColor.WHITE + "アイテムをクリックすると", ChatColor.WHITE + "何かアクションが発動します。"));
        item0.setItemMeta(itemmeta0);

        ItemStack item1 = new ItemStack(Material.COD);
        ItemMeta itemmeta1 = item1.getItemMeta();
        itemmeta1.setDisplayName(ChatColor.GOLD + "初期スポーン地点へワープ");
        itemmeta1.setLore(Arrays.asList(ChatColor.GRAY + "/warp spawn コマンドと同様"));
        item1.setItemMeta(itemmeta1);

        ItemStack item2 = new ItemStack(Material.GRASS);
        ItemMeta itemmeta2 = item2.getItemMeta();
        itemmeta2.setDisplayName(ChatColor.GOLD + "通常の素材世界へワープ");
        itemmeta2.setLore(Arrays.asList(ChatColor.GRAY + "/warp material コマンドと同様"));
        item2.setItemMeta(itemmeta2);

        ItemStack item3 = new ItemStack(Material.NETHERRACK);
        ItemMeta itemmeta3 = item3.getItemMeta();
        itemmeta3.setDisplayName(ChatColor.GOLD + "ネザーの素材世界へワープ");
        itemmeta3.setLore(Arrays.asList(ChatColor.GRAY + "/warp nether コマンドと同様"));
        item3.setItemMeta(itemmeta3);

        ItemStack item4 = new ItemStack(Material.RED_BED);
        ItemMeta itemmeta4 = item4.getItemMeta();
        itemmeta4.setDisplayName(ChatColor.GOLD + "サーバから切断");
        itemmeta4.setLore(Arrays.asList(ChatColor.GRAY + "当サーバから切断"));
        item4.setItemMeta(itemmeta4);

        ItemStack item5 = new ItemStack(Material.ROTTEN_FLESH);
        ItemMeta itemmeta5 = item5.getItemMeta();
        itemmeta5.setDisplayName(ChatColor.GOLD + "ゴミ箱");
        itemmeta5.setLore(Arrays.asList(ChatColor.GRAY + "不要なアイテムを入れると削除されます"));
        item5.setItemMeta(itemmeta5);

        ItemStack item6 = new ItemStack(Material.CHEST);
        ItemMeta itemmeta6 = item6.getItemMeta();
        itemmeta6.setDisplayName(ChatColor.GOLD + "保護する");
        itemmeta6.setLore(Arrays.asList(ChatColor.GRAY + "チェストなどを保護することが可能です"));
        item6.setItemMeta(itemmeta6);

        ItemStack item7 = new ItemStack(Material.BUCKET);
        ItemMeta itemmeta7 = item7.getItemMeta();
        itemmeta7.setDisplayName(ChatColor.GOLD + "保護を削除する");
        itemmeta7.setLore(Arrays.asList(ChatColor.GRAY + "あなた名義の保護を削除することが可能です"));
        item7.setItemMeta(itemmeta7);

        ItemStack item8 = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta itemmeta8 = item8.getItemMeta();
        itemmeta8.setDisplayName(ChatColor.GOLD + "保護コマンドを継続");
        itemmeta8.setLore(Arrays.asList(ChatColor.GRAY + "保護を連続して行う場合に利用", ChatColor.GRAY + "保護コマンドを実行後利用し、", ChatColor.GRAY + "利用を終了する場合もクリックが必要です"));
        item8.setItemMeta(itemmeta8);

        ItemStack item9 = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        ItemMeta itemmeta9 = item9.getItemMeta();
        itemmeta9.setDisplayName(ChatColor.GOLD + "プレイヤー一覧を表示");
        itemmeta9.setLore(Arrays.asList(ChatColor.GRAY + "オンラインプレイヤーの一覧を表示し、", ChatColor.GRAY + "頭をクリックすることで現在地を取得します。"));
        item9.setItemMeta(itemmeta9);

        ItemStack item10 = new ItemStack(Material.STONE_SWORD);
        ItemMeta itemmeta10 = item10.getItemMeta();
        itemmeta10.setDisplayName(ChatColor.RED + "自殺");
        itemmeta10.setLore(Arrays.asList(ChatColor.GRAY + "食料がない！自殺しよう...。", ChatColor.GRAY + "アイテムを全てドロップするので気をつけてね！"));
        item10.setItemMeta(itemmeta10);

        ItemStack item11 = new ItemStack(Material.PAPER);
        ItemMeta itemmeta11 = item11.getItemMeta();
        itemmeta11.setDisplayName(ChatColor.GOLD + "投票");
        itemmeta11.setLore(Arrays.asList(ChatColor.GRAY + "投票サイトのURLを表示します。", ChatColor.GRAY + "投票には登録が必要です。"));
        item11.setItemMeta(itemmeta11);

        Inventory inv = Bukkit.createInventory(player, 45, " 定食サーバメニュー ");
        inv.setItem(0, item0);
        inv.setItem(10, item1);
        inv.setItem(12, item2);
        inv.setItem(14, item3);
        inv.setItem(16, item4);
        inv.setItem(28, item5);
        inv.setItem(30, item6);
        inv.setItem(32, item7);
        inv.setItem(34, item8);
        inv.setItem(2, item9);
        inv.setItem(4, item10);
        inv.setItem(6, item11);
        player.openInventory(inv);
    }
}
