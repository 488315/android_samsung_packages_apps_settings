package com.samsung.android.settings.accessibility.vision.controllers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.provider.Settings;
import android.view.View;

import androidx.lifecycle.LifecycleObserver;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentMainFragment;
import com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentResetRadioPreference;
import com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentTestFragment;
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
public class ColorAdjustmentRadioButtonPreferenceController extends BasePreferenceController
        implements LifecycleObserver, SelectorWithWidgetPreference.OnClickListener {
    private static final int PERSONALIZED = 4;
    private final Map<String, Integer> mAccessibilityColorAdjustmentKeyToValueMap;
    private int mAccessibilityColorAdjustmentValue;
    private final ContentResolver mContentResolver;
    private OnChangeListener mOnChangeListener;
    private ColorAdjustmentResetRadioPreference mPreference;
    private final Resources mResources;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnChangeListener {}

    public ColorAdjustmentRadioButtonPreferenceController(Context context, String str) {
        super(context, str);
        this.mAccessibilityColorAdjustmentKeyToValueMap = new HashMap();
        this.mContentResolver = context.getContentResolver();
        this.mResources = context.getResources();
    }

    private Map<String, Integer> getColorAdjustmentValueToKeyMap() {
        if (this.mAccessibilityColorAdjustmentKeyToValueMap.size() == 0) {
            String[] stringArray = this.mResources.getStringArray(R.array.color_adjust_keys);
            int length = stringArray.length;
            for (int i = 0; i < length; i++) {
                this.mAccessibilityColorAdjustmentKeyToValueMap.put(
                        stringArray[i], Integer.valueOf(i));
            }
        }
        return this.mAccessibilityColorAdjustmentKeyToValueMap;
    }

    private void handlePreferenceChange(int i) {
        if (i == 4
                && Settings.System.getInt(this.mContext.getContentResolver(), "color_blind_test", 0)
                        == 0) {
            startColorTest();
        } else {
            Settings.Secure.putInt(this.mContentResolver, "color_adjustment_type", i);
            SecAccessibilityUtils.setColorAdjustment(this.mContext);
        }
    }

    private void startColorTest() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ColorAdjustmentTestFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.personalized_color, null);
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        ColorAdjustmentResetRadioPreference colorAdjustmentResetRadioPreference =
                (ColorAdjustmentResetRadioPreference)
                        preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = colorAdjustmentResetRadioPreference;
        ((SelectorWithWidgetPreference) colorAdjustmentResetRadioPreference).mListener = this;
        View view = colorAdjustmentResetRadioPreference.mAppendix;
        if (view != null) {
            view.setVisibility(8);
        }
        colorAdjustmentResetRadioPreference.mAppendixVisibility = 8;
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

    @Override // com.android.settings.core.BasePreferenceController
    public int getMetricsCategory() {
        return 3080;
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
        handlePreferenceChange(
                getColorAdjustmentValueToKeyMap().get(this.mPreferenceKey).intValue());
        OnChangeListener onChangeListener = this.mOnChangeListener;
        if (onChangeListener != null) {
            ColorAdjustmentResetRadioPreference colorAdjustmentResetRadioPreference =
                    this.mPreference;
            ColorAdjustmentMainFragment colorAdjustmentMainFragment =
                    (ColorAdjustmentMainFragment) onChangeListener;
            colorAdjustmentMainFragment.getClass();
            Iterator it = ((ArrayList) ColorAdjustmentMainFragment.sControllers).iterator();
            while (it.hasNext()) {
                ((AbstractPreferenceController) it.next())
                        .updateState(colorAdjustmentResetRadioPreference);
            }
            colorAdjustmentMainFragment.updateSeekBarPreference();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.mOnChangeListener = onChangeListener;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updatePreferenceCheckedState(int i) {
        if (this.mAccessibilityColorAdjustmentValue == i) {
            this.mPreference.setChecked(true);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mAccessibilityColorAdjustmentValue =
                Settings.Secure.getInt(this.mContentResolver, "color_adjustment_type", 2);
        this.mPreference.setChecked(false);
        updatePreferenceCheckedState(
                getColorAdjustmentValueToKeyMap().get(this.mPreference.getKey()).intValue());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public ColorAdjustmentRadioButtonPreferenceController(
            Context context, Lifecycle lifecycle, String str) {
        super(context, str);
        this.mAccessibilityColorAdjustmentKeyToValueMap = new HashMap();
        this.mContentResolver = context.getContentResolver();
        this.mResources = context.getResources();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
