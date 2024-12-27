package com.samsung.android.settings.accessibility.advanced;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityControlTimeoutPreferenceFragment;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.accessibility.base.search.AccessibilityBaseSearchIndexProvider;
import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAccessibilityControlTimeoutPreferenceFragment
        extends AccessibilityControlTimeoutPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(
                    R.xml.sec_accessibility_control_timeout_settings,
                    "com.samsung.android.settings.accessibility.advanced.AdvancedSettingsFragment",
                    "accessibility_control_timeout_preference_fragment");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.advanced.SecAccessibilityControlTimeoutPreferenceFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends AccessibilityBaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            if (!isPageSearchEnabled(context)) {
                return arrayList;
            }
            for (AbstractPreferenceController abstractPreferenceController :
                    getPreferenceControllers(context)) {
                if (abstractPreferenceController instanceof BasePreferenceController) {
                    ((BasePreferenceController) abstractPreferenceController)
                            .updateRawDataToIndex(arrayList);
                }
            }
            return arrayList;
        }
    }

    @Override // com.android.settings.accessibility.AccessibilityControlTimeoutPreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.CMC_SD_VT_NOT_SUPPORT;
    }

    @Override // com.android.settings.accessibility.AccessibilityControlTimeoutPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_accessibility_control_timeout_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        enableAutoFlowLogging(false);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getA11ySettingsMetricsFeatureProvider();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        DescriptionPreference descriptionPreference =
                (DescriptionPreference) findPreference("accessibility_control_timeout_description");
        FragmentActivity activity = getActivity();
        if (activity == null || descriptionPreference == null) {
            return;
        }
        activity.addOnConfigurationChangedListener(
                new SecAccessibilityControlTimeoutPreferenceFragment$$ExternalSyntheticLambda0(
                        descriptionPreference));
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.mMetricsFeatureProvider.clicked(
                    VolteConstants.ErrorCode.CMC_SD_VT_NOT_SUPPORT, "A11Y0001");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mMetricsFeatureProvider.visible(
                null, 0, VolteConstants.ErrorCode.CMC_SD_VT_NOT_SUPPORT, 0);
    }
}
