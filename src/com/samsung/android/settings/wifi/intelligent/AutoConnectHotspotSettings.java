package com.samsung.android.settings.wifi.intelligent;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.widget.SecRadioButtonPreference;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AutoConnectHotspotSettings extends SettingsPreferenceFragment
        implements SecRadioButtonPreference.OnClickListener {
    public FragmentActivity mActivity;
    public SecRadioButtonPreference mAutoConnectHotspotAlways;
    public SecRadioButtonPreference mAutoConnectHotspotAskBeforeConnecting;
    public SecRadioButtonPreference mAutoConnectHotspotNever;
    public SecSwitchPreference mDisconnectWhenNotInUseSwitch;
    public SemWifiManager mWifiManager;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_wifi_ap_wifi_auto_connect_hotspot_screen);
        getActivity().setTitle(WifiApUtils.getStringID(R.string.wifi_auto_connect_hotspot_title));
        findPreference("auto_connect_hotspot_header")
                .setTitle(
                        WifiApUtils.getStringID(
                                R.string.wifi_auto_connect_hotspot_detail_description));
        this.mWifiManager =
                (SemWifiManager) getContext().getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mAutoConnectHotspotNever =
                (SecRadioButtonPreference) findPreference("auto_connect_hotspot_never_option");
        this.mAutoConnectHotspotAskBeforeConnecting =
                (SecRadioButtonPreference)
                        findPreference("auto_connect_hotspot_ask_before_connecting_option");
        SecRadioButtonPreference secRadioButtonPreference =
                (SecRadioButtonPreference) findPreference("auto_connect_hotspot_always_option");
        this.mAutoConnectHotspotAlways = secRadioButtonPreference;
        this.mAutoConnectHotspotNever.mListener = this;
        this.mAutoConnectHotspotAskBeforeConnecting.mListener = this;
        secRadioButtonPreference.mListener = this;
        setAutoConnectHotspotRadioButton(this.mWifiManager.getAdvancedAutohotspotConnectSettings());
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) findPreference("disconnect_when_not_in_use");
        this.mDisconnectWhenNotInUseSwitch = secSwitchPreference;
        secSwitchPreference.setSummary(
                Utils.isTablet()
                        ? R.string.wifi_disconnect_when_not_in_use_description_tablet
                        : R.string.wifi_disconnect_when_not_in_use_description_phone);
        this.mDisconnectWhenNotInUseSwitch.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.samsung.android.settings.wifi.intelligent.AutoConnectHotspotSettings.1
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        AutoConnectHotspotSettings autoConnectHotspotSettings =
                                AutoConnectHotspotSettings.this;
                        autoConnectHotspotSettings.mWifiManager.setAdvancedAutohotspotLCDSettings(
                                (autoConnectHotspotSettings.mWifiManager
                                                                .getAdvancedAutohotspotLCDSettings()
                                                        == 2)
                                                ^ true
                                        ? 2
                                        : 1);
                        autoConnectHotspotSettings.refreshDisconnectWhenNotInUseSwitch();
                        return true;
                    }
                });
        refreshDisconnectWhenNotInUseSwitch();
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        String key = secRadioButtonPreference.getKey();
        key.getClass();
        switch (key) {
            case "auto_connect_hotspot_ask_before_connecting_option":
                this.mWifiManager.setAdvancedAutohotspotConnectSettings(2);
                setAutoConnectHotspotRadioButton(2);
                break;
            case "auto_connect_hotspot_always_option":
                this.mWifiManager.setAdvancedAutohotspotConnectSettings(3);
                setAutoConnectHotspotRadioButton(3);
                break;
            case "auto_connect_hotspot_never_option":
                this.mWifiManager.setAdvancedAutohotspotConnectSettings(1);
                setAutoConnectHotspotRadioButton(1);
                break;
        }
        refreshDisconnectWhenNotInUseSwitch();
    }

    public final void refreshDisconnectWhenNotInUseSwitch() {
        if (this.mWifiManager.getAdvancedAutohotspotConnectSettings() == 1) {
            this.mDisconnectWhenNotInUseSwitch.setChecked(false);
            this.mDisconnectWhenNotInUseSwitch.setEnabled(false);
        } else {
            this.mDisconnectWhenNotInUseSwitch.setEnabled(true);
            this.mDisconnectWhenNotInUseSwitch.setChecked(
                    this.mWifiManager.getAdvancedAutohotspotLCDSettings() == 2);
        }
    }

    public final void setAutoConnectHotspotRadioButton(int i) {
        if (i == 1) {
            this.mAutoConnectHotspotNever.setChecked(true);
            this.mAutoConnectHotspotAskBeforeConnecting.setChecked(false);
            this.mAutoConnectHotspotAlways.setChecked(false);
        } else if (i == 2) {
            this.mAutoConnectHotspotNever.setChecked(false);
            this.mAutoConnectHotspotAskBeforeConnecting.setChecked(true);
            this.mAutoConnectHotspotAlways.setChecked(false);
        } else {
            if (i != 3) {
                return;
            }
            this.mAutoConnectHotspotNever.setChecked(false);
            this.mAutoConnectHotspotAskBeforeConnecting.setChecked(false);
            this.mAutoConnectHotspotAlways.setChecked(true);
        }
    }
}
