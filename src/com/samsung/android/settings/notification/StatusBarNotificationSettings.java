package com.samsung.android.settings.notification;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class StatusBarNotificationSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_notification_statusbar_settings);
    public Handler mHandler;
    public SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.StatusBarNotificationSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!Utils.isTablet()) {
                ((ArrayList) nonIndexableKeys).add("show_date");
            }
            ((ArrayList) nonIndexableKeys).add("status_bar_notification_style_dot_description");
            return nonIndexableKeys;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri STATUS_BAR_NOTIFICATION_STYLE;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.STATUS_BAR_NOTIFICATION_STYLE = Settings.System.getUriFor("simple_status_bar");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            StatusBarNotificationSettings statusBarNotificationSettings =
                    StatusBarNotificationSettings.this;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    StatusBarNotificationSettings.SEARCH_INDEX_DATA_PROVIDER;
            statusBarNotificationSettings.updateVisible$2();
        }

        public final void setListening(boolean z) {
            if (z) {
                StatusBarNotificationSettings.this
                        .getContext()
                        .getContentResolver()
                        .registerContentObserver(this.STATUS_BAR_NOTIFICATION_STYLE, false, this);
            } else {
                StatusBarNotificationSettings.this
                        .getContext()
                        .getContentResolver()
                        .unregisterContentObserver(this);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getApplication();
        }
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new StatusBarNotificationStyleController(context, "status_bar_notification_style"));
        arrayList.add(
                new StatusBarBatteryPercentageController(
                        context, "show_battery_percent", settingsLifecycle));
        arrayList.add(new StatusBarNetworkSpeedController(context, "network_speed"));
        arrayList.add(new StatusBarShowNetworkInfoController(context, "show_network_info"));
        arrayList.add(new StatusBarShowDateController(context, "show_date", settingsLifecycle));
        arrayList.add(
                new StatusBarNotificationStyleDotDescriptionController(
                        context, "status_bar_notification_style_dot_description"));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "StatusBarNotificationSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_notification_statusbar_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getPreferenceScreen() == null) {
            return;
        }
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSettingsObserver.setListening(true);
        updateVisible$2();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mSettingsObserver.setListening(false);
    }

    public final void updateVisible$2() {
        Iterator<List<AbstractPreferenceController>> it = getPreferenceControllers().iterator();
        while (it.hasNext()) {
            for (AbstractPreferenceController abstractPreferenceController : it.next()) {
                if (abstractPreferenceController
                        instanceof StatusBarNotificationStyleDotDescriptionController) {
                    ((StatusBarNotificationStyleDotDescriptionController)
                                    abstractPreferenceController)
                            .updateVisible();
                }
            }
        }
    }
}
