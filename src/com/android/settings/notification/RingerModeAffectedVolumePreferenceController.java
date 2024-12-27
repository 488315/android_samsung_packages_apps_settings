package com.android.settings.notification;

import android.app.INotificationManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Vibrator;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RingerModeAffectedVolumePreferenceController
        extends VolumeSeekBarPreferenceController {
    protected int mMuteIcon;
    protected INotificationManager mNoMan;
    protected int mNormalIconId;
    protected int mRingerMode;
    protected int mSilentIconId;
    protected ComponentName mSuppressor;
    private final String mTag;
    protected int mVibrateIconId;
    protected Vibrator mVibrator;

    public RingerModeAffectedVolumePreferenceController(Context context, String str, String str2) {
        super(context, str);
        this.mRingerMode = 2;
        this.mTag = str2;
        Vibrator vibrator = (Vibrator) this.mContext.getSystemService(Vibrator.class);
        this.mVibrator = vibrator;
        if (vibrator != null && !vibrator.hasVibrator()) {
            this.mVibrator = null;
        }
        this.mVolumePreferenceListener =
                new VolumeSeekBarPreference
                        .Listener() { // from class:
                                      // com.android.settings.notification.RingerModeAffectedVolumePreferenceController$$ExternalSyntheticLambda1
                    @Override // com.android.settings.notification.VolumeSeekBarPreference.Listener
                    public final void onUpdateMuteState() {
                        RingerModeAffectedVolumePreferenceController.this
                                .updateContentDescription();
                    }
                };
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    public int getEffectiveRingerMode() {
        if (this.mVibrator == null && this.mRingerMode == 1) {
            return 0;
        }
        return this.mRingerMode;
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController
    public int getMuteIcon() {
        return this.mMuteIcon;
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public abstract boolean hintsMatch(int i);

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return true;
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void selectPreferenceIconState() {
        if (this.mPreference != null) {
            int effectiveRingerMode = getEffectiveRingerMode();
            if (effectiveRingerMode == 2) {
                VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
                int i = this.mNormalIconId;
                if (volumeSeekBarPreference.mIconResId == i) {
                    return;
                }
                volumeSeekBarPreference.mIconResId = i;
                volumeSeekBarPreference.updateIconView();
                return;
            }
            if (effectiveRingerMode == 1) {
                this.mMuteIcon = this.mVibrateIconId;
            } else {
                this.mMuteIcon = this.mSilentIconId;
            }
            VolumeSeekBarPreference volumeSeekBarPreference2 = this.mPreference;
            int muteIcon = getMuteIcon();
            if (volumeSeekBarPreference2.mIconResId == muteIcon) {
                return;
            }
            volumeSeekBarPreference2.mIconResId = muteIcon;
            volumeSeekBarPreference2.updateIconView();
        }
    }

    @VisibleForTesting
    public void setPreference(VolumeSeekBarPreference volumeSeekBarPreference) {
        this.mPreference = volumeSeekBarPreference;
    }

    @VisibleForTesting
    public void setVibrator(Vibrator vibrator) {
        this.mVibrator = vibrator;
    }

    public void updateContentDescription() {
        if (this.mPreference != null) {
            int effectiveRingerMode = getEffectiveRingerMode();
            if (effectiveRingerMode == 1) {
                this.mPreference.updateContentDescription(
                        this.mContext.getString(R.string.ringer_content_description_vibrate_mode));
            } else if (effectiveRingerMode == 0) {
                this.mPreference.updateContentDescription(
                        this.mContext.getString(R.string.ringer_content_description_silent_mode));
            } else {
                VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
                volumeSeekBarPreference.updateContentDescription(
                        volumeSeekBarPreference.getTitle());
            }
        }
    }

    public void updateEffectsSuppressor() {
        ComponentName effectsSuppressor =
                NotificationManager.from(this.mContext).getEffectsSuppressor();
        if (Objects.equals(effectsSuppressor, this.mSuppressor)) {
            return;
        }
        if (this.mNoMan == null) {
            this.mNoMan =
                    INotificationManager.Stub.asInterface(
                            ServiceManager.getService("notification"));
        }
        try {
            if (hintsMatch(this.mNoMan.getHintsFromListenerNoToken())) {
                this.mSuppressor = effectsSuppressor;
                if (this.mPreference != null) {
                    Context context = this.mContext;
                    String string =
                            effectsSuppressor != null
                                    ? context.getString(
                                            android.R.string.roamingText3,
                                            SuppressorHelper.getSuppressorCaption(
                                                    context, effectsSuppressor))
                                    : null;
                    VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
                    if (Objects.equals(string, volumeSeekBarPreference.mSuppressionText)) {
                        return;
                    }
                    volumeSeekBarPreference.mSuppressionText = string;
                    volumeSeekBarPreference.updateSuppressionText();
                }
            }
        } catch (RemoteException e) {
            RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("updateEffectsSuppressor: "), this.mTag);
        }
    }

    public boolean updateRingerMode() {
        int ringerModeInternal =
                ((VolumeSeekBarPreferenceController) this)
                        .mHelper.mAudioManager.getRingerModeInternal();
        if (this.mRingerMode == ringerModeInternal) {
            return false;
        }
        this.mRingerMode = ringerModeInternal;
        selectPreferenceIconState();
        updateContentDescription();
        return true;
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean useDynamicSliceSummary() {
        return true;
    }
}
