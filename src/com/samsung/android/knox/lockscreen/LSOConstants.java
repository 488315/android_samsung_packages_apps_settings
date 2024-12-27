package com.samsung.android.knox.lockscreen;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LSOConstants {
    public static final String ACTION_LSO_CONFIG_CHANGED_INTERNAL =
            "com.samsung.android.knox.intent.action.LSO_CONFIG_CHANGED_INTERNAL";
    public static final String ADMIN_LOCKSCREEN_WALLPAPER_DIR = "/data/system/enterprise/lso";
    public static final String ADMIN_LOCKSCREEN_WALLPAPER_PORTRAIT =
            "/data/system/enterprise/lso/lockscreen_wallpaper.jpg";
    public static final String ADMIN_LOCKSCREEN_WALLPAPER_RIPPLE =
            "/data/system/enterprise/lso/lockscreen_wallpaper_ripple.jpg";
    public static final int CUSTOM_LAYER = 2;
    public static final float DEFAULT_ALPHA_LEVEL = 1.0f;
    public static final int DEFAULT_LAYER = 1;
    public static final int EMERGENCY_PHONE_LAYER = 3;
    public static final String ENTERPRISE_PRIVATE_DIR = "/data/system/enterprise";
    public static final int ERROR_BAD_STATE = -6;
    public static final int ERROR_FAILED = -4;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_ALLOWED = -1;
    public static final int ERROR_NOT_READY = -5;
    public static final int ERROR_NOT_SUPPORTED = -3;
    public static final int ERROR_PERMISSION_DENIED = -2;
    public static final int ERROR_UNKNOWN = -2000;
    public static final String EXTRA_KNOX_WALLPAPER_ENABLED_INTERNAL =
            "com.samsung.android.knox.intent.extra.KNOX_WALLPAPER_ENABLED_INTERNAL";
    public static final int FEATURE_ALL = -1;
    public static final int FEATURE_ANY = 0;
    public static final int FEATURE_INVISIBLE_OVERLAY = 1;
    public static final int FEATURE_WALLPAPER = 2;
    public static final String LOCKSCREEN_WALLPAPER_DIR = "/data/data/com.sec.android.gallery3d";
    public static final String LSO_FEATURE_ALL = "LOCKSCREEN_ALL_FEATURE";
    public static final String LSO_FEATURE_ANY = "LOCKSCREEN_ANY_FEATURE";
    public static final String LSO_FEATURE_OVERLAY = "LOCKSCREEN_OVERLAY";
    public static final String LSO_FEATURE_WALLPAPER = "LOCKSCREEN_WALLPAPER";
    public static final String LSO_PRIVATE_DIR = "/data/system/enterprise/lso";
    public static final int MAX_SUPPORTED_LAYER = 3;
    public static final int RESET_ALL_LAYER = 0;
    public static final String SETTINGS_MDM_WALLPAPER_ENABLE_INTERNAL = "mdm_wallpaper_enabled";
    public static final String TAG = "LSO";

    public static String getLSOFeatureName(int i) {
        return i != -1
                ? i != 0
                        ? i != 1
                                ? i != 2
                                        ? SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                                i, "Unknown ")
                                        : LSO_FEATURE_WALLPAPER
                                : LSO_FEATURE_OVERLAY
                        : LSO_FEATURE_ANY
                : LSO_FEATURE_ALL;
    }

    public static String getLayerName(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "LAYER")
                                        : "EMERGENCY_PHONE_LAYER"
                                : "CUSTOM_LAYER"
                        : "DEFAULT_LAYER"
                : "RESET_ALL_LAYER";
    }
}
