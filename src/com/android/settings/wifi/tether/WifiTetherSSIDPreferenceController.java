package com.android.settings.wifi.tether;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.SoftApConfiguration;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.ValidatedEditTextPreference;
import com.android.settings.wifi.dpp.WifiDppConfiguratorActivity;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.knox.net.wifi.WifiPolicy;

import java.time.Duration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiTetherSSIDPreferenceController extends WifiTetherBasePreferenceController
        implements ValidatedEditTextPreference.Validator {
    static final String DEFAULT_SSID = "AndroidAP";
    public final MetricsFeatureProvider mMetricsFeatureProvider;
    public String mSSID;
    public final WifiDeviceNameTextValidator mWifiDeviceNameTextValidator;

    /* renamed from: $r8$lambda$lq8JQBstbK2qATO9iHG-TkQ8P9s, reason: not valid java name */
    public static /* synthetic */ void m1028$r8$lambda$lq8JQBstbK2qATO9iHGTkQ8P9s(
            WifiTetherSSIDPreferenceController wifiTetherSSIDPreferenceController, Intent intent) {
        wifiTetherSSIDPreferenceController.mMetricsFeatureProvider.action(
                0, 1712, 1595, Integer.MIN_VALUE, null);
        wifiTetherSSIDPreferenceController.mContext.startActivity(intent);
    }

    public WifiTetherSSIDPreferenceController(
            Context context,
            WifiTetherBasePreferenceController.OnTetherConfigUpdateListener
                    onTetherConfigUpdateListener,
            MetricsFeatureProvider metricsFeatureProvider) {
        super(context, onTetherConfigUpdateListener);
        this.mWifiDeviceNameTextValidator = new WifiDeviceNameTextValidator();
        this.mMetricsFeatureProvider = metricsFeatureProvider;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "wifi_tether_network_name";
    }

    public boolean isQrCodeButtonAvailable() {
        return ((WifiTetherSsidPreference) this.mPreference).isQrCodeButtonAvailable();
    }

    @Override // com.android.settings.widget.ValidatedEditTextPreference.Validator
    public final boolean isTextValid(String str) {
        return this.mWifiDeviceNameTextValidator.isTextValid(str);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String str = (String) obj;
        if (!TextUtils.equals(this.mSSID, str)) {
            this.mMetricsFeatureProvider.action(this.mContext, 1736, new Pair[0]);
        }
        this.mSSID = str;
        EditTextPreference editTextPreference = (EditTextPreference) preference;
        editTextPreference.setText(str);
        editTextPreference.setSummary(this.mSSID);
        this.mListener.onTetherConfigUpdated(this);
        return true;
    }

    @Override // com.android.settings.wifi.tether.WifiTetherBasePreferenceController
    public final void updateDisplay$1() {
        SoftApConfiguration softApConfiguration = this.mWifiManager.getSoftApConfiguration();
        if (softApConfiguration != null) {
            this.mSSID = softApConfiguration.getSsid();
        } else {
            this.mSSID = DEFAULT_SSID;
        }
        ((ValidatedEditTextPreference) this.mPreference).mValidator = this;
        if (!this.mWifiManager.isWifiApEnabled() || softApConfiguration == null) {
            ((WifiTetherSsidPreference) this.mPreference).mVisible = false;
        } else {
            Context context = this.mContext;
            Duration duration = WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION;
            final Intent intent = new Intent(context, (Class<?>) WifiDppConfiguratorActivity.class);
            int securityType = softApConfiguration.getSecurityType();
            if (securityType == 3 || securityType == 2 || securityType == 1 || securityType == 0) {
                intent.setAction("android.settings.WIFI_DPP_CONFIGURATOR_QR_CODE_GENERATOR");
                String removeFirstAndLastDoubleQuotes =
                        WifiDppUtils.removeFirstAndLastDoubleQuotes(softApConfiguration.getSsid());
                int securityType2 = softApConfiguration.getSecurityType();
                String str =
                        securityType2 == 3
                                ? WifiPolicy.SECURITY_TYPE_SAE
                                : (securityType2 == 1 || securityType2 == 2) ? "WPA" : "nopass";
                String removeFirstAndLastDoubleQuotes2 =
                        WifiDppUtils.removeFirstAndLastDoubleQuotes(
                                softApConfiguration.getPassphrase());
                if (!TextUtils.isEmpty(removeFirstAndLastDoubleQuotes)) {
                    intent.putExtra("ssid", removeFirstAndLastDoubleQuotes);
                }
                if (!TextUtils.isEmpty(str)) {
                    intent.putExtra("security", str);
                }
                if (!TextUtils.isEmpty(removeFirstAndLastDoubleQuotes2)) {
                    intent.putExtra("preSharedKey", removeFirstAndLastDoubleQuotes2);
                }
                intent.putExtra("hiddenSsid", softApConfiguration.isHiddenSsid());
                intent.putExtra("networkId", -1);
                intent.putExtra("isHotspot", true);
            } else {
                intent = null;
            }
            if (intent == null) {
                Log.e("WifiTetherSsidPref", "Invalid security to share hotspot");
                ((WifiTetherSsidPreference) this.mPreference).mVisible = false;
            } else {
                WifiTetherSsidPreference wifiTetherSsidPreference =
                        (WifiTetherSsidPreference) this.mPreference;
                wifiTetherSsidPreference.mClickListener =
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.wifi.tether.WifiTetherSSIDPreferenceController$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                WifiDppUtils.showLockScreen(
                                        r1.mContext,
                                        new Runnable() { // from class:
                                                         // com.android.settings.wifi.tether.WifiTetherSSIDPreferenceController$$ExternalSyntheticLambda1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                WifiTetherSSIDPreferenceController
                                                        .m1028$r8$lambda$lq8JQBstbK2qATO9iHGTkQ8P9s(
                                                                WifiTetherSSIDPreferenceController
                                                                        .this,
                                                                r2);
                                            }
                                        });
                            }
                        };
                wifiTetherSsidPreference.mVisible = true;
            }
        }
        EditTextPreference editTextPreference = (EditTextPreference) this.mPreference;
        editTextPreference.setText(this.mSSID);
        editTextPreference.setSummary(this.mSSID);
    }

    public WifiTetherSSIDPreferenceController(
            Context context,
            WifiTetherSettings$$ExternalSyntheticLambda2
                    wifiTetherSettings$$ExternalSyntheticLambda2) {
        super(context, wifiTetherSettings$$ExternalSyntheticLambda2);
        this.mWifiDeviceNameTextValidator = new WifiDeviceNameTextValidator();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl != null) {
            this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
            return;
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }
}
