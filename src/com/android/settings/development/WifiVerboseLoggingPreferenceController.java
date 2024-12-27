package com.android.settings.development;

import android.content.Context;
import android.net.wifi.WifiManager;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiVerboseLoggingPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final int SETTING_VALUE_OFF = 0;
    static final int SETTING_VALUE_ON = 1;
    public final WifiManager mWifiManager;

    public WifiVerboseLoggingPreferenceController(Context context) {
        super(context);
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "wifi_verbose_logging";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        this.mWifiManager.setVerboseLoggingEnabled(false);
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        this.mWifiManager.setVerboseLoggingEnabled(((Boolean) obj).booleanValue());
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference)
                .setChecked(this.mWifiManager.isVerboseLoggingEnabled());
    }
}
