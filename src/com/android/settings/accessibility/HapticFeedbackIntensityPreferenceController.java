package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class HapticFeedbackIntensityPreferenceController
        extends VibrationIntensityPreferenceController {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HapticFeedbackVibrationPreferenceConfig extends VibrationPreferenceConfig {
        public HapticFeedbackVibrationPreferenceConfig(Context context) {
            super(context, "haptic_feedback_intensity", 18);
        }

        @Override // com.android.settings.accessibility.VibrationPreferenceConfig
        public final int readIntensity() {
            if (Settings.System.getInt(this.mContentResolver, "haptic_feedback_enabled", 1) == 0) {
                return 0;
            }
            return super.readIntensity();
        }

        @Override // com.android.settings.accessibility.VibrationPreferenceConfig
        public final boolean updateIntensity(int i) {
            boolean updateIntensity = super.updateIntensity(i);
            int i2 = i == 0 ? 1 : 0;
            Settings.System.putInt(this.mContentResolver, "haptic_feedback_enabled", i2 ^ 1);
            ContentResolver contentResolver = this.mContentResolver;
            if (i2 != 0) {
                i = this.mDefaultIntensity;
            }
            Settings.System.putInt(contentResolver, "hardware_haptic_feedback_intensity", i);
            return updateIntensity;
        }
    }

    public HapticFeedbackIntensityPreferenceController(Context context, String str) {
        super(context, str, new HapticFeedbackVibrationPreferenceConfig(context));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.accessibility.VibrationIntensityPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public HapticFeedbackIntensityPreferenceController(Context context, String str, int i) {
        super(context, str, new HapticFeedbackVibrationPreferenceConfig(context), i);
    }
}
