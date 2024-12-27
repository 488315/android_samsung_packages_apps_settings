package com.android.settings.notification.modes;

import android.app.Application;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.search.BaseSearchIndexProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeSelectBypassingAppsFragment extends ZenModeFragmentBase {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.zen_mode_select_bypassing_apps);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.modes.ZenModeSelectBypassingAppsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    ZenModeSelectBypassingAppsFragment.SEARCH_INDEX_DATA_PROVIDER;
            ArrayList arrayList = new ArrayList();
            arrayList.add(
                    new ZenModeAllBypassingAppsPreferenceController(context, null, null, null));
            arrayList.add(
                    new ZenModeAddBypassingAppsPreferenceController(context, null, null, null));
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        FragmentActivity activity = getActivity();
        Application application = activity != null ? activity.getApplication() : null;
        NotificationBackend notificationBackend = new NotificationBackend();
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new ZenModeAllBypassingAppsPreferenceController(
                        context, application, this, notificationBackend));
        arrayList.add(
                new ZenModeAddBypassingAppsPreferenceController(
                        context, application, this, notificationBackend));
        return arrayList;
    }

    @Override // com.android.settings.notification.modes.ZenModesFragmentBase,
              // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ZenBypassingApps";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1588;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_select_bypassing_apps;
    }
}
