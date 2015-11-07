package net.jp.minecraft.plugins;

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

    File newConfig;
    FileConfiguration lastJoinPlayerConfig;

    private static TeisyokuPlugin2 instance;

    @Override
    public void onEnable(){
        PluginManager pm = Bukkit.getServer().getPluginManager();

        instance = this;

        //リスナー登録
        pm.registerEvents(new Listener_JoinEvent() , this);
        pm.registerEvents(new Listener_NetherGateEvent() , this);
        pm.registerEvents(new Listener_MineCartEvent() , this);
        pm.registerEvents(new Listener_DeathEvent() , this);
        pm.registerEvents(new Listener_EntityDamage() , this);;
        pm.registerEvents(new Listener_Gomibako() , this);
        pm.registerEvents(new Listener_LastJoin() , this);
        pm.registerEvents(new Listener_Tab() , this);
        pm.registerEvents(new Listener_Sign() , this);
        pm.registerEvents(new GUI_YesNo() , this);
        pm.registerEvents(new GUI_ClickEvent() , this);


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

    }

    //configを生成
    public void LastJoinPlayerConfig(){
        newConfig = new File(getDataFolder(),"LastJoinPlayersData.yml");
        lastJoinPlayerConfig = YamlConfiguration.loadConfiguration(newConfig);
        saveLastPlayerJoinConfig();
    }

    //LastPlayerJoinPlayersData.ymlの保存
    public void saveLastPlayerJoinConfig(){
        try{
            lastJoinPlayerConfig.save(newConfig);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //LastPlayerJoinPlayersData.ymlのリロード
    public void reloadLastPlayerJoinConfig(){
        try{
            lastJoinPlayerConfig.load(newConfig);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static TeisyokuPlugin2 getInstance(){
        return instance;
    }
}
