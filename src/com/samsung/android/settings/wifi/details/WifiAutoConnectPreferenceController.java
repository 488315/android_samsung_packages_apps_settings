package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.scloud.SCloudWifiDataManager;
import com.samsung.android.settings.wifi.WifiDevicePolicyManager;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiAutoConnectPreferenceController extends TogglePreferenceController {
    private static final String KEY_AUTO_CONNECT_PREF = "auto_connect";
    private static final String TAG = "WifiAutoConnectPrefCtrl";
    private SwitchPreferenceCompat mAutoConnectPref;
    private boolean mAutoJoinStore;
    private final Fragment mFragment;
    boolean mIsBlockUnsecureWifiAutojoin;
    private final String mSAScreenId;
    private final WifiEntry mWifiEntry;
    private final WifiManager mWifiManager;

    public WifiAutoConnectPreferenceController(
            Context context,
            WifiEntry wifiEntry,
            Fragment fragment,
            WifiManager wifiManager,
            String str) {
        super(context, KEY_AUTO_CONNECT_PREF);
        this.mWifiEntry = wifiEntry;
        this.mFragment = fragment;
        this.mAutoJoinStore = wifiEntry == null || wifiEntry.isAutoJoinEnabled();
        this.mIsBlockUnsecureWifiAutojoin =
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "rampart_blocked_unsecure_wifi_autojoin",
                                0)
                        == 1;
        this.mSAScreenId = str;
        this.mWifiManager = wifiManager;
    }

    private void exitActivity() {
        FragmentActivity activity = this.mFragment.getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    private boolean isCarrierNetworkAllowAutoJoinEnabled(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration == null
                ? this.mAutoJoinStore
                : Settings.Global.getInt(
                                        this.mContext.getContentResolver(),
                                        "sem_wifi_carrier_network_offload_enabled",
                                        1)
                                == 1
                        && wifiConfiguration.allowAutojoin;
    }

    private boolean isSupportAutoJoin() {
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry == null) {
            return false;
        }
        if (wifiEntry.getWifiConfiguration() == null) {
            return true;
        }
        WifiEntry wifiEntry2 = this.mWifiEntry;
        if (wifiEntry2.mSemFlags.isLocked) {
            return false;
        }
        return WifiDevicePolicyManager.canModifyNetwork(
                this.mContext, wifiEntry2.getWifiConfiguration());
    }

    private boolean isWeakSecuredWifiBlockedByAutoBlocker(int i) {
        return this.mIsBlockUnsecureWifiAutojoin && (i == 0 || i == 1 || i == 4);
    }

    private void syncAutoReconnectAndCarrierNetworkOffload(
            boolean z, WifiConfiguration wifiConfiguration) {
        if (z) {
            updateAllowAutojoin(true);
        }
        if (wifiConfiguration == null
                || this.mWifiManager.isCarrierNetworkOffloadEnabled(
                                wifiConfiguration.subscriptionId, false)
                        == z) {
            return;
        }
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "sem_wifi_carrier_network_offload_enabled",
                z ? 1 : 0);
    }

    private void updateAllowAutojoin(boolean z) {
        if (this.mWifiEntry.isAutoJoinEnabled() == z || !this.mWifiEntry.canSetAutoJoinEnabled()) {
            return;
        }
        this.mWifiEntry.setAutoJoinEnabled(z);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SwitchPreferenceCompat switchPreferenceCompat =
                (SwitchPreferenceCompat) preferenceScreen.findPreference(getPreferenceKey());
        this.mAutoConnectPref = switchPreferenceCompat;
        switchPreferenceCompat.setOnPreferenceChangeListener(this);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return isSupportAutoJoin() ? 0 : 2;
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
        if (isWeakSecuredWifiBlockedByAutoBlocker(this.mWifiEntry.getSecurity$1())) {
            return false;
        }
        WifiEntry wifiEntry = this.mWifiEntry;
        return wifiEntry.mSemFlags.isCarrierNetwork
                ? isCarrierNetworkAllowAutoJoinEnabled(wifiEntry.getWifiConfiguration())
                : this.mAutoJoinStore;
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
        SALogging.insertSALog(z ? 1L : 0L, this.mSAScreenId, "1022");
        HashMap hashMap = new HashMap();
        hashMap.put("security_type", String.valueOf(this.mWifiEntry.semGetHighestSecurity()));
        hashMap.put(
                "is_carrier_network", String.valueOf(this.mWifiEntry.mSemFlags.isCarrierNetwork));
        SALogging.insertSALog(this.mSAScreenId, "1022", hashMap, 0);
        WifiConfiguration wifiConfiguration = this.mWifiEntry.getWifiConfiguration();
        if (wifiConfiguration != null) {
            SCloudWifiDataManager.getInstance(this.mContext.getApplicationContext())
                    .syncToAddOrUpdated(wifiConfiguration);
        }
        if (this.mWifiEntry.mSemFlags.isCarrierNetwork) {
            syncAutoReconnectAndCarrierNetworkOffload(z, wifiConfiguration);
        } else {
            updateAllowAutojoin(z);
        }
        if (this.mWifiEntry.getConnectedState() == 2 && !z) {
            Context context = this.mContext;
            ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                    .reportIssue(
                            100,
                            WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerApi(
                                    -1,
                                    "disconnect",
                                    context.getPackageManager().getNameForUid(context.getUserId()),
                                    context.getPackageName()));
            this.mWifiManager.disconnect();
            exitActivity();
        }
        this.mAutoJoinStore = z;
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        int security$1 = this.mWifiEntry.getSecurity$1();
        if (!SemWifiEntryFlags.isWepAllowed(this.mContext) && security$1 == 1) {
            preference.setEnabled(false);
            preference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(R.string.wifi_wep_networks_blocked_summary));
        } else if (isWeakSecuredWifiBlockedByAutoBlocker(security$1)) {
            preference.setEnabled(false);
            preference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(R.string.wifi_auto_blocker_blocked_summary));
        }
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
