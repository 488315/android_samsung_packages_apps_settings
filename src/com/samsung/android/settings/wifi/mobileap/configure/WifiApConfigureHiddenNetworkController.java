package com.samsung.android.settings.wifi.mobileap.configure;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigureHiddenNetworkController extends TogglePreferenceController
        implements LifecycleEventObserver {
    private static final String BUNDLE_KEY_HIDDEN_NETWORK_SWITCH_VALUE =
            "bundle_key_hidden_network_switch_value";
    public static final String KEY_PREFERENCE = "hidden_network";
    private static final String TAG = "WifiApConfigureHiddenNetworkController";
    private Context mContext;
    private SecSwitchPreference mThisSwitchPreference;
    private WifiApEditSettings mWifiApEditSettings;

    public WifiApConfigureHiddenNetworkController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Log.i(TAG, "displayPreference");
        this.mThisSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(KEY_PREFERENCE);
        boolean isHiddenSsid =
                WifiApFrameworkUtils.getSemWifiManager(this.mContext)
                        .getSoftApConfiguration()
                        .isHiddenSsid();
        this.mThisSwitchPreference.setTitle(R.string.wifi_ap_hide_ssid);
        this.mThisSwitchPreference.setSummary(
                WifiApUtils.getString(this.mContext, R.string.wifi_ap_hide_ssid_hint));
        this.mThisSwitchPreference.setChecked(isHiddenSsid);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
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
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        Log.i(TAG, "isChecked");
        return this.mThisSwitchPreference.isChecked();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isHiddenNetwork() {
        return getThreadEnabled();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (isAvailable() && bundle.containsKey(BUNDLE_KEY_HIDDEN_NETWORK_SWITCH_VALUE)) {
            boolean z = bundle.getBoolean(BUNDLE_KEY_HIDDEN_NETWORK_SWITCH_VALUE);
            AbsAdapter$$ExternalSyntheticOutline0.m("onRestoreInstanceState: ", TAG, z);
            this.mThisSwitchPreference.setChecked(z);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (isAvailable()) {
            bundle.putBoolean(
                    BUNDLE_KEY_HIDDEN_NETWORK_SWITCH_VALUE, this.mThisSwitchPreference.isChecked());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("setChecked: ", TAG, z);
        SALogging.insertSALog(z ? 0L : 1L, "TETH_012", "8019");
        return true;
    }

    public void setHost(WifiApEditSettings wifiApEditSettings) {
        this.mWifiApEditSettings = wifiApEditSettings;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {}
}
