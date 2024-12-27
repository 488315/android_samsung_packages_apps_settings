package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.SecSwitchPreference;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.eternal.utils.EternalUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TapToClickPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String KEY_TAP_TO_CLICK = "key_tap_to_click";

    public TapToClickPreferenceController(Context context, String str) {
        super(context, KEY_TAP_TO_CLICK);
    }

    public static double getOneUIVersion(Context context) {
        if (!EternalUtils.isSemAvailable(context)) {
            return 1.0d;
        }
        int i = Build.VERSION.SEM_PLATFORM_INT - 90000;
        return Double.parseDouble(
                (i / EnterpriseContainerConstants.SYSTEM_SIGNED_APP)
                        + "."
                        + ((i % EnterpriseContainerConstants.SYSTEM_SIGNED_APP) / 100));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (getOneUIVersion(this.mContext) < 2.5d
                        || !TouchPadGestureSettingsController.isPogoKeyboardConnected(
                                this.mContext))
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
        return Settings.System.getInt(this.mContext.getContentResolver(), "tap_to_click_enabled", 1)
                == 1;
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
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        Settings.System.putInt(
                this.mContext.getContentResolver(), "tap_to_click_enabled", booleanValue ? 1 : 0);
        ((SecSwitchPreference) preference).setChecked(booleanValue);
        return false;
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
        ((SecSwitchPreference) preference).setChecked(isChecked());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
