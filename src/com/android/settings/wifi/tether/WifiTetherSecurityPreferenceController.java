package com.android.settings.wifi.tether;

import android.content.Context;
import android.net.wifi.SoftApCapability;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.IntFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiTetherSecurityPreferenceController extends WifiTetherBasePreferenceController
        implements WifiManager.SoftApCallback {
    boolean mIsWpa3Supported;
    public final Map mSecurityMap;
    public int mSecurityValue;
    boolean mShouldHidePreference;

    public WifiTetherSecurityPreferenceController(
            Context context,
            WifiTetherSettings$$ExternalSyntheticLambda2
                    wifiTetherSettings$$ExternalSyntheticLambda2) {
        super(context, wifiTetherSettings$$ExternalSyntheticLambda2);
        this.mSecurityMap = new LinkedHashMap();
        this.mIsWpa3Supported = true;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mShouldHidePreference =
                featureFactoryImpl
                        .getWifiFeatureProvider()
                        .getWifiHotspotRepository()
                        .isSpeedFeatureAvailable();
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("shouldHidePreference():"),
                this.mShouldHidePreference,
                "PrefControllerMixin");
        if (this.mShouldHidePreference) {
            return;
        }
        String[] stringArray =
                this.mContext.getResources().getStringArray(R.array.wifi_tether_security);
        String[] stringArray2 =
                this.mContext.getResources().getStringArray(R.array.wifi_tether_security_values);
        for (int i = 0; i < stringArray.length; i++) {
            this.mSecurityMap.put(
                    Integer.valueOf(Integer.parseInt(stringArray2[i])), stringArray[i]);
        }
        this.mWifiManager.registerSoftApCallback(context.getMainExecutor(), this);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "wifi_tether_security";
    }

    @Override // com.android.settings.wifi.tether.WifiTetherBasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (this.mShouldHidePreference) {
            return false;
        }
        return super.isAvailable();
    }

    public final void onCapabilityChanged(SoftApCapability softApCapability) {
        boolean areFeaturesSupported = softApCapability.areFeaturesSupported(4L);
        if (!areFeaturesSupported) {
            Log.i("wifi_tether_security", "WPA3 SAE is not supported on this device");
        }
        if (this.mIsWpa3Supported != areFeaturesSupported) {
            this.mIsWpa3Supported = areFeaturesSupported;
            updateDisplay$1();
        }
        this.mWifiManager.unregisterSoftApCallback(this);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        int parseInt = Integer.parseInt((String) obj);
        this.mSecurityValue = parseInt;
        preference.setSummary(
                (CharSequence) ((LinkedHashMap) this.mSecurityMap).get(Integer.valueOf(parseInt)));
        WifiTetherBasePreferenceController.OnTetherConfigUpdateListener
                onTetherConfigUpdateListener = this.mListener;
        if (onTetherConfigUpdateListener == null) {
            return true;
        }
        onTetherConfigUpdateListener.onTetherConfigUpdated(this);
        return true;
    }

    @Override // com.android.settings.wifi.tether.WifiTetherBasePreferenceController
    public final void updateDisplay$1() {
        Preference preference = this.mPreference;
        if (preference == null) {
            return;
        }
        ListPreference listPreference = (ListPreference) preference;
        if (!this.mIsWpa3Supported
                && ((LinkedHashMap) this.mSecurityMap)
                        .keySet()
                        .removeIf(
                                new WifiTetherSecurityPreferenceController$$ExternalSyntheticLambda0())) {
            final int i = 0;
            listPreference.setEntries(
                    (CharSequence[])
                            ((LinkedHashMap) this.mSecurityMap)
                                    .values().stream()
                                            .toArray(
                                                    new IntFunction() { // from class:
                                                                        // com.android.settings.wifi.tether.WifiTetherSecurityPreferenceController$$ExternalSyntheticLambda1
                                                        @Override // java.util.function.IntFunction
                                                        public final Object apply(int i2) {
                                                            switch (i) {
                                                                case 0:
                                                                    return new CharSequence[i2];
                                                                default:
                                                                    return new CharSequence[i2];
                                                            }
                                                        }
                                                    }));
            final int i2 = 1;
            listPreference.mEntryValues =
                    (CharSequence[])
                            ((LinkedHashMap) this.mSecurityMap)
                                    .keySet().stream()
                                            .map(
                                                    new WifiTetherSecurityPreferenceController$$ExternalSyntheticLambda2())
                                            .toArray(
                                                    new IntFunction() { // from class:
                                                                        // com.android.settings.wifi.tether.WifiTetherSecurityPreferenceController$$ExternalSyntheticLambda1
                                                        @Override // java.util.function.IntFunction
                                                        public final Object apply(int i22) {
                                                            switch (i2) {
                                                                case 0:
                                                                    return new CharSequence[i22];
                                                                default:
                                                                    return new CharSequence[i22];
                                                            }
                                                        }
                                                    });
        }
        int securityType = this.mWifiManager.getSoftApConfiguration().getSecurityType();
        if (((LinkedHashMap) this.mSecurityMap).get(Integer.valueOf(securityType)) == null) {
            securityType = 1;
        }
        this.mSecurityValue = securityType;
        listPreference.setSummary(
                (CharSequence)
                        ((LinkedHashMap) this.mSecurityMap).get(Integer.valueOf(securityType)));
        listPreference.setValue(String.valueOf(this.mSecurityValue));
    }
}
