package com.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.preference.SeekBarVolumizer;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class VolumeSeekBarPreferenceController
        extends AdjustVolumeRestrictedPreferenceController implements LifecycleObserver {
    protected AudioHelper mHelper;
    protected VolumeSeekBarPreference mPreference;
    protected VolumeSeekBarPreference.Callback mVolumePreferenceCallback;
    protected VolumeSeekBarPreference.Listener mVolumePreferenceListener;

    public VolumeSeekBarPreferenceController(Context context, String str) {
        super(context, str);
        setAudioHelper(new AudioHelper(context));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (isAvailable()) {
            setupVolPreference(preferenceScreen);
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
        VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
        if (volumeSeekBarPreference != null) {
            return volumeSeekBarPreference.mMax;
        }
        AudioHelper audioHelper = this.mHelper;
        return audioHelper.mAudioManager.getStreamMaxVolume(getAudioStream());
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMin() {
        VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
        if (volumeSeekBarPreference != null) {
            return volumeSeekBarPreference.mMin;
        }
        AudioHelper audioHelper = this.mHelper;
        int audioStream = getAudioStream();
        audioHelper.getClass();
        try {
            return audioHelper.mAudioManager.getStreamMinVolume(audioStream);
        } catch (IllegalArgumentException unused) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    audioStream, "Invalid stream type ", "AudioHelper");
            return audioHelper.mAudioManager.getStreamMinVolume(0);
        }
    }

    public abstract int getMuteIcon();

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_sound;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getSliderPosition() {
        VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
        if (volumeSeekBarPreference != null) {
            return volumeSeekBarPreference.mProgress;
        }
        AudioHelper audioHelper = this.mHelper;
        return audioHelper.mAudioManager.getStreamVolume(getAudioStream());
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
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
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
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
        VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
        if (volumeSeekBarPreference != null) {
            volumeSeekBarPreference.mStopped = true;
            SeekBarVolumizer seekBarVolumizer = volumeSeekBarPreference.mVolumizer;
            if (seekBarVolumizer != null) {
                seekBarVolumizer.stop();
                volumeSeekBarPreference.mVolumizer = null;
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
        if (volumeSeekBarPreference == null || !volumeSeekBarPreference.mStopped) {
            return;
        }
        volumeSeekBarPreference.init();
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setAudioHelper(AudioHelper audioHelper) {
        this.mHelper = audioHelper;
    }

    public void setCallback(VolumeSeekBarPreference.Callback callback) {
        this.mVolumePreferenceCallback = callback;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
        if (volumeSeekBarPreference != null) {
            volumeSeekBarPreference.setProgress(i, true);
        }
        AudioHelper audioHelper = this.mHelper;
        audioHelper.mAudioManager.setStreamVolume(getAudioStream(), i, 0);
        return true;
    }

    public void setupVolPreference(PreferenceScreen preferenceScreen) {
        VolumeSeekBarPreference volumeSeekBarPreference =
                (VolumeSeekBarPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = volumeSeekBarPreference;
        volumeSeekBarPreference.mCallback = this.mVolumePreferenceCallback;
        volumeSeekBarPreference.mListener = this.mVolumePreferenceListener;
        volumeSeekBarPreference.setStream(getAudioStream());
        VolumeSeekBarPreference volumeSeekBarPreference2 = this.mPreference;
        int muteIcon = getMuteIcon();
        if (volumeSeekBarPreference2.mMuteIconResId == muteIcon) {
            return;
        }
        volumeSeekBarPreference2.mMuteIconResId = muteIcon;
        volumeSeekBarPreference2.updateIconView();
    }

    @Override // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
