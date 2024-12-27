package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.accessibility.CaptioningManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.google.common.primitives.Ints;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CaptioningAppearancePreferenceController extends BasePreferenceController {
    private final CaptioningManager mCaptioningManager;

    public CaptioningAppearancePreferenceController(Context context, String str) {
        super(context, str);
        this.mCaptioningManager =
                (CaptioningManager) context.getSystemService(CaptioningManager.class);
    }

    private CharSequence geFontScaleSummary() {
        float[] fontScaleValuesArray = getFontScaleValuesArray();
        String[] stringArray =
                this.mContext
                        .getResources()
                        .getStringArray(R.array.captioning_font_size_selector_titles);
        float fontScale = this.mCaptioningManager.getFontScale();
        int length = fontScaleValuesArray.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                i = -1;
                break;
            }
            if (fontScaleValuesArray[i] == fontScale) {
                break;
            }
            i++;
        }
        return stringArray[i != -1 ? i : 0];
    }

    private float[] getFontScaleValuesArray() {
        String[] stringArray =
                this.mContext
                        .getResources()
                        .getStringArray(R.array.captioning_font_size_selector_values);
        int length = stringArray.length;
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = Float.parseFloat(stringArray[i]);
        }
        return fArr;
    }

    private CharSequence getPresetSummary() {
        return this.mContext.getResources()
                .getStringArray(R.array.captioning_preset_selector_titles)[
                Ints.indexOf(
                        this.mCaptioningManager.getRawUserStyle(),
                        this.mContext
                                .getResources()
                                .getIntArray(R.array.captioning_preset_selector_values))];
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            findPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
        }
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getString(
                R.string.preference_summary_default_combination,
                geFontScaleSummary(),
                getPresetSummary());
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
