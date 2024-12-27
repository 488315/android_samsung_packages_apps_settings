package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilityTimeoutController extends BasePreferenceController
        implements LifecycleObserver,
                OnStart,
                OnStop,
                SelectorWithWidgetPreference.OnClickListener {
    private static final List<String> ACCESSIBILITY_TIMEOUT_FEATURE_KEYS =
            Arrays.asList(
                    "accessibility_non_interactive_ui_timeout_ms",
                    "accessibility_interactive_ui_timeout_ms");
    private final Map<String, Integer> mAccessibilityTimeoutKeyToValueMap;
    private int mAccessibilityUiTimeoutValue;
    private final ContentResolver mContentResolver;
    private SelectorWithWidgetPreference mPreference;
    private final Resources mResources;
    private AccessibilitySettingsContentObserver mSettingsContentObserver;

    public AccessibilityTimeoutController(Context context, String str) {
        super(context, str);
        this.mAccessibilityTimeoutKeyToValueMap = new HashMap();
        this.mContentResolver = context.getContentResolver();
        this.mResources = context.getResources();
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                new AccessibilitySettingsContentObserver(new Handler(Looper.getMainLooper()));
        this.mSettingsContentObserver = accessibilitySettingsContentObserver;
        accessibilitySettingsContentObserver.registerKeysToObserverCallback(
                ACCESSIBILITY_TIMEOUT_FEATURE_KEYS,
                new AccessibilitySettingsContentObserver
                        .ContentObserverCallback() { // from class:
                                                     // com.android.settings.accessibility.AccessibilityTimeoutController$$ExternalSyntheticLambda0
                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str2) {
                        AccessibilityTimeoutController.this.lambda$new$0(str2);
                    }
                });
    }

    private Map<String, Integer> getTimeoutValueToKeyMap() {
        if (this.mAccessibilityTimeoutKeyToValueMap.size() == 0) {
            String[] stringArray =
                    this.mResources.getStringArray(
                            R.array.accessibility_timeout_control_selector_keys);
            int[] intArray =
                    this.mResources.getIntArray(R.array.accessibility_timeout_selector_values);
            int length = intArray.length;
            for (int i = 0; i < length; i++) {
                this.mAccessibilityTimeoutKeyToValueMap.put(
                        stringArray[i], Integer.valueOf(intArray[i]));
            }
        }
        return this.mAccessibilityTimeoutKeyToValueMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(String str) {
        updateState(this.mPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                (SelectorWithWidgetPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = selectorWithWidgetPreference;
        selectorWithWidgetPreference.mListener = this;
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
        String valueOf = String.valueOf(getTimeoutValueToKeyMap().get(this.mPreferenceKey));
        Settings.Secure.putString(
                this.mContentResolver, "accessibility_non_interactive_ui_timeout_ms", valueOf);
        Settings.Secure.putString(
                this.mContentResolver, "accessibility_interactive_ui_timeout_ms", valueOf);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mSettingsContentObserver.register(this.mContentResolver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                this.mSettingsContentObserver;
        ContentResolver contentResolver = this.mContentResolver;
        accessibilitySettingsContentObserver.getClass();
        contentResolver.unregisterContentObserver(accessibilitySettingsContentObserver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mAccessibilityUiTimeoutValue =
                AccessibilityTimeoutUtils.getSecureAccessibilityTimeoutValue(this.mContentResolver);
        this.mPreference.setChecked(false);
        if (this.mAccessibilityUiTimeoutValue
                == getTimeoutValueToKeyMap().get(this.mPreference.getKey()).intValue()) {
            this.mPreference.setChecked(true);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public AccessibilityTimeoutController(
            Context context,
            String str,
            AccessibilitySettingsContentObserver accessibilitySettingsContentObserver) {
        this(context, str);
        this.mSettingsContentObserver = accessibilitySettingsContentObserver;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
