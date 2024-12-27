package com.android.settings.display.darkmode;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.PowerManager;

import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.display.TwilightLocationDialog;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DarkModeScheduleSelectorController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String TAG = "DarkModeScheduleSelectorController";
    private final BedtimeSettings mBedtimeSettings;
    private int mCurrentMode;
    private final LocationManager mLocationManager;
    private final PowerManager mPowerManager;
    private DropDownPreference mPreference;
    private final UiModeManager mUiModeManager;

    public DarkModeScheduleSelectorController(Context context, String str) {
        super(context, str);
        this.mUiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mLocationManager = (LocationManager) context.getSystemService(LocationManager.class);
        this.mBedtimeSettings = new BedtimeSettings(context);
    }

    private int getCurrentMode() {
        int nightMode = this.mUiModeManager.getNightMode();
        return this.mPreference.findIndexOfValue(
                this.mContext.getString(
                        nightMode != 0
                                ? nightMode != 3
                                        ? R.string.dark_ui_auto_mode_never
                                        : (this.mBedtimeSettings.getBedtimeSettingsIntent() == null
                                                        || this.mUiModeManager
                                                                        .getNightModeCustomType()
                                                                != 1)
                                                ? R.string.dark_ui_auto_mode_custom
                                                : R.string.dark_ui_auto_mode_custom_bedtime
                                : R.string.dark_ui_auto_mode_auto));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (DropDownPreference) preferenceScreen.findPreference(getPreferenceKey());
        if (this.mBedtimeSettings.getBedtimeSettingsIntent() != null) {
            String[] stringArray =
                    this.mContext
                            .getResources()
                            .getStringArray(
                                    R.array.dark_ui_scheduler_with_bedtime_preference_titles);
            this.mPreference.setEntries(stringArray);
            this.mPreference.mEntryValues = stringArray;
        }
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
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        int findIndexOfValue = this.mPreference.findIndexOfValue((String) obj);
        if (findIndexOfValue == this.mCurrentMode) {
            return false;
        }
        if (findIndexOfValue
                == this.mPreference.findIndexOfValue(
                        this.mContext.getString(R.string.dark_ui_auto_mode_never))) {
            this.mUiModeManager.setNightMode(
                    (this.mContext.getResources().getConfiguration().uiMode & 32) != 0 ? 2 : 1);
        } else if (findIndexOfValue
                == this.mPreference.findIndexOfValue(
                        this.mContext.getString(R.string.dark_ui_auto_mode_auto))) {
            if (!this.mLocationManager.isLocationEnabled()) {
                TwilightLocationDialog.show(this.mContext);
                return true;
            }
            this.mUiModeManager.setNightMode(0);
        } else if (findIndexOfValue
                == this.mPreference.findIndexOfValue(
                        this.mContext.getString(R.string.dark_ui_auto_mode_custom))) {
            this.mUiModeManager.setNightMode(3);
        } else if (findIndexOfValue
                == this.mPreference.findIndexOfValue(
                        this.mContext.getString(R.string.dark_ui_auto_mode_custom_bedtime))) {
            this.mUiModeManager.setNightModeCustomType(1);
        }
        this.mCurrentMode = findIndexOfValue;
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
    public final void updateState(Preference preference) {
        this.mPreference.setEnabled(!this.mPowerManager.isPowerSaveMode());
        int currentMode = getCurrentMode();
        this.mCurrentMode = currentMode;
        this.mPreference.setValueIndex(currentMode);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
