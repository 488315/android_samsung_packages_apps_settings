package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TopLevelDisplayPreferenceController extends BasePreferenceController {
    public TopLevelDisplayPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_top_level_display)
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
        String string = this.mContext.getString(R.string.brightness_title);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        StringBuilder m =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(string);
        m.append(Utils.getTopLevelSummarySeparator(this.mContext));
        String m2 =
                SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                        .m(this.mContext, R.string.sec_eye_comfort_title, m);
        if (!Rune.supportNavigationBar()) {
            return m2;
        }
        StringBuilder m3 =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(m2);
        m3.append(Utils.getTopLevelSummarySeparator(this.mContext));
        String sb = m3.toString();
        if (Rune.supportNavigationBarForHardKey()) {
            return SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                    .m(
                            this.mContext,
                            R.string.navigationbar_gesture_category_name,
                            EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                    .m(sb));
        }
        return SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                .m(
                        this.mContext,
                        R.string.navigationbar_title,
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                sb));
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
