package com.android.settings.notification.app;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.settings.notification.app.BubblePrefSecInsetCategoryPreferenceController;
import com.samsung.android.settings.notification.app.NotificationsOnSecInsetCategoryNoStrokePreferenceController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppChannelListSettings extends NotificationSettings {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ((NotificationSettings) this).mControllers = arrayList;
        arrayList.add(new HeaderPreferenceController(context, this));
        ((NotificationSettings) this)
                .mControllers.add(
                        new NotificationsOnSecInsetCategoryNoStrokePreferenceController(
                                context, null));
        ((NotificationSettings) this)
                .mControllers.add(
                        new AllowSoundPreferenceController(
                                context, this.mBackend, this.mDependentFieldListener));
        ((NotificationSettings) this)
                .mControllers.add(
                        new BubblePrefSecInsetCategoryPreferenceController(context, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(new BubbleSummaryPreferenceController(context, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(new ChannelListPreferenceController(context, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(
                        new AppConversationListPreferenceController(context, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(
                        new InvalidConversationInfoPreferenceController(context, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(
                        new InvalidConversationPreferenceController(context, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(new DeletedChannelsPreferenceController(context, this.mBackend));
        return new ArrayList(((NotificationSettings) this).mControllers);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AppChannelListSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7309;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_app_channel_notification_settings;
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
            Log.w("AppChannelListSettings", "Missing package or uid or packageinfo");
            finish();
            return;
        }
        Log.d("AppChannelListSettings", "on resume");
        Iterator it = ((ArrayList) ((NotificationSettings) this).mControllers).iterator();
        while (it.hasNext()) {
            NotificationPreferenceController notificationPreferenceController =
                    (NotificationPreferenceController) it.next();
            notificationPreferenceController.onResume(
                    this.mAppRow,
                    this.mChannel,
                    this.mChannelGroup,
                    null,
                    null,
                    this.mSuspendedAppsAdmin,
                    this.mPreferenceFilter);
            notificationPreferenceController.displayPreference(getPreferenceScreen());
        }
        updatePreferenceStates();
        animatePanel();
    }
}
