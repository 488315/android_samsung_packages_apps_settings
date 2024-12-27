package com.samsung.android.settings.wifi.mobileap.autohotspot;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApAutoHotspotSwitchEnabler implements LifecycleEventObserver {
    public static final IntentFilter INTENT_FILTER;
    public final Activity mActivity;
    public AutoHotspotNetworkCallBack mAutoHotspotNetworkCallBack;
    public final AnonymousClass1 mBroadcastReceiver = new BroadcastReceiver() { // from class:
                // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAutoHotspotSwitchEnabler.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    IntentFilter intentFilter = WifiApAutoHotspotSwitchEnabler.INTENT_FILTER;
                    if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                            "Broadcast Received: ",
                            action,
                            "WifiApAutoHotspotSwitchEnabler",
                            "com.samsung.android.server.wifi.softap.smarttethering.internet.changed")) {
                        WifiApAutoHotspotSwitchEnabler.this.updateSwitchState(false);
                        return;
                    }
                    if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                        WifiApAutoHotspotSwitchEnabler.this.updateSwitchState(false);
                        return;
                    }
                    if (action.equals("android.intent.action.AIRPLANE_MODE")) {
                        WifiApAutoHotspotSwitchEnabler.this.updateSwitchState(false);
                        return;
                    }
                    if (action.equals(
                            "com.samsung.android.server.wifi.softap.smarttethering.changed")) {
                        WifiApAutoHotspotSwitchEnabler.this.updateSwitchState(false);
                    } else if (action.equals("ABSENT")) {
                        WifiApAutoHotspotSwitchEnabler.this.updateSwitchState(false);
                    } else if (action.equals(
                            "com.samsung.account.SAMSUNGACCOUNT_SIGNOUT_COMPLETED")) {
                        WifiApAutoHotspotSwitchEnabler.this.updateSwitchState(false);
                    }
                }
            };
    public final Context mContext;
    public final SettingsPreferenceFragment mFragmentActivity;
    public OnStateChangeListener mOnStateChangeListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AutoHotspotNetworkCallBack extends ConnectivityManager.NetworkCallback {
        public AutoHotspotNetworkCallBack() {}

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(
                Network network, NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            if (networkCapabilities.hasCapability(12)) {
                IntentFilter intentFilter = WifiApAutoHotspotSwitchEnabler.INTENT_FILTER;
                Log.d(
                        "WifiApAutoHotspotSwitchEnabler",
                        "AutoHotspotNetworkCallBack`s NET_CAPABILITY_INTERNET");
                if (Rune.isJapanModel()) {
                    new Handler()
                            .postDelayed(
                                    new WifiApAutoHotspotSwitchEnabler$AutoHotspotNetworkCallBack$$ExternalSyntheticLambda0(
                                            this, 0),
                                    1000L);
                } else {
                    new Handler()
                            .postDelayed(
                                    new WifiApAutoHotspotSwitchEnabler$AutoHotspotNetworkCallBack$$ExternalSyntheticLambda0(
                                            this, 1),
                                    500L);
                }
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            super.onLost(network);
            IntentFilter intentFilter = WifiApAutoHotspotSwitchEnabler.INTENT_FILTER;
            Log.d("WifiApAutoHotspotSwitchEnabler", "AutoHotspotNetworkCallBack`s onLost");
            new Handler()
                    .postDelayed(
                            new WifiApAutoHotspotSwitchEnabler$AutoHotspotNetworkCallBack$$ExternalSyntheticLambda0(
                                    this, 2),
                            100L);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnStateChangeListener {
        void onStateChanged(int i);
    }

    static {
        IntentFilter intentFilter = new IntentFilter();
        INTENT_FILTER = intentFilter;
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("ABSENT");
        intentFilter.addAction("com.samsung.account.SAMSUNGACCOUNT_SIGNIN_COMPLETED");
        intentFilter.addAction("com.samsung.account.SAMSUNGACCOUNT_SIGNOUT_COMPLETED");
        intentFilter.addAction("com.samsung.android.server.wifi.softap.smarttethering.changed");
        intentFilter.addAction(
                "com.samsung.android.server.wifi.softap.smarttethering.internet.changed");
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAutoHotspotSwitchEnabler$1] */
    public WifiApAutoHotspotSwitchEnabler(SettingsPreferenceFragment settingsPreferenceFragment) {
        Context context = settingsPreferenceFragment.getContext();
        this.mContext = context;
        this.mActivity = (Activity) context;
        this.mFragmentActivity = settingsPreferenceFragment;
        settingsPreferenceFragment.getSettingsLifecycle().addObserver(this);
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Lifecycle.Event event2 = Lifecycle.Event.ON_START;
        AnonymousClass1 anonymousClass1 = this.mBroadcastReceiver;
        if (event == event2) {
            Log.i("WifiApAutoHotspotSwitchEnabler", "on Start");
            if (this.mAutoHotspotNetworkCallBack != null) {
                WifiApFrameworkUtils.getConnectivityManager(this.mContext)
                        .unregisterNetworkCallback(this.mAutoHotspotNetworkCallBack);
            }
            this.mAutoHotspotNetworkCallBack = new AutoHotspotNetworkCallBack();
            WifiApFrameworkUtils.getConnectivityManager(this.mContext)
                    .registerNetworkCallback(
                            new NetworkRequest.Builder().build(),
                            this.mAutoHotspotNetworkCallBack,
                            new Handler(Looper.getMainLooper()));
            this.mContext.registerReceiver(anonymousClass1, INTENT_FILTER, 2);
            return;
        }
        if (event == Lifecycle.Event.ON_STOP) {
            Log.i("WifiApAutoHotspotSwitchEnabler", "onStop");
            if (this.mAutoHotspotNetworkCallBack != null) {
                WifiApFrameworkUtils.getConnectivityManager(this.mContext)
                        .unregisterNetworkCallback(this.mAutoHotspotNetworkCallBack);
            }
            this.mAutoHotspotNetworkCallBack = null;
            this.mContext.unregisterReceiver(anonymousClass1);
        }
    }

    public final void setChecked(boolean z) {
        SettingsPreferenceFragment settingsPreferenceFragment;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "setChecked: ", "WifiApAutoHotspotSwitchEnabler", z);
        WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, z);
        if (!z || (settingsPreferenceFragment = this.mFragmentActivity) == null) {
            Log.i("WifiApAutoHotspotSwitchEnabler", "Fragment Activity null");
        } else {
            Log.i("WifiApAutoHotspotSwitchEnabler", "Checking setCheck conditions.");
            boolean z2 = !WifiApSettingsUtils.isSimEnabled(this.mContext);
            boolean z3 = !WifiApSettingsUtils.isActiveNetworkHasInternet(this.mContext);
            boolean isSamsungAccountLoggedOut =
                    WifiApSettingsUtils.isSamsungAccountLoggedOut(this.mContext);
            boolean isDefaultPassphraseSet =
                    WifiApSoftApUtils.isDefaultPassphraseSet(this.mContext);
            if (z2 && WifiApFeatureUtils.isOneUIVersion6_0_AtMost()) {
                Log.i("WifiApAutoHotspotSwitchEnabler", "Sim check condition failed");
                WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, false);
                if ("CTC".equals(Utils.getSalesCode())) {
                    Context context = this.mContext;
                    Toast.makeText(
                                    context,
                                    WifiApUtils.getString(
                                            context,
                                            R.string.mobile_hotspot_dialog_nouim_or_nosim_warning),
                                    1)
                            .show();
                } else {
                    Context context2 = this.mContext;
                    Toast.makeText(
                                    context2,
                                    WifiApUtils.getString(
                                            context2, R.string.wifi_tether_dialog_nosim_warning),
                                    1)
                            .show();
                }
            } else if (z3) {
                Log.i("WifiApAutoHotspotSwitchEnabler", "Network check condition failed.");
                WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, false);
            } else if (isSamsungAccountLoggedOut) {
                Log.i("WifiApAutoHotspotSwitchEnabler", "Samsung Account check condition failed.");
                WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, false);
                WifiApSettingsUtils.launchAddSamsungAccountActivity(this.mActivity);
            } else if ((WifiApSettingsUtils.isCarrierTMO() || WifiApSettingsUtils.isCarrierNEWCO())
                    && isDefaultPassphraseSet) {
                Log.d(
                        "WifiApAutoHotspotSwitchEnabler",
                        "AutoHotspot switch FirstTime Configuration dialog");
                Settings.Secure.putInt(
                        this.mContext.getContentResolver(),
                        "autohotspot_waiting_for_password_change",
                        1);
                WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, false);
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mSourceMetricsCategory = 3400;
                launchRequest.mDestinationName = WifiApEditSettings.class.getCanonicalName();
                subSettingLauncher.setResultListener(settingsPreferenceFragment, 4);
                subSettingLauncher.setTitleRes(R.string.wifi_ap_first_time_configuration, null);
                subSettingLauncher.launch();
            } else if (WifiApFrameworkUtils.getSemWifiManager(this.mContext)
                            .isWifiSharingSupported()
                    && !WifiApFrameworkUtils.getSemWifiManager(this.mContext)
                            .isWifiSharingLiteSupported()) {
                Settings.Secure.putInt(
                        this.mContext.getContentResolver(), "wifi_ap_wifi_sharing", 1);
            }
        }
        updateSwitchState(false);
    }

    public final void updateSwitchState(boolean z) {
        boolean isTetheringRestricted = WifiApSettingsUtils.isTetheringRestricted(this.mContext);
        boolean isAirplaneModeOn = WifiApSettingsUtils.isAirplaneModeOn(this.mContext);
        boolean z2 = !WifiApSettingsUtils.isSimEnabled(this.mContext);
        boolean z3 = !WifiApSettingsUtils.isActiveNetworkHasInternet(this.mContext);
        boolean isSamsungAccountLoggedOut =
                WifiApSettingsUtils.isSamsungAccountLoggedOut(this.mContext);
        boolean z4 = !WifiApSettingsUtils.isNearByDeviceScanningEnabled(this.mContext);
        boolean isDefaultPassphraseSet = WifiApSoftApUtils.isDefaultPassphraseSet(this.mContext);
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "Updating Autohotspot Switch state - , isTetheringRestricted : ",
                        isTetheringRestricted,
                        ", isAirplaneModeOn : ",
                        isAirplaneModeOn,
                        ", isSimDisabled : ");
        m.append(z2);
        m.append(", isNetworkDisconnected : ");
        m.append(z3);
        m.append(", isSamsungAccountLoggedOut : ");
        m.append(isSamsungAccountLoggedOut);
        m.append(", isDefaultPassphraseSet: ");
        m.append(isDefaultPassphraseSet);
        m.append(", isNearByDeviceScanningDisabled : ");
        m.append(z4);
        m.append(", setNearByDeviceScanningEnabledIfRequired: ");
        m.append(z);
        Log.d("WifiApAutoHotspotSwitchEnabler", m.toString());
        if (isTetheringRestricted) {
            WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(5);
            return;
        }
        if (isAirplaneModeOn) {
            this.mOnStateChangeListener.onStateChanged(1);
            return;
        }
        if (z2 && WifiApFeatureUtils.isOneUIVersion6_0_AtMost()) {
            WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(2);
            return;
        }
        if (z3 && !WifiApFrameworkUtils.isAutoHotspotSetOn(this.mContext)) {
            WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(3);
            return;
        }
        if (isSamsungAccountLoggedOut) {
            WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(4);
            return;
        }
        if ((WifiApSettingsUtils.isCarrierTMO() || WifiApSettingsUtils.isCarrierNEWCO())
                && isDefaultPassphraseSet) {
            WifiApFrameworkUtils.setAutoHotspotDB(this.mContext, false);
            this.mOnStateChangeListener.onStateChanged(7);
            return;
        }
        if (z4) {
            if (!z) {
                this.mOnStateChangeListener.onStateChanged(6);
                return;
            }
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "nearby_scanning_enabled", 1);
            if (!WifiApSettingsUtils.isNearByDeviceScanningEnabled(r12)) {
                Log.i("WifiApSettingsUtils", "Failed to enable NearBy Device Scanning");
                this.mOnStateChangeListener.onStateChanged(6);
                return;
            }
            Log.i("WifiApSettingsUtils", "Success to enable NearBy Device Scanning");
        }
        this.mOnStateChangeListener.onStateChanged(-1);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAutoHotspotSwitchEnabler$1] */
    public WifiApAutoHotspotSwitchEnabler(Context context) {
        this.mContext = context;
    }
}
