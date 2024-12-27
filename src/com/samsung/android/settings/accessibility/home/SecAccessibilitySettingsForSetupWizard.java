package com.samsung.android.settings.accessibility.home;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilitySettingsForSetupWizard;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAccessibilitySettingsForSetupWizard extends AccessibilitySettingsForSetupWizard {
    @Override // com.android.settings.accessibility.AccessibilitySettingsForSetupWizard,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.NON_STANDARD_ERROR_CODE_BASE_CALL;
    }

    @Override // com.android.settings.accessibility.AccessibilitySettingsForSetupWizard,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_accessibility_settings_for_setup_wizard;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        enableAutoFlowLogging(false);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getA11ySettingsMetricsFeatureProvider();
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        this.mMetricsFeatureProvider.visible(
                getPrefContext(), 0, VolteConstants.ErrorCode.NON_STANDARD_ERROR_CODE_BASE_CALL, 0);
        super.onResume();
    }
}
