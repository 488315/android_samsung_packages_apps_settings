package com.samsung.android.settings.inputmethod;

import android.provider.Settings;

import androidx.fragment.app.FragmentActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class EnableCombinationKeyHelper {
    public static boolean isEnableCombinationKey(FragmentActivity fragmentActivity, int i) {
        return (Settings.System.getInt(
                                fragmentActivity.getContentResolver(),
                                "enable_language_change_combination_key",
                                7)
                        & i)
                == i;
    }
}
