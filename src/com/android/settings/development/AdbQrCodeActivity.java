package com.android.settings.development;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settings.wifi.dpp.WifiDppBaseActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AdbQrCodeActivity extends WifiDppBaseActivity {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1831;
    }

    @Override // com.android.settings.wifi.dpp.WifiDppBaseActivity,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AdbQrcodeScannerFragment adbQrcodeScannerFragment =
                (AdbQrcodeScannerFragment)
                        this.mFragmentManager.findFragmentByTag("adb_qr_code_scanner_fragment");
        if (adbQrcodeScannerFragment != null) {
            if (adbQrcodeScannerFragment.isVisible()) {
                return;
            }
            this.mFragmentManager.popBackStackImmediate();
        } else {
            AdbQrcodeScannerFragment adbQrcodeScannerFragment2 = new AdbQrcodeScannerFragment();
            FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
            fragmentManagerImpl.getClass();
            BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
            backStackRecord.replace(
                    R.id.fragment_container,
                    adbQrcodeScannerFragment2,
                    "adb_qr_code_scanner_fragment");
            backStackRecord.commitInternal(false);
        }
    }

    @Override // com.android.settings.wifi.dpp.WifiDppBaseActivity
    public final void handleIntent(Intent intent) {}
}
