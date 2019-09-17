package net.jp.minecraft.plugins.Utility;

public enum Permission {

    USER("teisyoku.user"),

    ADMIN("teisyoku.admin"),

    AD("teisyoku.ad"),
    AD_COOLTIME("teisyoku.ad.cooltime"),

    CALL("teisyoku.call"),

    CART("teisyoku.cart"),
    CART_GIVE("teisyoku.cart.give"),

    COLOR("teisyoku.color"),

    DAUNII_USE("teisyoku.daunii.use"),
    DAUNII_SUMMON("teisyoku.daunii.summon"),

    FLY_ME("teisyoku.fly.me"),
    FLY_OTHERS("teisyoku.fly.others"),

    HELP("teisyoku.help"),

    HORSE("teisyoku.horse"),
    HORSE_ADMIN("teisyoku.horse.admin"),

    PLAYERS("teisyoku.players"),

    TRASH("teisyoku.trash"),

    PORTAL_BYPASS_NETHER("teisyoku.portal.bypass.nether"),

    ;

    private final String perm;

    /**
     * パーミッションを定義
     *
     * @param permission パーミッション
     */
    Permission(String permission) {
        this.perm = permission;
    }

    /**
     * パーミッションを返却します。
     *
     * @return パーミッション
     */
    public String toString() { return this.perm; }
}
