package com.android.settings.emergency;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.emergencynumber.EmergencyNumberUtils;
import com.android.settingslib.widget.MainSwitchPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EmergencyGesturePreferenceController extends BasePreferenceController
        implements CompoundButton.OnCheckedChangeListener {
    EmergencyNumberUtils mEmergencyNumberUtils;
    private MainSwitchPreference mSwitchBar;

    public EmergencyGesturePreferenceController(Context context, String str) {
        super(context, str);
        this.mEmergencyNumberUtils = new EmergencyNumberUtils(context);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        MainSwitchPreference mainSwitchPreference =
                (MainSwitchPreference) preferenceScreen.findPreference(this.mPreferenceKey);
        this.mSwitchBar = mainSwitchPreference;
        mainSwitchPreference.setTitle(
                this.mContext.getString(R.string.emergency_gesture_switchbar_title));
        this.mSwitchBar.addOnSwitchChangeListener(this);
        this.mSwitchBar.updateStatus(isChecked());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_show_emergency_gesture_settings)
                ? 3
                : 0;
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

    public boolean isChecked() {
        Bundle call =
                this.mEmergencyNumberUtils
                        .mContext
                        .getContentResolver()
                        .call(
                                EmergencyNumberUtils.EMERGENCY_NUMBER_OVERRIDE_AUTHORITY,
                                "GET_EMERGENCY_GESTURE",
                                (String) null,
                                (Bundle) null);
        return call == null || call.getInt("emergency_setting_value", 1) == 1;
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

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        EmergencyNumberUtils emergencyNumberUtils = this.mEmergencyNumberUtils;
        emergencyNumberUtils.getClass();
        Bundle bundle = new Bundle();
        bundle.putInt("emergency_setting_value", z ? 1 : 0);
        emergencyNumberUtils
                .mContext
                .getContentResolver()
                .call(
                        EmergencyNumberUtils.EMERGENCY_NUMBER_OVERRIDE_AUTHORITY,
                        "SET_EMERGENCY_GESTURE",
                        (String) null,
                        bundle);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
