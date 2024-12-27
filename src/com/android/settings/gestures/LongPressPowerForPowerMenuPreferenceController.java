package com.android.settings.gestures;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LongPressPowerForPowerMenuPreferenceController extends BasePreferenceController
        implements PowerMenuSettingsUtils.SettingsStateCallback,
                SelectorWithWidgetPreference.OnClickListener,
                LifecycleObserver {
    private SelectorWithWidgetPreference mPreference;
    private final PowerMenuSettingsUtils mUtils;

    public LongPressPowerForPowerMenuPreferenceController(Context context, String str) {
        super(context, str);
        this.mUtils = new PowerMenuSettingsUtils(context);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                (SelectorWithWidgetPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = selectorWithWidgetPreference;
        if (selectorWithWidgetPreference != null) {
            selectorWithWidgetPreference.mListener = this;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return PowerMenuSettingsUtils.isLongPressPowerSettingAvailable(this.mContext) ? 0 : 3;
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

    @Override // com.android.settings.gestures.PowerMenuSettingsUtils.SettingsStateCallback
    public void onChange(Uri uri) {
        SelectorWithWidgetPreference selectorWithWidgetPreference = this.mPreference;
        if (selectorWithWidgetPreference != null) {
            updateState(selectorWithWidgetPreference);
        }
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public void onRadioButtonClicked(SelectorWithWidgetPreference selectorWithWidgetPreference) {
        Context context = this.mContext;
        Uri uri = PowerMenuSettingsUtils.POWER_BUTTON_LONG_PRESS_URI;
        if (Settings.Global.putInt(context.getContentResolver(), "power_button_long_press", 1)) {
            Settings.Global.putInt(
                    context.getContentResolver(),
                    "key_chord_power_volume_up",
                    context.getResources()
                            .getInteger(R.integer.config_minMillisBetweenInputUserActivityEvents));
        }
        SelectorWithWidgetPreference selectorWithWidgetPreference2 = this.mPreference;
        if (selectorWithWidgetPreference2 != null) {
            updateState(selectorWithWidgetPreference2);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        this.mUtils.registerObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        this.mUtils.unregisterObserver();
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
        if (preference instanceof SelectorWithWidgetPreference) {
            ((SelectorWithWidgetPreference) preference)
                    .setChecked(
                            !PowerMenuSettingsUtils.isLongPressPowerForAssistantEnabled(
                                    this.mContext));
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
