package com.android.settings.wifi.tether;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SeslSwitchBar;

import com.android.settings.SettingsActivity;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiTetherSwitchBarController
        implements LifecycleObserver,
                OnStart,
                OnStop,
                DataSaverBackend.Listener,
                CompoundButton.OnCheckedChangeListener {
    public static final IntentFilter WIFI_INTENT_FILTER =
            new IntentFilter(
                    WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED);
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    DataSaverBackend mDataSaverBackend;
    final ConnectivityManager.OnStartTetheringCallback mOnStartTetheringCallback =
            new ConnectivityManager
                    .OnStartTetheringCallback() { // from class:
                                                  // com.android.settings.wifi.tether.WifiTetherSwitchBarController.1
                public final void onTetheringFailed() {
                    super.onTetheringFailed();
                    Log.e("WifiTetherSBC", "Failed to start Wi-Fi Tethering.");
                    WifiTetherSwitchBarController wifiTetherSwitchBarController =
                            WifiTetherSwitchBarController.this;
                    wifiTetherSwitchBarController.handleWifiApStateChanged(
                            wifiTetherSwitchBarController.mWifiManager.getWifiApState());
                }
            };
    public final AnonymousClass2 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.wifi.tether.WifiTetherSwitchBarController.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED
                            .equals(intent.getAction())) {
                        WifiTetherSwitchBarController.this.handleWifiApStateChanged(
                                intent.getIntExtra("wifi_state", 14));
                    }
                }
            };
    public final SettingsMainSwitchBar mSwitchBar;
    public final WifiManager mWifiManager;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.wifi.tether.WifiTetherSwitchBarController$2] */
    public WifiTetherSwitchBarController(
            SettingsActivity settingsActivity, SettingsMainSwitchBar settingsMainSwitchBar) {
        this.mContext = settingsActivity;
        this.mSwitchBar = settingsMainSwitchBar;
        this.mDataSaverBackend = new DataSaverBackend(settingsActivity);
        this.mConnectivityManager =
                (ConnectivityManager) settingsActivity.getSystemService("connectivity");
        WifiManager wifiManager =
                (WifiManager) settingsActivity.getSystemService(ImsProfile.PDN_WIFI);
        this.mWifiManager = wifiManager;
        settingsMainSwitchBar.setChecked(wifiManager.getWifiApState() == 13);
        settingsMainSwitchBar.setEnabled(
                !this.mDataSaverBackend.mPolicyManager.getRestrictBackground());
    }

    public void handleWifiApStateChanged(int i) {
        if (i == 12 || i == 10) {
            return;
        }
        boolean z = i == 13;
        if (((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked() != z) {
            this.mSwitchBar.setChecked(z);
        }
        this.mSwitchBar.setEnabled(!this.mDataSaverBackend.mPolicyManager.getRestrictBackground());
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton.isEnabled()) {
            if (z) {
                int wifiApState = this.mWifiManager.getWifiApState();
                if (wifiApState == 13 || wifiApState == 12) {
                    return;
                }
                this.mSwitchBar.setEnabled(false);
                this.mConnectivityManager.startTethering(
                        0,
                        true,
                        this.mOnStartTetheringCallback,
                        new Handler(Looper.getMainLooper()));
                return;
            }
            int wifiApState2 = this.mWifiManager.getWifiApState();
            if (wifiApState2 == 13 || wifiApState2 == 12) {
                this.mSwitchBar.setEnabled(false);
                this.mConnectivityManager.stopTethering(0);
            }
        }
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDataSaverChanged(boolean z) {
        this.mSwitchBar.setEnabled(!this.mDataSaverBackend.mPolicyManager.getRestrictBackground());
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        this.mDataSaverBackend.addListener(this);
        this.mSwitchBar.addOnSwitchChangeListener(this);
        this.mContext.registerReceiver(this.mReceiver, WIFI_INTENT_FILTER, 2);
        handleWifiApStateChanged(this.mWifiManager.getWifiApState());
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        this.mDataSaverBackend.remListener(this);
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onAllowlistStatusChanged(int i, boolean z) {}

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDenylistStatusChanged(int i, boolean z) {}
}
