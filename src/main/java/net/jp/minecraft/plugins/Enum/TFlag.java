package net.jp.minecraft.plugins.Enum;

import net.jp.minecraft.plugins.API.API;
import net.jp.minecraft.plugins.API.API_PlayerDatabase;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TeisyokuPlugin2<br />
 * フラグの列挙型クラス
 *
 * @author syokkendesuyo
 */
public enum TFlag {

    /**
     * callコマンドの呼び出し音設定
     */
    CALL_SOUNDS("call_sounds", "callコマンドの呼び出し音設定"),

    /**
     * マインカートを自動でインベントリに保存
     */
    CART_AUTO_COLLECT("cart_auto_collect", "マインカートを自動でインベントリに保存"),

    /**
     * 飛行モードの状態をログイン時に継承する設定
     */
    FLY_SAVE_STATE("fly_save_state", "飛行モードの状態をログイン時に継承する設定"),

    /**
     * ポータルのエラーメッセージを表示
     */
    PORTAL_WARNING("portal_warning", "ポータルのエラーメッセージを表示"),

    /**
     * [Cart]看板の利用設定
     */
    SIGN_CART("sign_cart", "[Cart]看板の利用設定"),

    /**
     * 看板データの照会機能利用設定
     */
    SIGN_INFO("sign_info", "看板データの照会機能利用設定"),

    /**
     * [Cart]看板の看板データの照会機能利用設定
     */
    SIGN_INFO_CART("sign_info_cart", "[Cart]看板の看板データの照会機能利用設定"),

    ;

    /**
     * フラグ名
     */
    private final String flag;

    /**
     * フラグの説明
     */
    private final String description;

    /**
     * パーミッションを定義
     *
     * @param flag        パーミッション
     * @param description フラグの説明
     */
    TFlag(String flag, String description) {
        this.flag = flag;
        this.description = description;
    }

    /**
     * フラグを返却します。
     *
     * @return フラグ
     */
    public String toString() {
        return this.flag;
    }

    /**
     * PlayerDatabaseで使用されるフラグを返却します。
     *
     * @return フラグ
     */
    public String getTFlag() {
        return this.flag;
    }

    /**
     * PlayerDatabaseで使用されるフラグパスを返却します。<br />
     * フラグ名の前に"flags."が付与されたものです。
     *
     * @return フラグパス(flags.フラグパス)
     */
    public String getTFlagPath() {
        return "flags." + this.flag;
    }

    /**
     * フラグの説明文を返却します。
     *
     * @return フラグの説明
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * フラグの説明文を返却します。
     *
     * @param flag フラグ名
     * @return フラグの説明
     */
    public static String getTFlagDescription(String flag) {
        for (TFlag f : TFlag.values()) {
            if (f.getTFlag().equals(flag)) {
                return f.getDescription();
            }
        }
        return null;
    }

    /**
     * PlayerDatabaseで使用されるフラグを返却します。
     *
     * @param flag フラグ名
     * @return フラグ
     */
    public static TFlag getTFlag(String flag) {
        for (TFlag f : TFlag.values()) {
            if (f.getTFlag().equals(flag) || f.getTFlag().equals("flags." + flag)) {
                return f;
            }
        }
        return null;
    }

    /**
     * 登録されているフラグを全件返却します。
     *
     * @return フラグ名
     */
    public static String[] getTFlags() {
        String[] flags = new String[values().length];
        int index = 0;
        for (TFlag f : TFlag.values()) {
            flags[index++] = f.getTFlag();
        }
        return flags;
    }

    /**
     * PlayerDatabaseで使用されるフラグ名またはパスが存在するか確認するメソッド
     *
     * @param flag フラグ
     * @return 存在するかを示すBoolean型
     */
    public static Boolean contains(String flag) {
        return getTFlag(flag) != null;
    }

    /**
     * フラグ状態を取得します。<br />
     * 設定が存在しない場合は有効状態を示します。
     *
     * @param player プレイヤー
     * @param flag   フラグ名
     * @return フラグ状態
     */
    public static Boolean getTFlagStatus(OfflinePlayer player, String flag) {
        String flagData = API_PlayerDatabase.getString(player, "flags." + flag);
        if (!(flagData.equals("true") || flagData.equals("false"))) {
            return true;
        }
        return API_PlayerDatabase.getBoolean(player, "flags." + flag);
    }

    /**
     * プレイヤーのデータベースを参照しフラグを更新します。
     *
     * @param sender コマンド実行者
     * @param player 対象プレイヤー
     * @param flag   フラグ
     * @param bool   フラグの状態
     */
    public static void setTFlagStatus(CommandSender sender, Player player, TFlag flag, String bool) {
        //オンライン時のみ更新可能
        //TODO: オフライン状態でも変更可能にする
        if (!player.isOnline()) {
            Msg.warning(sender, "プレイヤー" + ChatColor.YELLOW + sender + ChatColor.RESET + "はオンラインではありません。");
            return;
        }

        //引数に不正な文字列があった場合は処理を終了する
        if (!(bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("false"))) {
            Msg.warning(sender, "引数「" + bool + "」は利用できません。trueまたはfalseを指定して下さい。");
            return;
        }

        //引数をBoolean型にキャスト
        Boolean value = Boolean.valueOf(bool);

        //ChatColorを設定
        ChatColor color = ChatColor.RED;
        if (value) {
            color = ChatColor.GREEN;
        }

        //設定を保存
        API_PlayerDatabase.set(player, flag.getTFlagPath(), value);
        Msg.success(sender, flag.getDescription() + "： " + color + value);
    }

    /**
     * フラグの状態を確認するメソッド
     *
     * @param sender       コマンド送信者
     * @param targetPlayer 対象プレイヤー
     */
    public static void showTFlagStatus(CommandSender sender, String targetPlayer) {
        OfflinePlayer player = API.getPlayer(targetPlayer);
        if (player == null) {
            Msg.info(sender, ChatColor.YELLOW + targetPlayer + ChatColor.RESET + "さんは存在しません");
            return;
        }
        String[] flags = TFlag.getTFlags();
        Msg.info(sender, ChatColor.YELLOW + targetPlayer + ChatColor.RESET + "さんのフラグ状態");
        for (String f : flags) {
            ChatColor color = ChatColor.RED;
            String status = "無効";
            if (TFlag.getTFlagStatus(player, f)) {
                color = ChatColor.GREEN;
                status = "有効";
            }
            Msg.blank(sender, color + "・" + ChatColor.RESET + f + ChatColor.DARK_GRAY
                    + " (" + color + status + ChatColor.DARK_GRAY + ") " + ChatColor.YELLOW + ":  " + ChatColor.RESET + TFlag.getTFlagDescription(f), 2);
        }
    }
}