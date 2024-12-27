package com.android.settings.notification.app;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppChannelsBypassingDndSettings extends NotificationSettings {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ((NotificationSettings) this).mControllers = arrayList;
        arrayList.add(new HeaderPreferenceController(context, this));
        List list = ((NotificationSettings) this).mControllers;
        AppChannelsBypassingDndPreferenceController appChannelsBypassingDndPreferenceController =
                new AppChannelsBypassingDndPreferenceController(context, new NotificationBackend());
        appChannelsBypassingDndPreferenceController.mChannels = new ArrayList();
        appChannelsBypassingDndPreferenceController.mDuplicateChannelNames = new HashSet();
        appChannelsBypassingDndPreferenceController.mChannelGroupNames = new HashMap();
        list.add(appChannelsBypassingDndPreferenceController);
        return new ArrayList(((NotificationSettings) this).mControllers);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AppChannelsBypassingDndSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1840;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.app_channels_bypassing_dnd_settings;
    }

    @Override // com.android.settings.notification.app.NotificationSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mUid < 0 || TextUtils.isEmpty(this.mPkg) || this.mPkgInfo == null) {
            Log.w("AppChannelsBypassingDndSettings", "Missing package or uid or packageinfo");
            finish();
            return;
        }
        Iterator it = ((ArrayList) ((NotificationSettings) this).mControllers).iterator();
        while (it.hasNext()) {
            NotificationPreferenceController notificationPreferenceController =
                    (NotificationPreferenceController) it.next();
            notificationPreferenceController.onResume(
                    this.mAppRow, null, null, null, null, this.mSuspendedAppsAdmin, null);
            notificationPreferenceController.displayPreference(getPreferenceScreen());
        }
        updatePreferenceStates();
    }
}
