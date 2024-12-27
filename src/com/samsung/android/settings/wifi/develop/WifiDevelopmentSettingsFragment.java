package com.samsung.android.settings.wifi.develop;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiDevelopmentSettingsFragment extends DashboardFragment {
    public SemWifiManager mSemWifiManager;
    public PreferenceScreen pktCapturePrefScreen;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiDevSettingsDashboard";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_development_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.pktCapturePrefScreen = getPreferenceScreen();
        if (this.mSemWifiManager.getIsPacketCaptureSupportedByDriver()) {
            return;
        }
        Log.d("WifiDevSettingsDashboard", "Packet Capture is not supported in this device model.");
        PreferenceScreen preferenceScreen = this.pktCapturePrefScreen;
        if (preferenceScreen == null) {
            Log.e("WifiDevSettingsDashboard", "Failed to get the preference screen object.");
            return;
        }
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference("pktCaptureCategory");
        if (preferenceCategory != null) {
            this.pktCapturePrefScreen.removePreference(preferenceCategory);
            Log.d(
                    "WifiDevSettingsDashboard",
                    "Successfully removed packet capture preference category from preference"
                        + " screen.");
        }
    }
}
