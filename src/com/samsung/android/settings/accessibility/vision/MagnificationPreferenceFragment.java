package com.samsung.android.settings.accessibility.vision;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.settings.R;
import com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MagnificationPreferenceFragment extends ToggleScreenMagnificationPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.magnification);

    @Override // com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "MagnificationPreferenceFragment";
    }

    @Override // com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment,
              // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3180;
    }

    @Override // com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.magnification;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        DescriptionPreference descriptionPreference =
                (DescriptionPreference) findPreference("description_text_preference");
        if (descriptionPreference != null) {
            descriptionPreference.setTitle(
                    getString(R.string.magnification_desc1)
                            + "\n\n"
                            + getString(R.string.magnification_desc2)
                            + "\n• "
                            + getString(R.string.magnification_desc_pinch_to_adjust)
                            + "\n• "
                            + getString(R.string.magnification_desc_swipe_2_fingers));
        }
    }

    @Override // com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment,
              // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mShortcutPreference.setOrder(1);
        return onCreateView;
    }
}
