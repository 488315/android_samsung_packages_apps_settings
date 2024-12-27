package com.android.settings.wifi.tether;

import android.content.Context;
import android.net.wifi.SoftApConfiguration;
import android.text.TextUtils;
import android.util.Pair;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.ValidatedEditTextPreference;
import com.android.settings.wifi.WifiUtils;
import com.android.settings.wifi.repository.WifiHotspotRepository;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiTetherPasswordPreferenceController extends WifiTetherBasePreferenceController
        implements ValidatedEditTextPreference.Validator {
    public final MetricsFeatureProvider mMetricsFeatureProvider;
    public String mPassword;
    public int mSecurityType;
    public final WifiHotspotRepository mWifiHotspotRepository;

    public WifiTetherPasswordPreferenceController(
            Context context,
            WifiTetherBasePreferenceController.OnTetherConfigUpdateListener
                    onTetherConfigUpdateListener,
            MetricsFeatureProvider metricsFeatureProvider) {
        super(context, onTetherConfigUpdateListener);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider =
                metricsFeatureProvider == null
                        ? featureFactoryImpl.getMetricsFeatureProvider()
                        : metricsFeatureProvider;
        WifiHotspotRepository wifiHotspotRepository =
                featureFactoryImpl.getWifiFeatureProvider().getWifiHotspotRepository();
        this.mWifiHotspotRepository = wifiHotspotRepository;
        if (wifiHotspotRepository.mWifiManager.getSoftApConfiguration().getSecurityType() != 0) {
            return;
        }
        wifiHotspotRepository.mWifiManager.queryLastConfiguredTetheredApPassphraseSinceBoot(
                wifiHotspotRepository.mAppContext.getMainExecutor(),
                wifiHotspotRepository.mLastPasswordListener);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "wifi_tether_network_password";
    }

    @Override // com.android.settings.widget.ValidatedEditTextPreference.Validator
    public final boolean isTextValid(String str) {
        return WifiUtils.isHotspotPasswordValid(this.mSecurityType, str);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String str = (String) obj;
        if (!TextUtils.equals(this.mPassword, str)) {
            this.mMetricsFeatureProvider.action(this.mContext, 1737, new Pair[0]);
        }
        this.mPassword = str;
        updatePasswordDisplay((EditTextPreference) this.mPreference);
        this.mListener.onTetherConfigUpdated(this);
        return true;
    }

    @Override // com.android.settings.wifi.tether.WifiTetherBasePreferenceController
    public final void updateDisplay$1() {
        SoftApConfiguration softApConfiguration = this.mWifiManager.getSoftApConfiguration();
        if (softApConfiguration.getSecurityType() == 0
                || !TextUtils.isEmpty(softApConfiguration.getPassphrase())) {
            this.mPassword = softApConfiguration.getPassphrase();
        } else {
            this.mPassword = this.mWifiHotspotRepository.generatePassword();
        }
        this.mSecurityType = softApConfiguration.getSecurityType();
        Preference preference = this.mPreference;
        ((ValidatedEditTextPreference) preference).mValidator = this;
        ((ValidatedEditTextPreference) preference).mIsPassword = true;
        ((ValidatedEditTextPreference) preference).mIsSummaryPassword = true;
        updatePasswordDisplay((EditTextPreference) preference);
    }

    public final void updatePasswordDisplay(EditTextPreference editTextPreference) {
        ValidatedEditTextPreference validatedEditTextPreference =
                (ValidatedEditTextPreference) editTextPreference;
        validatedEditTextPreference.setText(this.mPassword);
        if (TextUtils.isEmpty(this.mPassword)) {
            validatedEditTextPreference.mIsSummaryPassword = false;
            validatedEditTextPreference.setSummary(R.string.wifi_hotspot_no_password_subtext);
            validatedEditTextPreference.setVisible(false);
        } else {
            validatedEditTextPreference.mIsSummaryPassword = true;
            validatedEditTextPreference.setSummary(this.mPassword);
            validatedEditTextPreference.setVisible(true);
        }
    }
}
