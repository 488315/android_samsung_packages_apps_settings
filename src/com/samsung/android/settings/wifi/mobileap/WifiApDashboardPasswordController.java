package com.samsung.android.settings.wifi.mobileap;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApConfiguration;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApDashboardPasswordController extends BasePreferenceController {
    private Context mContext;
    private WifiApPreference mThisPreference;
    private WifiApSettings mWifiApSettings;

    public WifiApDashboardPasswordController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        WifiApPreference wifiApPreference =
                (WifiApPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mThisPreference = wifiApPreference;
        wifiApPreference.mPreferenceType = 1;
        wifiApPreference.notifyChanged();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        SALogging.insertSALog("TETH_010", "8008");
        if ((WifiApSettingsUtils.isCarrierTMO() || WifiApSettingsUtils.isCarrierNEWCO())
                && this.mWifiApSettings.getWifiApConfiguration().isDefaultPassphraseSet()) {
            this.mWifiApSettings.launchWifiApEditSettingsActivity(
                    R.string.wifi_ap_first_time_configuration, "intent_value_focus_password");
        } else {
            this.mWifiApSettings.launchWifiApEditSettingsActivity(
                    R.string.wifi_ap_menu_configure_hotspot, "intent_value_focus_password");
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setHost(WifiApSettings wifiApSettings) {
        this.mWifiApSettings = wifiApSettings;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        WifiApConfiguration wifiApConfiguration = this.mWifiApSettings.getWifiApConfiguration();
        String str = wifiApConfiguration.mPassphrase;
        boolean z = wifiApConfiguration.mSecurityType == 0;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Is Open Security Type: ", "WifiApConfiguration", z);
        if (!z) {
            boolean z2 = wifiApConfiguration.mSecurityType == 5;
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "Is Open Enhanced Security Type: ", "WifiApConfiguration", z2);
            if (!z2) {
                if (wifiApConfiguration.isDefaultPassphraseSet()) {
                    Context context = this.mContext;
                    str =
                            Settings.Secure.getString(
                                    context.getContentResolver(), "wifi_ap_random_password");
                    Log.i("WifiApSoftApUtils", "defaultTempPassphrase is [" + str + "]");
                    if (str == null || str.equals(ApnSettings.MVNO_NONE)) {
                        StringBuilder sb = new StringBuilder();
                        boolean z3 = WifiApSettingsUtils.DBG;
                        SecureRandom secureRandom =
                                new SecureRandom(
                                        WifiApSettingsUtils.longToBytes(
                                                SystemClock.uptimeMillis()));
                        String str2 = ApnSettings.MVNO_NONE;
                        for (int i = 0; i < 4; i++) {
                            StringBuilder m =
                                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                            .m(str2);
                            m.append("abcdefghijklmnopqrstuvwxyz".charAt(secureRandom.nextInt(26)));
                            str2 = m.toString();
                        }
                        sb.append(str2);
                        SecureRandom secureRandom2 =
                                new SecureRandom(
                                        WifiApSettingsUtils.longToBytes(
                                                SystemClock.uptimeMillis() + 1));
                        int i2 = 10;
                        for (int i3 = 1; i3 < 3; i3++) {
                            i2 *= 10;
                        }
                        Locale locale = Locale.US;
                        sb.append(
                                String.format(
                                        "%03d", Integer.valueOf(secureRandom2.nextInt(i2 - 1))));
                        sb.append(
                                ApnSettings.MVNO_NONE
                                        + "!@#$/^&*()"
                                                .charAt(
                                                        new SecureRandom(
                                                                        WifiApSettingsUtils
                                                                                .longToBytes(
                                                                                        SystemClock
                                                                                                .uptimeMillis()))
                                                                .nextInt(10)));
                        String sb2 = sb.toString();
                        Log.i(
                                "WifiApSoftApUtils",
                                "generate defaultTempPassphrase is [" + sb2 + "]");
                        Settings.Secure.putString(
                                context.getContentResolver(), "wifi_ap_random_password", sb2);
                        str = sb2;
                    }
                }
                this.mThisPreference.setSummary(str);
            }
        }
        str = this.mContext.getString(R.string.wifi_security_none);
        this.mThisPreference.setSummary(str);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
