package com.android.settings.wifi.details2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiPrivacyPreferenceController2 extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String KEY_WIFI_PRIVACY = "privacy";
    private static final int PREF_RANDOMIZATION_NONE = 1;
    private static final int PREF_RANDOMIZATION_PERSISTENT = 0;
    private WifiEntry mWifiEntry;
    private final WifiManager mWifiManager;

    public WifiPrivacyPreferenceController2(Context context) {
        super(context, KEY_WIFI_PRIVACY);
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
    }

    public static int translateMacRandomizedValueToPrefValue(int i) {
        return i == 1 ? 0 : 1;
    }

    public static int translatePrefValueToMacRandomizedValue(int i) {
        return i == 0 ? 1 : 0;
    }

    private void updateSummary(ListPreference listPreference, int i) {
        listPreference.setSummary(
                listPreference.mEntries[translateMacRandomizedValueToPrefValue(i)]);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 2;
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

    public int getRandomizationValue() {
        return this.mWifiEntry.getPrivacy();
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        int parseInt = Integer.parseInt((String) obj);
        if (this.mWifiEntry.getPrivacy() == parseInt) {
            return true;
        }
        this.mWifiEntry.setPrivacy(parseInt);
        if (this.mWifiEntry.getConnectedState() == 2) {
            this.mWifiEntry.disconnect(null);
            this.mWifiEntry.connect(null);
        }
        updateSummary((ListPreference) preference, parseInt);
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void setWifiEntry(WifiEntry wifiEntry) {
        this.mWifiEntry = wifiEntry;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        ListPreference listPreference = (ListPreference) preference;
        int randomizationValue = getRandomizationValue();
        boolean canSetPrivacy = this.mWifiEntry.canSetPrivacy();
        preference.setSelectable(canSetPrivacy);
        listPreference.setValue(Integer.toString(randomizationValue));
        updateSummary(listPreference, randomizationValue);
        if (canSetPrivacy) {
            return;
        }
        listPreference.setSummary(R.string.wifi_privacy_settings_ephemeral_summary);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
