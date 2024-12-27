package com.android.settings.emergency;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.emergencynumber.EmergencyNumberUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EmergencyGestureEntrypointPreferenceController extends BasePreferenceController {
    static final String ACTION_EMERGENCY_GESTURE_SETTINGS =
            "com.android.settings.action.emergency_gesture_settings";
    private final EmergencyNumberUtils mEmergencyNumberUtils;
    Intent mIntent;
    private boolean mUseCustomIntent;

    public EmergencyGestureEntrypointPreferenceController(Context context, String str) {
        super(context, str);
        this.mEmergencyNumberUtils = new EmergencyNumberUtils(context);
        String string =
                context.getResources().getString(R.string.emergency_gesture_settings_package);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        Intent intent = new Intent(ACTION_EMERGENCY_GESTURE_SETTINGS).setPackage(string);
        if (canResolveIntent(intent)) {
            this.mUseCustomIntent = true;
            this.mIntent = intent;
        }
    }

    private boolean canHandleClicks() {
        return (this.mUseCustomIntent && this.mIntent == null) ? false : true;
    }

    private boolean canResolveIntent(Intent intent) {
        return this.mContext.getPackageManager().resolveActivity(intent, 0) != null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mContext
                                .getResources()
                                .getBoolean(R.bool.config_show_emergency_gesture_settings)
                        && canHandleClicks())
                ? 0
                : 3;
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
        Context context = this.mContext;
        Bundle call =
                this.mEmergencyNumberUtils
                        .mContext
                        .getContentResolver()
                        .call(
                                EmergencyNumberUtils.EMERGENCY_NUMBER_OVERRIDE_AUTHORITY,
                                "GET_EMERGENCY_GESTURE",
                                (String) null,
                                (Bundle) null);
        return context.getText(
                (call == null || call.getInt("emergency_setting_value", 1) == 1)
                        ? R.string.gesture_setting_on
                        : R.string.gesture_setting_off);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Intent intent;
        if (!TextUtils.equals(getPreferenceKey(), preference.getKey())
                || (intent = this.mIntent) == null) {
            return super.handlePreferenceTreeClick(preference);
        }
        intent.setFlags(67108864);
        this.mContext.startActivity(this.mIntent);
        return true;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public boolean shouldSuppressFromSearch() {
        return this.mUseCustomIntent;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        boolean canHandleClicks = canHandleClicks();
        if (preference != null) {
            preference.setEnabled(canHandleClicks);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
