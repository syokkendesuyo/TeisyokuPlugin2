package net.jp.minecraft.plugins.teisyokuplugin2;

import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_Ad;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_BaiPoint;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_Call;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_Cart;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_CartHelp;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_Color;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_Daunii;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_Fly;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_GoodGame;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_Horse;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_HorseTicket;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_Nickname;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_PlayerData;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_Players;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_RailwaysInfo;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_SignEdit;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_TFlag;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_TPS;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_TPoint;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_TabName;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_Teisyoku;
import net.jp.minecraft.plugins.teisyokuplugin2.command.Command_Trash;
import net.jp.minecraft.plugins.teisyokuplugin2.config.CustomConfig;
import net.jp.minecraft.plugins.teisyokuplugin2.gui.GUI_ClickEvent;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_Chat;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_Daunii_1_15;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_Death;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_EntityDamage;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_Fly;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_Horse;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_JoinQuit;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_Minecart;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_MobGrief;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_Portal;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_Sign;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_SignColor;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_SignEdit;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_SkeletonHorse;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_SpawnEgg;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_Tab;
import net.jp.minecraft.plugins.teisyokuplugin2.listener.Listener_TicksPerSecond_1_15;
import net.jp.minecraft.plugins.teisyokuplugin2.migration.PlayerDatabaseMigration;
import net.jp.minecraft.plugins.teisyokuplugin2.tpoint.TPointBuyGUI;
import net.jp.minecraft.plugins.teisyokuplugin2.tpoint.TPointIndexGUI;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Color;
import net.jp.minecraft.plugins.teisyokuplugin2.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;

public class TeisyokuPlugin2 extends JavaPlugin implements Listener {

    /**
     * インスタンス
     */
    private static TeisyokuPlugin2 instance;

    /**
     * サポートバージョン
     */
    private static String supportVersion = "1.15.2-R0.1-SNAPSHOT";

    /**
     * Teisyoku.yml
     */
    public CustomConfig configTeisyoku;

    /**
     * TPoint.yml
     */
    public CustomConfig configTPoint;

    /**
     * Gift.yml
     */
    public CustomConfig configGift;

    /**
     * Horses,yml
     */
    public CustomConfig configHorses;

    /**
     * Railways.yml
     */
    public CustomConfig configRailways;

    public static TeisyokuPlugin2 getInstance() {
        return instance;
    }

