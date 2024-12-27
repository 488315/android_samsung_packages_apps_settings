package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;

import com.android.settings.core.SliderPreferenceController;
import com.android.settings.widget.SeekBarPreference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FloatingMenuTransparencyPreferenceController extends SliderPreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    static final float DEFAULT_TRANSPARENCY = 0.45f;
    private static final int FADE_ENABLED = 1;
    static final float MAXIMUM_TRANSPARENCY = 1.0f;
    private static final float MAX_PROGRESS = 90.0f;
    private static final float MIN_PROGRESS = 0.0f;
    static final float PRECISION = 100.0f;
    final ContentObserver mContentObserver;
    private final ContentResolver mContentResolver;
    private SeekBarPreference mPreference;

    public FloatingMenuTransparencyPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentResolver = context.getContentResolver();
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.accessibility.FloatingMenuTransparencyPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        FloatingMenuTransparencyPreferenceController.this
                                .updateAvailabilityStatus();
                    }
                };
    }

    private int convertTransparencyFloatToInt(float f) {
        return Math.round(f * PRECISION);
    }

    private float convertTransparencyIntToFloat(int i) {
        return i / PRECISION;
    }

    private float getTransparency() {
        float f =
                1.0f
                        - Settings.Secure.getFloat(
                                this.mContentResolver,
                                "accessibility_floating_menu_opacity",
                                DEFAULT_TRANSPARENCY);
        return (f < 0.0f || f > 0.9f) ? DEFAULT_TRANSPARENCY : f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAvailabilityStatus() {
        this.mPreference.setEnabled(
                AccessibilityUtil.isFloatingMenuEnabled(this.mContext)
                        && (Settings.Secure.getInt(
                                        this.mContentResolver,
                                        "accessibility_floating_menu_fade_enabled",
                                        1)
                                == 1));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SeekBarPreference seekBarPreference =
                (SeekBarPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = seekBarPreference;
        seekBarPreference.mContinuousUpdates = true;
        seekBarPreference.setMax(getMax());
        this.mPreference.setMin(getMin());
        this.mPreference.mHapticFeedbackMode = 2;
        updateAvailabilityStatus();
        updateState(this.mPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return AccessibilityUtil.isFloatingMenuEnabled(this.mContext) ? 0 : 5;
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
        return 90;
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
        return convertTransparencyFloatToInt(getTransparency());
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContentResolver.unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_button_mode"),
                false,
                this.mContentObserver);
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_floating_menu_fade_enabled"),
                false,
                this.mContentObserver);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        return Settings.Secure.putFloat(
                this.mContentResolver,
                "accessibility_floating_menu_opacity",
                1.0f - convertTransparencyIntToFloat(i));
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
