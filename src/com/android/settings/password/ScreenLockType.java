package com.android.settings.password;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public enum ScreenLockType {
    NONE(0, "unlock_set_off", 0),
    SWIPE(0, "unlock_set_none", 0),
    PATTERN(65536, "unlock_set_pattern", 65536),
    PIN(131072, "unlock_set_pin", 196608),
    PASSWORD(262144, "unlock_set_password", 393216),
    MANAGED(
            NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME,
            "unlock_set_managed",
            NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME),
    /* JADX INFO: Fake field, exist only in values array */
    EF109(458752, "unlock_set_cac_pin", 458752),
    /* JADX INFO: Fake field, exist only in values array */
    EF123(458752, "unlock_set_ucm_pin", 458752),
    /* JADX INFO: Fake field, exist only in values array */
    ENTERPRISE("unlock_set_enterprise_identity"),
    /* JADX INFO: Fake field, exist only in values array */
    EF151(397312, "unlock_set_fingerprint", 397312),
    /* JADX INFO: Fake field, exist only in values array */
    FACE("switch_face"),
    /* JADX INFO: Fake field, exist only in values array */
    FINGERPRINT("switch_fingerprint"),
    /* JADX INFO: Fake field, exist only in values array */
    FACE_SCAN_SUW("unlock_set_face"),
    /* JADX INFO: Fake field, exist only in values array */
    CHANGE_LOCK_TYPE("pref_lock_type"),
    /* JADX INFO: Fake field, exist only in values array */
    TWO_FACTOR("unlock_set_two_factor"),
    /* JADX INFO: Fake field, exist only in values array */
    RESET_WITH_SAMSUNG_ACCOUNT("switch_reset_with_sa");

    public final int defaultQuality;
    public final int maxQuality;
    public final String preferenceKey;

    ScreenLockType(String str) {
        this(9437184, str, 9437184);
    }

    public static ScreenLockType fromQuality(int i) {
        if (i == 0) {
            return SWIPE;
        }
        if (i == 65536) {
            return PATTERN;
        }
        if (i == 131072 || i == 196608) {
            return PIN;
        }
        if (i == 262144 || i == 327680 || i == 393216) {
            return PASSWORD;
        }
        if (i != 524288) {
            return null;
        }
        return MANAGED;
    }

    ScreenLockType(int i, String str, int i2) {
        this.defaultQuality = i;
        this.maxQuality = i2;
        this.preferenceKey = str;
    }
}
