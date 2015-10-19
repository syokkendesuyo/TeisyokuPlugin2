package net.jp.minecraft.plugins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
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
        pm.registerEvents(new JoinEvent() , this);
        pm.registerEvents(new NetherGateEvent() , this);
        pm.registerEvents(new MineCartEvent() , this);
        pm.registerEvents(new YesNoGUI() , this);
        pm.registerEvents(new GUIClickEvent() , this);
        pm.registerEvents(new DeathEvent() , this);
        pm.registerEvents(new Gomibako() , this);
        pm.registerEvents(new Listener_LastJoin() , this);
        pm.registerEvents(new Listener_Tab() , this);

        //ヘルプコマンド
        getCommand("help").setExecutor(new HelpCommand());

        //Flyコマンド
        //TODO 関数化
        getCommand("fly").setExecutor(new Command_fly());

        //定食コマンド
        getCommand("t").setExecutor(new TeisyokuCommand());
        getCommand("teisyoku").setExecutor(new TeisyokuCommand());

        //ゴミ箱
        getCommand("gomi").setExecutor(new GomibakoCommand());
        getCommand("gomibako").setExecutor(new GomibakoCommand());

        //プレイヤー一覧
        //TODO ミドルクリックでテレポート
        getCommand("p").setExecutor(new PlayersCommand());
        getCommand("players").setExecutor(new PlayersCommand());

        getCommand("lastjoin").setExecutor(new Command_LastJoin());

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
