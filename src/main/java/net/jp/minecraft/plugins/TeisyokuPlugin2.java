package net.jp.minecraft.plugins;

import net.jp.minecraft.plugins.Commands.Command_TabName;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class TeisyokuPlugin2 extends JavaPlugin implements Listener {

    File newConfig_last;
    File newConfig_nick;
    File newConfig_cart;
    File newConfig_tpoint;
    File newConfig_tpoint_settings;

    FileConfiguration lastJoinPlayerConfig;
    FileConfiguration NickConfig;
    FileConfiguration CartConfig;
    FileConfiguration TPointConfig;
    FileConfiguration TPointSettingsConfig;

    private static TeisyokuPlugin2 instance;

    @Override
    public void onEnable(){
        PluginManager pm = Bukkit.getServer().getPluginManager();

        instance = this;

        //リスナー登録
        pm.registerEvents(new Listener_JoinEvent() , this);
        pm.registerEvents(new Listener_NetherGateEvent() , this);
        pm.registerEvents(new Listener_DeathEvent() , this);
        pm.registerEvents(new Listener_EntityDamage() , this);;
        pm.registerEvents(new Listener_WitherSpawmCancel() , this);
        pm.registerEvents(new Listener_MineCartEvent() , this);
        pm.registerEvents(new Listener_Gomibako() , this);
        pm.registerEvents(new Listener_LastJoin() , this);
        pm.registerEvents(new Listener_Chat() , this);
        pm.registerEvents(new Listener_Tab() , this);
        pm.registerEvents(new Listener_Sign() , this);
        pm.registerEvents(new GUI_YesNo() , this);
        pm.registerEvents(new GUI_ClickEvent() , this);
        pm.registerEvents(new Listener_SignColor() , this);
        pm.registerEvents(new Listener_TPoint() , this);




        //ヘルプコマンド
        getCommand("help").setExecutor(new Command_Help());

        //Flyコマンド
        getCommand("fly").setExecutor(new Command_Fly());

        //定食コマンド
        getCommand("t").setExecutor(new Command_Teisyoku());
        getCommand("teisyoku").setExecutor(new Command_Teisyoku());

        //ゴミ箱
        getCommand("gomi").setExecutor(new Command_Gomibako());
        getCommand("gomibako").setExecutor(new Command_Gomibako());

        //プレイヤー一覧
        //TODO ミドルクリックでテレポート
        getCommand("p").setExecutor(new Command_Players());
        getCommand("players").setExecutor(new Command_Players());

        //最終ログイン確認
        getCommand("last").setExecutor(new Command_Last());

        //広告コマンド
        getCommand("ad").setExecutor(new Command_Ad());
        getCommand("notice").setExecutor(new Command_Ad());

        //呼び出しコマンド
        getCommand("call").setExecutor(new Command_Call());

        //マインカートを渡すコマンド
        getCommand("cart").setExecutor(new Command_Cart());
        getCommand("minecart").setExecutor(new Command_Cart());

        //ニックネームコマンド
        getCommand("nick").setExecutor(new Command_Nick());
        getCommand("nickname").setExecutor(new Command_Nick());

        //ポイントコマンド
        getCommand("tt").setExecutor(new Command_TPoint());
        getCommand("point").setExecutor(new Command_TPoint());
        getCommand("tpoint").setExecutor(new Command_TPoint());

        //鉄道情報コマンド
        getCommand("ri").setExecutor(new Command_RailwayInfo());
        getCommand("railwayinfo").setExecutor(new Command_RailwayInfo());

        getCommand("tabname").setExecutor(new Command_TabName());

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().broadcastMessage("");
                Bukkit.getServer().broadcastMessage(Messages.getNormalPrefix() +"当サーバに投票すると素敵な商品が入手できます。");
                Bukkit.getServer().broadcastMessage(Messages.getNormalPrefix() +"詳細はこちら >> " + ChatColor.YELLOW + ChatColor.UNDERLINE + "http://bit.ly/teisyoku_vote");
                Bukkit.getServer().broadcastMessage("");
            }
        }, 0L, 20*60*45L);


        LastJoinPlayerConfig();
        saveLastPlayerJoinConfig();

        NickConfig();
        saveNickConfig();

        TPointConfig();
        saveTPointConfig();

        CartConfig();
        saveCartConfig();

    }



    /*
        LastCommandのconfig
     */

    //configを生成
    public void LastJoinPlayerConfig(){
        newConfig_last = new File(getDataFolder(),"LastJoinPlayersData.yml");
        lastJoinPlayerConfig = YamlConfiguration.loadConfiguration(newConfig_last);
        saveLastPlayerJoinConfig();
    }

    //LastPlayerJoinPlayersData.ymlの保存
    public void saveLastPlayerJoinConfig(){
        try{
            lastJoinPlayerConfig.save(newConfig_last);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //LastPlayerJoinPlayersData.ymlのリロード
    public void reloadLastPlayerJoinConfig(){
        try{
            lastJoinPlayerConfig.load(newConfig_last);
            lastJoinPlayerConfig.save(newConfig_last);

        }catch(Exception e){
            e.printStackTrace();
        }
    }



    /*
        NickCommandのconfig
     */

    //configを生成
    public void NickConfig(){
        newConfig_nick = new File(getDataFolder(),"NickData.yml");
        NickConfig = YamlConfiguration.loadConfiguration(newConfig_nick);
        saveLastPlayerJoinConfig();
    }

    //LastPlayerJoinPlayersData.ymlの保存
    public void saveNickConfig(){
        try{
            NickConfig.save(newConfig_nick);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //LastPlayerJoinPlayersData.ymlのリロード
    public void reloadNickConfig(){
        try{
            NickConfig.load(newConfig_nick);
            NickConfig.save(newConfig_nick);

        }catch(Exception e){
            e.printStackTrace();
        }
    }



    /*
        TPointSettingsのconfig
     */

    //configを生成
    public void TpointSettingsConfig(){
        newConfig_tpoint_settings = new File(getDataFolder(),"TPoint_Settings.yml");
        TPointSettingsConfig = YamlConfiguration.loadConfiguration(newConfig_tpoint);
        saveLastPlayerJoinConfig();
    }

    //LastPlayerJoinPlayersData.ymlの保存
    public void saveTpointSettingsConfig(){
        try{
            TPointSettingsConfig.save(newConfig_tpoint_settings);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //LastPlayerJoinPlayersData.ymlのリロード
    public void reloadTpointSettingsConfig(){
        try{
            TPointSettingsConfig.load(newConfig_tpoint_settings);
            TPointSettingsConfig.save(newConfig_tpoint_settings);

        }catch(Exception e){
            e.printStackTrace();
        }
    }



    /*
        TPointのconfig
     */

    //configを生成
    public void TPointConfig(){
        newConfig_tpoint = new File(getDataFolder(),"TPoint.yml");
        TPointConfig = YamlConfiguration.loadConfiguration(newConfig_tpoint);
        saveLastPlayerJoinConfig();
    }

    //TPoint.ymlの保存
    public void saveTPointConfig(){
        try{
            TPointConfig.save(newConfig_tpoint);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //TPoint.ymlのリロード
    public void reloadTPointConfig(){
        try{
            TPointConfig.load(newConfig_tpoint);
            TPointConfig.save(newConfig_tpoint);

        }catch(Exception e){
            e.printStackTrace();
        }
    }



    /*
        Cartのconfig
     */

    public void CartConfig(){
        newConfig_cart = new File(getDataFolder(),"Cart.yml");
        CartConfig = YamlConfiguration.loadConfiguration(newConfig_cart);
        saveLastPlayerJoinConfig();
    }

    public void saveCartConfig(){
        try{
            CartConfig.save(newConfig_cart);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void reloadCartConfig(){
        try{
            CartConfig.load(newConfig_cart);
            CartConfig.save(newConfig_cart);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /*
        インスタンス
     */
    public static TeisyokuPlugin2 getInstance(){
        return instance;
    }
}
