package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.DeviceConfig;
import android.provider.Settings;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.server.accessibility.FeatureFlagsImpl;
import com.android.settings.R;
import com.android.settings.core.SliderPreferenceController;

import com.samsung.android.settings.accessibility.base.widget.A11ySeekBarPreference;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DaltonizerSaturationSeekbarPreferenceController extends SliderPreferenceController
        implements LifecycleEventObserver {
    private static final int DEFAULT_SATURATION_LEVEL = 7;
    private static final int SATURATION_MAX = 10;
    private static final int SATURATION_MIN = 0;
    final ContentObserver mContentObserver;
    private final ContentResolver mContentResolver;
    private int mSliderPosition;
    A11ySeekBarPreference preference;

    public DaltonizerSaturationSeekbarPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.accessibility.DaltonizerSaturationSeekbarPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        if (uri.equals(
                                Settings.Secure.getUriFor(
                                        "accessibility_display_daltonizer_saturation_level"))) {
                            DaltonizerSaturationSeekbarPreferenceController.this.updateSeekBar();
                        } else {
                            DaltonizerSaturationSeekbarPreferenceController
                                    daltonizerSaturationSeekbarPreferenceController =
                                            DaltonizerSaturationSeekbarPreferenceController.this;
                            daltonizerSaturationSeekbarPreferenceController.updateState(
                                    daltonizerSaturationSeekbarPreferenceController.preference);
                        }
                    }
                };
        ContentResolver contentResolver = context.getContentResolver();
        this.mContentResolver = contentResolver;
        this.mSliderPosition =
                Settings.Secure.getInt(
                        contentResolver, "accessibility_display_daltonizer_saturation_level", 7);
    }

    private int getCurrentLevel() {
        return Settings.Secure.getInt(
                this.mContentResolver, "accessibility_display_daltonizer_saturation_level", 7);
    }

    private CharSequence getLevelStateDescription(int i) {
        return this.mContext.getString(
                R.string.color_correction_intensity_level, Integer.valueOf(i + 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSeekBar() {
        if (this.preference != null) {
            int currentLevel = getCurrentLevel();
            this.preference.setValueInternal(currentLevel, true);
            this.mSliderPosition = currentLevel;
            this.preference.setStateDescription(getLevelStateDescription(currentLevel));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        A11ySeekBarPreference a11ySeekBarPreference =
                (A11ySeekBarPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.preference = a11ySeekBarPreference;
        a11ySeekBarPreference.setMax(getMax());
        this.preference.setMin(getMin());
        setSliderPosition(this.mSliderPosition);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!FeatureFlagsImpl.accessibility_is_cached) {
            try {
                FeatureFlagsImpl.enableColorCorrectionSaturation =
                        DeviceConfig.getProperties("accessibility", new String[0])
                                .getBoolean(
                                        "com.android.server.accessibility.enable_color_correction_saturation",
                                        true);
                FeatureFlagsImpl.accessibility_is_cached = true;
            } catch (NullPointerException e) {
                throw new RuntimeException(
                        "Cannot read value from namespace accessibility from DeviceConfig. It could"
                            + " be that the code using flag executed before SettingsProvider"
                            + " initialization. Please use fixed read-only flag by adding"
                            + " is_fixed_read_only: true in flag declaration.",
                        e);
            }
        }
        return FeatureFlagsImpl.enableColorCorrectionSaturation ? 0 : 2;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMax() {
        return 10;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMin() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getSliderPosition() {
        return this.mSliderPosition;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_RESUME) {
            updateSeekBar();
            this.mContentResolver.registerContentObserver(
                    Settings.Secure.getUriFor("accessibility_display_daltonizer"),
                    false,
                    this.mContentObserver);
            this.mContentResolver.registerContentObserver(
                    Settings.Secure.getUriFor("accessibility_display_daltonizer_saturation_level"),
                    false,
                    this.mContentObserver);
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            this.mContentResolver.unregisterContentObserver(this.mContentObserver);
        }
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        if (i < getMin() || i > getMax()) {
            return false;
        }
        this.mSliderPosition = i;
        Settings.Secure.putInt(
                this.mContentResolver, "accessibility_display_daltonizer_saturation_level", i);
        this.preference.setStateDescription(getLevelStateDescription(this.mSliderPosition));
        return true;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(
                DaltonizerRadioButtonPreferenceController.getSecureAccessibilityDaltonizerValue(
                                this.mContentResolver, "accessibility_display_daltonizer")
                        != 0);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
