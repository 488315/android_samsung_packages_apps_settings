package com.android.settings.core;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.slice.builders.SliceAction;

import com.android.settings.widget.SeekBarPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SliderPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public SliderPreferenceController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getControlType() {
        return 2;
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public abstract int getMax();

    public abstract int getMin();

    public SliceAction getSliceEndItem(Context context) {
        return null;
    }

    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getSliceType() {
        return 2;
    }

    public abstract int getSliderPosition();

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        CharSequence summary = getSummary();
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(Integer.valueOf(getSliderPosition()));
        builder.addAttributeInt(getMax(), "max");
        builder.addAttributeInt(getMin(), "min");
        builder.mSummary = summary == null ? null : (String) summary;
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        return builder.build();
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

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
        return setSliderPosition(((Integer) obj).intValue());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public abstract boolean setSliderPosition(int i);

    @Override // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        int parseInt = Integer.parseInt(controlValue.getValue());
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        ControlResult.ResultCode resultCode = ControlResult.ResultCode.SUCCESS;
        builder.mResultCode = resultCode;
        int min = getMin();
        ControlResult.ResultCode resultCode2 = ControlResult.ResultCode.FAIL;
        if (parseInt < min || parseInt > getMax()) {
            ControlResult.Builder builder2 = new ControlResult.Builder(getPreferenceKey());
            builder2.mResultCode = resultCode2;
            builder2.mErrorCode = ControlResult.ErrorCode.OUT_OF_RANGE;
            return new ControlResult(builder2);
        }
        if (parseInt != getSliderPosition()) {
            if (needUserInteraction(Integer.valueOf(parseInt))
                            != Controllable$ControllableType.NO_INTERACTION
                    && controlValue.mForceChange) {
                ignoreUserInteraction();
            }
            if (!setSliderPosition(parseInt)) {
                if (getControlErrorCode() != ControlResult.ErrorCode.REQUEST_USER_INTERACTION) {
                    builder.mResultCode = resultCode2;
                    builder.mErrorCode = getControlErrorCode();
                    builder.setErrorMsg(getControlErrorMessage());
                    return new ControlResult(builder);
                }
                builder.mResultCode = ControlResult.ResultCode.REQUEST_SUCCESS;
            }
        }
        ControlResult.Builder builder3 = new ControlResult.Builder(getPreferenceKey());
        builder3.mResultCode = resultCode;
        return new ControlResult(builder3);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference instanceof SeekBarPreference) {
            ((SeekBarPreference) preference).setProgress(getSliderPosition());
        } else if (preference instanceof androidx.preference.SeekBarPreference) {
            ((androidx.preference.SeekBarPreference) preference)
                    .setValueInternal(getSliderPosition(), true);
        }
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
