package com.samsung.android.settings.asbase.audio;

import android.R;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.settings.notification.AudioHelper;
import com.android.settings.notification.SuppressorHelper;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.asbase.utils.SecAudioHelper;
import com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.presence.ServiceTuple;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecRingVolumePreferenceController extends SecVolumeSeekBarPreferenceController {
    private static final String KEY_RING_VOLUME = "ring_volume";
    private final AudioManager mAudioManager;
    private final H mHandler;
    private int mMuteIcon;
    private final RingReceiver mReceiver;
    private int mRingerMode;
    private ComponentName mSuppressor;
    private Vibrator mVibrator;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class H extends Handler {
        public final WeakReference mControlRef;

        public H(SecRingVolumePreferenceController secRingVolumePreferenceController) {
            super(Looper.getMainLooper());
            this.mControlRef = new WeakReference(secRingVolumePreferenceController);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            WeakReference weakReference;
            int i = message.what;
            if (i != 1) {
                if (i == 2 && (weakReference = this.mControlRef) != null) {
                    ((SecRingVolumePreferenceController) weakReference.get()).updateRingerMode();
                    return;
                }
                return;
            }
            WeakReference weakReference2 = this.mControlRef;
            if (weakReference2 != null) {
                ((SecRingVolumePreferenceController) weakReference2.get())
                        .updateEffectsSuppressor();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RingReceiver extends BroadcastReceiver {
        public final WeakReference mControlRef;
        public boolean mRegistered;

        public RingReceiver(SecRingVolumePreferenceController secRingVolumePreferenceController) {
            this.mControlRef = new WeakReference(secRingVolumePreferenceController);
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (((SecRingVolumePreferenceController) this.mControlRef.get()).mHandler != null) {
                if ("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED".equals(action)) {
                    ((SecRingVolumePreferenceController) this.mControlRef.get())
                            .mHandler.sendEmptyMessage(1);
                } else if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(action)) {
                    ((SecRingVolumePreferenceController) this.mControlRef.get())
                            .mHandler.sendEmptyMessage(2);
                }
            }
        }

        public final void register(boolean z) {
            if (this.mRegistered == z) {
                return;
            }
            Context context =
                    ((AbstractPreferenceController)
                                    ((SecRingVolumePreferenceController) this.mControlRef.get()))
                            .mContext;
            if (context != null) {
                if (z) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED");
                    intentFilter.addAction("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");
                    context.registerReceiver(this, intentFilter, 2);
                } else {
                    context.unregisterReceiver(this);
                }
            }
            this.mRegistered = z;
        }
    }

    public SecRingVolumePreferenceController(Context context) {
        this(context, KEY_RING_VOLUME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEffectsSuppressor() {
        ComponentName effectsSuppressor =
                NotificationManager.from(this.mContext).getEffectsSuppressor();
        if (Objects.equals(effectsSuppressor, this.mSuppressor)) {
            return;
        }
        this.mSuppressor = effectsSuppressor;
        if (this.mPreference != null) {
            Context context = this.mContext;
            String string =
                    effectsSuppressor != null
                            ? context.getString(
                                    R.string.roamingText3,
                                    SuppressorHelper.getSuppressorCaption(
                                            context, effectsSuppressor))
                            : null;
            SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
            if (!Objects.equals(string, secVolumeSeekBarPreference.mSuppressionText)) {
                secVolumeSeekBarPreference.mSuppressionText = string;
                secVolumeSeekBarPreference.updateSuppressionText();
            }
        }
        updatePreferenceIcon(
                ((AudioHelper) ((SecVolumeSeekBarPreferenceController) this).mHelper)
                        .mAudioManager.getStreamVolume(getAudioStream()));
    }

    private void updatePreferenceIcon() {
        SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
        if (secVolumeSeekBarPreference != null) {
            int i = this.mRingerMode;
            if (i == 1) {
                this.mMuteIcon = com.android.settings.R.drawable.ic_volume_ringer_vibrate;
                secVolumeSeekBarPreference.showIcon(
                        com.android.settings.R.drawable.ic_volume_ringer_vibrate);
            } else if (i != 0) {
                secVolumeSeekBarPreference.showIcon(
                        com.android.settings.R.drawable.ic_notifications);
            } else {
                this.mMuteIcon = com.android.settings.R.drawable.ic_notifications_off_24dp;
                secVolumeSeekBarPreference.showIcon(
                        com.android.settings.R.drawable.ic_notifications_off_24dp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRingerMode() {
        this.mRingerMode =
                ((AudioHelper) ((SecVolumeSeekBarPreferenceController) this).mHelper)
                        .mAudioManager.getRingerModeInternal();
        SecAudioHelper secAudioHelper = ((SecVolumeSeekBarPreferenceController) this).mHelper;
        updatePreferenceIcon(
                ((AudioHelper) secAudioHelper).mAudioManager.getStreamVolume(getAudioStream()));
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getAudioStream() {
        return 2;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mAudioManager.shouldShowRingtoneVolume() ? 0 : 3;
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
        return com.android.settings.R.drawable.tw_ic_audio_mute_mtrl;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_RING_VOLUME;
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
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), KEY_RING_VOLUME);
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
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        super.onPause();
        this.mReceiver.register(false);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        super.onResume();
        this.mReceiver.register(true);
        updateEffectsSuppressor();
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

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean useDynamicSliceSummary() {
        return true;
    }

    public boolean wasRingerModeVibrate() {
        return this.mVibrator != null && this.mRingerMode == 1;
    }

    public SecRingVolumePreferenceController(Context context, String str) {
        super(context, str);
        this.mRingerMode = -1;
        this.mAudioManager =
                (AudioManager) this.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        Vibrator defaultVibrator =
                ((VibratorManager) this.mContext.getSystemService("vibrator_manager"))
                        .getDefaultVibrator();
        this.mVibrator = defaultVibrator;
        if (defaultVibrator != null && !defaultVibrator.hasVibrator()) {
            this.mVibrator = null;
        }
        updateRingerMode();
        this.mReceiver = new RingReceiver(this);
        this.mHandler = new H(this);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public void updatePreferenceIcon(int i) {
        SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
        if (secVolumeSeekBarPreference != null) {
            int i2 = this.mRingerMode;
            if (i2 == 1) {
                this.mMuteIcon = com.android.settings.R.drawable.tw_ic_audio_vibrate_mtrl;
                secVolumeSeekBarPreference.showIcon(
                        com.android.settings.R.drawable.tw_ic_audio_vibrate_mtrl);
            } else if (i2 == 0) {
                this.mMuteIcon = com.android.settings.R.drawable.tw_ic_audio_mute_mtrl;
                secVolumeSeekBarPreference.showIcon(
                        com.android.settings.R.drawable.tw_ic_audio_mute_mtrl);
            } else {
                this.mMuteIcon = com.android.settings.R.drawable.tw_ic_audio_sound_ringtone;
                secVolumeSeekBarPreference.showIcon(
                        com.android.settings.R.drawable.tw_ic_audio_sound_ringtone);
            }
        }
    }
}
