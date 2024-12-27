package com.samsung.android.settings.biometrics.fingerprint;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintFragment extends SettingsPreferenceFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8214;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("FpstFingerprintFragment", "==onCreate()");
        FingerprintManager fingerprintManagerOrNull =
                Utils.getFingerprintManagerOrNull(getActivity());
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, FingerprintEntry.class.getName());
        if (fingerprintManagerOrNull != null) {
            intent.putExtra("sensor_status", fingerprintManagerOrNull.semGetSensorStatus());
        }
        intent.putExtra("from_biometric_fragment", true);
        startActivity(intent);
        finishFragment();
    }
}
