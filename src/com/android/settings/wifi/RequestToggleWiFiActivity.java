package com.android.settings.wifi;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RequestToggleWiFiActivity extends AlertActivity
        implements DialogInterface.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public CharSequence mAppLabel;
    public WifiManager mWiFiManager;
    public final StateChangeReceiver mReceiver = new StateChangeReceiver();
    public final RequestToggleWiFiActivity$$ExternalSyntheticLambda0 mTimeoutCommand =
            new Runnable() { // from class:
                             // com.android.settings.wifi.RequestToggleWiFiActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RequestToggleWiFiActivity requestToggleWiFiActivity =
                            RequestToggleWiFiActivity.this;
                    int i = RequestToggleWiFiActivity.$r8$clinit;
                    if (requestToggleWiFiActivity.isFinishing()
                            || requestToggleWiFiActivity.isDestroyed()) {
                        return;
                    }
                    requestToggleWiFiActivity.finish();
                }
            };

    @VisibleForTesting protected IActivityManager mActivityManager = ActivityManager.getService();
    public int mState = -1;
    public int mLastUpdateState = -1;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StateChangeReceiver extends BroadcastReceiver {
        public final IntentFilter mFilter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");

        public StateChangeReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            AlertActivity alertActivity = RequestToggleWiFiActivity.this;
            if (alertActivity.isFinishing() || alertActivity.isDestroyed()) {
                return;
            }
            int wifiState = RequestToggleWiFiActivity.this.mWiFiManager.getWifiState();
            if (wifiState == 1 || wifiState == 3) {
                RequestToggleWiFiActivity requestToggleWiFiActivity =
                        RequestToggleWiFiActivity.this;
                int i = requestToggleWiFiActivity.mState;
                if (i == 2 || i == 4) {
                    requestToggleWiFiActivity.setResult(-1);
                    RequestToggleWiFiActivity.this.finish();
                }
            }
        }
    }

    @VisibleForTesting
    public CharSequence getAppLabel() {
        try {
            String launchedFromPackage =
                    this.mActivityManager.getLaunchedFromPackage(getActivityToken());
            if (TextUtils.isEmpty(launchedFromPackage)) {
                Log.d("RequestToggleWiFiActivity", "Package name is null");
                return null;
            }
            try {
                return getPackageManager()
                        .getApplicationInfo(launchedFromPackage, 0)
                        .loadSafeLabel(getPackageManager(), 1000.0f, 5);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e(
                        "RequestToggleWiFiActivity",
                        "Couldn't find app with package name " + launchedFromPackage);
                return null;
            }
        } catch (RemoteException unused2) {
            Log.e("RequestToggleWiFiActivity", "Can not get the package from activity manager");
            return null;
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            finish();
            return;
        }
        if (i != -1) {
            return;
        }
        int i2 = this.mState;
        if (i2 == 1) {
            this.mWiFiManager.setWifiEnabled(true);
            this.mState = 2;
            scheduleToggleTimeout();
            updateUi();
            return;
        }
        if (i2 != 3) {
            return;
        }
        this.mWiFiManager.setWifiEnabled(false);
        this.mState = 4;
        scheduleToggleTimeout();
        updateUi();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mWiFiManager = (WifiManager) getSystemService(WifiManager.class);
        setResult(0);
        CharSequence appLabel = getAppLabel();
        this.mAppLabel = appLabel;
        if (TextUtils.isEmpty(appLabel)) {
            finish();
            return;
        }
        String action = getIntent().getAction();
        if (TextUtils.isEmpty(action)) {
            finish();
            return;
        }
        action.getClass();
        if (action.equals("android.net.wifi.action.REQUEST_ENABLE")) {
            this.mState = 1;
        } else if (action.equals("android.net.wifi.action.REQUEST_DISABLE")) {
            this.mState = 3;
        } else {
            finish();
        }
    }

    public final void onStart() {
        super.onStart();
        StateChangeReceiver stateChangeReceiver = this.mReceiver;
        RequestToggleWiFiActivity.this.registerReceiver(
                stateChangeReceiver, stateChangeReceiver.mFilter);
        int wifiState = this.mWiFiManager.getWifiState();
        int i = this.mState;
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        if (wifiState == 0) {
                            scheduleToggleTimeout();
                        } else if (wifiState == 1) {
                            setResult(-1);
                            finish();
                            return;
                        } else if (wifiState == 2 || wifiState == 3) {
                            this.mState = 3;
                        }
                    }
                } else if (wifiState == 1) {
                    setResult(-1);
                    finish();
                    return;
                } else if (wifiState == 2) {
                    this.mState = 4;
                    scheduleToggleTimeout();
                }
            } else if (wifiState == 0 || wifiState == 1) {
                this.mState = 1;
            } else if (wifiState == 2) {
                scheduleToggleTimeout();
            } else if (wifiState == 3) {
                setResult(-1);
                finish();
                return;
            }
        } else if (wifiState == 2) {
            this.mState = 2;
            scheduleToggleTimeout();
        } else if (wifiState == 3) {
            setResult(-1);
            finish();
            return;
        }
        updateUi();
    }

    public final void onStop() {
        StateChangeReceiver stateChangeReceiver = this.mReceiver;
        RequestToggleWiFiActivity.this.unregisterReceiver(stateChangeReceiver);
        getWindow().getDecorView().removeCallbacks(this.mTimeoutCommand);
        super.onStop();
    }

    public final void scheduleToggleTimeout() {
        getWindow().getDecorView().postDelayed(this.mTimeoutCommand, 10000L);
    }

    public final void updateUi() {
        int i = this.mLastUpdateState;
        int i2 = this.mState;
        if (i == i2) {
            return;
        }
        this.mLastUpdateState = i2;
        if (i2 == 1) {
            ((AlertActivity) this).mAlertParams.mPositiveButtonText = getString(R.string.allow);
            AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
            alertParams.mPositiveButtonListener = this;
            alertParams.mNegativeButtonText = getString(R.string.deny);
            AlertController.AlertParams alertParams2 = ((AlertActivity) this).mAlertParams;
            alertParams2.mNegativeButtonListener = this;
            alertParams2.mMessage =
                    getString(R.string.wifi_ask_enable, new Object[] {this.mAppLabel});
        } else if (i2 == 2) {
            ((AlertActivity) this)
                    .mAlert.setButton(
                            -1,
                            (CharSequence) null,
                            (DialogInterface.OnClickListener) null,
                            (Message) null);
            ((AlertActivity) this)
                    .mAlert.setButton(
                            -2,
                            (CharSequence) null,
                            (DialogInterface.OnClickListener) null,
                            (Message) null);
            AlertController.AlertParams alertParams3 = ((AlertActivity) this).mAlertParams;
            alertParams3.mPositiveButtonText = null;
            alertParams3.mPositiveButtonListener = null;
            alertParams3.mNegativeButtonText = null;
            alertParams3.mNegativeButtonListener = null;
            alertParams3.mMessage = getString(R.string.wifi_starting);
        } else if (i2 == 3) {
            ((AlertActivity) this).mAlertParams.mPositiveButtonText = getString(R.string.allow);
            AlertController.AlertParams alertParams4 = ((AlertActivity) this).mAlertParams;
            alertParams4.mPositiveButtonListener = this;
            alertParams4.mNegativeButtonText = getString(R.string.deny);
            AlertController.AlertParams alertParams5 = ((AlertActivity) this).mAlertParams;
            alertParams5.mNegativeButtonListener = this;
            alertParams5.mMessage =
                    getString(R.string.wifi_ask_disable, new Object[] {this.mAppLabel});
        } else if (i2 == 4) {
            ((AlertActivity) this)
                    .mAlert.setButton(
                            -1,
                            (CharSequence) null,
                            (DialogInterface.OnClickListener) null,
                            (Message) null);
            ((AlertActivity) this)
                    .mAlert.setButton(
                            -2,
                            (CharSequence) null,
                            (DialogInterface.OnClickListener) null,
                            (Message) null);
            AlertController.AlertParams alertParams6 = ((AlertActivity) this).mAlertParams;
            alertParams6.mPositiveButtonText = null;
            alertParams6.mPositiveButtonListener = null;
            alertParams6.mNegativeButtonText = null;
            alertParams6.mNegativeButtonListener = null;
            alertParams6.mMessage = getString(R.string.wifi_stopping);
        }
        setupAlert();
    }

    public final void dismiss() {}
}
