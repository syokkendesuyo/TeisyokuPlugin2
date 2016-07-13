package net.jp.minecraft.plugins.API;

import eu.manuelgu.discordmc.MessageAPI;
import org.bukkit.Bukkit;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class API_Discord {

    public static void sendToDiscord(String string){

        if(Bukkit.getServer().getPluginManager().isPluginEnabled("DiscordMC") == false){
            return;
        }

        try{
            MessageAPI.sendToDiscord(" __**[Teisyoku]**__ " + string);
            return;
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
}
