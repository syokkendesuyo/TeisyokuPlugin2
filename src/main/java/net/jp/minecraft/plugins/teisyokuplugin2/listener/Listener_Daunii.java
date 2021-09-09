package net.jp.minecraft.plugins.teisyokuplugin2.listener;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.util.BlockUtil;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Item;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import net.jp.minecraft.plugins.teisyokuplugin2.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Listener_Daunii implements Listener {

    public static String DauniiName = ChatColor.AQUA + "" + ChatColor.BOLD + "だうにーくん";
    private static Random rand = new Random();
    private int price = 10000;
    private String GUIName = ChatColor.RED + "" + ChatColor.BOLD + "だうにーくん";
    private final String AttrName = "teisyoku_daunii";

    /**
     * 装備に特殊能力を付与して返すメソッド
     * ダイヤ装備でない場合そのまま返される
     *
     * @param item ダイヤ装備のみ
     * @return 強化されたアイテム
     */
    private ItemStack setAttribute(ItemStack item) {
        // スロット判定
        EquipmentSlot slot;
        Material type = item.getType();
        if (type.equals(Material.DIAMOND_HELMET)) {
            slot = EquipmentSlot.HEAD;
        } else if (type.equals(Material.DIAMOND_CHESTPLATE)) {
            slot = EquipmentSlot.CHEST;
        } else if (type.equals(Material.DIAMOND_LEGGINGS)) {
            slot = EquipmentSlot.LEGS;
        } else if (type.equals(Material.DIAMOND_BOOTS)) {
            slot = EquipmentSlot.FEET;
        } else {
            return item;
        }
        // 初期化
        item = dauniiReset(item);
        // アイテムのグレード判定
        double multiplier;
        if (type.equals(Material.DIAMOND_HELMET) || type.equals(Material.DIAMOND_CHESTPLATE) ||
            type.equals(Material.DIAMOND_LEGGINGS) || type.equals(Material.DIAMOND_BOOTS)) {
            multiplier = 1;
        } else {
            return item;
        }
        // 能力をItemMetaに追加
        int count = 3;
        ItemMeta meta = item.getItemMeta();
        for (int c = 0; c < count; c++) {
            Ability ability = createAbility(slot, multiplier);
            meta.addAttributeModifier(ability.Attribute, ability.Modifier);
        }
        // 初期能力を足しなおす
        if (type.equals(Material.DIAMOND_HELMET)) {
            addArmorAttribute(meta, slot, 3, 2);
        } else if (type.equals(Material.DIAMOND_CHESTPLATE)) {
            addArmorAttribute(meta, slot, 8, 2);
        } else if (type.equals(Material.DIAMOND_LEGGINGS)) {
            addArmorAttribute(meta, slot, 6, 2);
        } else if (type.equals(Material.DIAMOND_BOOTS)) {
            addArmorAttribute(meta, slot, 3, 2);
        }
        // 能力がついたItemMetaを戻す
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Nameにthis.AttrNameがついているAttributeModifierをすべて削除する
     *
     * @param item リセットしたいアイテム
     * @return リセットされたアイテム
     */
    private ItemStack dauniiReset(ItemStack item) {
        // ItemMetaを取得
        // エラー防止
        if (!item.hasItemMeta())
            return item;
        ItemMeta meta = item.getItemMeta();
        // AttributeModifierを調べてthis.AttrNameがついてたら削除する
        // エラー防止
        if (!meta.hasAttributeModifiers())
            return item;
        for (Map.Entry<Attribute, AttributeModifier> entry : meta.getAttributeModifiers().entries()) {
            if (Objects.isNull(entry))
                continue;
            if (entry.getValue().getName().equals(AttrName))
                meta.removeAttributeModifier(entry.getKey(), entry.getValue());
        }
        // itemにItemMetaを戻してitemを返す
        item.setItemMeta(meta);
        return item;
    }

    /**
     * ダイヤモンド相当以上の装備かを判定するメソッド
     *
     * @param material 検査するmaterial
     * @return ダイヤモンド相当以上の装備かどうか
     */
    private boolean isHighLevelArmor(Material material) {
        // ハイレベルな装備
        Material[] targets = {
                Material.DIAMOND_HELMET,
                Material.DIAMOND_CHESTPLATE,
                Material.DIAMOND_LEGGINGS,
                Material.DIAMOND_BOOTS
        };

        return BlockUtil.scanMaterial(material, targets);
    }

    /**
     * GENERIC_ARMOR、GENERIC_ARMOR_TOUGHNESSのAttributeを追加する(主に復元に使用)
     *
     * @param meta      対象のItemMeta
     * @param slot      装備箇所
     * @param armor     GENERIC_ARMOR(GUI上で「防具」と表現される値)
     * @param toughness GENERIC_ARMOR_TOUGHNESS(GUI上で「防具強度」と表現される値)
     * @return Attributeが追加されたItemMeta
     */
    private ItemMeta addArmorAttribute(ItemMeta meta, EquipmentSlot slot, double armor, double toughness) {
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                createModifier(armor, AttributeModifier.Operation.ADD_NUMBER, slot));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
                createModifier(toughness, AttributeModifier.Operation.ADD_NUMBER, slot));
        return meta;
    }

    /**
     * Attributeをランダムに生成するメソッド
     *
     * @param slot       Attributeを適用する装備スロット
     * @param multiplier どれだけ増減させるか(場合により小数点以下切り上げ、1以上)
     * @return AttributeModifierの配列
     */
    private Ability createAbility(EquipmentSlot slot, double multiplier) {
        // エラー防止
        assert multiplier >= 1;

        // 追加される能力
        Attribute[] Attributes = {
                Attribute.GENERIC_ATTACK_DAMAGE,
                Attribute.GENERIC_KNOCKBACK_RESISTANCE,
                Attribute.GENERIC_MAX_HEALTH,
                Attribute.GENERIC_MOVEMENT_SPEED
        };

        // Attributeを作る
        // 能力の種類を決定する
        Attribute attr = Attributes[rand.nextInt(Attributes.length)];
        // 能力値を引き出す
        double amount = 0;
        AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_NUMBER;
        if (attr.equals(Attribute.GENERIC_ATTACK_DAMAGE)) {
            amount = Math.ceil(1 * multiplier);
        } else if (attr.equals(Attribute.GENERIC_KNOCKBACK_RESISTANCE)) {
            amount = 0.1 * multiplier;
        } else if (attr.equals(Attribute.GENERIC_MAX_HEALTH)) {
            amount = Math.ceil(2 * multiplier);
        } else if (attr.equals(Attribute.GENERIC_MOVEMENT_SPEED)) {
            amount = 0.1 * multiplier;
            operation = AttributeModifier.Operation.ADD_SCALAR;
        }
        // 能力を作る
        return new Ability(attr, createModifier(amount, operation, slot));
    }

    /**
     * AttributeModifierを作るのをほんの少し楽にする
     *
     * @param amount    amount
     * @param operation operation
     * @param slot      slot
     * @return 生成されたAttributeModifier
     */
    private AttributeModifier createModifier(double amount, AttributeModifier.Operation operation, EquipmentSlot slot) {
        return new AttributeModifier(UUID.randomUUID(), AttrName, amount, operation, slot);
    }

    /**
     * AttributeとAttributeModifierの組み合わせ。
     */
    private class Ability {
        Ability(Attribute attr, AttributeModifier modifier) {
            Attribute = attr;
            Modifier = modifier;
        }

        Attribute Attribute;
        AttributeModifier Modifier;
    }

    private Inventory setItem(int point) {//インベントリを作成
        Inventory GUI = Bukkit.createInventory(null, 9, GUIName);

        String[] lore_otheritem = {ChatColor.GRAY + "中央にアイテムを置いてね!!"};
        ItemStack otheritem = Item.customItem(ChatColor.GRAY + "外枠", 1, Material.LIGHT_GRAY_STAINED_GLASS_PANE, lore_otheritem);

        String[] lore_status = {ChatColor.GREEN + "" + ChatColor.BOLD + price + " TPoint" + ChatColor.RESET + "" + ChatColor.BLUE + "でダイヤ防具を強化できるよ!!"};
        ItemStack item_status = Item.customItem(ChatColor.AQUA + "" + ChatColor.BOLD + point + " TPoint", 1, Material.COD, lore_status);

        String[] lore_closeitem = {ChatColor.GRAY + "クリックすると閉じるよ!!"};
        ItemStack closeitem = Item.customItem(ChatColor.AQUA + "インベントリを閉じる", 1, Material.BARRIER, lore_closeitem);

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
    public void openDaunii(PlayerInteractAtEntityEvent event) {//だうにーくんをクリックしたかどうか
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
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.daunii")) {
            Msg.warning(player, "だうにーくんは有効化されていません");
            return;
        }

        //実行コマンドのパーミッションを確認
        if (!(player.hasPermission(Permission.DAUNII_USE.toString()) || player.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(player, Permission.DAUNII_USE);
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.IRON_INGOT)) {
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
    public void clickGUI(InventoryClickEvent event) {//だうにーくんのGUIをクリックしたかどうか
        if (event.getInventory().getSize() != 9) {
            return;
        }

        if (!(event.getView().getTitle().equals(GUIName))) {
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
    public void closeGUI(InventoryCloseEvent event) {//だうにーくんのGUIを閉じた時、attrを付与する 但し、ダイヤ防具のみ
        Inventory inv = event.getInventory();
        if (inv.getSize() != 9) {
            return;
        }
        if (!(event.getView().getTitle().equals(GUIName))) {
            return;
        }
        Player player = (Player) event.getPlayer();
        ItemStack item = inv.getItem(4);
        if (item == null) {
            return;
        }
        if (!isHighLevelArmor(item.getType())) {
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
            player.getInventory().addItem(setAttribute(item));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
            return;
        }
        Msg.warning(player, ChatColor.DARK_GRAY + "不明なエラーが発生したため、防具強化をキャンセルします");
        player.getInventory().addItem(item);
    }

    @EventHandler
    public void removeDaunii(PlayerInteractAtEntityEvent event) {//もし鉄インゴットで右クリックしたら
        Entity entity = event.getRightClicked();
        if (!(event.getRightClicked().getType().equals(EntityType.IRON_GOLEM)) || !(Objects.equals(entity.getCustomName(), DauniiName)) || entity.getCustomName() == null) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.getType().equals(Material.IRON_INGOT)) {
            return;
        }

        //実行コマンドのパーミッションを確認
        if (!(player.hasPermission(Permission.DAUNII_SUMMON.toString()) || player.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(player, Permission.DAUNII_SUMMON);
            return;
        }

        PlayerUtil.playSound(player, Sound.ENTITY_ENDER_DRAGON_DEATH);
        Entity setDaunii = entity.getVehicle();
        if (setDaunii == null) {
            entity.remove();
            return;
        }

        setDaunii.remove();
        entity.remove();
    }

    @EventHandler
    public void dauniiDamageEvent(EntityDamageEvent event) {//だうにーくんを倒すことはできない --> 下のアーマースタンドが残ってしまうのを防ぐため
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
