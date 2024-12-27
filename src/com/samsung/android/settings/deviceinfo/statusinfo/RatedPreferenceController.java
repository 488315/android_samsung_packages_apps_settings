package com.samsung.android.settings.deviceinfo.statusinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.deviceinfo.SecAboutDeviceItems;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RatedPreferenceController extends BasePreferenceController {
    public RatedPreferenceController(Context context, String str) {
        super(context, str);
    }

    public static String getRatedValue(Context context) {
        String itemValue = SecAboutDeviceItems.getItemValue("rated");
        if (TextUtils.isEmpty(itemValue)) {
            itemValue =
                    SemFloatingFeature.getInstance()
                            .getString("SEC_FLOATING_FEATURE_SETTINGS_CONFIG_ELECTRIC_RATED_VALUE");
        }
        return isVariableRatedModel()
                ? SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                        .m(
                                context,
                                R.string.device_info_rated_suffix_max,
                                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                        itemValue, " "))
                : itemValue;
    }

    public static boolean isVariableRatedModel() {
        int i = SecAboutDeviceItems.ABOUTITEM_STATUS_RATED_MAX;
        return i == -1
                ? SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_VARIABLE_RATED")
                : i != 0;
    }

    private boolean supportRated() {
        int i = SecAboutDeviceItems.ABOUTITEM_STATUS_RATED;
        if (i != -1) {
            return i == 2;
        }
        if (Rune.isChinaOpen() || Rune.isChinaHKTWModel()) {
            return false;
        }
        return !TextUtils.isEmpty(
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_SETTINGS_CONFIG_ELECTRIC_RATED_VALUE"));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return supportRated() ? 0 : 3;
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
        return getRatedValue(this.mContext);
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
