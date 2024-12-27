package com.samsung.android.settings.vpn.bixby;

import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class RoutineUtils {
    public static String getVpnProfileKey(String str) {
        if (str == null || str.length() <= 2) {
            return null;
        }
        if (str.startsWith("1") || str.startsWith(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)) {
            return str.substring(2);
        }
        return null;
    }
}
