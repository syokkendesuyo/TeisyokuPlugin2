package net.jp.minecraft.plugins.API;

import java.io.File;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class API_Players {

    public static int getTotalPlayers(){
        try{
            return new File("world/playerdata/").list().length;
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
