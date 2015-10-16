package net.jp.minecraft.plugins;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Permissions {

    //ヘルプパーミッション
    public static String getHelpCommandPermisson(){
        String permission = "teisyoku.command.Help";
        return permission;
    }
    public static String getHelpPermisson(){
        String permission = "teisyoku.Help";
        return permission;
    }

    //飛行パーミッション
    public static String getFlyCommandPermisson(){
        String permission = "teisyoku.command.fly";
        return permission;
    }
    public static String getFlyPermisson(){
        String permission = "teisyoku.fly";
        return permission;
    }

    //定食メニューパーミッション
    public static String getTeisyokuCommandPermisson(){
        String permisson = "teisyoku.command.menu";
        return permisson;
    }
    public static String getTeisyokuPermisson(){
        String permisson = "teisyoku.menu";
        return permisson;
    }

    //プレイヤー一覧パーミッション
    public static String getPlayersCommandPermisson(){
        String permisson = "teisyoku.command.players";
        return permisson;
    }
    public static String getPlayersPermisson(){
        String permisson = "teisyoku.players";
        return permisson;
    }

    //ゴミ箱パーミッション
    public static String getGomibakoCommandPermisson(){
        String string = "teisyoku.command.gomibako";
        return string;
    }
    public static String getGomibakoPermisson(){
        String string = "teisyoku.gomibako";
        return string;
    }
}