package com.samsung.android.settings.asbase.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.notification.AudioHelper;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.asbase.utils.SecAudioHelper;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSystemVolumePreferenceController extends SecVolumeSeekBarPreferenceController {
    private static final String KEY_SYSTEM_VOLUME = "system_volume";
    private static final int UPDATE_RINGER_MODE = 1;
    Handler mHandler;
    private final RingReceiver mReceiver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RingReceiver extends BroadcastReceiver {
        public boolean mRegistered;

        public RingReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(intent.getAction())) {
                SecSystemVolumePreferenceController.this.mHandler.sendEmptyMessage(1);
            }
        }

        public final void register(boolean z) {
            if (this.mRegistered == z) {
                return;
            }
            if (z) {
                ((AbstractPreferenceController) SecSystemVolumePreferenceController.this)
                        .mContext.registerReceiver(
                                this,
                                AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                                        "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION"),
                                2);
            } else {
                ((AbstractPreferenceController) SecSystemVolumePreferenceController.this)
                        .mContext.unregisterReceiver(this);
            }
            this.mRegistered = z;
        }
    }

    public SecSystemVolumePreferenceController(Context context) {
        super(context, KEY_SYSTEM_VOLUME);
        this.mReceiver = new RingReceiver();
        this.mHandler =
                new Handler(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.samsung.android.settings.asbase.audio.SecSystemVolumePreferenceController.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what != 1) {
                            return;
                        }
                        SecSystemVolumePreferenceController secSystemVolumePreferenceController =
                                SecSystemVolumePreferenceController.this;
                        SecAudioHelper secAudioHelper =
                                ((SecVolumeSeekBarPreferenceController)
                                                secSystemVolumePreferenceController)
                                        .mHelper;
                        secSystemVolumePreferenceController.updatePreferenceIcon(
                                ((AudioHelper) secAudioHelper)
                                        .mAudioManager.getStreamVolume(
                                                secSystemVolumePreferenceController
                                                        .getAudioStream()));
                    }
                };
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getAudioStream() {
        return 1;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getMuteIcon() {
        return R.drawable.tw_ic_audio_system_mute_mtrl;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getNormalIcon() {
        return R.drawable.tw_ic_audio_system_mtrl;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SYSTEM_VOLUME;
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
        return TextUtils.equals(getPreferenceKey(), KEY_SYSTEM_VOLUME);
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
    public void onPause() {
        super.onPause();
        this.mReceiver.register(false);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public void onResume() {
        super.onResume();
        this.mReceiver.register(true);
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
        if (i > 0) {
            this.mPreference.showIcon(getNormalIcon());
        } else {
            this.mPreference.showIcon(getMuteIcon());
        }
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean useDynamicSliceSummary() {
        return true;
    }
}
