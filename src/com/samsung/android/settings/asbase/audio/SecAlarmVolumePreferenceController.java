package com.samsung.android.settings.asbase.audio;

import android.content.Context;
import android.content.Intent;
import android.media.AudioSystem;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.notification.AudioHelper;

import com.samsung.android.settings.asbase.utils.SecAudioHelper;
import com.samsung.android.settings.asbase.utils.SoundUtils;
import com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAlarmVolumePreferenceController extends SecVolumeSeekBarPreferenceController {
    private static final String KEY_ALARM_VOLUME = "alarm_volume";

    public SecAlarmVolumePreferenceController(Context context) {
        super(context, KEY_ALARM_VOLUME);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getAudioStream() {
        return 4;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mContext.getResources().getBoolean(R.bool.config_show_alarm_volume)
                        && !AudioSystem.isSingleVolume(
                                ((SecVolumeSeekBarPreferenceController) this).mHelper.mContext)
                        && SoundUtils.isInstalledClockApp(this.mContext))
                ? 0
                : 3;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_ALARM_VOLUME;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), KEY_ALARM_VOLUME);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public void onResume() {
        super.onResume();
        SecAudioHelper secAudioHelper = ((SecVolumeSeekBarPreferenceController) this).mHelper;
        updatePreferenceIcon(
                ((AudioHelper) secAudioHelper).mAudioManager.getStreamVolume(getAudioStream()));
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public void updatePreferenceIcon(int i) {
        SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
        if (secVolumeSeekBarPreference != null) {
            secVolumeSeekBarPreference.showIcon(R.drawable.tw_ic_alarm_volume_mtrl);
        }
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
