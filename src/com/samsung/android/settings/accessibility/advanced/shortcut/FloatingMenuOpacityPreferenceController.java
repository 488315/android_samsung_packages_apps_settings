package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;

import com.android.settings.accessibility.AccessibilityUtil;
import com.android.settings.core.SliderPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.base.widget.A11ySeekBarWithButtonsPreference;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FloatingMenuOpacityPreferenceController extends SliderPreferenceController
        implements LifecycleObserver, OnResume, OnPause, A11yStatusLoggingProvider {
    static final int DEFAULT_TRANSPARENCY = 3;
    static final float[] TRANSPARENCY = {1.0f, 0.88f, 0.75f, 0.62f, 0.49f, 0.36f, 0.23f, 0.1f};
    private final ContentObserver mContentObserver;
    private final ContentResolver mContentResolver;
    private A11ySeekBarWithButtonsPreference mPreference;

    public FloatingMenuOpacityPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentResolver = context.getContentResolver();
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.accessibility.advanced.shortcut.FloatingMenuOpacityPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        FloatingMenuOpacityPreferenceController.this.updateAvailabilityStatus();
                    }
                };
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
        A11ySeekBarWithButtonsPreference a11ySeekBarWithButtonsPreference =
                (A11ySeekBarWithButtonsPreference)
                        preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = a11ySeekBarWithButtonsPreference;
        a11ySeekBarWithButtonsPreference.setMax(getMax());
        this.mPreference.setMin(getMin());
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
        return 7;
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
        float f =
                Settings.Secure.getFloat(
                        this.mContext.getContentResolver(),
                        "accessibility_floating_menu_opacity",
                        0.75f);
        int i = 0;
        while (true) {
            float[] fArr = TRANSPARENCY;
            if (i >= fArr.length) {
                return 3;
            }
            if (f == fArr[i]) {
                return i;
            }
            i++;
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        if (SecAccessibilityUtils.isAccessibilityButtonEmpty(this.mContext)
                || !AccessibilityUtil.isFloatingMenuEnabled(this.mContext)) {
            return Map.of();
        }
        return Map.of(
                "A11YS6006",
                Settings.Secure.getInt(
                                        context.getContentResolver(),
                                        "accessibility_floating_menu_fade_enabled",
                                        1)
                                == 1
                        ? String.valueOf(getSliderPosition())
                        : "Off");
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
        double max = i / (getMax() * 1.0d);
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        A11ySeekBarWithButtonsPreference a11ySeekBarWithButtonsPreference = this.mPreference;
        if (a11ySeekBarWithButtonsPreference != null) {
            a11ySeekBarWithButtonsPreference.setStateDescription(percentInstance.format(max));
        }
        return Settings.Secure.putFloat(
                this.mContext.getContentResolver(),
                "accessibility_floating_menu_opacity",
                TRANSPARENCY[i]);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
