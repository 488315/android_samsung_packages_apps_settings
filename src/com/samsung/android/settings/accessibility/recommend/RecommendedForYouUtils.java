package com.samsung.android.settings.accessibility.recommend;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class RecommendedForYouUtils {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long OPERATION_TIME_LIMIT = TimeUnit.DAYS.toMillis(1);

    public static boolean isFeatureOperate24Hours(Context context, String str) {
        return System.currentTimeMillis()
                        - SecAccessibilityUtils.getAccessibilitySharedPreferences(context)
                                .getLong(str, 0L)
                >= OPERATION_TIME_LIMIT;
    }

    public static boolean isFeatureUsedMoreCount(Context context, int i, String str) {
        return SecAccessibilityUtils.getAccessibilitySharedPreferences(context).getInt(str, 0) >= i;
    }

    public static void updateFeatureConditionForProfile(
            Context context, String str, String str2, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            SharedPreferences accessibilitySharedPreferences =
                    SecAccessibilityUtils.getAccessibilitySharedPreferences(context);
            long j = accessibilitySharedPreferences.getLong(str, 0L);
            if (z && j == 0) {
                accessibilitySharedPreferences
                        .edit()
                        .putLong(str, System.currentTimeMillis())
                        .apply();
            } else if (!z) {
                accessibilitySharedPreferences.edit().putLong(str, 0L).apply();
            }
        }
        if (TextUtils.isEmpty(str2) || !z) {
            return;
        }
        SharedPreferences accessibilitySharedPreferences2 =
                SecAccessibilityUtils.getAccessibilitySharedPreferences(context);
        int i = accessibilitySharedPreferences2.getInt(str2, 0);
        if (i < Integer.MAX_VALUE) {
            i++;
        }
        accessibilitySharedPreferences2.edit().putInt(str2, i).apply();
    }
}
