package net.jp.minecraft.plugins.Utility;

public enum Permission {

    USER("teisyoku.user"),

    ADMIN("teisyoku.admin"),

    AD("teisyoku.ad"),
    AD_COOLTIME("teisyoku.ad.cooltime")

    ;

    private final String perm;

    /**
     *
     *
     * @param permission パーミッション
     */
    private Permission(String permission) {
        this.perm = permission;
    }

    /**
     * パーミッションを返却します。
     *
     * @return パーミッション
     */
    public String toString() { return this.perm; }
}
