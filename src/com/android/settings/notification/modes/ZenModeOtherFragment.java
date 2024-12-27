package com.android.settings.notification.modes;

import android.content.Context;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeOtherFragment extends ZenModeFragmentBase {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new ZenModeOtherPreferenceController(
                        context, "modes_category_alarm", this.mBackend));
        arrayList.add(
                new ZenModeOtherPreferenceController(
                        context, "modes_category_media", this.mBackend));
        arrayList.add(
                new ZenModeOtherPreferenceController(
                        context, "modes_category_system", this.mBackend));
        arrayList.add(
                new ZenModeOtherPreferenceController(
                        context, "modes_category_reminders", this.mBackend));
        arrayList.add(
                new ZenModeOtherPreferenceController(
                        context, "modes_category_events", this.mBackend));
        return arrayList;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 141;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.modes_other_settings;
    }
}
