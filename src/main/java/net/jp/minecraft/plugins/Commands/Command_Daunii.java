package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Listener.Listener_Daunii_1_9_R1;
import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;

public class Command_Daunii implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {

        if (!(sender instanceof Player)) {
            Msg.warning(sender, "コンソールからコマンドを送信することはできません");
            return false;
        }

        if (!(sender.hasPermission("teisyoku.admin"))) {
            sender.sendMessage(Messages.getNoPermissionMessage("teisyoku.admin"));
            return false;
        }

        if (!(args.length == 0)) {
            Msg.warning(sender, "引数が多すぎです");
            return false;
        }
        Player player = (Player) sender;
        IronGolem Daunii = (IronGolem) player.getWorld().spawnEntity(player.getLocation(), EntityType.IRON_GOLEM);
        Daunii.setCustomName(Listener_Daunii_1_9_R1.DauniiName);
        Daunii.setCustomNameVisible(true);
        Daunii.setRemoveWhenFarAway(false);

        Location loc = player.getLocation();

        ArmorStand setDaunii = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        setDaunii.setMarker(true);
        setDaunii.setGravity(false);
        setDaunii.setSmall(true);
        setDaunii.setVisible(false);
        setDaunii.setPassenger(Daunii);

        Msg.success(player, "だうにーくんを召喚しました");
        return true;

    }

}