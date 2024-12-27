package com.android.settings.core;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.RadioItemDataInfo;
import com.samsung.android.settings.widget.SecHorizontalRadioPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SecSingleChoicePreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public SecSingleChoicePreferenceController(Context context, String str) {
        super(context, str);
    }

    private void setAttributes(SecHorizontalRadioPreference secHorizontalRadioPreference) {
        RadioItemDataInfo.Builder builder =
                new RadioItemDataInfo.Builder(getEntryValues(), getEntries());
        builder.mImageList = getImageEntries();
        builder.mSubTitleList = getSubEntries();
        builder.mTitleColorList = getTitleColorList();
        secHorizontalRadioPreference.setRadioItemDataInfo(builder.build());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference instanceof SecHorizontalRadioPreference) {
            setAttributes((SecHorizontalRadioPreference) findPreference);
        }
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
        return 101;
    }

    public abstract ArrayList<String> getEntries();

    public abstract ArrayList<String> getEntryValues();

    public abstract ArrayList<Integer> getImageEntries();

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

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

    public abstract ArrayList<String> getSubEntries();

    public ArrayList<Integer> getTitleColorList() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        CharSequence summary = getSummary();
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(getSelectedValue());
        builder.addAttributeStringArrayList("entries", getEntries());
        builder.addAttributeStringArrayList("entryValues", getEntryValues());
        ArrayList<Integer> imageEntries = getImageEntries();
        if (builder.mBundle.containsKey("imageEntries")) {
            Log.e(
                    "ControlValue.Builder",
                    "the value of the attribute (imageEntries) will be replaced with a new one");
        }
        builder.mBundle.putIntegerArrayList("imageEntries", imageEntries);
        builder.addAttributeStringArrayList("subEntries", getSubEntries());
        builder.mSummary = summary == null ? null : (String) summary;
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        return builder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
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
        setSelectedValue((String) obj);
        return true;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference instanceof SecHorizontalRadioPreference) {
            ((SecHorizontalRadioPreference) preference).setValue(getSelectedValue());
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
