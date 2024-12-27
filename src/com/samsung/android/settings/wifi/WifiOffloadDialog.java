package com.samsung.android.settings.wifi;

import android.app.StatusBarManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.secutil.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.ButtonBarHandler;

import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiOffloadDialog extends AppCompatActivity implements ButtonBarHandler {
    public WifiOffloadDialogFragment fragment;
    public ConnectivityManager mCm;
    public WifiOffloadDialog mContext;
    public AnonymousClass2 mNetworkCallback;
    public SemWifiManager mSemWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.WifiOffloadDialog$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    /* renamed from: -$$Nest$mdoFinish, reason: not valid java name */
    public static void m1317$$Nest$mdoFinish(WifiOffloadDialog wifiOffloadDialog, boolean z) {
        if (z && wifiOffloadDialog.mSemWifiManager != null) {
            Log.i("WifiOffloadDialog", "Start 12 hours timer");
            wifiOffloadDialog.mSemWifiManager.startTimerForWifiOffload();
        }
        wifiOffloadDialog.getClass();
        try {
            AnonymousClass2 anonymousClass2 = wifiOffloadDialog.mNetworkCallback;
            if (anonymousClass2 != null) {
                wifiOffloadDialog.mCm.unregisterNetworkCallback(anonymousClass2);
            }
        } catch (IllegalArgumentException unused) {
            Log.d("WifiOffloadDialog", "No need to unregister");
        }
        wifiOffloadDialog.finish();
    }

    @Override // com.android.settings.ButtonBarHandler
    public final Button getNextButton() {
        return null;
    }

    @Override // com.android.settings.ButtonBarHandler
    public final boolean hasNextButton() {
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        setFinishOnTouchOutside(true);
        requestWindowFeature(1);
        Intent intent = getIntent();
        if (intent != null) {
            intent.getStringExtra("EXTRA_PACKAGE_NAME");
        }
        getWindow().setGravity(80);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settings.wifi.WifiOffloadDialog$2] */
    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public final void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.mSemWifiManager =
                (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService("connectivity");
        this.mCm = connectivityManager;
        try {
            AnonymousClass2 anonymousClass2 = this.mNetworkCallback;
            if (anonymousClass2 != null) {
                connectivityManager.unregisterNetworkCallback(anonymousClass2);
            }
        } catch (IllegalArgumentException unused) {
            Log.d("WifiOffloadDialog", "No need to unregister");
        }
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addTransportType(1);
        this.mNetworkCallback =
                new ConnectivityManager
                        .NetworkCallback() { // from class:
                                             // com.samsung.android.settings.wifi.WifiOffloadDialog.2
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onCapabilitiesChanged(
                            Network network, NetworkCapabilities networkCapabilities) {
                        if (networkCapabilities.hasCapability(16)) {
                            WifiOffloadDialog.m1317$$Nest$mdoFinish(WifiOffloadDialog.this, false);
                        }
                    }
                };
        this.mCm.registerNetworkCallback(builder.build(), this.mNetworkCallback);
        WifiOffloadDialogFragment wifiOffloadDialogFragment = new WifiOffloadDialogFragment();
        this.fragment = wifiOffloadDialogFragment;
        wifiOffloadDialogFragment.setCancelable(true);
        WifiOffloadDialogFragment wifiOffloadDialogFragment2 = this.fragment;
        wifiOffloadDialogFragment2.mListener = new AnonymousClass1();
        wifiOffloadDialogFragment2.show(getSupportFragmentManager(), "wifioffloaddialog.fragment");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        StatusBarManager statusBarManager =
                (StatusBarManager) this.mContext.getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.collapsePanels();
        }
    }
}
