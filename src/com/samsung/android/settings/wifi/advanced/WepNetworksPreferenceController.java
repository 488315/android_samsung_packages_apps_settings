package com.samsung.android.settings.wifi.advanced;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settingslib.wifi.WifiWarningDialogController;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WepNetworksPreferenceController extends TogglePreferenceController {
    private final WifiWarningDialogController mWarningDialogController;
    Preference mWepNetworksPref;
    private final WifiManager mWifiManager;

    public WepNetworksPreferenceController(Context context, String str) {
        super(context, str);
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mWarningDialogController = new WifiWarningDialogController(this.mContext);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mWepNetworksPref = findPreference;
        refreshSummary(findPreference);
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
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mWifiManager.isWepSupported()
                ? this.mContext.getResources().getString(R.string.wifi_allow_wep_networks_summary)
                : this.mContext
                        .getResources()
                        .getString(R.string.wifi_allow_wep_networks_summary_carrier_not_allow);
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
        return SemWifiEntryFlags.isWepAllowed(this.mContext);
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (z || connectionInfo == null || connectionInfo.getCurrentSecurityType() != 1) {
            this.mWifiManager.setWepAllowed(z);
            SemWifiEntryFlags.isWepAllowed = -1;
            return true;
        }
        WifiWarningDialogController wifiWarningDialogController = this.mWarningDialogController;
        wifiWarningDialogController.getClass();
        Intent wifiWarningIntent = WifiWarningDialogController.getWifiWarningIntent();
        wifiWarningIntent.putExtra("req_type", 0);
        wifiWarningIntent.putExtra("extra_type", 8);
        try {
            wifiWarningDialogController.mContext.startActivity(wifiWarningIntent);
        } catch (ActivityNotFoundException unused) {
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
