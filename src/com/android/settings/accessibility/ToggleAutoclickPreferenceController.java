package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.ArrayMap;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.LayoutPreference;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ToggleAutoclickPreferenceController extends BasePreferenceController
        implements LifecycleObserver,
                OnStart,
                OnStop,
                SelectorWithWidgetPreference.OnClickListener,
                SharedPreferences.OnSharedPreferenceChangeListener {
    private static final int AUTOCLICK_CUSTOM_MODE = 2000;
    private static final int AUTOCLICK_OFF_MODE = 0;
    private static final String KEY_AUTOCLICK_CUSTOM_SEEKBAR = "autoclick_custom_seekbar";
    private Map<String, Integer> mAccessibilityAutoclickKeyToValueMap;
    private final ContentResolver mContentResolver;
    private SelectorWithWidgetPreference mDelayModePref;
    private final Resources mResources;
    private LayoutPreference mSeekBerPreference;
    private final SharedPreferences mSharedPreferences;

    public ToggleAutoclickPreferenceController(Context context, String str) {
        super(context, str);
        this.mAccessibilityAutoclickKeyToValueMap = new ArrayMap();
        this.mSharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        this.mContentResolver = context.getContentResolver();
        this.mResources = context.getResources();
    }

    private Map<String, Integer> getAutoclickModeToKeyMap() {
        if (this.mAccessibilityAutoclickKeyToValueMap.size() == 0) {
            String[] stringArray =
                    this.mResources.getStringArray(
                            R.array.accessibility_autoclick_control_selector_keys);
            int[] intArray =
                    this.mResources.getIntArray(R.array.accessibility_autoclick_selector_values);
            int length = intArray.length;
            for (int i = 0; i < length; i++) {
                this.mAccessibilityAutoclickKeyToValueMap.put(
                        stringArray[i], Integer.valueOf(intArray[i]));
            }
        }
        return this.mAccessibilityAutoclickKeyToValueMap;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                (SelectorWithWidgetPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mDelayModePref = selectorWithWidgetPreference;
        selectorWithWidgetPreference.mListener = this;
        this.mSeekBerPreference =
                (LayoutPreference) preferenceScreen.findPreference(KEY_AUTOCLICK_CUSTOM_SEEKBAR);
        updateState(this.mDelayModePref);
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
        int intValue = getAutoclickModeToKeyMap().get(this.mPreferenceKey).intValue();
        Settings.Secure.putInt(
                this.mContentResolver, "accessibility_autoclick_enabled", intValue != 0 ? 1 : 0);
        this.mSharedPreferences.edit().putInt("delay_mode", intValue).apply();
        if (intValue != AUTOCLICK_CUSTOM_MODE) {
            Settings.Secure.putInt(
                    this.mContentResolver, "accessibility_autoclick_delay", intValue);
        }
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        updateState(this.mDelayModePref);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
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
        int i =
                Settings.Secure.getInt(
                                        this.mContext.getContentResolver(),
                                        "accessibility_autoclick_enabled",
                                        0)
                                == 1
                        ? this.mSharedPreferences.getInt("delay_mode", AUTOCLICK_CUSTOM_MODE)
                        : 0;
        int intValue = getAutoclickModeToKeyMap().get(this.mDelayModePref.getKey()).intValue();
        this.mDelayModePref.setChecked(i == intValue);
        if (intValue == AUTOCLICK_CUSTOM_MODE) {
            this.mSeekBerPreference.setVisible(this.mDelayModePref.mChecked);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
