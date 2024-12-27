package com.android.settings.gestures;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.dashboard.suggestions.SuggestionFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DoubleTapPowerSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.double_tap_power_settings);

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DoubleTapPower";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 752;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.double_tap_power_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getSuggestionFeatureProvider().getClass();
        SuggestionFeatureProviderImpl.getSharedPrefs(context)
                .edit()
                .putBoolean("pref_double_tap_power_suggestion_complete", true)
                .apply();
    }
}