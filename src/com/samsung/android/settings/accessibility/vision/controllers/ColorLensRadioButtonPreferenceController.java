package com.samsung.android.settings.accessibility.vision.controllers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.provider.Settings;
import android.view.View;

import androidx.lifecycle.LifecycleObserver;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.settings.accessibility.base.widget.ColorSelectorWithWidgetPreference;
import com.samsung.android.settings.accessibility.vision.color.ColorLensFragment;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ColorLensRadioButtonPreferenceController extends BasePreferenceController
        implements LifecycleObserver, SelectorWithWidgetPreference.OnClickListener {
    private final Map<String, Integer> mAccessibilityColorFilterKeyToValueMap;
    private int mAccessibilityColorFilterValue;
    private final ContentResolver mContentResolver;
    private OnChangeListener mOnChangeListener;
    private ColorSelectorWithWidgetPreference mPreference;
    private final Resources mResources;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnChangeListener {}

    public ColorLensRadioButtonPreferenceController(Context context, String str) {
        super(context, str);
        this.mAccessibilityColorFilterKeyToValueMap = new HashMap();
        this.mContentResolver = context.getContentResolver();
        this.mResources = context.getResources();
    }

    private Map<String, Integer> getColorFilterValueToKeyMap() {
        if (this.mAccessibilityColorFilterKeyToValueMap.size() == 0) {
            String[] stringArray = this.mResources.getStringArray(R.array.color_lens_keys);
            int[] intArray = this.mResources.getIntArray(R.array.color_lens_values);
            int length = intArray.length;
            for (int i = 0; i < length; i++) {
                this.mAccessibilityColorFilterKeyToValueMap.put(
                        stringArray[i], Integer.valueOf(intArray[i]));
            }
        }
        return this.mAccessibilityColorFilterKeyToValueMap;
    }

    private void handlePreferenceChange(int i) {
        Settings.Secure.putInt(this.mContentResolver, "color_lens_type", i);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        ColorSelectorWithWidgetPreference colorSelectorWithWidgetPreference =
                (ColorSelectorWithWidgetPreference)
                        preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = colorSelectorWithWidgetPreference;
        ((SelectorWithWidgetPreference) colorSelectorWithWidgetPreference).mListener = this;
        View view = colorSelectorWithWidgetPreference.mAppendix;
        if (view != null) {
            view.setVisibility(8);
        }
        colorSelectorWithWidgetPreference.mAppendixVisibility = 8;
        updateState(this.mPreference);
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

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public void onRadioButtonClicked(SelectorWithWidgetPreference selectorWithWidgetPreference) {
        handlePreferenceChange(getColorFilterValueToKeyMap().get(this.mPreferenceKey).intValue());
        OnChangeListener onChangeListener = this.mOnChangeListener;
        if (onChangeListener != null) {
            ColorSelectorWithWidgetPreference colorSelectorWithWidgetPreference = this.mPreference;
            Iterator it =
                    ((ArrayList) ((ColorLensFragment) onChangeListener).controllers).iterator();
            while (it.hasNext()) {
                ((AbstractPreferenceController) it.next())
                        .updateState(colorSelectorWithWidgetPreference);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setColorDrawable(int i) {
        GradientDrawable gradientDrawable =
                (GradientDrawable) this.mResources.getDrawable(R.drawable.color_filter_colorchip);
        if (gradientDrawable == null) {
            return;
        }
        gradientDrawable.setColor(
                this.mResources.getIntArray(R.array.color_lens_colorchip_entries)[i]);
        this.mPreference.setIcon(gradientDrawable);
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.mOnChangeListener = onChangeListener;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updatePreferenceCheckedState(int i) {
        if (this.mAccessibilityColorFilterValue == i) {
            this.mPreference.setChecked(true);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mAccessibilityColorFilterValue =
                Settings.Secure.getInt(this.mContentResolver, "color_lens_type", 0);
        this.mPreference.setChecked(false);
        int intValue = getColorFilterValueToKeyMap().get(this.mPreference.getKey()).intValue();
        updatePreferenceCheckedState(intValue);
        setColorDrawable(intValue);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public ColorLensRadioButtonPreferenceController(
            Context context, Lifecycle lifecycle, String str) {
        super(context, str);
        this.mAccessibilityColorFilterKeyToValueMap = new HashMap();
        this.mContentResolver = context.getContentResolver();
        this.mResources = context.getResources();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
