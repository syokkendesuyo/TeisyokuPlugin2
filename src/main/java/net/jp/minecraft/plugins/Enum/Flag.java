package net.jp.minecraft.plugins.Enum;

/**
 * TeisyokuPlugin2<br />
 * フラグの列挙型クラス
 *
 * @author syokkendesuyo
 */
public class Flag {

    /**
     * TeisyokuPlugin2<br />
     * フラグを定義する列挙型クラス
     *
     * @author syokkendesuyo
     */
    public enum TFlag {

        CALL_SOUNDS("call_sounds", "callコマンドの呼び出し音設定"),

        CART_AUTO_COLLECT("cart_auto_collect", "マインカートを自動でインベントリに保存"),

        FLY_SAVE_STATE("fly_save_state", "飛行モードの状態をログイン時に継承する設定"),

        SIGN_INFO("sign_info", "看板データの照会機能利用設定"),

        SIGN_CART("sign_cart", "[Cart]看板の利用設定"),

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
         * PlayerDatabaseで使用されるフラグを返却します。
         *
         * @param flag フラグ名
         * @return フラグ
         */
        public static TFlag getTFlag(String flag) {
            TFlag[] flags = TFlag.values();
            for (TFlag f : flags) {
                if (f.getTFlag().equals(flag) || f.getTFlag().equals("flags." + flag)) {
                    return f;
                }
            }
            return null;
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
    }
}