package com.samsung.android.settings.display.controller;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.secutil.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecScrollFilterForChromePreferenceController
        extends SecDisplayPreferenceBaseController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public Context mContext;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "sem_scroll_filter_for_chrome";
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final ArrayList getUriListRequiringObservation() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        try {
            this.mContext
                    .getPackageManager()
                    .getPackageInfo("com.android.chrome", 0)
                    .getLongVersionCode();
        } catch (Exception e) {
            Log.secE(
                    "PrefControllerMixin", "Error while calling packageInfo.getLongVersionCode", e);
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        ((Boolean) obj).getClass();
        ActivityManager activityManager =
                (ActivityManager) this.mContext.getSystemService("activity");
        List<ActivityManager.RecentTaskInfo> recentTasks = activityManager.getRecentTasks(15, 1);
        int size = recentTasks.size();
        for (int i = 0; i < size; i++) {
            ActivityManager.RecentTaskInfo recentTaskInfo = recentTasks.get(i);
            ComponentName component = recentTaskInfo.baseIntent.getComponent();
            if ((component != null
                            ? component.getPackageName()
                            : ApnSettings.MVNO_NONE + recentTaskInfo.baseIntent.getPackage())
                    .contains("com.android.chrome")) {
                activityManager.semRemoveTask(recentTaskInfo.persistentId, 4);
            }
        }
        return true;
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final void updatePreference$11() {}
}
