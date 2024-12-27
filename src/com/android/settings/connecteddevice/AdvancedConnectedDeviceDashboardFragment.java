package com.android.settings.connecteddevice;

import android.content.Context;
import android.provider.SearchIndexableResource;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.print.PrintSettingPreferenceController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.uwb.UwbPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AdvancedConnectedDeviceDashboardFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.connecteddevice.AdvancedConnectedDeviceDashboardFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    AdvancedConnectedDeviceDashboardFragment.SEARCH_INDEX_DATA_PROVIDER;
            ArrayList arrayList = new ArrayList();
            arrayList.add(new PrintSettingPreferenceController(context));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.connected_devices_advanced;
            return Arrays.asList(searchIndexableResource);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        PrintSettingPreferenceController printSettingPreferenceController =
                new PrintSettingPreferenceController(context);
        if (settingsLifecycle != null) {
            settingsLifecycle.addObserver(printSettingPreferenceController);
        }
        arrayList.add(printSettingPreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AdvancedConnectedDeviceFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1264;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.connected_devices_advanced;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        UwbPreferenceController uwbPreferenceController =
                (UwbPreferenceController) use(UwbPreferenceController.class);
        if (uwbPreferenceController == null
                || !uwbPreferenceController.isUwbSupportedOnDevice()
                || getSettingsLifecycle() == null) {
            return;
        }
        getSettingsLifecycle().addObserver(uwbPreferenceController);
    }
}
