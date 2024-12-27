package com.android.settings.biometrics.combination;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CombinedBiometricProfileSettings extends BiometricsSettingsBase {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new CombinedBiometricSearchIndexProvider(
                    R.xml.security_settings_combined_biometric_profile);

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getFacePreferenceKey() {
        return "biometric_face_settings_profile";
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getFingerprintPreferenceKey() {
        return "biometric_fingerprint_settings_profile";
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BiometricProfileSetting";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1879;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.security_settings_combined_biometric_profile;
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getUnlockPhonePreferenceKey() {
        return "biometric_settings_biometric_keyguard_profile";
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getUseInAppsPreferenceKey() {
        return "biometric_settings_biometric_app_profile";
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((BiometricSettingsAppPreferenceController)
                        use(BiometricSettingsAppPreferenceController.class))
                .setUserId(this.mUserId);
    }
}
