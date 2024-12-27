package com.android.settings.dashboard.suggestions;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SuggestionFeatureProviderImpl {
    public static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences("suggestions", 0);
    }
}
