package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Preferences {
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
    }
}
