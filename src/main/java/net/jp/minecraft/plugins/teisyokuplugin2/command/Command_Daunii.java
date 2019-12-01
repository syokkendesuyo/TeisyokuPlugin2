package net.jp.minecraft.plugins.teisyokuplugin2.command;

import net.jp.minecraft.plugins.teisyokuplugin2.TeisyokuPlugin2;
import net.jp.minecraft.plugins.teisyokuplugin2.api.API;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_Daunii_1_13;
import net.jp.minecraft.plugins.teisyokuplugin2.module.Permission;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class Command_Daunii implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.configTeisyoku.getConfig().getBoolean("functions.daunii")) {
            Msg.commandNotEnabled(sender, commandLabel);
            return true;
        }

        if (!(sender instanceof Player)) {
            help(sender, commandLabel);
            return true;
        }

        if (args.length == 0) {
            summon((Player) sender);
            return true;
        }

        //ヘルプ
        if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
            help(sender, commandLabel);
            return true;
        }

        //パーミッションの確認コマンドを追加
        if (args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("perms") || args[0].equalsIgnoreCase("permission")) {
            Msg.checkPermission(sender,
                    Permission.USER,
                    Permission.DAUNII_USE,
                    Permission.DAUNII_SUMMON,
                    Permission.ADMIN
            );
            return true;
        }
        help(sender, commandLabel);
        return true;
    }

    /**
     * だうにーくんを召喚します
     *
     * @param player プレイヤー
     * @return 結果
     */
    private boolean summon(Player player) {
        //実行コマンドのパーミッションを確認
        if (!API.hasPermission(player, Permission.DAUNII_SUMMON, Permission.ADMIN)) {
            Msg.noPermissionMessage(player, Permission.DAUNII_SUMMON);
            return true;
        }

        IronGolem Daunii = (IronGolem) player.getWorld().spawnEntity(player.getLocation(), EntityType.IRON_GOLEM);
        Daunii.setCustomName(Listener_Daunii_1_13.DauniiName);
        Daunii.setCustomNameVisible(true);
        Daunii.setRemoveWhenFarAway(false);

        Location loc = player.getLocation();

        ArmorStand setDaunii = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        setDaunii.setMarker(true);
        setDaunii.setGravity(false);
        setDaunii.setSmall(true);
        setDaunii.setVisible(false);
        setDaunii.addPassenger(Daunii);

        Msg.success(player, "だうにーくんを召喚しました");
        return true;
    }

    /**
     * dauniiコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        String description = "だうにーくんを召喚する";
        if (!(sender instanceof Player)) {
            description = "ヘルプを表示 (ゲーム内から使用した場合、だうにーくんを召喚)";
        }
        Msg.success(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + "", description);
        Msg.commandFormat(sender, commandLabel + " <help|?>", "ヘルプを表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}