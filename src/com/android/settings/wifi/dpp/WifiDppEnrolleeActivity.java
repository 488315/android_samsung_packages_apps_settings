package com.android.settings.wifi.dpp;

import android.content.Intent;
import android.util.EventLog;
import android.util.Log;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settingslib.wifi.WifiRestrictionsCache;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiDppEnrolleeActivity extends WifiDppBaseActivity
        implements WifiDppQrCodeScannerFragment.OnScanWifiDppSuccessListener {
    protected WifiRestrictionsCache mWifiRestrictionsCache;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1596;
    }

    @Override // com.android.settings.wifi.dpp.WifiDppBaseActivity
    public final void handleIntent(Intent intent) {
        String action = intent != null ? intent.getAction() : null;
        if (action == null) {
            finish();
            return;
        }
        if (this.mWifiRestrictionsCache == null) {
            this.mWifiRestrictionsCache =
                    WifiRestrictionsCache.getInstance(getApplicationContext());
        }
        if (!this.mWifiRestrictionsCache.isConfigWifiAllowed().booleanValue()) {
            Log.e("WifiDppEnrolleeActivity", "The user is not allowed to configure Wi-Fi.");
            finish();
            EventLog.writeEvent(
                    1397638484,
                    "202017876",
                    Integer.valueOf(getApplicationContext().getUserId()),
                    "The user is not allowed to configure Wi-Fi.");
        } else if (action.equals("android.settings.WIFI_DPP_ENROLLEE_QR_CODE_SCANNER")) {
            showQrCodeScannerFragment(intent.getStringExtra("ssid"));
        } else {
            Log.e("WifiDppEnrolleeActivity", "Launch with an invalid action");
            finish();
        }
    }

    public void showQrCodeScannerFragment(String str) {
        WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment =
                (WifiDppQrCodeScannerFragment)
                        this.mFragmentManager.findFragmentByTag("qr_code_scanner_fragment");
        if (wifiDppQrCodeScannerFragment != null) {
            if (wifiDppQrCodeScannerFragment.isVisible()) {
                return;
            }
            this.mFragmentManager.popBackStackImmediate();
        } else {
            WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment2 =
                    new WifiDppQrCodeScannerFragment(str);
            FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
            fragmentManagerImpl.getClass();
            BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
            backStackRecord.replace(
                    R.id.fragment_container,
                    wifiDppQrCodeScannerFragment2,
                    "qr_code_scanner_fragment");
            backStackRecord.commitInternal(false);
        }
    }

    @Override // com.android.settings.wifi.dpp.WifiDppQrCodeScannerFragment.OnScanWifiDppSuccessListener
    public final void onScanWifiDppSuccess(WifiQrCode wifiQrCode) {}
}
