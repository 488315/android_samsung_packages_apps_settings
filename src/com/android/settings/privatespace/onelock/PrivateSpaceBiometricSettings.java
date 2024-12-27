package com.android.settings.privatespace.onelock;

import android.content.Context;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.biometrics.combination.BiometricsSettingsBase;
import com.android.settings.privatespace.PrivateSpaceMaintainer;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceBiometricSettings extends BiometricsSettingsBase {
    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getFacePreferenceKey() {
        return "private_space_face_unlock_settings";
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getFingerprintPreferenceKey() {
        return "private_space_fingerprint_unlock_settings";
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final String getLogTag() {
        return "PSBiometricSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2040;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.private_space_biometric_settings;
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getUnlockPhonePreferenceKey() {
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getUseInAppsPreferenceKey() {
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        if (Flags.allowPrivateProfile()
                && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace()
                && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            super.onAttach(context);
            UserHandle privateProfileHandle =
                    PrivateSpaceMaintainer.getInstance(context).getPrivateProfileHandle();
            if (privateProfileHandle != null) {
                this.mUserId = privateProfileHandle.getIdentifier();
            } else {
                this.mUserId = -1;
                Log.e(
                        "PSBiometricSettings",
                        "Private profile user handle is not expected to be null.");
            }
        }
    }
}
