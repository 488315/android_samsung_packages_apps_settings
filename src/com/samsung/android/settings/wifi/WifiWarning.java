package com.samsung.android.settings.wifi;

import android.app.AlertDialog;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.internal.app.AlertActivity;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController;
import com.samsung.android.settings.wifi.warning.MobileHotspotWithNanDialog;
import com.samsung.android.settings.wifi.warning.MobileHotspotWithP2pDialog;
import com.samsung.android.settings.wifi.warning.MobileHotspotWithoutP2pDialog;
import com.samsung.android.settings.wifi.warning.NanWithP2pDialog;
import com.samsung.android.settings.wifi.warning.P2pWithNanDialog;
import com.samsung.android.settings.wifi.warning.WepBlockedDialog;
import com.samsung.android.settings.wifi.warning.WepDisconnectDialog;
import com.samsung.android.settings.wifi.warning.WifiSharingDialog;
import com.samsung.android.settings.wifi.warning.WifiWarningControlDialog;
import com.samsung.android.settings.wifi.warning.WifiWarningControlDialog$$ExternalSyntheticLambda0;
import com.samsung.android.settings.wifi.warning.WifiWarningEmptyDialog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiWarning extends AlertActivity {
    public Context mContext;
    public WifiWarningControlDialog mDialog;
    public IntentFilter mIntentFilter;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class: com.samsung.android.settings.wifi.WifiWarning.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    int semGetMyUserId = UserHandle.semGetMyUserId();
                    Log.i(
                            "WifiWarning",
                            "onReceive: action " + action + " userID :" + semGetMyUserId);
                    if (TextUtils.isEmpty(action) || semGetMyUserId != 0) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                semGetMyUserId,
                                "do nothing, action is null or Knox userID:",
                                "WifiWarning");
                        return;
                    }
                    if (WifiWarning.this.mDialog == null) {
                        Log.e("WifiWarning", "Undefined dialog. ignore action");
                        return;
                    }
                    action.getClass();
                    if (action.equals("android.intent.action.AIRPLANE_MODE")) {
                        WifiWarning wifiWarning = WifiWarning.this;
                        wifiWarning.mDialog.mIsAirplaneMode =
                                Settings.System.getInt(
                                                wifiWarning.mContext.getContentResolver(),
                                                "airplane_mode_on",
                                                0)
                                        != 0;
                    } else if (action.equals(
                            WifiApMobileDataSharedTodayPreferenceController
                                    .ACTION_WIFI_AP_STATE_CHANGED)) {
                        WifiWarning.this.mDialog.handleWifiApStateChanged(
                                intent.getIntExtra("wifi_state", 14));
                    }
                }
            };
    public boolean mWifiEnableCheck;

    static {
        Debug.semIsProductDev();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        Log.i("WifiWarning", "onCreate");
        super.onCreate(bundle);
        this.mContext = getApplicationContext();
        IntentFilter intentFilter = new IntentFilter();
        this.mIntentFilter = intentFilter;
        intentFilter.addAction(
                WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED);
        this.mIntentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        WifiWarningControlDialog wifiWarningControlDialog = null;
        this.mContext.registerReceiver(
                this.mReceiver, this.mIntentFilter, "android.permission.NETWORK_SETTINGS", null);
        setupAlert();
        getWindow().setLayout(0, 0);
        setTheme(R.style.SettingsTheme);
        Intent intent = getIntent();
        Context context = this.mContext;
        int intExtra = intent.getIntExtra("req_type", -1);
        int intExtra2 = intent.getIntExtra("extra_type", -1);
        String stringExtra = intent.getStringExtra("friendly_name");
        Log.d(
                "WifiWarning",
                CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0.m(
                        "Try to enable ", " and ", intExtra, intExtra2, " is enabled"));
        if (!TextUtils.isEmpty(stringExtra)) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "friendly name ", stringExtra, "WifiWarning");
        }
        if (intExtra2 == 1 && intExtra != 2 && intExtra != 6) {
            wifiWarningControlDialog = new MobileHotspotWithoutP2pDialog(this, context);
        } else if (intExtra2 == 1 && intExtra == 2) {
            wifiWarningControlDialog = new MobileHotspotWithP2pDialog(this, context);
        } else if (intExtra2 == 6 && intExtra == 2) {
            wifiWarningControlDialog = new NanWithP2pDialog(this, context);
        } else if (intExtra2 == 5) {
            wifiWarningControlDialog = new WifiSharingDialog(this, context);
        } else if (intExtra2 == 1 && intExtra == 6) {
            wifiWarningControlDialog = new MobileHotspotWithNanDialog(this, context);
        } else if (intExtra2 == 2 && intExtra == 6) {
            wifiWarningControlDialog = new P2pWithNanDialog(this, context);
        } else if (intExtra == 7 && !TextUtils.isEmpty(stringExtra)) {
            wifiWarningControlDialog = new WifiWarningEmptyDialog(context, this, stringExtra);
        } else if (intExtra2 == 8) {
            wifiWarningControlDialog = new WepDisconnectDialog(this, context);
        } else if (intExtra2 == 9) {
            String stringExtra2 = intent.getStringExtra("ssid");
            WepBlockedDialog wepBlockedDialog = new WepBlockedDialog(this, context);
            wepBlockedDialog.mSsid = stringExtra2;
            wifiWarningControlDialog = wepBlockedDialog;
        }
        this.mDialog = wifiWarningControlDialog;
        if (wifiWarningControlDialog == null) {
            Log.e("WifiWarning", "Not defined warning dialog.");
            finish();
            return;
        }
        if (bundle != null) {
            this.mWifiEnableCheck = bundle.getBoolean("wifi_enablewarning_check_mode");
        }
        WifiWarningControlDialog wifiWarningControlDialog2 = this.mDialog;
        wifiWarningControlDialog2.getClass();
        if (wifiWarningControlDialog2 instanceof WifiWarningEmptyDialog) {
            wifiWarningControlDialog2.setAlertDialog();
            return;
        }
        wifiWarningControlDialog2.mBuilder.setPositiveButton(
                wifiWarningControlDialog2.getPositiveButtonText(),
                wifiWarningControlDialog2.getClickListener());
        wifiWarningControlDialog2.setNegativeButton();
        wifiWarningControlDialog2.setAlertDialog();
        AlertDialog create = wifiWarningControlDialog2.mBuilder.create();
        wifiWarningControlDialog2.mApDisableDialog = create;
        create.setOnDismissListener(new WifiWarningControlDialog$$ExternalSyntheticLambda0());
        wifiWarningControlDialog2.mApDisableDialog.show();
    }

    public final void onDestroy() {
        super.onDestroy();
        this.mContext.unregisterReceiver(this.mReceiver);
        WifiWarningControlDialog wifiWarningControlDialog = this.mDialog;
        if (wifiWarningControlDialog != null) {
            WifiP2pManager.Channel channel = wifiWarningControlDialog.mChannel;
            if (channel != null) {
                channel.close();
            }
            wifiWarningControlDialog.mTimeoutHandler.removeMessages(0);
            wifiWarningControlDialog.mHasSentBroadcast = false;
        }
    }

    public final void onResume() {
        super.onResume();
        StatusBarManager statusBarManager =
                (StatusBarManager) this.mContext.getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.collapsePanels();
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                new StringBuilder("onClick mWifiEnableCheck ="),
                this.mWifiEnableCheck,
                "WifiWarning");
        bundle.putBoolean("wifi_enablewarning_check_mode", this.mWifiEnableCheck);
    }

    public final void onStop() {
        super.onStop();
        WifiWarningControlDialog wifiWarningControlDialog = this.mDialog;
        if (wifiWarningControlDialog == null || wifiWarningControlDialog.mHasSentBroadcast) {
            return;
        }
        wifiWarningControlDialog.sendNotifySelectedBroadcast(false);
    }

    public final void onUserLeaveHint() {
        super.onUserLeaveHint();
        WifiWarningControlDialog wifiWarningControlDialog = this.mDialog;
        if (wifiWarningControlDialog != null) {
            wifiWarningControlDialog.sendBroadcastCancelEnablingWifi();
        }
    }
}
