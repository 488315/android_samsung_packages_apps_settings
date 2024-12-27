package com.android.settings.wifi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkRequestDialogActivity extends FragmentActivity
        implements WifiManager.NetworkRequestMatchCallback {
    NetworkRequestDialogBaseFragment mDialogFragment;
    public final AnonymousClass1 mHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.android.settings.wifi.NetworkRequestDialogActivity.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 0) {
                        return;
                    }
                    removeMessages(0);
                    NetworkRequestDialogActivity.this.stopScanningAndPopErrorDialog(
                            NetworkRequestErrorDialogFragment.ERROR_DIALOG_TYPE.TIME_OUT);
                }
            };
    boolean mIsSpecifiedSsid;
    public WifiConfiguration mMatchedConfig;
    ProgressDialog mProgressDialog;
    boolean mShowingErrorDialog;
    public WifiManager.NetworkRequestUserSelectionCallback mUserSelectionCallback;

    public void dismissDialogs() {
        NetworkRequestDialogBaseFragment networkRequestDialogBaseFragment = this.mDialogFragment;
        if (networkRequestDialogBaseFragment != null) {
            networkRequestDialogBaseFragment.dismissAllowingStateLoss();
            this.mDialogFragment = null;
        }
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.mProgressDialog = null;
        }
    }

    public final void onAbort() {
        stopScanningAndPopErrorDialog(NetworkRequestErrorDialogFragment.ERROR_DIALOG_TYPE.ABORT);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent != null) {
            this.mIsSpecifiedSsid =
                    intent.getBooleanExtra(
                            "com.android.settings.wifi.extra.REQUEST_IS_FOR_SINGLE_NETWORK", false);
        }
        if (this.mIsSpecifiedSsid) {
            showProgressDialog$1(getString(R.string.network_connection_searching_message));
            return;
        }
        NetworkRequestDialogFragment networkRequestDialogFragment =
                new NetworkRequestDialogFragment();
        this.mDialogFragment = networkRequestDialogFragment;
        networkRequestDialogFragment.show(
                getSupportFragmentManager(), "NetworkRequestDialogActivity");
    }

    public final void onMatch(List list) {
        if (this.mShowingErrorDialog) {
            return;
        }
        removeMessages(0);
        if (!this.mIsSpecifiedSsid) {
            NetworkRequestDialogBaseFragment networkRequestDialogBaseFragment =
                    this.mDialogFragment;
            if (networkRequestDialogBaseFragment != null) {
                networkRequestDialogBaseFragment.onMatch(list);
                return;
            }
            return;
        }
        if (this.mMatchedConfig == null) {
            WifiConfiguration wifiConfig = WifiUtils.getWifiConfig(null, (ScanResult) list.get(0));
            this.mMatchedConfig = wifiConfig;
            String sanitizeSsid = android.net.wifi.WifiInfo.sanitizeSsid(wifiConfig.SSID);
            dismissDialogs();
            this.mDialogFragment = new NetworkRequestSingleSsidDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("DIALOG_REQUEST_SSID", sanitizeSsid);
            bundle.putBoolean("DIALOG_IS_TRYAGAIN", false);
            this.mDialogFragment.setArguments(bundle);
            this.mDialogFragment.show(getSupportFragmentManager(), "NetworkRequestDialogActivity");
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        removeMessages(0);
        WifiManager wifiManager = (WifiManager) getSystemService(WifiManager.class);
        if (wifiManager != null) {
            wifiManager.unregisterNetworkRequestMatchCallback(this);
        }
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        WifiManager wifiManager = (WifiManager) getSystemService(WifiManager.class);
        if (wifiManager != null) {
            wifiManager.registerNetworkRequestMatchCallback(
                    new HandlerExecutor(this.mHandler), this);
        }
        sendEmptyMessageDelayed(0, 30000L);
    }

    public final void onUserSelectionCallbackRegistration(
            WifiManager.NetworkRequestUserSelectionCallback networkRequestUserSelectionCallback) {
        if (this.mIsSpecifiedSsid) {
            this.mUserSelectionCallback = networkRequestUserSelectionCallback;
            return;
        }
        NetworkRequestDialogBaseFragment networkRequestDialogBaseFragment = this.mDialogFragment;
        if (networkRequestDialogBaseFragment != null) {
            networkRequestDialogBaseFragment.onUserSelectionCallbackRegistration(
                    networkRequestUserSelectionCallback);
        }
    }

    public final void onUserSelectionConnectFailure(WifiConfiguration wifiConfiguration) {
        if (isFinishing()) {
            return;
        }
        Toast.makeText(this, R.string.network_connection_connect_failure, 0).show();
        setResult(-1);
        finish();
    }

    public final void onUserSelectionConnectSuccess(WifiConfiguration wifiConfiguration) {
        if (isFinishing()) {
            return;
        }
        Toast.makeText(this, R.string.network_connection_connect_successful, 0).show();
        setResult(-1);
        finish();
    }

    public final void showProgressDialog$1(String str) {
        dismissDialogs();
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.mProgressDialog = progressDialog;
        progressDialog.setIndeterminate(true);
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setMessage(str);
        this.mProgressDialog.show();
    }

    public final void stopScanningAndPopErrorDialog(
            NetworkRequestErrorDialogFragment.ERROR_DIALOG_TYPE error_dialog_type) {
        dismissDialogs();
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.mDestroyed || supportFragmentManager.isStateSaved()) {
            return;
        }
        NetworkRequestErrorDialogFragment networkRequestErrorDialogFragment =
                new NetworkRequestErrorDialogFragment();
        networkRequestErrorDialogFragment.mRejectCallback = this.mUserSelectionCallback;
        Bundle bundle = new Bundle();
        bundle.putSerializable("DIALOG_ERROR_TYPE", error_dialog_type);
        networkRequestErrorDialogFragment.setArguments(bundle);
        networkRequestErrorDialogFragment.show(
                supportFragmentManager, "NetworkRequestDialogActivity");
        this.mShowingErrorDialog = true;
    }
}
