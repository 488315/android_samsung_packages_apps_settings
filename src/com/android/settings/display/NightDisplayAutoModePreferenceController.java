package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.ColorDisplayManager;
import android.location.LocationManager;

import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NightDisplayAutoModePreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private ColorDisplayManager mColorDisplayManager;
    private final LocationManager mLocationManager;
    private DropDownPreference mPreference;

    public NightDisplayAutoModePreferenceController(Context context, String str) {
        super(context, str);
        this.mColorDisplayManager =
                (ColorDisplayManager) context.getSystemService(ColorDisplayManager.class);
        this.mLocationManager = (LocationManager) context.getSystemService(LocationManager.class);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        DropDownPreference dropDownPreference =
                (DropDownPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = dropDownPreference;
        dropDownPreference.setEntries(
                new CharSequence[] {
                    this.mContext.getString(R.string.night_display_auto_mode_never),
                    this.mContext.getString(R.string.night_display_auto_mode_custom),
                    this.mContext.getString(R.string.night_display_auto_mode_twilight)
                });
        this.mPreference.mEntryValues =
                new CharSequence[] {String.valueOf(0), String.valueOf(1), String.valueOf(2)};
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return ColorDisplayManager.isNightDisplayAvailable(this.mContext) ? 0 : 3;
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
        if (!String.valueOf(2).equals(obj) || this.mLocationManager.isLocationEnabled()) {
            return this.mColorDisplayManager.setNightDisplayAutoMode(
                    Integer.parseInt((String) obj));
        }
        TwilightLocationDialog.show(this.mContext);
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
        this.mPreference.setValue(
                String.valueOf(this.mColorDisplayManager.getNightDisplayAutoMode()));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
