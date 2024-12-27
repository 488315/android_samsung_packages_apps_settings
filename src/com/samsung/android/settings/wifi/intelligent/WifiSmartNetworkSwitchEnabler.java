package com.samsung.android.settings.wifi.intelligent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.preference.Preference;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiSmartNetworkSwitchEnabler implements CompoundButton.OnCheckedChangeListener {
    public final SnsRadioButtonPreference mAgg;
    public final Context mContext;
    public boolean mEnabled;
    public final SecPreferenceScreen mExcludedGroup;
    public final IntentFilter mFilter;
    public boolean mIsSwitchForIndividualAppsEnabled;
    public final SnsRadioButtonPreference mNormal;
    public final SettingsMainSwitchBar mSwitchBar;
    public final SwitchPreferenceCompat mSwitchForIndividualApps;
    public final WifiManager mWifiManager;
    public final AnonymousClass1 mMobileDataObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.wifi.intelligent.WifiSmartNetworkSwitchEnabler.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    WifiSmartNetworkSwitchEnabler.this.updateSmartNetworkSwitchServiceCheck();
                }
            };
    public final AnonymousClass2 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.intelligent.WifiSmartNetworkSwitchEnabler.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                        int intExtra = intent.getIntExtra("wifi_state", 4);
                        if (intExtra == 3 || intExtra == 1) {
                            WifiSmartNetworkSwitchEnabler.this
                                    .updateSmartNetworkSwitchServiceCheck();
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                        WifiSmartNetworkSwitchEnabler.this.updateSmartNetworkSwitchServiceCheck();
                    } else if (action.equals("android.intent.action.SIM_STATE_CHANGED")
                            || action.equals("android.intent.action.ANY_DATA_STATE")) {
                        WifiSmartNetworkSwitchEnabler.this.updateSmartNetworkSwitchServiceCheck();
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.wifi.intelligent.WifiSmartNetworkSwitchEnabler$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.wifi.intelligent.WifiSmartNetworkSwitchEnabler$2] */
    public WifiSmartNetworkSwitchEnabler(
            SmartNetworkSwitchSettings smartNetworkSwitchSettings,
            SettingsMainSwitchBar settingsMainSwitchBar,
            SecPreferenceScreen secPreferenceScreen,
            SnsRadioButtonPreference snsRadioButtonPreference,
            SnsRadioButtonPreference snsRadioButtonPreference2,
            SwitchPreferenceCompat switchPreferenceCompat) {
        Context context = smartNetworkSwitchSettings.getContext();
        this.mContext = context;
        this.mExcludedGroup = secPreferenceScreen;
        if (switchPreferenceCompat != null) {
            this.mSwitchForIndividualApps = switchPreferenceCompat;
            boolean z =
                    Settings.Global.getInt(
                                    context.getContentResolver(),
                                    "wifi_switch_for_individual_apps_enabled",
                                    0)
                            == 1;
            this.mIsSwitchForIndividualAppsEnabled = z;
            switchPreferenceCompat.setChecked(z);
            Log.d(
                    "WifiSmartNetworkSwitchEnabler",
                    "Switch for individual apps: " + this.mIsSwitchForIndividualAppsEnabled);
            switchPreferenceCompat.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.samsung.android.settings.wifi.intelligent.WifiSmartNetworkSwitchEnabler.3
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            Log.d(
                                    "WifiSmartNetworkSwitchEnabler",
                                    "onPreferenceChange: SwitchForIndividualApps - " + obj);
                            return Settings.Global.putInt(
                                    WifiSmartNetworkSwitchEnabler.this.mContext
                                            .getContentResolver(),
                                    "wifi_switch_for_individual_apps_enabled",
                                    ((Boolean) obj).booleanValue() ? 1 : 0);
                        }
                    });
        }
        this.mSwitchBar = settingsMainSwitchBar;
        context.getString(R.string.screen_wifi_adaptive_wifi_setting);
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.ANY_DATA_STATE");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        this.mEnabled =
                Settings.Global.getInt(
                                context.getContentResolver(),
                                "wifi_watchdog_poor_network_test_enabled",
                                0)
                        == 1;
        Log.d(
                "WifiSmartNetworkSwitchEnabler",
                "WifiSnsSettingDialog appears with SNS Enabled : " + this.mEnabled);
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        if (this.mEnabled) {
            settingsMainSwitchBar.setChecked(true);
        } else {
            settingsMainSwitchBar.setChecked(false);
        }
        this.mAgg = snsRadioButtonPreference2;
        this.mNormal = snsRadioButtonPreference;
        updateRadioGroup();
        if (snsRadioButtonPreference2 == null || snsRadioButtonPreference == null) {
            return;
        }
        snsRadioButtonPreference2.mListener = this;
        snsRadioButtonPreference.mListener = this;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.Global.getInt(
                this.mContext.getContentResolver(),
                "wifi_watchdog_poor_network_aggressive_mode_on",
                0);
        int i =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "wifi_num_of_switch_to_mobile_data_toggle",
                        -1);
        int i2 = i == -1 ? 0 : i + 1;
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "wifi_num_of_switch_to_mobile_data_toggle", i2);
        if (z) {
            Settings.Global.putInt(
                    this.mContext.getContentResolver(),
                    "wifi_watchdog_poor_network_test_enabled",
                    1);
            Log.d("WifiSmartNetworkSwitchEnabler", "SNS On pressed" + i2);
            SnsRadioButtonPreference snsRadioButtonPreference = this.mNormal;
            if (snsRadioButtonPreference != null && this.mAgg != null) {
                snsRadioButtonPreference.setEnabled(true);
                this.mAgg.setEnabled(true);
            }
            SwitchPreferenceCompat switchPreferenceCompat = this.mSwitchForIndividualApps;
            if (switchPreferenceCompat != null) {
                switchPreferenceCompat.setEnabled(true);
            }
        } else {
            Settings.Global.putInt(
                    this.mContext.getContentResolver(),
                    "wifi_watchdog_poor_network_test_enabled",
                    0);
            SnsRadioButtonPreference snsRadioButtonPreference2 = this.mNormal;
            if (snsRadioButtonPreference2 != null && this.mAgg != null) {
                snsRadioButtonPreference2.setEnabled(false);
                this.mAgg.setEnabled(false);
            }
            SwitchPreferenceCompat switchPreferenceCompat2 = this.mSwitchForIndividualApps;
            if (switchPreferenceCompat2 != null) {
                switchPreferenceCompat2.setEnabled(false);
            }
            Log.d("WifiSmartNetworkSwitchEnabler", "SNS Off pressed");
        }
        LoggingHelper.insertEventLogging(3118, 3119, z);
    }

    public final void updateRadioGroup() {
        SnsRadioButtonPreference snsRadioButtonPreference;
        boolean z =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "wifi_watchdog_poor_network_aggressive_mode_on",
                                0)
                        == 1;
        SnsRadioButtonPreference snsRadioButtonPreference2 = this.mNormal;
        if (snsRadioButtonPreference2 == null || (snsRadioButtonPreference = this.mAgg) == null) {
            return;
        }
        if (z) {
            snsRadioButtonPreference.setChecked(true);
            this.mNormal.setChecked(false);
        } else {
            snsRadioButtonPreference2.setChecked(true);
            this.mAgg.setChecked(false);
        }
        boolean z2 = this.mEnabled;
        if (z2) {
            return;
        }
        this.mNormal.setEnabled(z2);
        this.mAgg.setEnabled(this.mEnabled);
    }

    public final void updateSmartNetworkSwitchServiceCheck() {
        this.mEnabled =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "wifi_watchdog_poor_network_test_enabled",
                                0)
                        == 1;
        if (this.mExcludedGroup == null) {
            return;
        }
        if (!this.mWifiManager.isWifiEnabled()) {
            this.mExcludedGroup.setSelectable(false);
            SwitchPreferenceCompat switchPreferenceCompat = this.mSwitchForIndividualApps;
            if (switchPreferenceCompat != null) {
                switchPreferenceCompat.setEnabled(false);
            }
            SnsRadioButtonPreference snsRadioButtonPreference = this.mNormal;
            if (snsRadioButtonPreference == null || this.mAgg == null) {
                return;
            }
            snsRadioButtonPreference.setEnabled(false);
            this.mAgg.setEnabled(false);
            return;
        }
        int updateSmartNetworkSwitchAvailability =
                Utils.updateSmartNetworkSwitchAvailability(this.mContext);
        if (updateSmartNetworkSwitchAvailability == 1) {
            this.mExcludedGroup.setSelectable(true);
            SwitchPreferenceCompat switchPreferenceCompat2 = this.mSwitchForIndividualApps;
            if (switchPreferenceCompat2 != null) {
                switchPreferenceCompat2.setEnabled(this.mEnabled);
            }
            SnsRadioButtonPreference snsRadioButtonPreference2 = this.mNormal;
            if (snsRadioButtonPreference2 == null || this.mAgg == null || !this.mEnabled) {
                return;
            }
            snsRadioButtonPreference2.setEnabled(true);
            this.mAgg.setEnabled(true);
            return;
        }
        if (updateSmartNetworkSwitchAvailability == 2) {
            this.mExcludedGroup.setSelectable(false);
            SwitchPreferenceCompat switchPreferenceCompat3 = this.mSwitchForIndividualApps;
            if (switchPreferenceCompat3 != null) {
                switchPreferenceCompat3.setEnabled(false);
            }
            SnsRadioButtonPreference snsRadioButtonPreference3 = this.mNormal;
            if (snsRadioButtonPreference3 == null || this.mAgg == null) {
                return;
            }
            snsRadioButtonPreference3.setEnabled(false);
            this.mAgg.setEnabled(false);
            return;
        }
        if (updateSmartNetworkSwitchAvailability == 3) {
            this.mExcludedGroup.setSelectable(false);
            SwitchPreferenceCompat switchPreferenceCompat4 = this.mSwitchForIndividualApps;
            if (switchPreferenceCompat4 != null) {
                switchPreferenceCompat4.setEnabled(false);
            }
            SnsRadioButtonPreference snsRadioButtonPreference4 = this.mNormal;
            if (snsRadioButtonPreference4 == null || this.mAgg == null) {
                return;
            }
            snsRadioButtonPreference4.setEnabled(false);
            this.mAgg.setEnabled(false);
            return;
        }
        if (updateSmartNetworkSwitchAvailability == 4) {
            this.mExcludedGroup.setSelectable(false);
            SwitchPreferenceCompat switchPreferenceCompat5 = this.mSwitchForIndividualApps;
            if (switchPreferenceCompat5 != null) {
                switchPreferenceCompat5.setEnabled(false);
            }
            SnsRadioButtonPreference snsRadioButtonPreference5 = this.mNormal;
            if (snsRadioButtonPreference5 == null || this.mAgg == null) {
                return;
            }
            snsRadioButtonPreference5.setEnabled(false);
            this.mAgg.setEnabled(false);
            return;
        }
        if (updateSmartNetworkSwitchAvailability != 5) {
            return;
        }
        this.mExcludedGroup.setSelectable(false);
        SwitchPreferenceCompat switchPreferenceCompat6 = this.mSwitchForIndividualApps;
        if (switchPreferenceCompat6 != null) {
            switchPreferenceCompat6.setEnabled(false);
        }
        SnsRadioButtonPreference snsRadioButtonPreference6 = this.mNormal;
        if (snsRadioButtonPreference6 == null || this.mAgg == null) {
            return;
        }
        snsRadioButtonPreference6.setEnabled(false);
        this.mAgg.setEnabled(false);
    }
}
