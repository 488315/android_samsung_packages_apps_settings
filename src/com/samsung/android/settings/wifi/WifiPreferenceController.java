package com.samsung.android.settings.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.core.TogglePreferenceController;
import com.android.settings.wifi.WifiEnabler;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.settings.ImsProfile;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause, OnStart, OnStop {
    private static final String KEY_WIFI = "wifi_settings";
    private static final String TAG = "WifiPreferenceController";
    private final IntentFilter mIntentFilter;
    private BroadcastReceiver mReceiver;
    private WifiEnabler mWifiEnabler;
    private SecSwitchPreferenceScreen mWifiEnablerPreference;
    private final WifiManager mWifiManager;

    public WifiPreferenceController(Context context, String str) {
        super(context, str);
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mIntentFilter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
    }

    private BroadcastReceiver getWifiStateChangeReceiver() {
        if (this.mReceiver == null) {
            this.mReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.samsung.android.settings.wifi.WifiPreferenceController.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if (!"android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())
                                    || WifiPreferenceController.this.mWifiEnablerPreference
                                            == null) {
                                return;
                            }
                            WifiPreferenceController wifiPreferenceController =
                                    WifiPreferenceController.this;
                            wifiPreferenceController.updateState(
                                    wifiPreferenceController.mWifiEnablerPreference);
                        }
                    };
        }
        return this.mReceiver;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mWifiEnablerPreference = secSwitchPreferenceScreen;
        this.mWifiEnabler =
                new WifiEnabler(
                        this.mContext, null, null, secSwitchPreferenceScreen, null, null, null);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int wifiState = this.mWifiManager.getWifiState();
        return (wifiState == 0 || wifiState == 2) ? 5 : 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public IntentFilter getIntentFilter() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                "android.net.wifi.WIFI_STATE_CHANGED");
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean hasAsyncUpdate() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        int wifiState = this.mWifiManager.getWifiState();
        return wifiState == 3 || wifiState == 2;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContext.unregisterReceiver(this.mReceiver);
        WifiEnabler wifiEnabler = this.mWifiEnabler;
        if (wifiEnabler != null) {
            wifiEnabler.pause();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mContext.registerReceiver(getWifiStateChangeReceiver(), this.mIntentFilter);
        updateState(this.mWifiEnablerPreference);
        WifiEnabler wifiEnabler = this.mWifiEnabler;
        if (wifiEnabler != null) {
            wifiEnabler.resume();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        WifiEnabler wifiEnabler = this.mWifiEnabler;
        if (wifiEnabler != null) {
            Context context = this.mContext;
            Log.d("WifiEnabler", NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
            wifiEnabler.mContext = context;
            context.registerReceiver(wifiEnabler.mReceiver, wifiEnabler.mIntentFilter);
            wifiEnabler.mRegisteredReceiver = true;
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        WifiEnabler wifiEnabler = this.mWifiEnabler;
        if (wifiEnabler != null) {
            wifiEnabler.stop();
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        this.mWifiManager.setWifiEnabled(z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (SemWifiUtils.isSatelliteModeOn(this.mContext)) {
            return;
        }
        int wifiState = this.mWifiManager.getWifiState();
        if (wifiState == 0 || wifiState == 2) {
            preference.setEnabled(false);
        } else {
            preference.setEnabled(true);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
