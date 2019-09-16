package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Permission;
import net.jp.minecraft.plugins.Utility.Sounds;
import net.jp.minecraft.plugins.Utility.Item;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Listener_Daunii_1_12 implements Listener {

    private int price = 10000;

    public static String DauniiName = ChatColor.AQUA + "" + ChatColor.BOLD + "だうにーくん";
    private String GUIName = ChatColor.RED + "" + ChatColor.BOLD + "だうにーくん";
    private String head = "head";
    private String body = "chest";
    private String legs = "legs";
    private String feet = "feet";
    private int head_id = 2001;
    private int body_id = 2002;
    private int legs_id = 2003;
    private int feet_id = 2004;
    private static Random rand = new Random();

    private ItemStack setAttr(ItemStack item) {//Attributeの設定
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = new NBTTagList();
        int count = 0;
        int per = 0;
        int health = 0;
        int damage = 0;
        int knock = 0;
        int speed = 0;
        int id = 0;
        String region;
        if (item.getType().equals(Material.DIAMOND_HELMET)) {//ダイヤヘルメットの時
            id = head_id;
            region = head;
            while (count < 3) {
                per = rand.nextInt(3);
                if (per == 0) {
                    health++;
                } else if (per == 1) {
                    damage++;
                } else if (per == 2) {
                    speed++;
                }
                count++;
            }
        } else if (item.getType().equals(Material.DIAMOND_CHESTPLATE)) {//ダイヤチェストの時
            id = body_id;
            region = body;
            while (count < 3) {
                per = rand.nextInt(3);
                if (per == 0) {
                    health++;
                } else if (per == 1) {
                    damage++;
                } else if (per == 2) {
                    knock++;
                }
                count++;
            }
        } else if (item.getType().equals(Material.DIAMOND_LEGGINGS)) {//ダイヤレギンスの時
            id = legs_id;
            region = legs;
            while (count < 3) {
                per = rand.nextInt(3);
                if (per == 0) {
                    damage++;
                } else if (per == 1) {
                    knock++;
                } else if (per == 2) {
                    speed++;
                }
                count++;
            }
        } else if (item.getType().equals(Material.DIAMOND_BOOTS)) {//ダイヤブーツの時
            id = feet_id;
            region = feet;
            while (count < 3) {
                per = rand.nextInt(3);
                if (per == 0) {
                    health++;
                } else if (per == 1) {
                    knock++;
                } else if (per == 2) {
                    speed++;
                }
                count++;
            }
        } else {
            return item;
        }
        count = 0;
        while (count < 3) {//attrを付与
            if (health > 0) {
                modifiers.add(MaxHealth(region, id, count + 810));
                health--;
            }
            if (damage > 0) {
                modifiers.add(AttackDamage(region, id, count + 810));
                damage--;
            }
            if (knock > 0) {
                modifiers.add(knockbackResistance(region, id, count + 810));
                knock--;
            }
            if (speed > 0) {
                modifiers.add(movementSpeed(region, id, count + 810));
                speed--;
            }
            count++;
        }
        if (id == 2001) {
            modifiers.add(armor(region, 3, id, 105));
        } else if (id == 2002) {
            modifiers.add(armor(region, 8, id, 105));
        } else if (id == 2003) {
            modifiers.add(armor(region, 6, id, 105));
        } else if (id == 2004) {
            modifiers.add(armor(region, 3, id, 105));
        }
        modifiers.add(armorToughness(region, 2, id, 106));
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);
        return item;//ランダムにattrの付いたアイテムを返す
    }

    private NBTTagCompound MaxHealth(String region, int slot, int count) {//MaxHealthを返す
        NBTTagCompound attr = new NBTTagCompound();
        attr.set("AttributeName", new NBTTagString("generic.maxHealth"));
        attr.set("Name", new NBTTagString("generic.maxHealth"));
        attr.set("Amount", new NBTTagInt(2));
        attr.set("Operation", new NBTTagInt(0));
        attr.set("UUIDLeast", new NBTTagInt(slot));
        attr.set("UUIDMost", new NBTTagInt(count));
        attr.set("Slot", new NBTTagString(region));
        return attr;
    }

    private NBTTagCompound AttackDamage(String region, int slot, int count) {//AttackDamageを返す
        NBTTagCompound attr = new NBTTagCompound();
        attr.set("AttributeName", new NBTTagString("generic.attackDamage"));
        attr.set("Name", new NBTTagString("generic.attackDamage"));
        attr.set("Amount", new NBTTagInt(1));
        attr.set("Operation", new NBTTagInt(0));
        attr.set("UUIDLeast", new NBTTagInt(slot));
        attr.set("UUIDMost", new NBTTagInt(count));
        attr.set("Slot", new NBTTagString(region));
        return attr;
    }

    private NBTTagCompound knockbackResistance(String region, int slot, int count) {//KnockBackResistanceを返す
        NBTTagCompound attr = new NBTTagCompound();
        attr.set("AttributeName", new NBTTagString("generic.knockbackResistance"));
        attr.set("Name", new NBTTagString("generic.knockbackResistance"));
        attr.set("Amount", new NBTTagDouble(0.1));
        attr.set("Operation", new NBTTagInt(0));
        attr.set("UUIDLeast", new NBTTagInt(slot));
        attr.set("UUIDMost", new NBTTagInt(count));
        attr.set("Slot", new NBTTagString(region));
        return attr;
    }

    private NBTTagCompound movementSpeed(String region, int slot, int count) {//MovementSpeedを返す
        NBTTagCompound attr = new NBTTagCompound();
        attr.set("AttributeName", new NBTTagString("generic.movementSpeed"));
        attr.set("Name", new NBTTagString("generic.movementSpeed"));
        attr.set("Amount", new NBTTagDouble(0.1));
        attr.set("Operation", new NBTTagInt(1));
        attr.set("UUIDLeast", new NBTTagInt(slot));
        attr.set("UUIDMost", new NBTTagInt(count));
        attr.set("Slot", new NBTTagString(region));
        return attr;
    }

    private NBTTagCompound armor(String region, int amount, int slot, int count) {//Armorを返す
        NBTTagCompound attr = new NBTTagCompound();
        attr.set("AttributeName", new NBTTagString("generic.armor"));
        attr.set("Name", new NBTTagString("generic.armor"));
        attr.set("Amount", new NBTTagInt(amount));
        attr.set("Operation", new NBTTagInt(0));
        attr.set("UUIDLeast", new NBTTagInt(slot));
        attr.set("UUIDMost", new NBTTagInt(count));
        attr.set("Slot", new NBTTagString(region));
        return attr;
    }

    private NBTTagCompound armorToughness(String region, int amount, int slot, int count) {//ArmorToughnessを返す
        NBTTagCompound attr = new NBTTagCompound();
        attr.set("AttributeName", new NBTTagString("generic.armorToughness"));
        attr.set("Name", new NBTTagString("generic.armorToughness"));
        attr.set("Amount", new NBTTagDouble(amount));
        attr.set("Operation", new NBTTagInt(0));
        attr.set("UUIDLeast", new NBTTagInt(slot));
        attr.set("UUIDMost", new NBTTagInt(count));
        attr.set("Slot", new NBTTagString(region));
        return attr;
    }

    private Inventory setItem(int point) {//インベントリを作成
        Inventory GUI = Bukkit.createInventory(null, 9, GUIName);

        String lore_otheritem[] = {ChatColor.GRAY + "真ん中にアイテムを置いてね!!"};
        ItemStack otheritem = Item.customItem(ChatColor.GRAY + "外枠", 1, Material.STAINED_GLASS_PANE, (short) 8, lore_otheritem);

        String lore_status[] = {ChatColor.GREEN + "" + ChatColor.BOLD + price + " TPoint" + ChatColor.RESET + "" + ChatColor.BLUE + "でダイヤ防具を強化できるよ!!"};
        ItemStack item_status = Item.customItem(ChatColor.AQUA + "" + ChatColor.BOLD + point + " TPoint", 1, Material.COOKED_FISH, (short) 0, lore_status);

        String lore_closeitem[] = {ChatColor.GRAY + "クリックすると閉じるよ!!"};
        ItemStack closeitem = Item.customItem(ChatColor.AQUA + "インベントリを閉じる", 1, Material.BARRIER, (short) 0, lore_closeitem);

        GUI.setItem(0, item_status);
        GUI.setItem(1, otheritem);
        GUI.setItem(2, otheritem);
        GUI.setItem(3, otheritem);
        GUI.setItem(5, otheritem);
        GUI.setItem(6, otheritem);
        GUI.setItem(7, otheritem);
        GUI.setItem(8, closeitem);
        return GUI;
    }

    @EventHandler
    public void opendaunii(PlayerInteractAtEntityEvent event) {//だうにーくんをクリックしたかどうか
        if (!(event.getRightClicked().getType().equals(EntityType.IRON_GOLEM))) {
            return;
        }
        Entity entity = event.getRightClicked();
        if (entity.getCustomName() == null) {
            return;
        }
        if (!(entity.getCustomName().equals(DauniiName))) {
            return;
        }

        Player player = event.getPlayer();

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.TeisyokuConfig.getBoolean("function.daunii")) {
            Msg.warning(player, "だうにー君は有効化されていません");
            return;
        }

        //実行コマンドのパーミッションを確認
        if (!(player.hasPermission(Permission.DAUNII_USE.toString()) || player.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(player, Permission.DAUNII_USE);
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if ((item != null) && (item.getType().equals(Material.IRON_INGOT))) {
            return;
        }
        player.openInventory(setItem(Listener_TPoint.int_status(player)));
    }

    @EventHandler
    public void changeName(PlayerInteractEntityEvent event) {//名前変更回避
        if (!(event.getRightClicked().getType().equals(EntityType.IRON_GOLEM))) {
            return;
        }
        Entity entity = event.getRightClicked();
        if (entity.getCustomName() == null) {
            return;
        }
        if (!(entity.getCustomName().equals(DauniiName))) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.NAME_TAG)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void ClickGUI(InventoryClickEvent event) {//だうにーくんのGUIをクリックしたかどうか
        if (!(event.getInventory().getSize() == 9)) {
            return;
        }

        if (!(event.getInventory().getName().equals(GUIName))) {
            return;
        }

        if ((event.getRawSlot() == 0) || (event.getRawSlot() == 1) || (event.getRawSlot() == 2) || (event.getRawSlot() == 3) || (event.getRawSlot() == 5) || (event.getRawSlot() == 6) || (event.getRawSlot() == 7)) {
            event.setCancelled(true);
            return;
        }
        if (event.getRawSlot() == 8) {
            event.getWhoClicked().closeInventory();
        }
    }

    @EventHandler
    public void CloseGUI(InventoryCloseEvent event) {//だうにーくんのGUIを閉じた時、attrを付与する 但し、ダイヤ防具のみ
        Inventory inv = event.getInventory();
        if (!(inv.getSize() == 9)) {
            return;
        }
        if (!(event.getInventory().getName().equals(GUIName))) {
            return;
        }
        Player player = (Player) event.getPlayer();
        ItemStack item = inv.getItem(4);
        if (item == null) {
            return;
        }
        if (!(((item.getType().equals(Material.DIAMOND_HELMET)) || (item.getType().equals(Material.DIAMOND_CHESTPLATE)) || (item.getType().equals(Material.DIAMOND_LEGGINGS)) || (item.getType().equals(Material.DIAMOND_BOOTS))))) {
            Msg.warning(player, ChatColor.RED + "ダイヤ防具以外は強化できません!!");
            player.getInventory().addItem(item);
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1, 1);
            return;
        }
        if (!(Listener_TPoint.canBuy(price, player))) {
            Msg.warning(player, ChatColor.RED + "" + price + "TPoint 必要です!!");
            player.getInventory().addItem(item);
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1, 1);
            return;
        }
        if (Listener_TPoint.subtractPoint(price, player, Bukkit.getConsoleSender())) {
            Msg.success(player, ChatColor.GOLD + "防具を強化しました!!");
            player.getInventory().addItem(setAttr(item));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
            return;
        }
        Msg.warning(player, ChatColor.DARK_GRAY + "不明なエラーが発生したため、防具強化をキャンセルします");
        player.getInventory().addItem(item);
    }

    @EventHandler
    public void removedaunii(PlayerInteractAtEntityEvent event) {//もし鉄インゴットで右クリックしたら
        Entity entity = event.getRightClicked();
        if (!(event.getRightClicked().getType().equals(EntityType.IRON_GOLEM)) || !(entity.getCustomName().equals(DauniiName)) || entity.getCustomName() == null) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if ((item == null) || (!(item.getType().equals(Material.IRON_INGOT)))) {
            return;
        }

        //実行コマンドのパーミッションを確認
        if (!(player.hasPermission(Permission.DAUNII_SUMMON.toString()) || player.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(player, Permission.DAUNII_SUMMON);
            return;
        }

        Sounds.play(player, Sound.ENTITY_ENDERDRAGON_DEATH);
        Entity setDaunii = entity.getVehicle();
        if (setDaunii == null) {
            entity.remove();
            return;
        }

        setDaunii.remove();
        entity.remove();
    }

    @EventHandler
    public void DauniiDamage(EntityDamageEvent event) {//だうにーくんを倒すことはできない --> 下のアーマースタンドが残ってしまうのを防ぐため
        if (!(event.getEntityType().equals(EntityType.IRON_GOLEM))) {
            return;
        }
        if (event.getEntity().getCustomName() == null) {
            return;
        }
        if (!(event.getEntity().getCustomName().equals(DauniiName))) {
            return;
        }
        event.setCancelled(true);
    }

}
