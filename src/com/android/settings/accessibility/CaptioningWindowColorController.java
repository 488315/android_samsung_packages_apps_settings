package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.provider.Settings;
import android.view.accessibility.CaptioningManager;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CaptioningWindowColorController extends BasePreferenceController
        implements ListDialogPreference.OnValueChangedListener {
    private int mCachedNonDefaultOpacity;
    private final CaptionHelper mCaptionHelper;

    public CaptioningWindowColorController(Context context, String str) {
        super(context, str);
        this.mCachedNonDefaultOpacity = 16777215;
        this.mCaptionHelper = new CaptionHelper(context);
    }

    private int getNonDefaultOpacity(boolean z) {
        int parseOpacity = CaptionUtils.parseOpacity(this.mCaptionHelper.getWindowColor());
        if (!z) {
            this.mCachedNonDefaultOpacity = parseOpacity;
            return parseOpacity;
        }
        int i = this.mCachedNonDefaultOpacity;
        if (i != 16777215) {
            parseOpacity = i;
        }
        this.mCachedNonDefaultOpacity = 16777215;
        return parseOpacity;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        ColorPreference colorPreference =
                (ColorPreference) preferenceScreen.findPreference(getPreferenceKey());
        Resources resources = this.mContext.getResources();
        int[] intArray = resources.getIntArray(R.array.captioning_color_selector_values);
        String[] stringArray = resources.getStringArray(R.array.captioning_color_selector_titles);
        int[] iArr = new int[intArray.length + 1];
        String[] strArr = new String[stringArray.length + 1];
        System.arraycopy(intArray, 0, iArr, 1, intArray.length);
        System.arraycopy(stringArray, 0, strArr, 1, stringArray.length);
        iArr[0] = 0;
        strArr[0] = this.mContext.getString(R.string.color_none);
        colorPreference.mEntryTitles = strArr;
        colorPreference.setValues(iArr);
        colorPreference.setValue(CaptionUtils.parseColor(this.mCaptionHelper.getWindowColor()));
        colorPreference.mOnValueChangedListener = this;
        colorPreference.seslSetSummaryColor(
                this.mContext.getColorStateList(R.color.text_color_primary_dark));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.accessibility.ListDialogPreference.OnValueChangedListener
    public void onValueChanged(ListDialogPreference listDialogPreference, int i) {
        Settings.Secure.putInt(
                this.mCaptionHelper.mContentResolver,
                "accessibility_captioning_window_color",
                CaptionUtils.mergeColorOpacity(
                        i, getNonDefaultOpacity(CaptioningManager.CaptionStyle.hasColor(i))));
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
