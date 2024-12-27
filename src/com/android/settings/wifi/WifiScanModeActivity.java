package com.android.settings.wifi;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.wifi.WifiPermissionChecker;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiScanModeActivity extends FragmentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    String mApp;
    public AlertDialogFragment mDialog;
    WifiPermissionChecker mWifiPermissionChecker;

    public void createDialog() {
        UserManager userManager =
                (UserManager) getApplicationContext().getSystemService(UserManager.class);
        if (userManager == null ? false : userManager.isGuestUser()) {
            Log.e(
                    "WifiScanModeActivity",
                    "Guest user is not allowed to configure Wi-Fi Scan Mode!");
            EventLog.writeEvent(1397638484, "235601169", -1, "User is a guest");
            finish();
            return;
        }
        UserManager userManager2 =
                (UserManager) getApplicationContext().getSystemService(UserManager.class);
        if (!(userManager2 != null
                ? true ^ userManager2.hasUserRestriction("no_config_location")
                : true)) {
            Log.e("WifiScanModeActivity", "This user is not allowed to configure Wi-Fi Scan Mode!");
            finish();
        } else if (this.mDialog == null) {
            AlertDialogFragment alertDialogFragment = new AlertDialogFragment(this.mApp);
            this.mDialog = alertDialogFragment;
            alertDialogFragment.show(getSupportFragmentManager(), "dialog");
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        Intent intent = getIntent();
        if (bundle != null) {
            this.mApp = bundle.getString("app");
        } else {
            if (intent == null
                    || !"android.net.wifi.action.REQUEST_SCAN_ALWAYS_AVAILABLE"
                            .equals(intent.getAction())) {
                finish();
                return;
            }
            refreshAppLabel();
        }
        createDialog();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        AlertDialogFragment alertDialogFragment = this.mDialog;
        if (alertDialogFragment != null) {
            alertDialogFragment.dismissInternal(false, false);
            this.mDialog = null;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        createDialog();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("app", this.mApp);
    }

    public void refreshAppLabel() {
        if (this.mWifiPermissionChecker == null) {
            this.mWifiPermissionChecker = new WifiPermissionChecker(this);
        }
        String str = this.mWifiPermissionChecker.mLaunchedPackage;
        if (TextUtils.isEmpty(str)) {
            this.mApp = null;
        } else {
            this.mApp = Utils.getApplicationLabel(getApplicationContext(), str).toString();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class AlertDialogFragment extends InstrumentedDialogFragment {
        public final String mApp;

        public AlertDialogFragment(String str) {
            this.mApp = str;
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 543;
        }

        @Override // androidx.fragment.app.DialogFragment,
                  // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            WifiScanModeActivity wifiScanModeActivity = (WifiScanModeActivity) getActivity();
            int i = WifiScanModeActivity.$r8$clinit;
            wifiScanModeActivity.setResult(0);
            wifiScanModeActivity.finish();
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.P.mMessage =
                    TextUtils.isEmpty(this.mApp)
                            ? getString(R.string.wifi_scan_always_turn_on_message_unknown)
                            : getString(R.string.wifi_scan_always_turnon_message, this.mApp);
            builder.setPositiveButton(
                    R.string.wifi_scan_always_confirm_allow,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.wifi.WifiScanModeActivity.AlertDialogFragment.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            WifiScanModeActivity wifiScanModeActivity =
                                    (WifiScanModeActivity) AlertDialogFragment.this.getActivity();
                            int i2 = WifiScanModeActivity.$r8$clinit;
                            ((WifiManager)
                                            wifiScanModeActivity
                                                    .getApplicationContext()
                                                    .getSystemService(WifiManager.class))
                                    .setScanAlwaysAvailable(true);
                            wifiScanModeActivity.setResult(-1);
                            wifiScanModeActivity.finish();
                        }
                    });
            builder.setNegativeButton(
                    R.string.wifi_scan_always_confirm_deny,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.wifi.WifiScanModeActivity.AlertDialogFragment.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            WifiScanModeActivity wifiScanModeActivity =
                                    (WifiScanModeActivity) AlertDialogFragment.this.getActivity();
                            int i2 = WifiScanModeActivity.$r8$clinit;
                            wifiScanModeActivity.setResult(0);
                            wifiScanModeActivity.finish();
                        }
                    });
            return builder.create();
        }

        public AlertDialogFragment() {
            this.mApp = null;
        }
    }
}
