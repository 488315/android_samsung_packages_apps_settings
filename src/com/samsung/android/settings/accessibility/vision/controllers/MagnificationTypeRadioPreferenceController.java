package com.samsung.android.settings.accessibility.vision.controllers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.MagnificationCapabilities;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MagnificationTypeRadioPreferenceController extends BasePreferenceController
        implements LifecycleObserver,
                OnStart,
                OnStop,
                SelectorWithWidgetPreference.OnClickListener {
    private final ContentObserver mContentObserver;
    private final ContentResolver mContentResolver;
    private final Map<String, Integer> mMagnificationTypeValueMap;
    private SelectorWithWidgetPreference mPreference;
    private final Resources mResources;

    public MagnificationTypeRadioPreferenceController(Context context, String str) {
        super(context, str);
        this.mMagnificationTypeValueMap = new HashMap();
        this.mContentResolver = context.getContentResolver();
        this.mResources = context.getResources();
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.accessibility.vision.controllers.MagnificationTypeRadioPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        MagnificationTypeRadioPreferenceController.this.refresh();
                    }
                };
    }

    private Map<String, Integer> getMagnificationValueToKeyMap() {
        if (this.mMagnificationTypeValueMap.size() == 0) {
            String[] stringArray =
                    this.mResources.getStringArray(R.array.magnification_type_selector_keys);
            int[] intArray = this.mResources.getIntArray(R.array.magnification_type_values);
            int length = intArray.length;
            for (int i = 0; i < length; i++) {
                this.mMagnificationTypeValueMap.put(stringArray[i], Integer.valueOf(intArray[i]));
            }
        }
        return this.mMagnificationTypeValueMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh() {
        SelectorWithWidgetPreference selectorWithWidgetPreference = this.mPreference;
        if (selectorWithWidgetPreference == null) {
            return;
        }
        if (MagnificationCapabilities.getCapabilities(selectorWithWidgetPreference.getContext())
                == getMagnificationValueToKeyMap().get(this.mPreference.getKey()).intValue()) {
            this.mPreference.setChecked(true);
        } else {
            this.mPreference.setChecked(false);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                (SelectorWithWidgetPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = selectorWithWidgetPreference;
        selectorWithWidgetPreference.mListener = this;
        refresh();
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
        int intValue =
                getMagnificationValueToKeyMap()
                        .get(selectorWithWidgetPreference.getKey())
                        .intValue();
        ContentResolver contentResolver = this.mPreference.getContext().getContentResolver();
        Settings.Secure.putIntForUser(
                contentResolver,
                "accessibility_magnification_capability",
                intValue,
                contentResolver.getUserId());
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_magnification_capability"),
                false,
                this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        try {
            this.mContentResolver.unregisterContentObserver(this.mContentObserver);
        } catch (IllegalArgumentException unused) {
        }
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
