package com.android.settings.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.wifi.WifiPickerTrackerHelper;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiPickerTracker;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CarrierWifiTogglePreferenceController extends TogglePreferenceController
        implements WifiPickerTracker.WifiPickerTrackerCallback {
    protected static final String CARRIER_WIFI_NETWORK_PREF_KEY = "carrier_wifi_network";
    protected static final String CARRIER_WIFI_TOGGLE_PREF_KEY = "carrier_wifi_toggle";
    private static final String TAG = "CarrierWifiTogglePreferenceController";
    protected Preference mCarrierNetworkPreference;
    protected final Context mContext;
    protected boolean mIsCarrierProvisionWifiEnabled;
    protected int mSubId;
    protected WifiPickerTrackerHelper mWifiPickerTrackerHelper;

    public CarrierWifiTogglePreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mCarrierNetworkPreference =
                preferenceScreen.findPreference(CARRIER_WIFI_NETWORK_PREF_KEY);
        updateCarrierNetworkPreference();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsCarrierProvisionWifiEnabled ? 0 : 2;
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

    public String getCarrierNetworkSsid() {
        MergedCarrierEntry mergedCarrierEntry;
        WifiPickerTrackerHelper wifiPickerTrackerHelper = this.mWifiPickerTrackerHelper;
        if (wifiPickerTrackerHelper == null
                || (mergedCarrierEntry =
                                wifiPickerTrackerHelper.mWifiPickerTracker.getMergedCarrierEntry())
                        == null) {
            return null;
        }
        return mergedCarrierEntry.getSsid();
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
        return R.string.menu_key_network;
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

    public void init(Lifecycle lifecycle, int i) {
        this.mSubId = i;
        WifiPickerTrackerHelper wifiPickerTrackerHelper =
                new WifiPickerTrackerHelper(lifecycle, this.mContext, this);
        this.mWifiPickerTrackerHelper = wifiPickerTrackerHelper;
        this.mIsCarrierProvisionWifiEnabled =
                wifiPickerTrackerHelper.isCarrierNetworkProvisionEnabled(this.mSubId);
    }

    public boolean isCarrierNetworkActive() {
        WifiPickerTrackerHelper wifiPickerTrackerHelper = this.mWifiPickerTrackerHelper;
        if (wifiPickerTrackerHelper == null) {
            return false;
        }
        return wifiPickerTrackerHelper.isCarrierNetworkActive();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        MergedCarrierEntry mergedCarrierEntry =
                this.mWifiPickerTrackerHelper.mWifiPickerTracker.getMergedCarrierEntry();
        if (mergedCarrierEntry == null) {
            Log.e(
                    "WifiPickerTrackerHelper",
                    "Failed to get MergedCarrierEntry to query enabled status");
            return false;
        }
        boolean isCarrierNetworkOffloadEnabled =
                mergedCarrierEntry.mWifiManager.isCarrierNetworkOffloadEnabled(
                        mergedCarrierEntry.mSubscriptionId, true);
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "isCarrierNetworkEnabled:",
                "WifiPickerTrackerHelper",
                isCarrierNetworkOffloadEnabled);
        return isCarrierNetworkOffloadEnabled;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public void onWifiEntriesChanged() {
        updateCarrierNetworkPreference();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public void onWifiStateChanged() {
        updateCarrierNetworkPreference();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        WifiPickerTrackerHelper wifiPickerTrackerHelper = this.mWifiPickerTrackerHelper;
        if (wifiPickerTrackerHelper == null) {
            return false;
        }
        wifiPickerTrackerHelper.setCarrierNetworkEnabled(z);
        return true;
    }

    public void updateCarrierNetworkPreference() {
        if (this.mCarrierNetworkPreference == null) {
            return;
        }
        if (getAvailabilityStatus() != 0 || !isCarrierNetworkActive()) {
            this.mCarrierNetworkPreference.setVisible(false);
        } else {
            this.mCarrierNetworkPreference.setVisible(true);
            this.mCarrierNetworkPreference.setSummary(getCarrierNetworkSsid());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public void onWifiEntriesChanged(int i) {
        onWifiEntriesChanged();
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public void onNumSavedNetworksChanged() {}

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public void onNumSavedSubscriptionsChanged() {}

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public /* bridge */ /* synthetic */ void onScanRequested() {}
}
