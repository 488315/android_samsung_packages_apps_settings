package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.settings.R;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MediaVibrationIntensityPreferenceController
        extends VibrationIntensityPreferenceController {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MediaVibrationPreferenceConfig extends VibrationPreferenceConfig {
        public MediaVibrationPreferenceConfig(Context context) {
            super(context, "media_vibration_intensity", 19);
        }
    }

    public MediaVibrationIntensityPreferenceController(Context context, String str) {
        super(context, str, new MediaVibrationPreferenceConfig(context));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getResources().getBoolean(R.bool.config_media_vibration_supported)
                ? 0
                : 3;
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

    public MediaVibrationIntensityPreferenceController(Context context, String str, int i) {
        super(context, str, new MediaVibrationPreferenceConfig(context), i);
    }
}