package com.android.settings.display;

import android.content.Context;

import androidx.lifecycle.Lifecycle;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.google.common.collect.ImmutableList;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DeviceStateAutoRotateDetailsFragment extends DashboardFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.device_state_auto_rotate_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.display.DeviceStateAutoRotateDetailsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ImmutableList createPreferenceControllers =
                    DeviceStateAutoRotationHelper.createPreferenceControllers(context);
            ArrayList arrayList = new ArrayList();
            ImmutableList.Itr listIterator = createPreferenceControllers.listIterator(0);
            while (listIterator.hasNext()) {
                ((BasePreferenceController) ((AbstractPreferenceController) listIterator.next()))
                        .updateRawDataToIndex(arrayList);
            }
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return DeviceStateAutoRotationHelper.createPreferenceControllers(context);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DeviceStateAutoRotateDetailsFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_TIMER_F;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.device_state_auto_rotate_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        Lifecycle lifecycle = getLifecycle();
        Iterator it = useAll(DeviceStateAutoRotateSettingController.class).iterator();
        while (it.hasNext()) {
            ((DeviceStateAutoRotateSettingController) it.next()).init(lifecycle);
        }
    }
}
