package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo azuhata
 */
public class Listener_Chat implements Listener{

    static File cfile;
    static FileConfiguration config;
    static File df = TeisyokuPlugin2.getInstance().getDataFolder();

    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent event) {

        cfile = new File(df, "PlayerDatabase" + File.separator + event.getPlayer().getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        FileConfiguration playerData = config;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        try {
            String NickName = TeisyokuPlugin2.getInstance().NickConfig.getString(uuid + ".nick");
            String sender = event.getPlayer().getName().toString();

            String Name;
            if(NickName == null){
                Name = sender;
                player.setDisplayName(Name);
            }else{
                String color = playerData.getString("nick_color");
                if(color.equalsIgnoreCase("aqua")){
                    Name = ChatColor.AQUA + "" + NickName + " " + ChatColor.RESET + sender;
                    player.setDisplayName(Name);
                    return;
                }
                if(color.equalsIgnoreCase("pink")){
                    Name = ChatColor.LIGHT_PURPLE + "" + NickName + " " + ChatColor.RESET + sender;
                    player.setDisplayName(Name);
                    return;
                }
                else{
                    Name = ChatColor.GRAY + "" + NickName + " " + ChatColor.RESET + sender;
                    player.setDisplayName(Name);
                    return;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @EventHandler
    public void ChatReplace(AsyncPlayerChatEvent event) {
        String msg = event.getMessage().replaceAll("Penguin", ChatColor.AQUA + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Penguin" + ChatColor.RESET);
        msg = msg.replaceAll("penguin", ChatColor.AQUA + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Penguin" + ChatColor.RESET);
        msg = msg.replaceAll("rorikon", ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Rorikon" + ChatColor.RESET);
        msg = msg.replaceAll("Rorikon", ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Rorikon" + ChatColor.RESET);
        event.setMessage(msg);
        return;
    }

    @EventHandler
    public void ChatBP1(AsyncPlayerChatEvent event){
        String msg = event.getMessage();
        String regex = "\\d{1,20}BP";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(msg);

        if(m.find()){
            String match = m.group();
            String result = msg.replaceAll(msg,match);

            msg = msg.replaceAll(result, ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "" +  ChatColor.BOLD + result + ChatColor.RESET);
            event.setMessage(msg);
        }
    }

    @EventHandler
    public void ChatBP2(AsyncPlayerChatEvent event){
        String msg = event.getMessage();
        String regex = "[-]\\d{1,20}BP";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(msg);

        if(m.find()){
            String match = m.group();
            String result = msg.replaceAll(msg,match);

            msg = msg.replaceAll(result, ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "" +  ChatColor.BOLD + result + ChatColor.RESET);
            event.setMessage(msg);
        }
        return;
    }
}
