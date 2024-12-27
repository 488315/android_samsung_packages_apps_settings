package com.samsung.android.settings.asbase.audio;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.notification.AdjustVolumeRestrictedPreferenceController;
import com.android.settings.notification.AudioHelper;

import com.samsung.android.settings.asbase.utils.SecAudioHelper;
import com.samsung.android.settings.asbase.widget.SecVolumeIcon;
import com.samsung.android.settings.asbase.widget.SecVolumeIconMotion;
import com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference;
import com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference$$ExternalSyntheticLambda0;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecVolumeSeekBarPreferenceController
        extends AdjustVolumeRestrictedPreferenceController implements LifecycleObserver {
    protected SecAudioHelper mHelper;
    protected SecVolumeSeekBarPreference mPreference;
    protected SecVolumeSeekBarPreference.Callback mVolumePreferenceCallback;

    public SecVolumeSeekBarPreferenceController(Context context, String str) {
        super(context, str);
        setAudioHelper(new SecAudioHelper(context));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (isAvailable()) {
            SecVolumeSeekBarPreference secVolumeSeekBarPreference =
                    (SecVolumeSeekBarPreference)
                            preferenceScreen.findPreference(getPreferenceKey());
            this.mPreference = secVolumeSeekBarPreference;
            int audioStream = getAudioStream();
            secVolumeSeekBarPreference.mStream = audioStream;
            secVolumeSeekBarPreference.setMax(
                    secVolumeSeekBarPreference.mAudioManager.getStreamMaxVolume(audioStream));
            secVolumeSeekBarPreference.setMin(
                    secVolumeSeekBarPreference.mAudioManager.getStreamMinVolumeInt(
                            secVolumeSeekBarPreference.mStream));
            SecVolumeSeekBarPreference secVolumeSeekBarPreference2 = this.mPreference;
            int muteIcon = getMuteIcon();
            if (secVolumeSeekBarPreference2.mMuteIconResId == muteIcon) {
                return;
            }
            secVolumeSeekBarPreference2.mMuteIconResId = muteIcon;
            SecVolumeIcon secVolumeIcon = secVolumeSeekBarPreference2.mVolumeIcon;
            if (secVolumeIcon != null) {
                secVolumeIcon.updateLayout(false);
            }
        }
    }

    public abstract int getAudioStream();

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMax() {
        SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
        if (secVolumeSeekBarPreference != null) {
            return secVolumeSeekBarPreference.mMax;
        }
        SecAudioHelper secAudioHelper = this.mHelper;
        return ((AudioHelper) secAudioHelper).mAudioManager.getStreamMaxVolume(getAudioStream());
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMin() {
        SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
        if (secVolumeSeekBarPreference != null) {
            return secVolumeSeekBarPreference.mMin;
        }
        SecAudioHelper secAudioHelper = this.mHelper;
        int audioStream = getAudioStream();
        secAudioHelper.getClass();
        try {
            return ((AudioHelper) secAudioHelper).mAudioManager.getStreamMinVolumeInt(audioStream);
        } catch (IllegalArgumentException unused) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    audioStream, "Invalid stream type ", "AudioHelper");
            return ((AudioHelper) secAudioHelper)
                    .mAudioManager.getStreamMinVolumeInt(Integer.MIN_VALUE);
        }
    }

    public int getMuteIcon() {
        return R.drawable.tw_ic_audio_mute_mtrl;
    }

    public int getNormalIcon() {
        return R.drawable.tw_ic_audio_sound_ringtone;
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_sound;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getSliderPosition() {
        SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
        if (secVolumeSeekBarPreference != null) {
            return secVolumeSeekBarPreference.mProgress;
        }
        SecAudioHelper secAudioHelper = this.mHelper;
        return ((AudioHelper) secAudioHelper).mAudioManager.getStreamVolume(getAudioStream());
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    public int getVibrateIcon() {
        return R.drawable.tw_ic_audio_vibrate_mtrl;
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
        if (secVolumeSeekBarPreference != null) {
            secVolumeSeekBarPreference.mCallback = null;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
        if (secVolumeSeekBarPreference != null) {
            secVolumeSeekBarPreference.mCallback = this.mVolumePreferenceCallback;
            if (secVolumeSeekBarPreference.mStopped) {
                SecVolumeIconMotion secVolumeIconMotion =
                        new SecVolumeIconMotion(secVolumeSeekBarPreference.getContext());
                SecVolumeIcon secVolumeIcon = secVolumeSeekBarPreference.mVolumeIcon;
                if (secVolumeIcon != null && secVolumeSeekBarPreference.mSeekBar != null) {
                    secVolumeIcon.initialize(
                            secVolumeIconMotion, secVolumeSeekBarPreference.mStream);
                }
                secVolumeSeekBarPreference.setupCustomInit();
            }
            secVolumeSeekBarPreference.mFirstBind = true;
            new Handler()
                    .postDelayed(
                            new SecVolumeSeekBarPreference$$ExternalSyntheticLambda0(
                                    secVolumeSeekBarPreference),
                            50L);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
        if (secVolumeSeekBarPreference != null) {
            secVolumeSeekBarPreference.mStopped = true;
            secVolumeSeekBarPreference.mAudioManager.abandonAudioFocusRequest(
                    secVolumeSeekBarPreference.mFocusRequest);
            SecSeekBarVolumizerDTMF secSeekBarVolumizerDTMF =
                    secVolumeSeekBarPreference.mVolumizerDTMF;
            if (secSeekBarVolumizerDTMF != null) {
                secSeekBarVolumizerDTMF.stop();
                secVolumeSeekBarPreference.mVolumizerDTMF = null;
            }
            SecSeekBarVolumizer secSeekBarVolumizer = secVolumeSeekBarPreference.mVolumizer;
            if (secSeekBarVolumizer != null) {
                secSeekBarVolumizer.stop();
                secVolumeSeekBarPreference.mVolumizer = null;
            }
        }
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setAudioHelper(SecAudioHelper secAudioHelper) {
        this.mHelper = secAudioHelper;
    }

    public void setCallback(SecVolumeSeekBarPreference.Callback callback) {
        this.mVolumePreferenceCallback = callback;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
        if (secVolumeSeekBarPreference != null) {
            secVolumeSeekBarPreference.setProgress(i, true);
        }
        SecAudioHelper secAudioHelper = this.mHelper;
        ((AudioHelper) secAudioHelper).mAudioManager.setStreamVolume(getAudioStream(), i, 0);
        return true;
    }

    public abstract void updatePreferenceIcon(int i);

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
