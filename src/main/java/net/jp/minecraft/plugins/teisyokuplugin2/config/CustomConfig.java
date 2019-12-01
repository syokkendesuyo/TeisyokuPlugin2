package net.jp.minecraft.plugins.teisyokuplugin2.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * config.yml以外の設定ファイルも扱えるクラス
 *
 * @author HimaJyun
 * @see <a href=https://jyn.jp/bukkit-plugin-development-8/>[Bukkitプラグイン制作講座-其之八]複数のymlファイルを扱う</a>
 * @see <a href=https://github.com/HimaJyun/BukkitPluginDevelopment/blob/master/Part0-9/Part8/src/main/java/jp/jyn/part8/CustomConfig.java>BukkitPluginDevelopment@HimaJyun</a>
 */
public class CustomConfig {

    private final File configFile;
    private final String file;
    private final Plugin plugin;
    private FileConfiguration config = null;

    /**
     * config.ymlを設定として読み書きするカスタムコンフィグクラスをインスタンス化します。
     *
     * @param plugin ロード対象のプラグイン
     */
    public CustomConfig(Plugin plugin) {
        this(plugin, "config.yml");
    }

    /**
     * 指定したファイル名で設定を読み書きするカスタムコンフィグクラスをインスタンス化します。
     *
     * @param plugin   ロード対象のプラグイン
     * @param fileName 読み込みファイル名
     */
    public CustomConfig(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.file = fileName;
        configFile = new File(plugin.getDataFolder(), file);
    }

    /**
     * デフォルト設定を保存します。
     */
    public void saveDefaultConfig() {
        if (!configFile.exists()) {
            plugin.saveResource(file, false);
        }
    }

    /**
     * 読み込んだFileConfiguretionを提供します。
     *
     * @return 読み込んだ設定
     */
    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    /**
     * 設定を保存します。
     */
    public void saveConfig() {
        if (config == null) {
            return;
        }
        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }

    /**
     * 設定をリロードします。
     */
    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);

        final InputStream defConfigStream = plugin.getResource(file);
        if (defConfigStream == null) {
            return;
        }

        config.setDefaults(
                YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, StandardCharsets.UTF_8)));
    }
}