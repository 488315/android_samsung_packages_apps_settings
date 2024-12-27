package com.samsung.android.settings.asbase.vibration;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.VibratorManager;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Rune$$ExternalSyntheticOutline0;
import com.samsung.android.settings.asbase.widget.SecVibrationIcon;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVibrationIntensityHardPressVibrationFeedbackController
        extends SecVibrationSeekBarPreferenceController {
    private static final VibrationAttributes SEP_VIBRATION_ATTRIBUTES =
            new VibrationAttributes.Builder().setUsage(50).build();

    public SecVibrationIntensityHardPressVibrationFeedbackController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return (Rune$$ExternalSyntheticOutline0.m(
                                "SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME",
                                "SupportForceTouch")
                        && ((VibratorManager) this.mContext.getSystemService("vibrator_manager"))
                                        .getDefaultVibrator()
                                        .semGetSupportedVibrationType()
                                > 0
                        && this.mContext
                                .getResources()
                                .getBoolean(R.bool.config_sms_decode_gsm_8bit_data))
                ? 0
                : 3;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public VibrationEffect getEffect(int i) {
        return VibrationEffect.semCreateHaptic(50038, -1);
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public int getMaxVibrationIntensity() {
        return 4;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public Ringtone getRingtone(int i) {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public int getSAEventId() {
        return 7220;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public int getStream() {
        return 0;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public String getSyncDbName(int i) {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public String getSystemDBName() {
        return "SEM_VIBRATION_FORCE_TOUCH_INTENSITY";
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public int getTitle() {
        return com.android.settings.R.string.force_touch_title;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public VibrationAttributes getVibrationAttributes() {
        return SEP_VIBRATION_ATTRIBUTES;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public void updatePreferenceIcon(int i) {
        SecVibrationIcon secVibrationIcon;
        SecVibrationSeekBarPreference secVibrationSeekBarPreference = this.mPreference;
        if (secVibrationSeekBarPreference == null
                || (secVibrationIcon = secVibrationSeekBarPreference.mVibrationIcon) == null) {
            return;
        }
        secVibrationIcon.updateLayout(false);
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
