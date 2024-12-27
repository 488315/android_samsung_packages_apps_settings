package com.samsung.android.settings.wifi.advanced;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class Hotspot20Settings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public final boolean mHasSwitch = true;
    public final IntentFilter mIntentFilter = new IntentFilter();
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.advanced.Hotspot20Settings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    Log.d("Hotspot20Settings", "Wificonnect>>> " + intent.getAction());
                    String action = intent.getAction();
                    action.getClass();
                    if (!action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                        if (action.equals("android.net.wifi.CONFIGURED_NETWORKS_CHANGE")) {
                            Hotspot20Settings.this.getPasspointConfigurations();
                            return;
                        }
                        return;
                    }
                    int intExtra = intent.getIntExtra("wifi_state", 4);
                    Hotspot20Settings hotspot20Settings = Hotspot20Settings.this;
                    hotspot20Settings.getClass();
                    if (intExtra == 0 || intExtra == 1) {
                        ((SettingsActivity) hotspot20Settings.getActivity())
                                .finishPreferencePanel(null);
                    }
                }
            };
    public PreferenceScreen mScreen;
    public SettingsMainSwitchBar mSwitchBar;
    public WifiManager mWifiManager;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 106;
    }

    public final void getPasspointConfigurations() {
        this.mScreen.removeAll();
        try {
            for (PasspointConfiguration passpointConfiguration :
                    this.mWifiManager.getPasspointConfigurations()) {
                if (passpointConfiguration.getHomeSp() != null) {
                    String fqdn = passpointConfiguration.getHomeSp().getFqdn();
                    String friendlyName = passpointConfiguration.getHomeSp().getFriendlyName();
                    if (!TextUtils.isEmpty(friendlyName)
                            && !TextUtils.isEmpty(fqdn)
                            && !"Vendor Hotspot2.0 Profile".equals(friendlyName)) {
                        this.mScreen.addPreference(
                                new Hotspot20Preference(getContext(), passpointConfiguration));
                    }
                }
            }
            if (this.mScreen.getPreferenceCount() == 0) {
                WifiHistoryNoItemsPreference wifiHistoryNoItemsPreference =
                        new WifiHistoryNoItemsPreference(getContext());
                wifiHistoryNoItemsPreference.setTitle(R.string.wifi_no_saved_network);
                wifiHistoryNoItemsPreference.seslSetSubheaderRoundedBackground(0);
                wifiHistoryNoItemsPreference.setSelectable(false);
                this.mScreen.addPreference(wifiHistoryNoItemsPreference);
                this.mScreen.seslSetSubheaderRoundedBackground(0);
            }
        } catch (UnsupportedOperationException e) {
            Log.d(
                    "Hotspot20Settings",
                    "Caught exception while setPasspointPreferenceCategory: " + e);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        if (this.mHasSwitch) {
            settingsMainSwitchBar.show();
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        WifiInfo connectionInfo;
        this.mWifiManager.setWifiPasspointEnabled(z);
        if (z
                || (connectionInfo = this.mWifiManager.getConnectionInfo()) == null
                || !connectionInfo.isPasspointAp()) {
            return;
        }
        Context context = getContext();
        ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                .reportIssue(
                        100,
                        WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerApi(
                                -1,
                                "disconnect",
                                context.getPackageManager().getNameForUid(context.getUserId()),
                                context.getPackageName()));
        this.mWifiManager.disconnect();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_hotspot20_settings);
        this.mWifiManager = (WifiManager) getActivity().getSystemService(ImsProfile.PDN_WIFI);
        this.mIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.mIntentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mScreen = preferenceScreen;
        if (preferenceScreen == null) {
            ((SettingsActivity) getActivity()).finishPreferencePanel(null);
        } else {
            preferenceScreen.mOrderingAsAdded = true;
            preferenceScreen.setSelectable(false);
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mSwitchBar.hide();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(this.mReceiver);
        this.mSwitchBar.removeOnSwitchChangeListener(this);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity().registerReceiver(this.mReceiver, this.mIntentFilter);
        getPasspointConfigurations();
        SALogging.insertSALog("WIFI_260");
        this.mSwitchBar.addOnSwitchChangeListener(this);
        this.mSwitchBar.setChecked(this.mWifiManager.isWifiPasspointEnabled());
    }
}
