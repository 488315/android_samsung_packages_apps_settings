package com.android.settings.accessibility;

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
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.google.common.primitives.Ints;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DaltonizerRadioButtonPreferenceController extends BasePreferenceController
        implements LifecycleObserver, SelectorWithWidgetPreference.OnClickListener {
    private static final String TYPE = "accessibility_display_daltonizer";
    private final Map<String, Integer> mAccessibilityDaltonizerKeyToValueMap;
    private int mAccessibilityDaltonizerValue;
    private final ContentResolver mContentResolver;
    private OnChangeListener mOnChangeListener;
    private SelectorWithWidgetPreference mPreference;
    private final Resources mResources;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnChangeListener {}

    public DaltonizerRadioButtonPreferenceController(Context context, String str) {
        super(context, str);
        this.mAccessibilityDaltonizerKeyToValueMap = new HashMap();
        this.mContentResolver = context.getContentResolver();
        this.mResources = context.getResources();
    }

    private int getAccessibilityDaltonizerValue() {
        return getSecureAccessibilityDaltonizerValue(this.mContentResolver, TYPE);
    }

    private Map<String, Integer> getDaltonizerValueToKeyMap() {
        if (this.mAccessibilityDaltonizerKeyToValueMap.size() == 0) {
            String[] stringArray = this.mResources.getStringArray(R.array.daltonizer_mode_keys);
            int[] intArray = this.mResources.getIntArray(R.array.daltonizer_type_values);
            int length = intArray.length;
            for (int i = 0; i < length; i++) {
                this.mAccessibilityDaltonizerKeyToValueMap.put(
                        stringArray[i], Integer.valueOf(intArray[i]));
            }
        }
        return this.mAccessibilityDaltonizerKeyToValueMap;
    }

    public static int getSecureAccessibilityDaltonizerValue(
            ContentResolver contentResolver, String str) {
        Integer tryParse;
        String string = Settings.Secure.getString(contentResolver, str);
        if (string == null || (tryParse = Ints.tryParse(string)) == null) {
            return 12;
        }
        return tryParse.intValue();
    }

    private void handlePreferenceChange(String str) {
        putSecureString(TYPE, str);
    }

    private void putSecureString(String str, String str2) {
        Settings.Secure.putString(this.mContentResolver, str, str2);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                (SelectorWithWidgetPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = selectorWithWidgetPreference;
        selectorWithWidgetPreference.mListener = this;
        View view = selectorWithWidgetPreference.mAppendix;
        if (view != null) {
            view.setVisibility(8);
        }
        selectorWithWidgetPreference.mAppendixVisibility = 8;
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
        handlePreferenceChange(
                String.valueOf(getDaltonizerValueToKeyMap().get(this.mPreferenceKey).intValue()));
        OnChangeListener onChangeListener = this.mOnChangeListener;
        if (onChangeListener != null) {
            SelectorWithWidgetPreference selectorWithWidgetPreference2 = this.mPreference;
            ((ToggleDaltonizerPreferenceFragment) onChangeListener).getClass();
            Iterator it = ((ArrayList) ToggleDaltonizerPreferenceFragment.sControllers).iterator();
            while (it.hasNext()) {
                ((AbstractPreferenceController) it.next())
                        .updateState(selectorWithWidgetPreference2);
            }
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
        if (this.mAccessibilityDaltonizerValue == i) {
            this.mPreference.setChecked(true);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mAccessibilityDaltonizerValue = getAccessibilityDaltonizerValue();
        this.mPreference.setChecked(false);
        updatePreferenceCheckedState(
                getDaltonizerValueToKeyMap().get(this.mPreference.getKey()).intValue());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public DaltonizerRadioButtonPreferenceController(
            Context context, Lifecycle lifecycle, String str) {
        super(context, str);
        this.mAccessibilityDaltonizerKeyToValueMap = new HashMap();
        this.mContentResolver = context.getContentResolver();
        this.mResources = context.getResources();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
