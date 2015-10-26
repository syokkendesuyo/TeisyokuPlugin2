package net.jp.minecraft.plugins;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Messages implements Listener {

    /**
     * Prefixを提供
     * @return string
     */
    public static String getSuccessPrefix(){
        String string = ChatColor.GREEN + "[Teisyoku] "+ ChatColor.DARK_GRAY +">" + ChatColor.GRAY + "> "+ ChatColor.WHITE;
        return string;
    }

    public static String getDenyPrefix(){
        String string = ChatColor.RED + "[Teisyoku] "+ ChatColor.DARK_GRAY +">" + ChatColor.GRAY + "> "+ ChatColor.WHITE;
        return string;
    }

    public static String getNormalPrefix(){
        String string = ChatColor.AQUA + "[Teisyoku] "+ ChatColor.DARK_GRAY +">" + ChatColor.GRAY + "> "+ ChatColor.WHITE;
        return string;
    }

    public static String getYellowPrefix(){
        String string = ChatColor.YELLOW + "[Teisyoku] "+ ChatColor.DARK_GRAY +">" + ChatColor.GRAY + "> "+ ChatColor.WHITE;
        return string;
    }

    public static String getAdPrefix(String name){
        String string = ChatColor.GOLD + "[お知らせ] "+ChatColor.YELLOW + name + " " + ChatColor.DARK_GRAY +">" + ChatColor.GRAY + "> "+ ChatColor.WHITE;
        return string;
    }

    public static String getCommandFormat(String command , String discription){
        String string = ChatColor.GRAY + "   > "+ ChatColor.YELLOW + "/" + command + ChatColor.DARK_GRAY  + "  : "+ ChatColor.RESET + discription;
        return string;
    }

    public static String getReset(){
        String string = "" + ChatColor.RESET;
        return string;
    }

    public static String getArray(){
        String string = ChatColor.GRAY + "   > " + ChatColor.RESET;
        return string;
    }


    /**
     * ヘルプメッセーいを提供
     * @param sender
     */
    public static void HelpMessage(CommandSender sender){
        if(!(sender.hasPermission(Permissions.getHelpPermisson()))){
            sender.sendMessage(Messages.getNoPermissionMesssage(Permissions.getHelpPermisson()));
            return;
        }

        sender.sendMessage(getSuccessPrefix() + "ヘルプ");
        sender.sendMessage(getCommandFormat("help", "当サーバのヘルプをご覧頂けます"));
        sender.sendMessage(getCommandFormat("teisyoku", "当サーバ専用のメニューを表示します"));
    }


    /**
     * パーミッション関連
     */
    //パーミッションを灰色で表示する
    public static String getPermissionNode(String permission){
        String string = ChatColor.GRAY + " (" + permission + ")" + ChatColor.RESET;
        return string;
    }

    //パーミッションが無い時のメッセージ
    public static String getNoPermissionMesssage(String permission){
        String permissionFormat = getPermissionNode(permission);
        String string = getDenyPrefix() + "パーミッションがありません" + permissionFormat;
        return string;
    }

    //パーミッションの確認コマンド
    public static String getCheckPermissionMessage(String permission){
        String permissionFormat = permission;
        String string = getNormalPrefix() + "パーミッション：" + permissionFormat;
        return string;
    }

}
