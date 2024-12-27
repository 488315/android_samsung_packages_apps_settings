package com.samsung.android.settings.asbase.audio;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.text.TextUtils;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.notification.AudioHelper;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.presence.ServiceTuple;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecNotificationVolumePreferenceController extends SecRingVolumePreferenceController {
    private static final String KEY_NOTIFICATION_VOLUME = "notification_volume";
    private AudioManager mAudioManager;

    public SecNotificationVolumePreferenceController(Context context) {
        super(context, KEY_NOTIFICATION_VOLUME);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (this.mAudioManager.shouldShowRingtoneVolume()) {
            return;
        }
        this.mPreference.setOrder(0);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getAudioStream() {
        return 5;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getMuteIcon() {
        return R.drawable.tw_ic_audio_noti_mute_mtrl;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getNormalIcon() {
        return R.drawable.tw_ic_audio_noti_mtrl;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_NOTIFICATION_VOLUME;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getVibrateIcon() {
        return R.drawable.tw_ic_audio_noti_vibrate_mtrl;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), KEY_NOTIFICATION_VOLUME);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public void onResume() {
        super.onResume();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController,
              // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public void updatePreferenceIcon(int i) {
        if (this.mPreference != null) {
            if (this.mAudioManager.shouldShowRingtoneVolume()) {
                if (i > 0) {
                    this.mPreference.showIcon(getNormalIcon());
                    return;
                } else if (wasRingerModeVibrate()) {
                    this.mPreference.showIcon(getVibrateIcon());
                    return;
                } else {
                    this.mPreference.showIcon(getMuteIcon());
                    return;
                }
            }
            int ringerModeInternal =
                    ((AudioHelper) ((SecVolumeSeekBarPreferenceController) this).mHelper)
                            .mAudioManager.getRingerModeInternal();
            if (this.mPreference != null) {
                if (wasRingerModeVibrate()) {
                    this.mPreference.showIcon(getVibrateIcon());
                } else if (ringerModeInternal == 0) {
                    this.mPreference.showIcon(getMuteIcon());
                } else {
                    this.mPreference.showIcon(getNormalIcon());
                }
            }
        }
    }
}
