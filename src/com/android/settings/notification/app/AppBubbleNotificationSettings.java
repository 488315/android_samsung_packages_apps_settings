package com.android.settings.notification.app;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.android.settings.R;
import com.android.settings.notification.AppBubbleListPreferenceController;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.notification.AllRadioBubblePreferenceController;
import com.samsung.android.settings.notification.NoneRadioBubblePreferenceController;
import com.samsung.android.settings.notification.SelectedRadioBubblePreferenceController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppBubbleNotificationSettings extends NotificationSettings
        implements GlobalBubblePermissionObserverMixin.Listener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public GlobalBubblePermissionObserverMixin mObserverMixin;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.app.AppBubbleNotificationSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return new ArrayList(
                    AppBubbleNotificationSettings.getPreferenceControllers(context, null, null));
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return false;
        }
    }

    public static List getPreferenceControllers(
            Context context,
            AppBubbleNotificationSettings appBubbleNotificationSettings,
            NotificationSettings.DependentFieldListener dependentFieldListener) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new HeaderPreferenceController(context, appBubbleNotificationSettings));
        arrayList.add(new AppBubbleListPreferenceController(context, new NotificationBackend()));
        FragmentManager childFragmentManager =
                appBubbleNotificationSettings != null
                        ? appBubbleNotificationSettings.getChildFragmentManager()
                        : null;
        AllRadioBubblePreferenceController allRadioBubblePreferenceController =
                new AllRadioBubblePreferenceController(context, new NotificationBackend());
        allRadioBubblePreferenceController.mListener = dependentFieldListener;
        allRadioBubblePreferenceController.mFragmentManager = childFragmentManager;
        allRadioBubblePreferenceController.mIsAppPage = true;
        arrayList.add(allRadioBubblePreferenceController);
        SelectedRadioBubblePreferenceController selectedRadioBubblePreferenceController =
                new SelectedRadioBubblePreferenceController(context, new NotificationBackend());
        selectedRadioBubblePreferenceController.mListener = dependentFieldListener;
        selectedRadioBubblePreferenceController.mFragmentManager = childFragmentManager;
        selectedRadioBubblePreferenceController.mIsAppPage = true;
        arrayList.add(selectedRadioBubblePreferenceController);
        NoneRadioBubblePreferenceController noneRadioBubblePreferenceController =
                new NoneRadioBubblePreferenceController(context, new NotificationBackend());
        noneRadioBubblePreferenceController.mListener = dependentFieldListener;
        noneRadioBubblePreferenceController.mIsAppPage = true;
        arrayList.add(noneRadioBubblePreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ((NotificationSettings) this).mControllers =
                getPreferenceControllers(context, this, this.mDependentFieldListener);
        return new ArrayList(((NotificationSettings) this).mControllers);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AppBubNotiSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1700;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_app_bubble_notification_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        GlobalBubblePermissionObserverMixin globalBubblePermissionObserverMixin =
                this.mObserverMixin;
        globalBubblePermissionObserverMixin
                .mContext
                .getContentResolver()
                .unregisterContentObserver(globalBubblePermissionObserverMixin);
        super.onPause();
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
            Log.w("AppBubNotiSettings", "Missing package or uid or packageinfo");
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
        GlobalBubblePermissionObserverMixin globalBubblePermissionObserverMixin =
                new GlobalBubblePermissionObserverMixin(getContext(), this);
        this.mObserverMixin = globalBubblePermissionObserverMixin;
        globalBubblePermissionObserverMixin
                .mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("notification_bubbles"),
                        false,
                        globalBubblePermissionObserverMixin);
    }
}
