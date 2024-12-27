package com.samsung.android.settings.notification.app;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.notification.app.AppLinkPreferenceController;
import com.android.settings.notification.app.BlockPreferenceController;
import com.android.settings.notification.app.DescriptionPreferenceController;
import com.android.settings.notification.app.HeaderPreferenceController;
import com.android.settings.notification.app.NotificationSettings;
import com.android.settings.notification.app.NotificationsOffPreferenceController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ChannelGroupNotificationSettings extends NotificationSettings {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ((NotificationSettings) this).mControllers = arrayList;
        arrayList.add(new HeaderPreferenceController(context, this));
        ((NotificationSettings) this)
                .mControllers.add(
                        new BlockPreferenceController(
                                context, this.mDependentFieldListener, this.mBackend, false));
        ((NotificationSettings) this).mControllers.add(new AppLinkPreferenceController(context));
        ((NotificationSettings) this)
                .mControllers.add(new NotificationsOffPreferenceController(context, null));
        ((NotificationSettings) this)
                .mControllers.add(new DescriptionPreferenceController(context, null));
        ((NotificationSettings) this)
                .mControllers.add(new SecMoreSettingPreferenceController(context, null));
        ((NotificationSettings) this)
                .mControllers.add(
                        new NotificationsOnSecInsetCategoryNoStrokePreferenceController(
                                context, null));
        ((NotificationSettings) this)
                .mControllers.add(
                        new NotificationsOnSecInsetCategoryOnePreferenceController(context, null));
        return new ArrayList(((NotificationSettings) this).mControllers);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ChannelGroupSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1218;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_notification_group_settings;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0140  */
    @Override // com.android.settings.notification.app.NotificationSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.notification.app.ChannelGroupNotificationSettings.onResume():void");
    }
}