    /**
     * 再起動を含む起動時に呼び出されるメソッド
     */
    public void onEnable() {
        String version = Bukkit.getBukkitVersion();
        Msg.info(Bukkit.getConsoleSender(), "Running on " + version);

        PluginManager pm = Bukkit.getServer().getPluginManager();

        instance = this;

        pm.registerEvents(new Listener_Chat(), this);
        pm.registerEvents(new Listener_Death(), this);
        pm.registerEvents(new Listener_EntityDamage(), this);
        pm.registerEvents(new Listener_Fly(), this);
        pm.registerEvents(new Listener_Horse(), this);
        pm.registerEvents(new Listener_JoinQuit(), this);
        pm.registerEvents(new Listener_Minecart(), this);
        pm.registerEvents(new Listener_MobGrief(), this);
        pm.registerEvents(new Listener_Portal(), this);
        pm.registerEvents(new Listener_Sign(), this);
        pm.registerEvents(new Listener_SignColor(), this);
        pm.registerEvents(new Listener_SignEdit(), this);
        pm.registerEvents(new Listener_SkeletonHorse(), this);
        pm.registerEvents(new Listener_SpawnEgg(), this);

        pm.registerEvents(new GUI_ClickEvent(), this);

        pm.registerEvents(new TPointIndexGUI(), this);
        pm.registerEvents(new TPointBuyGUI(), this);


        getCommand("fly").setExecutor(new Command_Fly());

        getCommand("t").setExecutor(new Command_Teisyoku());
        getCommand("teisyoku").setExecutor(new Command_Teisyoku());

        getCommand("p").setExecutor(new Command_Players());
        getCommand("players").setExecutor(new Command_Players());

        getCommand("playerdata").setExecutor(new Command_PlayerData());
        getCommand("pd").setExecutor(new Command_PlayerData());
        getCommand("last").setExecutor(new Command_PlayerData());

        getCommand("ad").setExecutor(new Command_Ad());
        getCommand("notice").setExecutor(new Command_Ad());

        getCommand("call").setExecutor(new Command_Call());

        getCommand("cart").setExecutor(new Command_Cart());
        getCommand("minecart").setExecutor(new Command_Cart());

        getCommand("nick").setExecutor(new Command_Nickname());
        getCommand("nickname").setExecutor(new Command_Nickname());

        getCommand("tt").setExecutor(new Command_TPoint());
        getCommand("point").setExecutor(new Command_TPoint());
        getCommand("tpoint").setExecutor(new Command_TPoint());

        getCommand("ri").setExecutor(new Command_RailwaysInfo());
        getCommand("railwaysinfo").setExecutor(new Command_RailwaysInfo());

        getCommand("tabname").setExecutor(new Command_TabName());

        getCommand("horse").setExecutor(new Command_Horse());

        getCommand("carthelp").setExecutor(new Command_CartHelp());
        getCommand("ch").setExecutor(new Command_CartHelp());

        getCommand("color").setExecutor(new Command_Color());

        getCommand("gg").setExecutor(new Command_GoodGame());

        getCommand("bp").setExecutor(new Command_BaiPoint());

        getCommand("se").setExecutor(new Command_SignEdit());

        getCommand("horseticket").setExecutor(new Command_HorseTicket());
        getCommand("ht").setExecutor(new Command_HorseTicket());

        getCommand("sign").setExecutor(new Command_SignEdit());

        getCommand("tflag").setExecutor(new Command_TFlag());

        getCommand("trash").setExecutor(new Command_Trash());
        getCommand("gomi").setExecutor(new Command_Trash());
        getCommand("gomibako").setExecutor(new Command_Trash());

        //サポートバージョンを確認
        if (version.equals(supportVersion)) {

            //events
            pm.registerEvents(new Listener_Tab(), this);
            pm.registerEvents(new Listener_Daunii_1_15(), this);

            //commands
            getCommand("daunii").setExecutor(new Command_Daunii());
            getCommand("tps").setExecutor(new Command_TPS());
            getCommand("status").setExecutor(new Command_TPS());
            getCommand("s").setExecutor(new Command_TPS());

            //schedule
            BukkitScheduler scheduler_tps = Bukkit.getServer().getScheduler();
            scheduler_tps.scheduleSyncRepeatingTask(this, new Runnable() {
                public void run() {
                    double tps = Listener_TicksPerSecond_1_15.getTps(1);
                    if (Listener_TicksPerSecond_1_15.getTps(1) < 16) {
                        Msg.warning(Bukkit.getConsoleSender(), "現在TPSが低下しています：" + ChatColor.YELLOW + Listener_TicksPerSecond_1_15.doubleToString(tps), true);
                    }
                }
            }, 0L, 6000L);

            //message
            Msg.success(Bukkit.getConsoleSender(), version + "用に作成された一部機能が開放されました");
        } else {
            Msg.warning(Bukkit.getConsoleSender(), version + "はサポート対象外のバージョンです");
        }

        // コンフィグを作成
        configTeisyoku = new CustomConfig(this, "Teisyoku.yml");
        configTeisyoku.saveDefaultConfig();

        configTPoint = new CustomConfig(this, "TPoint.yml");
        configTPoint.saveDefaultConfig();

        configGift = new CustomConfig(this, "Gift.yml");
        configGift.saveDefaultConfig();

        configHorses = new CustomConfig(this, "Horses.yml");
        configHorses.saveDefaultConfig();

        configRailways = new CustomConfig(this, "Railways.yml");
        configRailways.saveDefaultConfig();

        //古いデータを移行
        PlayerDatabaseMigration.migration();

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                List<String> ad = configTeisyoku.getConfig().getStringList("ad");
                for (String s : ad) {
                    Msg.info(Bukkit.getConsoleSender(), Color.convert(s), true);
                }
            }
        }, 0L, 54000L);
    }
}