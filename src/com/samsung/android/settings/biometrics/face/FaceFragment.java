package com.samsung.android.settings.biometrics.face;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FaceFragment extends SettingsPreferenceFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8400;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("FcstFaceFragment", "==onCreate()");
        Intent intent = new Intent();
        intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, FaceEntry.class.getName());
        intent.putExtra("from_biometric_fragment", true);
        startActivity(intent);
        finishFragment();
    }
}
