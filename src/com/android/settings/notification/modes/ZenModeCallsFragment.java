package com.android.settings.notification.modes;

import android.R;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeCallsFragment extends ZenModeFragmentBase {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new ZenModePrioritySendersPreferenceController(
                        context, "zen_mode_settings_category_calls", false, this.mBackend));
        arrayList.add(
                new ZenModeRepeatCallersPreferenceController(
                        context,
                        this.mBackend,
                        context.getResources()
                                .getInteger(
                                        R.integer.leanback_setup_alpha_backward_in_content_delay)));
        return arrayList;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1838;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return com.android.settings.R.xml.modes_calls_settings;
    }

    @Override // com.android.settings.notification.modes.ZenModesFragmentBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        ZenModePrioritySendersPreferenceController zenModePrioritySendersPreferenceController =
                (ZenModePrioritySendersPreferenceController)
                        use(ZenModePrioritySendersPreferenceController.class);
        if (zenModePrioritySendersPreferenceController.mIsMessages) {
            zenModePrioritySendersPreferenceController.updateChannelCounts$2();
        }
        zenModePrioritySendersPreferenceController.updateSummaries$1();
    }
}
