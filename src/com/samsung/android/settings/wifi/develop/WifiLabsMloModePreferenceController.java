package com.samsung.android.settings.wifi.develop;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiLabsMloModePreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private final Context mContext;
    private int mCurrentValue;
    private SecDropDownPreference mPreference;

    public WifiLabsMloModePreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    private int getCurrentMode() {
        return Settings.Secure.getInt(
                this.mContext.getContentResolver(), "sec_wifi_mlo_link_count", 0);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecDropDownPreference secDropDownPreference =
                (SecDropDownPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secDropDownPreference;
        if (secDropDownPreference != null) {
            secDropDownPreference.setEntries(
                    this.mContext.getResources().getStringArray(R.array.sec_wifi_mlo_entries));
            this.mPreference.mEntryValues =
                    this.mContext.getResources().getStringArray(R.array.sec_wifi_mlo_values);
            int currentMode = getCurrentMode();
            this.mCurrentValue = currentMode;
            this.mPreference.setValueIndex(currentMode);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "sec_wifi_7_mode_enabled", 1)
                        == 1
                ? 0
                : 5;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mPreference.mEntries[this.mCurrentValue];
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
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "sec_wifi_mlo_link_count",
                Integer.parseInt((String) obj));
        if (this.mPreference != null) {
            int currentMode = getCurrentMode();
            this.mCurrentValue = currentMode;
            this.mPreference.setValueIndex(currentMode);
        }
        refreshSummary(preference);
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mPreference != null) {
            int currentMode = getCurrentMode();
            this.mCurrentValue = currentMode;
            this.mPreference.setValueIndex(currentMode);
        }
        refreshSummary(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public void updateState() {
        SecDropDownPreference secDropDownPreference = this.mPreference;
        if (secDropDownPreference != null) {
            secDropDownPreference.setEnabled(getAvailabilityStatus() == 0);
            updateState(this.mPreference);
        }
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
