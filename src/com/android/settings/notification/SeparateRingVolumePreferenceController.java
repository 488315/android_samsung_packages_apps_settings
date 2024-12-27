package com.android.settings.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioSystem;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.settings.R;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SeparateRingVolumePreferenceController
        extends RingerModeAffectedVolumePreferenceController {
    private static final String KEY_SEPARATE_RING_VOLUME = "separate_ring_volume";
    private static final String TAG = "SeparateRingVolumePreferenceController";
    private final H mHandler;
    private final RingReceiver mReceiver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class H extends Handler {
        public H() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            SeparateRingVolumePreferenceController separateRingVolumePreferenceController =
                    SeparateRingVolumePreferenceController.this;
            if (i == 1) {
                separateRingVolumePreferenceController.updateEffectsSuppressor();
            } else {
                if (i != 2) {
                    return;
                }
                separateRingVolumePreferenceController.updateRingerMode();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RingReceiver extends BroadcastReceiver {
        public boolean mRegistered;

        public RingReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED".equals(action)) {
                SeparateRingVolumePreferenceController.this.mHandler.sendEmptyMessage(1);
            } else if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(action)) {
                SeparateRingVolumePreferenceController.this.mHandler.sendEmptyMessage(2);
            }
        }

        public final void register(boolean z) {
            if (this.mRegistered == z) {
                return;
            }
            if (z) {
                ((AbstractPreferenceController) SeparateRingVolumePreferenceController.this)
                        .mContext.registerReceiver(
                                this,
                                ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                                        "android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED",
                                        "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION"));
            } else {
                ((AbstractPreferenceController) SeparateRingVolumePreferenceController.this)
                        .mContext.unregisterReceiver(this);
            }
            this.mRegistered = z;
        }
    }

    public SeparateRingVolumePreferenceController(Context context) {
        this(context, KEY_SEPARATE_RING_VOLUME);
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController
    public int getAudioStream() {
        return 2;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !AudioSystem.isSingleVolume(
                        ((VolumeSeekBarPreferenceController) this).mHelper.mContext)
                ? 0
                : 3;
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController,
              // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController,
              // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController,
              // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SEPARATE_RING_VOLUME;
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController,
              // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController,
              // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController
    public boolean hintsMatch(int i) {
        return ((i & 4) == 0 && (i & 1) == 0) ? false : true;
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController,
              // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController,
              // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController,
              // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        super.onPause();
        this.mReceiver.register(false);
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        super.onResume();
        this.mReceiver.register(true);
        updateEffectsSuppressor();
        selectPreferenceIconState();
        updateContentDescription();
        VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
        if (volumeSeekBarPreference != null) {
            volumeSeekBarPreference.setVisible(getAvailabilityStatus() == 0);
        }
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController,
              // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public SeparateRingVolumePreferenceController(Context context, String str) {
        super(context, str, TAG);
        this.mReceiver = new RingReceiver();
        this.mHandler = new H();
        this.mNormalIconId = R.drawable.ic_ring_volume;
        this.mVibrateIconId = R.drawable.ic_volume_ringer_vibrate;
        this.mSilentIconId = R.drawable.ic_ring_volume_off;
        updateRingerMode();
    }
}
