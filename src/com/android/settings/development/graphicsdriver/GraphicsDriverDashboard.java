package com.android.settings.development.graphicsdriver;

import android.content.Context;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.development.DeveloperOptionAwareMixin;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.MainSwitchBarController;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.development.DevelopmentSettingsEnabler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class GraphicsDriverDashboard extends DashboardFragment
        implements DeveloperOptionAwareMixin {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.graphics_driver_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.development.graphicsdriver.GraphicsDriverDashboard$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(context);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "GraphicsDriverDashboard";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1613;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.graphics_driver_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
        getSettingsLifecycle()
                .addObserver(
                        new GraphicsDriverGlobalSwitchBarController(
                                settingsActivity,
                                new MainSwitchBarController(settingsMainSwitchBar)));
        settingsMainSwitchBar.setTitle(
                getContext().getString(R.string.graphics_driver_main_switch_title));
        settingsMainSwitchBar.show();
    }
}
