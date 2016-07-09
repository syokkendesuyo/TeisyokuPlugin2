package net.jp.minecraft.plugins.Config;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Connection_TeisyokuConfig {

    public static File newConfig_teisyoku2;
    public static FileConfiguration TeisyokuConfig2;


    /**
     * Configを生成するメソッド
     * @param name 生成するConfig名
     */
    public static void createConfig(String name)
    {
        newConfig_teisyoku2= new File(TeisyokuPlugin2.getInstance().getDataFolder(), name);
        TeisyokuConfig2 = YamlConfiguration.loadConfiguration(newConfig_teisyoku2);
        saveConfig();
    }


    /**
     * Teisyoku.ymlセーブ
     */
    public static void saveConfig()
    {
        try {
            TeisyokuConfig2.save(newConfig_teisyoku2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Teisyoku.ymlリロード
     */
    public void reloadConfig()
    {
        try {
            //最新のデータフォルダをロード
            TeisyokuConfig2.load(newConfig_teisyoku2);
            //セーブしてデータを維持
            TeisyokuConfig2.save(newConfig_teisyoku2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Teisyoku.ymlから文字列を取り出す
     * @param path
     * @return データ
     */
    public static String getString(String path){
        return TeisyokuConfig2.getString( path );
    }


    /**
     * Teisyoku.ymlから文字列(１次元配列)を取り出す
     * @param path
     * @return
     */
    public static List getStringList(String path) { return TeisyokuConfig2.getStringList( path ); }



    /**
     * Teisyoku.ymlから数値を取り出す
     * @param path
     * @return データ
     */
    public static int getInt(String path){
        return TeisyokuConfig2.getInt( path );
    }


    /**
     * Teisyoku.ymlに文字列や数値をセーブ
     * @param path
     * @return boolean
     */
    public static boolean setConfig(String path , Object value){
        try {
            TeisyokuConfig2.set( path , value );
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Teisyoku.ymlにリストを追加
     * @param path
     * @param s
     * @return
     */
    public static boolean addArrayList(String path , String s){
        try{
            List list = TeisyokuConfig2.getList(path);
            list.add(s);
            saveConfig();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Teisyoku.ymlのリスト検索
     * @param list
     * @param match
     * @return
     */
    public static boolean listMatch(List<String> list , String match){
        for(String s : list){
            if(s.equalsIgnoreCase(match)){
                return true;
            }
        }
        return false;
    }

    /**
     * Teisyoku.ymlにデータがあるかチェック、無ければ生成
     * @param path
     * @param value
     */
    public static void nullCheckAndSet(String path , Object value){
        if(TeisyokuConfig2.get( path ) == null){
            TeisyokuConfig2.set( path , value );
            saveConfig();
        }
    }
}
