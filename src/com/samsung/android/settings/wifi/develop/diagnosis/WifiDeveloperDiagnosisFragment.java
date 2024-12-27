package com.samsung.android.settings.wifi.develop.diagnosis;

import android.os.Bundle;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentManager;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.settings.wifi.develop.OnTabSelectedListener;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiDeveloperDiagnosisFragment extends SettingsPreferenceFragment {
    public NetworkDiagnosisSettings mNetworkDiagnosisFragment;
    public WifiDiagnosisSettings mWifiDiagnosisFragment;

    public static void addFragment(
            BackStackRecord backStackRecord,
            SettingsPreferenceFragment settingsPreferenceFragment,
            String str) {
        if (settingsPreferenceFragment == null) {
            backStackRecord.doAddOp(
                    R.id.tabcontent,
                    str.equals("Wi-Fi Diagnosis")
                            ? new WifiDiagnosisSettings()
                            : new NetworkDiagnosisSettings(),
                    str,
                    1);
        } else {
            backStackRecord.attach(settingsPreferenceFragment);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_develop_diagnosis_main_fragment;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        DiagnosisTabPreference diagnosisTabPreference =
                (DiagnosisTabPreference) findPreference("diagnosis_tab_preference");
        if (diagnosisTabPreference != null) {
            diagnosisTabPreference.mOnTabSelectedListener =
                    new OnTabSelectedListener() { // from class:
                                                  // com.samsung.android.settings.wifi.develop.diagnosis.WifiDeveloperDiagnosisFragment$$ExternalSyntheticLambda0
                        @Override // com.samsung.android.settings.wifi.develop.OnTabSelectedListener
                        public final void onTabSelected(int i) {
                            WifiDeveloperDiagnosisFragment wifiDeveloperDiagnosisFragment =
                                    WifiDeveloperDiagnosisFragment.this;
                            FragmentManager childFragmentManager =
                                    wifiDeveloperDiagnosisFragment.getChildFragmentManager();
                            BackStackRecord m =
                                    DialogFragment$$ExternalSyntheticOutline0.m(
                                            childFragmentManager, childFragmentManager);
                            wifiDeveloperDiagnosisFragment.mWifiDiagnosisFragment =
                                    (WifiDiagnosisSettings)
                                            childFragmentManager.findFragmentByTag(
                                                    "Wi-Fi Diagnosis");
                            wifiDeveloperDiagnosisFragment.mNetworkDiagnosisFragment =
                                    (NetworkDiagnosisSettings)
                                            childFragmentManager.findFragmentByTag(
                                                    "Network Diagnosis");
                            WifiDiagnosisSettings wifiDiagnosisSettings =
                                    wifiDeveloperDiagnosisFragment.mWifiDiagnosisFragment;
                            if (wifiDiagnosisSettings != null) {
                                m.detach(wifiDiagnosisSettings);
                            }
                            NetworkDiagnosisSettings networkDiagnosisSettings =
                                    wifiDeveloperDiagnosisFragment.mNetworkDiagnosisFragment;
                            if (networkDiagnosisSettings != null) {
                                m.detach(networkDiagnosisSettings);
                            }
                            if (i == 0) {
                                WifiDeveloperDiagnosisFragment.addFragment(
                                        m,
                                        wifiDeveloperDiagnosisFragment.mWifiDiagnosisFragment,
                                        "Wi-Fi Diagnosis");
                            } else if (i == 1) {
                                WifiDeveloperDiagnosisFragment.addFragment(
                                        m,
                                        wifiDeveloperDiagnosisFragment.mNetworkDiagnosisFragment,
                                        "Network Diagnosis");
                            }
                            m.commitInternal(false);
                        }
                    };
        }
    }
}
