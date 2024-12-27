package com.android.settings.emergency;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.emergencynumber.EmergencyNumberUtils;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EmergencyGestureSoundPreferenceController extends TogglePreferenceController {
    EmergencyNumberUtils mEmergencyNumberUtils;

    public EmergencyGestureSoundPreferenceController(Context context, String str) {
        super(context, str);
        this.mEmergencyNumberUtils = new EmergencyNumberUtils(context);
    }

    private static boolean isGestureAvailable(Context context) {
        return context.getResources().getBoolean(R.bool.config_show_emergency_gesture_settings);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return isGestureAvailable(this.mContext) ? 0 : 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        Bundle call =
                this.mEmergencyNumberUtils
                        .mContext
                        .getContentResolver()
                        .call(
                                EmergencyNumberUtils.EMERGENCY_NUMBER_OVERRIDE_AUTHORITY,
                                "GET_EMERGENCY_SOUND",
                                (String) null,
                                (Bundle) null);
        return call == null || call.getInt("emergency_setting_value", 0) == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        EmergencyNumberUtils emergencyNumberUtils = this.mEmergencyNumberUtils;
        emergencyNumberUtils.getClass();
        Bundle bundle = new Bundle();
        bundle.putInt("emergency_setting_value", z ? 1 : 0);
        emergencyNumberUtils
                .mContext
                .getContentResolver()
                .call(
                        EmergencyNumberUtils.EMERGENCY_NUMBER_OVERRIDE_AUTHORITY,
                        "SET_EMERGENCY_SOUND",
                        (String) null,
                        bundle);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
