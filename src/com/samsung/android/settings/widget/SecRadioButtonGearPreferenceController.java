package com.samsung.android.settings.widget;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecRadioButtonGearPreferenceController extends BasePreferenceController
        implements SecRadioButtonGearPreference.OnClickListener {
    protected OnChangeListener mOnChangeListener;
    protected SecRadioButtonGearPreference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnChangeListener {}

    public SecRadioButtonGearPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecRadioButtonGearPreference secRadioButtonGearPreference =
                (SecRadioButtonGearPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secRadioButtonGearPreference;
        if (secRadioButtonGearPreference != null) {
            secRadioButtonGearPreference.mListener = this;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

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

    public void onRadioButtonClicked(SecRadioButtonGearPreference secRadioButtonGearPreference) {
        OnChangeListener onChangeListener = this.mOnChangeListener;
        if (onChangeListener != null) {
            SecRadioButtonGearPreferenceControllersHandler
                    secRadioButtonGearPreferenceControllersHandler =
                            (SecRadioButtonGearPreferenceControllersHandler) onChangeListener;
            if (TextUtils.equals(
                    secRadioButtonGearPreferenceControllersHandler.mSelectedKey,
                    getPreferenceKey())) {
                return;
            }
            secRadioButtonGearPreferenceControllersHandler.mSelectedKey = getPreferenceKey();
            SecRadioButtonGearPreferenceControllersHandler.OnChangeListener onChangeListener2 =
                    secRadioButtonGearPreferenceControllersHandler.mOnChangeListener;
            if (onChangeListener2 != null) {
                onChangeListener2.onCheckedChanged(secRadioButtonGearPreferenceControllersHandler);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setChecked(String str, boolean z) {
        boolean equals = TextUtils.equals(str, getPreferenceKey());
        this.mPreference.setChecked(equals);
        if (z) {
            this.mPreference.callChangeListener(Boolean.valueOf(equals));
        }
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.mOnChangeListener = onChangeListener;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
