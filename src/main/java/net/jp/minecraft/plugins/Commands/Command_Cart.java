package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Messages;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_Cart implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player)) {
            Msg.warning(sender, "コンソールからコマンドを送信することはできません");
            return true;
        }

        if (args.length > 2) {
            Msg.warning(sender, "引数が多すぎです");
            return true;
        }

        if (args.length == 0) {
            Msg.success(sender, "マインカートをインベントリに追加しました");
            Player player = (Player) sender;
            ItemStack cart = new ItemStack(Material.MINECART);
            player.getInventory().addItem(cart);
            return true;
        } else if (args.length == 1) {
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (!(player == null)) {
                if (!(sender.hasPermission("teisyoku.admin"))) {
                    sender.sendMessage(Messages.getNoPermissionMessage("teisyoku.admin"));
                    return true;
                } else {
                    ItemStack cart = new ItemStack(Material.MINECART);
                    player.getInventory().addItem(cart);
                    Msg.success(sender, "プレイヤー " + ChatColor.YELLOW + args[0] + ChatColor.RESET + " にマインカートを渡しました");
                    Msg.success(player, "プレイヤー " + ChatColor.YELLOW + sender.getName() + ChatColor.RESET + " からマインカートを渡されました");
                    return true;
                }
            } else {
                //プレイヤーが居ないのでエラー
                sender.sendMessage(Messages.getDenyPrefix() + "プレイヤー " + ChatColor.YELLOW + args[0] + ChatColor.RESET + " はオンラインではありません");
                return true;
            }
        } else if (args.length == 2) {
            Player player = (Player) sender;
            String playerUniqueId = player.getUniqueId().toString();
            File userdata = new File(TeisyokuPlugin2.getInstance().getDataFolder(), File.separator + "PlayerDatabase");
            File f = new File(userdata, File.separator + playerUniqueId + ".yml");
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
            if (args[0].equalsIgnoreCase("auto_remove")) {
                if (args[1].equalsIgnoreCase("true")) {
                    try {
                        playerData.set("auto_cart_remove", true);
                        Msg.success(player, "マインカートから降りた時に自動削除する：" + ChatColor.GREEN + " true");
                        playerData.save(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("false")) {
                    try {
                        playerData.set("auto_cart_remove", false);
                        Msg.success(player, "マインカートから降りた時に自動削除する：" + ChatColor.RED + " false");
                        playerData.save(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                Msg.warning(player, "引数「" + args[1] + "」は利用できません。trueかfalseを指定して下さい。");
                return true;
            } else {
                Msg.warning(player, "引数「" + args[0] + "」は利用できません。");
            }
        }
        return true;
    }
}
