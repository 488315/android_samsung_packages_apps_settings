package com.samsung.android.knox.restriction;

import android.content.Context;

import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SPDControlPolicy {
    public static final int SPD_ENFORCE_NONE = 0;
    public static final int SPD_ENFORCE_OFF = 2;
    public static final int SPD_ENFORCE_ON = 1;
    public static final int SPD_FAILED = -1;
    public static final int SPD_OFF = 4;
    public static final int SPD_ON = 3;
    public static String TAG = "SPDControlPolicy";

    public SPDControlPolicy(ContextInfo contextInfo, Context context) {}

    public int getAutoSecurityPolicyUpdateMode() {
        return -1;
    }

    public boolean setAutoSecurityPolicyUpdateMode(int i) {
        return false;
    }
}
