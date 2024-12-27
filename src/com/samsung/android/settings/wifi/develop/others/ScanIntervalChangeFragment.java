package com.samsung.android.settings.wifi.develop.others;

import android.content.om.IOverlayManager;
import android.os.Bundle;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ScanIntervalChangeFragment extends SettingsPreferenceFragment {
    public View mContentView;
    public IOverlayManager mOverlayManager;
    public AnonymousClass1 mRadiobuttonChangeListener =
            new RadioGroup
                    .OnCheckedChangeListener() { // from class:
                                                 // com.samsung.android.settings.wifi.develop.others.ScanIntervalChangeFragment.1
                @Override // android.widget.RadioGroup.OnCheckedChangeListener
                public final void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.scan_short) {
                        ScanIntervalChangeFragment.m1339$$Nest$msetResourceOverlayForScan(
                                ScanIntervalChangeFragment.this, true, false);
                        Settings.Global.putInt(
                                ScanIntervalChangeFragment.this.getContentResolver(),
                                "sem_wifi_settings_framework_scan_interval",
                                1);
                    } else if (i == R.id.scan_default) {
                        Settings.Global.putInt(
                                ScanIntervalChangeFragment.this.getContentResolver(),
                                "sem_wifi_settings_framework_scan_interval",
                                0);
                        ScanIntervalChangeFragment.m1339$$Nest$msetResourceOverlayForScan(
                                ScanIntervalChangeFragment.this, false, false);
                    } else {
                        Settings.Global.putInt(
                                ScanIntervalChangeFragment.this.getContentResolver(),
                                "sem_wifi_settings_framework_scan_interval",
                                2);
                        ScanIntervalChangeFragment.m1339$$Nest$msetResourceOverlayForScan(
                                ScanIntervalChangeFragment.this, false, true);
                    }
                }
            };
    public RadioGroup mScanIntervalRadioGroup;

    /* renamed from: -$$Nest$msetResourceOverlayForScan, reason: not valid java name */
    public static void m1339$$Nest$msetResourceOverlayForScan(
            ScanIntervalChangeFragment scanIntervalChangeFragment, boolean z, boolean z2) {
        scanIntervalChangeFragment.getClass();
        try {
            scanIntervalChangeFragment.mOverlayManager.setEnabled(
                    "com.samsung.android.wifi.decrease.scan.interval.resources", z, 0);
            scanIntervalChangeFragment.mOverlayManager.setEnabled(
                    "com.samsung.android.wifi.increase.scan.interval.resources", z2, 0);
        } catch (Exception e) {
            Log.w("ScanIntervalChange", "Failed to set RRO ", e);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mOverlayManager =
                IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_scan_interval_change, (ViewGroup) null);
        this.mContentView = inflate;
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (this.mRadiobuttonChangeListener != null) {
            this.mRadiobuttonChangeListener = null;
        }
        super.onDestroy();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        RadioGroup radioGroup = (RadioGroup) this.mContentView.findViewById(R.id.scan_radioGroup);
        this.mScanIntervalRadioGroup = radioGroup;
        radioGroup.setOnCheckedChangeListener(this.mRadiobuttonChangeListener);
        int i =
                Settings.Global.getInt(
                        getContentResolver(), "sem_wifi_settings_framework_scan_interval", 0);
        if (i == 1) {
            this.mScanIntervalRadioGroup.check(R.id.scan_short);
        } else if (i == 0) {
            this.mScanIntervalRadioGroup.check(R.id.scan_default);
        } else {
            this.mScanIntervalRadioGroup.check(R.id.scan_long);
        }
    }
}
