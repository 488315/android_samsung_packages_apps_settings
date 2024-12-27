package com.android.settings.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioSystem;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationVolumePreferenceController
        extends RingerModeAffectedVolumePreferenceController {
    private static final String KEY_NOTIFICATION_VOLUME = "notification_volume";
    private static final String TAG = "NotificationVolumePreferenceController";
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
            NotificationVolumePreferenceController notificationVolumePreferenceController =
                    NotificationVolumePreferenceController.this;
            if (i == 1) {
                notificationVolumePreferenceController.updateEffectsSuppressor();
                return;
            }
            if (i == 2) {
                if (notificationVolumePreferenceController.updateRingerMode()) {
                    notificationVolumePreferenceController.updateEnabledState();
                }
            } else {
                if (i != 3) {
                    return;
                }
                notificationVolumePreferenceController.selectPreferenceIconState();
                notificationVolumePreferenceController.updateContentDescription();
                notificationVolumePreferenceController.updateEnabledState();
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
                NotificationVolumePreferenceController.this.mHandler.sendEmptyMessage(1);
                return;
            }
            if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(action)) {
                NotificationVolumePreferenceController.this.mHandler.sendEmptyMessage(2);
            } else if ("android.media.VOLUME_CHANGED_ACTION".equals(action)
                    && intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 5) {
                NotificationVolumePreferenceController.this
                        .mHandler
                        .obtainMessage(
                                3,
                                intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", -1),
                                0)
                        .sendToTarget();
            }
        }

        public final void register(boolean z) {
            if (this.mRegistered == z) {
                return;
            }
            if (z) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED");
                intentFilter.addAction("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");
                intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
                ((AbstractPreferenceController) NotificationVolumePreferenceController.this)
                        .mContext.registerReceiver(this, intentFilter);
            } else {
                ((AbstractPreferenceController) NotificationVolumePreferenceController.this)
                        .mContext.unregisterReceiver(this);
            }
            this.mRegistered = z;
        }
    }

    public NotificationVolumePreferenceController(Context context) {
        this(context, KEY_NOTIFICATION_VOLUME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEnabledState() {
        VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
        if (volumeSeekBarPreference != null) {
            volumeSeekBarPreference.setEnabled(this.mRingerMode == 2);
        }
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (this.mPreference == null) {
            setupVolPreference(preferenceScreen);
        }
        updateEffectsSuppressor();
        selectPreferenceIconState();
        updateContentDescription();
        updateEnabledState();
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController
    public int getAudioStream() {
        return 5;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!this.mContext.getResources().getBoolean(R.bool.config_show_notification_volume)
                || AudioSystem.isSingleVolume(
                        ((VolumeSeekBarPreferenceController) this).mHelper.mContext)) {
            return 3;
        }
        return this.mRingerMode == 2 ? 0 : 5;
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

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController
    public int getEffectiveRingerMode() {
        if (this.mVibrator == null && this.mRingerMode == 1) {
            return 0;
        }
        if (this.mRingerMode == 2
                && ((VolumeSeekBarPreferenceController) this)
                                .mHelper.mAudioManager.getStreamVolume(5)
                        == 0) {
            return 0;
        }
        return this.mRingerMode;
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
        return KEY_NOTIFICATION_VOLUME;
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
        return ((i & 1) != 0) || ((i & 2) != 0);
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
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController,
              // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.notification.RingerModeAffectedVolumePreferenceController
    public void updateContentDescription() {
        if (this.mPreference != null) {
            int effectiveRingerMode = getEffectiveRingerMode();
            if (effectiveRingerMode == 1) {
                TextView textView = this.mPreference.mTitle;
                if (textView != null) {
                    textView.setAccessibilityLiveRegion(1);
                }
                this.mPreference.updateContentDescription(
                        this.mContext.getString(
                                R.string.notification_volume_content_description_vibrate_mode));
                return;
            }
            if (effectiveRingerMode == 0) {
                TextView textView2 = this.mPreference.mTitle;
                if (textView2 != null) {
                    textView2.setAccessibilityLiveRegion(1);
                }
                VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
                volumeSeekBarPreference.updateContentDescription(
                        this.mContext.getString(
                                R.string.volume_content_description_silent_mode,
                                volumeSeekBarPreference.getTitle()));
                return;
            }
            TextView textView3 = this.mPreference.mTitle;
            if (textView3 != null) {
                textView3.setAccessibilityLiveRegion(0);
            }
            VolumeSeekBarPreference volumeSeekBarPreference2 = this.mPreference;
            volumeSeekBarPreference2.updateContentDescription(volumeSeekBarPreference2.getTitle());
        }
    }

    public NotificationVolumePreferenceController(Context context, String str) {
        super(context, str, TAG);
        this.mReceiver = new RingReceiver();
        this.mHandler = new H();
        this.mNormalIconId = R.drawable.ic_notifications;
        this.mVibrateIconId = R.drawable.ic_volume_ringer_vibrate;
        this.mSilentIconId = R.drawable.ic_notifications_off_24dp;
        if (updateRingerMode()) {
            updateEnabledState();
        }
    }
}
