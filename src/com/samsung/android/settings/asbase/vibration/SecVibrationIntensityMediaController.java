package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;

import com.android.settings.R;

import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.asbase.widget.SecVibrationIcon;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVibrationIntensityMediaController extends SecVibrationSeekBarPreferenceController {
    private static final VibrationAttributes SEP_VIBRATION_ATTRIBUTES =
            new VibrationAttributes.Builder().setUsage(19).build();

    public SecVibrationIntensityMediaController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return VibUtils.isSupportDcHaptic(this.mContext) ? 3 : 0;
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
        return VibUtils.isSupportDcHaptic(this.mContext)
                ? VibrationEffect.semCreateWaveform(50124, -1)
                : VibrationEffect.semCreateHaptic(50025, -1);
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
    public Ringtone getRingtone(int i) {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public int getSAEventId() {
        return 4022;
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
        return 3;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public String getSyncDbName(int i) {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public String getSystemDBName() {
        return "media_vibration_intensity";
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public int getTitle() {
        return R.string.sec_vibration_media;
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
