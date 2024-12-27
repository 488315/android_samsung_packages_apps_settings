package com.android.settings.bluetooth;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.ObservableActivity;

import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.util.ThemeHelper;
import com.google.android.setupdesign.util.ThemeResolver;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class QrCodeScanModeActivity extends ObservableActivity {
    public FragmentManagerImpl mFragmentManager;

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        onCreate$com$android$settings$bluetooth$QrCodeScanModeBaseActivity(bundle);
    }

    public final void onCreate$com$android$settings$bluetooth$QrCodeScanModeBaseActivity(
            Bundle bundle) {
        super.onCreate(bundle);
        Logger logger = ThemeHelper.LOG;
        int i = PartnerConfigHelper.isSetupWizardDayNightEnabled(this) ? 2132083865 : 2132083866;
        if (ThemeResolver.defaultResolver == null) {
            ThemeResolver.defaultResolver = new ThemeResolver(2132083859, true);
        }
        int i2 = ThemeResolver.defaultResolver.defaultTheme;
        setTheme(
                new ThemeResolver(i, true)
                        .resolve(
                                SystemProperties.get(
                                        "setupwizard.theme", "SudThemeGlifV3_DayNight"),
                                true ^ PartnerConfigHelper.isSetupWizardDayNightEnabled(this)));
        setContentView(R.layout.qrcode_scan_mode_activity);
        this.mFragmentManager = getSupportFragmentManager();
        if (bundle == null) {
            Intent intent = getIntent();
            finish();
            String action = intent != null ? intent.getAction() : null;
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "handleIntent(), action = ", action, "QrCodeScanModeActivity");
            if (action == null) {
                finish();
                return;
            }
            if (!action.equals("android.settings.BLUETOOTH_LE_AUDIO_QR_CODE_SCANNER")) {
                Log.e("QrCodeScanModeActivity", "Launch with an invalid action");
                finish();
                return;
            }
            if (intent == null) {
                Log.d(
                        "QrCodeScanModeActivity",
                        "intent is null, can not get bluetooth information from intent.");
                return;
            }
            Log.d("QrCodeScanModeActivity", "showQrCodeScannerFragment");
            intent.getBooleanExtra("bluetooth_sink_is_group", false);
            Log.d("QrCodeScanModeActivity", "get extra from intent");
            QrCodeScanModeFragment qrCodeScanModeFragment =
                    (QrCodeScanModeFragment)
                            this.mFragmentManager.findFragmentByTag("qr_code_scanner_fragment");
            if (qrCodeScanModeFragment != null) {
                if (qrCodeScanModeFragment.isVisible()) {
                    return;
                }
                this.mFragmentManager.popBackStackImmediate();
            } else {
                QrCodeScanModeFragment qrCodeScanModeFragment2 = new QrCodeScanModeFragment();
                FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
                fragmentManagerImpl.getClass();
                BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
                backStackRecord.replace(
                        R.id.fragment_container,
                        qrCodeScanModeFragment2,
                        "qr_code_scanner_fragment");
                backStackRecord.commitInternal(false);
            }
        }
    }
}
