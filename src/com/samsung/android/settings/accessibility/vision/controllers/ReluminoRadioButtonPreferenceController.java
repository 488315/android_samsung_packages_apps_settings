package com.samsung.android.settings.accessibility.vision.controllers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.provider.Settings;

import androidx.lifecycle.LifecycleObserver;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.vision.color.ReluminoFragment;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ReluminoRadioButtonPreferenceController extends BasePreferenceController
        implements LifecycleObserver, SelectorWithWidgetPreference.OnClickListener {
    private final String TAG;
    private final Map<String, Integer> mAccessibilityReluminoKeyToValueMap;
    private final ContentResolver mContentResolver;
    private OnChangeListener mOnChangeListener;
    private SelectorWithWidgetPreference mPreference;
    private int mReluminoValue;
    private final Resources mResources;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnChangeListener {}

    public ReluminoRadioButtonPreferenceController(Context context, String str) {
        super(context, str);
        this.TAG = "ReluminoRadioButtonPreferenceController";
        this.mAccessibilityReluminoKeyToValueMap = new HashMap();
        this.mContentResolver = context.getContentResolver();
        this.mResources = context.getResources();
    }

    private Map<String, Integer> getReluminoValueToKeyMap() {
        if (this.mAccessibilityReluminoKeyToValueMap.size() == 0) {
            String[] stringArray = this.mResources.getStringArray(R.array.relumino_keys);
            int length = stringArray.length;
            for (int i = 0; i < length; i++) {
                this.mAccessibilityReluminoKeyToValueMap.put(stringArray[i], Integer.valueOf(i));
            }
        }
        return this.mAccessibilityReluminoKeyToValueMap;
    }

    private void handlePreferenceChange(int i) {
        Settings.Secure.putInt(this.mContentResolver, "relumino_type", i);
        SecAccessibilityUtils.setRelumino(this.mContext);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                (SelectorWithWidgetPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = selectorWithWidgetPreference;
        selectorWithWidgetPreference.mListener = this;
        updateState(selectorWithWidgetPreference);
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
        SemLog.d(
                "ReluminoRadioButtonPreferenceController",
                "onRadioButtonClicked : " + selectorWithWidgetPreference);
        handlePreferenceChange(getReluminoValueToKeyMap().get(this.mPreferenceKey).intValue());
        OnChangeListener onChangeListener = this.mOnChangeListener;
        if (onChangeListener != null) {
            SelectorWithWidgetPreference selectorWithWidgetPreference2 = this.mPreference;
            ((ReluminoFragment) onChangeListener).getClass();
            Iterator it = ((ArrayList) ReluminoFragment.sControllers).iterator();
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
        SemLog.d(
                "ReluminoRadioButtonPreferenceController",
                "mReluminoValue : " + this.mReluminoValue + "value:" + i);
        if (this.mReluminoValue == i) {
            this.mPreference.setChecked(true);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mReluminoValue = Settings.Secure.getInt(this.mContentResolver, "relumino_type", 0);
        this.mPreference.setChecked(false);
        updatePreferenceCheckedState(
                getReluminoValueToKeyMap().get(this.mPreference.getKey()).intValue());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public ReluminoRadioButtonPreferenceController(
            Context context, Lifecycle lifecycle, String str) {
        super(context, str);
        this.TAG = "ReluminoRadioButtonPreferenceController";
        this.mAccessibilityReluminoKeyToValueMap = new HashMap();
        this.mContentResolver = context.getContentResolver();
        this.mResources = context.getResources();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
