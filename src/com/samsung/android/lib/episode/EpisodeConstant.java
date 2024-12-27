package com.samsung.android.lib.episode;

import android.os.SemSystemProperties;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class EpisodeConstant {
    public static final String DTD_VERSION;

    static {
        String str;
        switch (SemSystemProperties.getInt("ro.build.version.oneui", 0)) {
            case 60000:
            case 60100:
            case 60101:
                str = "6.89";
                break;
            default:
                str = "7.51";
                break;
        }
        DTD_VERSION = str;
    }
}
