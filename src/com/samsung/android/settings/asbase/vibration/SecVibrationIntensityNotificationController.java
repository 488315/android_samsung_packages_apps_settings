package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.asbase.widget.SecVibrationIcon;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVibrationIntensityNotificationController
        extends SecVibrationSeekBarPreferenceController {
    private static final AudioAttributes NOTI_VIBRATION_ATTRIBUTES =
            new AudioAttributes.Builder()
                    .setContentType(4)
                    .setUsage(5)
                    .setHapticChannelsMuted(false)
                    .build();
    private static final VibrationAttributes SEP_VIBRATION_ATTRIBUTES =
            new VibrationAttributes.Builder().setUsage(49).build();

    public SecVibrationIntensityNotificationController(Context context, String str) {
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
        return VibrationEffect.semCreateHaptic(
                Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "notification_vibration_sep_index",
                        50034),
                -1);
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
        Uri actualDefaultRingtoneUri;
        Ringtone semGetRingtone;
        if (!VibRune.SUPPORT_SYNC_WITH_HAPTIC
                || (actualDefaultRingtoneUri =
                                RingtoneManager.getActualDefaultRingtoneUri(this.mContext, 2))
                        == null
                || !AudioManager.hasHapticChannels(this.mContext, actualDefaultRingtoneUri)
                || (semGetRingtone =
                                RingtoneManager.semGetRingtone(
                                        this.mContext, 0, actualDefaultRingtoneUri))
                        == null) {
            return null;
        }
        semGetRingtone.setAudioAttributes(NOTI_VIBRATION_ATTRIBUTES);
        semGetRingtone.setVolume(0.0f);
        semGetRingtone.setLooping(false);
        return semGetRingtone;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public int getSAEventId() {
        return 4004;
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
        return 5;
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public String getSyncDbName(int i) {
        return "sync_vibration_with_notification";
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public String getSystemDBName() {
        return "SEM_VIBRATION_NOTIFICATION_INTENSITY";
    }

    @Override // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController
    public int getTitle() {
        return R.string.sec_vibration_notification;
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
