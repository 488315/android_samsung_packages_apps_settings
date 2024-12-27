package com.samsung.android.settings.wifi.warning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.CompoundButton;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiWarningControlDialog implements CompoundButton.OnCheckedChangeListener {
    public final Activity mActivity;
    public AlertDialog mApDisableDialog;
    public final AlertDialog.Builder mBuilder;
    public final WifiP2pManager.Channel mChannel;
    public final Context mContext;
    public final int mDialogTheme;
    public boolean mHasSentBroadcast;
    public final Intent mIntent;
    public boolean mIsAirplaneMode;
    public final SemWifiManager mSemWifiManager;
    public final TimeoutHandler mTimeoutHandler;
    public ProgressDialog mTurningOffDialog;
    public ProgressDialog mWifiApTurningOffDialog;
    public boolean mWifiEnableCheck;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TimeoutHandler extends Handler {
        public TimeoutHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            WifiWarningControlDialog.this.dismissProgressDialog();
        }
    }

    public WifiWarningControlDialog(Activity activity, Context context) {
        ContextThemeWrapper contextThemeWrapper =
                new ContextThemeWrapper(context, R.style.SettingsTheme);
        this.mContext = contextThemeWrapper;
        this.mActivity = activity;
        int i = Utils.isNightMode(context) ? 4 : 5;
        this.mDialogTheme = i;
        this.mBuilder = new AlertDialog.Builder(activity, i);
        this.mWifiManager = (WifiManager) contextThemeWrapper.getSystemService(ImsProfile.PDN_WIFI);
        WifiP2pManager wifiP2pManager =
                (WifiP2pManager) contextThemeWrapper.getSystemService("wifip2p");
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        if (wifiP2pManager != null) {
            WifiP2pManager.Channel initialize =
                    wifiP2pManager.initialize(contextThemeWrapper, Looper.getMainLooper(), null);
            this.mChannel = initialize;
            if (initialize == null) {
                Log.e(
                        "WifiWarningControlDialog",
                        "Failed to set up connection with wifi p2p service");
            }
        } else {
            Log.e("WifiWarningControlDialog", "mWifiP2pManager is null !");
        }
        if (contextThemeWrapper
                .getPackageManager()
                .hasSystemFeature("android.hardware.wifi.aware")) {}
        this.mIntent = new Intent("com.samsung.android.settings.wifi.notifySelected");
        this.mTimeoutHandler = new TimeoutHandler(Looper.getMainLooper());
    }

    public final void disableMobileHotspot() {
        if (handleWifiApStateChanged(this.mWifiManager.getWifiApState())) {
            return;
        }
        if (this.mWifiApTurningOffDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(this.mActivity, this.mDialogTheme);
            if (Rune.isSamsungDexMode(this.mContext)) {
                progressDialog.getWindow().clearFlags(2);
            }
            this.mWifiApTurningOffDialog = progressDialog;
        }
        if (!this.mWifiApTurningOffDialog.isShowing()) {
            this.mWifiApTurningOffDialog.setMessage(
                    Rune.isJapanModel()
                            ? this.mContext.getString(R.string.wifi_ap_turning_off_jpn)
                            : this.mContext.getString(R.string.wifi_ap_turning_off));
            this.mWifiApTurningOffDialog.setCancelable(false);
            this.mWifiApTurningOffDialog.show();
        }
        this.mTimeoutHandler.sendEmptyMessageDelayed(0, 20000L);
        SemWifiManager semWifiManager = this.mSemWifiManager;
        if (semWifiManager != null) {
            semWifiManager.setWifiApEnabled((SoftApConfiguration) null, false);
        }
    }

    public final void dismissApDisableDialog() {
        AlertDialog alertDialog = this.mApDisableDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mApDisableDialog.dismiss();
    }

    public final void dismissProgressDialog() {
        Log.d("WifiWarningControlDialog", "dismissProgressDialog");
        ProgressDialog progressDialog = this.mWifiApTurningOffDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            AlertDialog alertDialog = this.mApDisableDialog;
            if (alertDialog == null || !alertDialog.isShowing()) {
                ProgressDialog progressDialog2 = this.mTurningOffDialog;
                if (progressDialog2 != null && progressDialog2.isShowing()) {
                    this.mTurningOffDialog.dismiss();
                    this.mTurningOffDialog = null;
                }
            } else {
                this.mApDisableDialog.dismiss();
                Intent intent =
                        new Intent("com.samsung.android.net.wifi.WIFI_DIALOG_CANCEL_ACTION");
                intent.putExtra("called_dialog", 1);
                this.mContext.sendBroadcast(intent, "android.permission.NETWORK_SETTINGS");
            }
        } else {
            this.mWifiApTurningOffDialog.dismiss();
            this.mWifiApTurningOffDialog = null;
        }
        this.mActivity.finish();
    }

    public abstract DialogInterface.OnClickListener getClickListener();

    public String getPositiveButtonText() {
        return this.mContext.getString(R.string.ok);
    }

    public boolean handleWifiApStateChanged(int i) {
        return false;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton.getId() == R.id.checkbox) {
            this.mWifiEnableCheck = z;
            SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onCheckedChanged mWifiEnableCheck ="),
                    this.mWifiEnableCheck,
                    "WifiWarningControlDialog");
        }
    }

    public final void sendBroadcastAsUser(String str, boolean z) {
        this.mHasSentBroadcast = true;
        this.mIntent.putExtra("type", str);
        this.mIntent.putExtra("selected", z);
        this.mContext.sendBroadcastAsUser(
                this.mIntent, UserHandle.CURRENT, "android.permission.NETWORK_SETTINGS");
    }

    public final void sendBroadcastCancelEnablingWifi() {
        Log.d("WifiWarningControlDialog", "sendBroadcastCancelEnablingWifi");
        Intent intent = new Intent("com.samsung.android.net.wifi.WIFI_DIALOG_CANCEL_ACTION");
        intent.putExtra("called_dialog", 1);
        this.mContext.sendBroadcastAsUser(
                intent, UserHandle.ALL, "android.permission.NETWORK_SETTINGS");
    }

    public final void sendEnableNanBroadcast(boolean z) {
        Log.d("WifiWarningControlDialog", "sendEnableNanBroadcast: enabled: " + z);
        Intent intent = new Intent("com.samsung.android.settings.wifi.enableNan");
        intent.putExtra("enabled", z);
        this.mContext.sendBroadcastAsUser(
                intent, UserHandle.CURRENT, "android.permission.NETWORK_SETTINGS");
    }

    public abstract void setAlertDialog();

    public abstract void setNegativeButton();

    public final void showProgressDialog$1() {
        if (this.mTurningOffDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(this.mContext, this.mDialogTheme);
            if (Rune.isSamsungDexMode(this.mContext)) {
                progressDialog.getWindow().clearFlags(2);
            }
            this.mTurningOffDialog = progressDialog;
        }
        if (this.mTurningOffDialog.isShowing()) {
            return;
        }
        this.mTurningOffDialog.setCancelable(false);
        this.mTurningOffDialog.show();
    }

    public void sendNotifySelectedBroadcast(boolean z) {}
}
