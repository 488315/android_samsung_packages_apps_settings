package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.asbase.widget.SecVibrationIcon;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVibrationIntensityTouchVibrationController
        extends SecVibrationSeekBarPreferenceController {
    private static final String KEY_SYSTEM_VIBRATION = "system_vibration";
    private static final VibrationAttributes SEP_VIBRATION_ATTRIBUTES =
            new VibrationAttributes.Builder().setUsage(18).build();

    public SecVibrationIntensityTouchVibrationController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SYSTEM_VIBRATION;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public Ringtone getRingtone(int i) {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public int getSAEventId() {
        return 4005;
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
        return 1;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public String getSyncDbName(int i) {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public String getSystemDBName() {
        return "VIB_FEEDBACK_MAGNITUDE";
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public int getTitle() {
        return R.string.sec_sound_system;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        int i =
                Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "VIB_FEEDBACK_MAGNITUDE",
                        VibUtils.isSupportDcHaptic(this.mContext) ? 1 : 2);
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(Integer.valueOf(i));
        builder.addAttributeInt(VibUtils.isSupportDcHaptic(this.mContext) ? 1 : 0, "isDcHaptic");
        builder.addAttributeInt(0, "min");
        builder.addAttributeInt(getMaxVibrationIntensity(), "max");
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        return builder.build();
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

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003c, code lost:

       if (r1 == 2) goto L10;
    */
    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.samsung.android.settings.cube.ControlResult setValue(
            com.samsung.android.settings.cube.ControlValue r8) {
        /*
            r7 = this;
            com.samsung.android.settings.cube.ControlResult$Builder r0 = new com.samsung.android.settings.cube.ControlResult$Builder
            java.lang.String r1 = r7.getPreferenceKey()
            r0.<init>(r1)
            java.lang.String r1 = r8.getValue()
            int r1 = java.lang.Integer.parseInt(r1)
            java.lang.String r2 = "isDcHaptic"
            int r8 = r8.getAttributeInt(r2)
            r2 = 1
            if (r8 != r2) goto L1c
            r8 = r2
            goto L1d
        L1c:
            r8 = 0
        L1d:
            android.content.Context r3 = r7.mContext
            boolean r3 = com.samsung.android.settings.asbase.utils.VibUtils.isSupportDcHaptic(r3)
            r4 = 2
            r5 = 5
            r6 = 3
            if (r3 == 0) goto L36
            if (r8 != 0) goto L3f
            if (r1 != r5) goto L2e
        L2c:
            r1 = r6
            goto L3f
        L2e:
            if (r1 < r6) goto L32
            r1 = r4
            goto L3f
        L32:
            if (r1 < r2) goto L3f
            r1 = r2
            goto L3f
        L36:
            if (r8 == 0) goto L3f
            if (r1 != r6) goto L3c
            r1 = r5
            goto L3f
        L3c:
            if (r1 != r4) goto L3f
            goto L2c
        L3f:
            android.content.Context r7 = r7.mContext
            android.content.ContentResolver r7 = r7.getContentResolver()
            java.lang.String r8 = "VIB_FEEDBACK_MAGNITUDE"
            android.provider.Settings.System.putInt(r7, r8, r1)
            com.samsung.android.settings.cube.ControlResult$ResultCode r7 = com.samsung.android.settings.cube.ControlResult.ResultCode.SUCCESS
            r0.mResultCode = r7
            com.samsung.android.settings.cube.ControlResult r7 = r0.build()
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.vibration.SecVibrationIntensityTouchVibrationController.setValue(com.samsung.android.settings.cube.ControlValue):com.samsung.android.settings.cube.ControlResult");
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
