package com.samsung.android.settings.core;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecColorPickerPreferenceController extends BasePreferenceController {
    public SecColorPickerPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getControlType() {
        return 102;
    }

    public abstract ArrayList<String> getEntryValues();

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    public abstract ArrayList<Integer> getItemEntries();

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public abstract String getSelectedValue();

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(getSelectedValue());
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        return builder.build();
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

    public abstract boolean setSelectedValue(String str);

    @Override // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        String value = controlValue.getValue();
        ArrayList<String> entryValues = getEntryValues();
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        ControlResult.ErrorCode errorCode = ControlResult.ErrorCode.OUT_OF_RANGE;
        ControlResult.ResultCode resultCode = ControlResult.ResultCode.FAIL;
        if (entryValues == null || entryValues.isEmpty()) {
            builder.mResultCode = resultCode;
            builder.mErrorCode = errorCode;
            return new ControlResult(builder);
        }
        if (!entryValues.contains(value)) {
            builder.mResultCode = resultCode;
            builder.mErrorCode = errorCode;
            return new ControlResult(builder);
        }
        if (!value.equals(getSelectedValue())) {
            if (needUserInteraction(value) != Controllable$ControllableType.NO_INTERACTION
                    && controlValue.mForceChange) {
                ignoreUserInteraction();
            }
            if (!setSelectedValue(value)) {
                if (getControlErrorCode() != ControlResult.ErrorCode.REQUEST_USER_INTERACTION) {
                    builder.mResultCode = resultCode;
                    builder.mErrorCode = getControlErrorCode();
                    builder.setErrorMsg(getControlErrorMessage());
                    return new ControlResult(builder);
                }
                builder.mResultCode = ControlResult.ResultCode.REQUEST_SUCCESS;
            }
        }
        ControlResult.Builder builder2 = new ControlResult.Builder(getPreferenceKey());
        builder2.mResultCode = ControlResult.ResultCode.SUCCESS;
        return new ControlResult(builder2);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
