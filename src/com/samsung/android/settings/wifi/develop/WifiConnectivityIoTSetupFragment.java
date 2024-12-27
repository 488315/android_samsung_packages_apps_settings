package com.samsung.android.settings.wifi.develop;

import android.content.Context;
import android.net.wifi.MloLink;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;
import com.sec.ims.settings.ImsProfile;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiConnectivityIoTSetupFragment extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public WifiApPreference mBssidDetailsLayoutPreference;
    public Context mContext;
    public LayoutPreference mIoTDescriptionLayoutPreference;
    public final AnonymousClass1 mOnWifiConnectivityIoTSetupSwitchChangeListener =
            new WifiConnectivityIoTSetupSwitchEnabler.OnStateChangeListener() { // from class:
                // com.samsung.android.settings.wifi.develop.WifiConnectivityIoTSetupFragment.1
                @Override // com.samsung.android.settings.wifi.develop.WifiConnectivityIoTSetupSwitchEnabler.OnStateChangeListener
                public final void onStateChanged(int i) {
                    String str;
                    String str2;
                    String str3;
                    String str4;
                    boolean z;
                    int i2;
                    ScanResult filteredScanResult;
                    WifiConnectivityIoTSetupFragment wifiConnectivityIoTSetupFragment =
                            WifiConnectivityIoTSetupFragment.this;
                    int i3 =
                            Settings.Secure.getInt(
                                    wifiConnectivityIoTSetupFragment.mContext.getContentResolver(),
                                    "sec_wifi_iot_setup_enabled",
                                    0);
                    DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                            "switch onStateChanged() - resultCode: ",
                            ", value: ",
                            i,
                            i3,
                            "WifiConnectivityIoTSetupFragment");
                    WifiInfo connectionInfo =
                            wifiConnectivityIoTSetupFragment.mWifiManager.getConnectionInfo();
                    if (connectionInfo != null) {
                        str2 = connectionInfo.getSSID().replaceAll("\"", ApnSettings.MVNO_NONE);
                        str3 = connectionInfo.getBSSID();
                        i2 = connectionInfo.getFrequency();
                        wifiConnectivityIoTSetupFragment.mWifiConnectivityIoTSetupSwitchEnabler
                                .getClass();
                        str4 = WifiConnectivityIoTSetupSwitchEnabler.getFrequencyBand(i2);
                        List<MloLink> associatedMloLinks = connectionInfo.getAssociatedMloLinks();
                        z = associatedMloLinks != null && associatedMloLinks.size() > 0;
                        List<ScanResult> scanResults =
                                wifiConnectivityIoTSetupFragment.mWifiManager.getScanResults();
                        if (scanResults == null
                                || (filteredScanResult =
                                                wifiConnectivityIoTSetupFragment
                                                        .mWifiConnectivityIoTSetupSwitchEnabler
                                                        .getFilteredScanResult(scanResults))
                                        == null) {
                            str = ApnSettings.MVNO_NONE;
                        } else {
                            String str5 = filteredScanResult.BSSID;
                            int i4 = filteredScanResult.frequency;
                            wifiConnectivityIoTSetupFragment.mWifiConnectivityIoTSetupSwitchEnabler
                                    .getClass();
                            String frequencyBand =
                                    WifiConnectivityIoTSetupSwitchEnabler.getFrequencyBand(i4);
                            StringBuilder m =
                                    PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                            i4, "Matching BSSID's:", str5, "\nChannel:", "\nBand:");
                            m.append(frequencyBand);
                            str = m.toString();
                        }
                    } else {
                        str = ApnSettings.MVNO_NONE;
                        str2 = str;
                        str3 = str2;
                        str4 = str3;
                        z = false;
                        i2 = 0;
                    }
                    StringBuilder m2 =
                            SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                    "onStateChanged() - connectedSsid: ",
                                    str2,
                                    ", connectedBSSID: ",
                                    str3,
                                    ", connectedFreq: ");
                    m2.append(i2);
                    m2.append(", isNoMLOLink: ");
                    m2.append(z);
                    m2.append(", matchResult: ");
                    Utils$$ExternalSyntheticOutline0.m(m2, str, "WifiConnectivityIoTSetupFragment");
                    wifiConnectivityIoTSetupFragment.mSwitchBar.setChecked(i3 == 1);
                    wifiConnectivityIoTSetupFragment.mSsidDetailsLayoutPreference.setSummary(
                            ApnSettings.MVNO_NONE);
                    wifiConnectivityIoTSetupFragment.mBssidDetailsLayoutPreference.setSummary(
                            ApnSettings.MVNO_NONE);
                    wifiConnectivityIoTSetupFragment.mScanResultsLayoutTextView.setText(
                            ApnSettings.MVNO_NONE);
                    switch (i) {
                        case 1:
                        case 2:
                            wifiConnectivityIoTSetupFragment.mSwitchBar.setEnabled(false);
                            wifiConnectivityIoTSetupFragment.mScanResultsLayoutTextView.setText(
                                    "Wi-Fi state is Disconnected \nPlease try Wi-Fi ON/OFF");
                            break;
                        case 3:
                            wifiConnectivityIoTSetupFragment.mSwitchBar.setEnabled(false);
                            if (str3 != null) {
                                WifiApPreference wifiApPreference =
                                        wifiConnectivityIoTSetupFragment
                                                .mSsidDetailsLayoutPreference;
                                StringBuilder m3 =
                                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                                "SSID:", str2, "\nBSSID:", str3, "\nBand:");
                                m3.append(str4);
                                wifiApPreference.setSummary(m3.toString());
                            }
                            wifiConnectivityIoTSetupFragment.mBssidDetailsLayoutPreference
                                    .setSummary("No Matching BSSID's exist with Connected SSID");
                            wifiConnectivityIoTSetupFragment.mScanResultsLayoutTextView.setText(
                                    "Switch is disabled due to below reasons:\n"
                                        + "1. There is no available 2.4GHz BSSID on current"
                                        + " network.\n"
                                        + "2. Make sure your 2.4GHz and 5GHz AP is configured with"
                                        + " same SSID, Security & Password.\n"
                                        + "NOTE: After successful connection , please do Wi-Fi"
                                        + " ON/OFF to disable this feature.");
                            break;
                        case 4:
                            wifiConnectivityIoTSetupFragment.mSwitchBar.setEnabled(true);
                            if (str3 != null) {
                                WifiApPreference wifiApPreference2 =
                                        wifiConnectivityIoTSetupFragment
                                                .mSsidDetailsLayoutPreference;
                                StringBuilder m4 =
                                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                                "SSID:", str2, "\nBSSID:", str3, "\nBand:");
                                m4.append(str4);
                                wifiApPreference2.setSummary(m4.toString());
                            }
                            wifiConnectivityIoTSetupFragment.mBssidDetailsLayoutPreference
                                    .setSummary(str);
                            wifiConnectivityIoTSetupFragment.mScanResultsLayoutTextView.setText(
                                    "Please try below steps in case of connection failure to 2.4GHz"
                                        + " BSSID: \n"
                                        + "1. Turn Wi-Fi ON/OFF\n"
                                        + "2. Connect to SSID which supports & operates on both 2.4"
                                        + " & 5GHz bands.\n"
                                        + "3. Click on \"Connect to 2.4GHz for IOT setup\"\n"
                                        + "4. For more details on connection failure refer to"
                                        + " Connection History");
                            break;
                        case 5:
                            wifiConnectivityIoTSetupFragment.mSwitchBar.setEnabled(false);
                            if (str3 != null) {
                                WifiApPreference wifiApPreference3 =
                                        wifiConnectivityIoTSetupFragment
                                                .mSsidDetailsLayoutPreference;
                                StringBuilder m5 =
                                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                                "SSID:", str2, "\nBSSID:", str3, "\nBand:");
                                m5.append(str4);
                                wifiApPreference3.setSummary(m5.toString());
                            }
                            wifiConnectivityIoTSetupFragment.mBssidDetailsLayoutPreference
                                    .setSummary(str);
                            wifiConnectivityIoTSetupFragment.mScanResultsLayoutTextView.setText(
                                    "Switch is disabled due to below reasons:\n"
                                        + "1. There is no available 2.4GHz BSSID on current"
                                        + " network.\n"
                                        + "2. Make sure your 2.4GHz and 5GHz AP is configured with"
                                        + " same SSID, Security & Password.\n"
                                        + "NOTE: After successful connection , please do Wi-Fi"
                                        + " ON/OFF to disable this feature.");
                            break;
                        case 6:
                            wifiConnectivityIoTSetupFragment.mSwitchBar.setEnabled(true);
                            if (str3 != null) {
                                WifiApPreference wifiApPreference4 =
                                        wifiConnectivityIoTSetupFragment
                                                .mSsidDetailsLayoutPreference;
                                StringBuilder m6 =
                                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                                "SSID:", str2, "\nBSSID:", str3, "\nBand:");
                                m6.append(str4);
                                wifiApPreference4.setSummary(m6.toString());
                            }
                            wifiConnectivityIoTSetupFragment.mBssidDetailsLayoutPreference
                                    .setSummary(str);
                            wifiConnectivityIoTSetupFragment.mScanResultsLayoutTextView.setText(
                                    ApnSettings.MVNO_NONE);
                            break;
                        case 7:
                            wifiConnectivityIoTSetupFragment.mSwitchBar.setEnabled(false);
                            WifiApPreference wifiApPreference5 =
                                    wifiConnectivityIoTSetupFragment.mSsidDetailsLayoutPreference;
                            StringBuilder m7 =
                                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                            "SSID:", str2, "\nBSSID:", str3, "\nBand:");
                            m7.append(str4);
                            wifiApPreference5.setSummary(m7.toString());
                            wifiConnectivityIoTSetupFragment.mBssidDetailsLayoutPreference
                                    .setSummary(ApnSettings.MVNO_NONE);
                            wifiConnectivityIoTSetupFragment.mScanResultsLayoutTextView.setText(
                                    "Device is connected to 2.4GHz BSSID");
                            break;
                        case 8:
                            wifiConnectivityIoTSetupFragment.mSwitchBar.setEnabled(false);
                            WifiApPreference wifiApPreference6 =
                                    wifiConnectivityIoTSetupFragment.mSsidDetailsLayoutPreference;
                            StringBuilder m8 =
                                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                            "SSID:", str2, "\nBSSID:", str3, "\nBand:");
                            m8.append(str4);
                            wifiApPreference6.setSummary(m8.toString());
                            wifiConnectivityIoTSetupFragment.mBssidDetailsLayoutPreference
                                    .setSummary(ApnSettings.MVNO_NONE);
                            wifiConnectivityIoTSetupFragment.mScanResultsLayoutTextView.setText(
                                    "Device is connected to 2.4GHz BSSID");
                            break;
                        default:
                            if (str3 != null) {
                                wifiConnectivityIoTSetupFragment.mSwitchBar.setEnabled(false);
                                WifiApPreference wifiApPreference7 =
                                        wifiConnectivityIoTSetupFragment
                                                .mSsidDetailsLayoutPreference;
                                StringBuilder m9 =
                                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                                "SSID:", str2, "\nBSSID:", str3, "\nBand:");
                                m9.append(str4);
                                wifiApPreference7.setSummary(m9.toString());
                                wifiConnectivityIoTSetupFragment.mBssidDetailsLayoutPreference
                                        .setSummary(str);
                                wifiConnectivityIoTSetupFragment.mScanResultsLayoutTextView.setText(
                                        "Device is trying to connect to 2.4GHz BSSID");
                                break;
                            }
                            break;
                    }
                }
            };
    public LayoutPreference mScanResultsLayoutPreference;
    public TextView mScanResultsLayoutTextView;
    public WifiApPreference mSsidDetailsLayoutPreference;
    public SettingsMainSwitchBar mSwitchBar;
    public WifiConnectivityIoTSetupSwitchEnabler mWifiConnectivityIoTSetupSwitchEnabler;
    public WifiManager mWifiManager;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiConnectivityIoTSetupFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_iot_setup_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Log.d("WifiConnectivityIoTSetupFragment", " onActivityCreated");
        this.mWifiConnectivityIoTSetupSwitchEnabler.mOnStateChangeListener =
                this.mOnWifiConnectivityIoTSetupSwitchChangeListener;
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.show();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "onCheckedChanged: ", "WifiConnectivityIoTSetupFragment", z);
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "sec_wifi_iot_setup_enabled", z ? 1 : 0);
        if (z) {
            this.mSwitchBar.setEnabled(false);
        }
        this.mWifiConnectivityIoTSetupSwitchEnabler.setChecked(z);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        this.mContext = getContext();
        Log.d("WifiConnectivityIoTSetupFragment", "onCreate called ");
        LayoutPreference layoutPreference =
                (LayoutPreference) findPreference("iot_description_layout_preference");
        this.mIoTDescriptionLayoutPreference = layoutPreference;
        layoutPreference.seslSetSubheaderRoundedBackground(0);
        ((TextView) this.mIoTDescriptionLayoutPreference.mRootView.findViewById(R.id.title))
                .setText(
                        "This feature ensures that Mobile device stays connected to 2.4GHz band"
                            + " when Wi-Fi AP is configured with same name on multiple bands.This"
                            + " is useful when setting up IOT devices which are connected to 2.4GHz"
                            + " band. \n"
                            + "NOTE : This feature is reset by switching Wi-Fi ON/OFF");
        WifiApPreference wifiApPreference =
                (WifiApPreference) findPreference("connected_ssid_preference");
        this.mSsidDetailsLayoutPreference = wifiApPreference;
        wifiApPreference.setTitle("Connected SSID");
        this.mSsidDetailsLayoutPreference.mSummaryMaxLines = 5;
        WifiApPreference wifiApPreference2 = (WifiApPreference) findPreference("bssid_preference");
        this.mBssidDetailsLayoutPreference = wifiApPreference2;
        wifiApPreference2.setTitle("Matching BSSID");
        this.mBssidDetailsLayoutPreference.mSummaryMaxLines = 5;
        LayoutPreference layoutPreference2 =
                (LayoutPreference) findPreference("matching_scan_results_preference");
        this.mScanResultsLayoutPreference = layoutPreference2;
        layoutPreference2.seslSetSubheaderRoundedBackground(0);
        this.mScanResultsLayoutTextView =
                (TextView) this.mScanResultsLayoutPreference.mRootView.findViewById(R.id.title);
        this.mWifiConnectivityIoTSetupSwitchEnabler =
                new WifiConnectivityIoTSetupSwitchEnabler(this);
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
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.addOnSwitchChangeListener(this);
        }
        this.mWifiConnectivityIoTSetupSwitchEnabler.updateSwitchState(false);
    }
}
