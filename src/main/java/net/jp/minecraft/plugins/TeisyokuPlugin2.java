package net.jp.minecraft.plugins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class TeisyokuPlugin2 extends JavaPlugin implements Listener {
    @Override
    public void onEnable(){
        PluginManager pm = Bukkit.getServer().getPluginManager();

        //リスナー登録
        pm.registerEvents(new JoinEvent() , this);
        pm.registerEvents(new NetherGateEvent() , this);
        pm.registerEvents(new MineCartEvent() , this);
        pm.registerEvents(new YesNoGUI() , this);
        pm.registerEvents(new GUIClickEvent() , this);
        pm.registerEvents(new DeathEvent() , this);
        pm.registerEvents(new Gomibako() , this);

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
    }
}
