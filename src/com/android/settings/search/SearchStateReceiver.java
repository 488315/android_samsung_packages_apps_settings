package com.android.settings.search;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.FeatureFlagUtils;
import android.util.Log;

import com.android.settings.SettingsApplication;
import com.android.settings.homepage.SettingsHomepageActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SearchStateReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (FeatureFlagUtils.isEnabled(context, "settings_search_always_expand")) {}
        if (intent == null) {
            Log.w("SearchStateReceiver", "Null intent");
            return;
        }
        SettingsHomepageActivity homeActivity =
                ((SettingsApplication) context.getApplicationContext()).getHomeActivity();
        if (homeActivity == null) {
            return;
        }
        String action = intent.getAction();
        Log.d("SearchStateReceiver", "action: " + action);
        action.getClass();
        switch (action) {
            case "com.android.settings.SEARCH_START":
                homeActivity.mMainFragment.setMenuHighlightShowed(false);
                break;
            case "com.android.settings.SEARCH_EXIT":
                homeActivity.mMainFragment.setMenuHighlightShowed(true);
                break;
            case "com.samsung.android.settings.CLEAR_SEARCH_FOCUS":
                homeActivity.mMainFragment.setHighlightPreferenceKey(null);
                break;
        }
    }
}
