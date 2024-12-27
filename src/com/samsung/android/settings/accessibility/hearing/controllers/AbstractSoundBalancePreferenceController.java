package com.samsung.android.settings.accessibility.hearing.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.core.SliderPreferenceController;

import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.widget.A11ySeekBarPreference;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.presence.ServiceTuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AbstractSoundBalancePreferenceController extends SliderPreferenceController
        implements LifecycleObserver, DefaultLifecycleObserver, AccessibilityObservableController {
    public static final int CENTER_POSITION = 100;
    static final String DB_RUN_AMPLIFY_AMBIENT_SOUND = "run_amplify_ambient_sound";
    public static final int MAX_AUDIO_BALANCE = 200;
    public static final float SNAP_THRESHOLD = 6.0f;
    private static final String TAG = "BalancePrefController";
    private boolean mTrackingTouch;
    private final BroadcastReceiver mVolumeChangeReceiver;
    private Preference preference;
    private SoundBalancePreviewPlayer soundBalancePreviewPlayer;

    public AbstractSoundBalancePreferenceController(Context context, String str) {
        super(context, str);
        this.mVolumeChangeReceiver =
                new BroadcastReceiver() { // from class:
                    // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (intent == null || intent.getAction() == null) {
                            return;
                        }
                        String action = intent.getAction();
                        Log.d(AbstractSoundBalancePreferenceController.TAG, action);
                        if ((action.equals("android.media.AUDIO_BECOMING_NOISY")
                                        || (action.equals(
                                                        "android.media.STREAM_DEVICES_CHANGED_ACTION")
                                                && intent.getIntExtra(
                                                                "android.media.EXTRA_VOLUME_STREAM_TYPE",
                                                                -1)
                                                        == 3))
                                && AbstractSoundBalancePreferenceController.this
                                                .soundBalancePreviewPlayer
                                        != null) {
                            AbstractSoundBalancePreferenceController.this.soundBalancePreviewPlayer
                                    .pauseMediaPlayer();
                        }
                    }
                };
        this.mTrackingTouch = false;
    }

    private CharSequence getBalanceStateDescription(int i) {
        return AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                this.mContext.getString(
                        R.string.sound_balance_seekbar_description_left,
                        Integer.valueOf((getMax() - getMin()) - i)),
                ", ",
                this.mContext.getString(
                        R.string.sound_balance_seekbar_description_right, Integer.valueOf(i)));
    }

    private boolean isBalanceAdjusted() {
        return getBalanceDbValue(getDbKey()) != 0.0f;
    }

    private boolean isDisabledStatus() {
        boolean z =
                Settings.System.getInt(this.mContext.getContentResolver(), "all_sound_off", 0) != 0;
        boolean z2 =
                Settings.System.getInt(
                                this.mContext.getContentResolver(), DB_RUN_AMPLIFY_AMBIENT_SOUND, 0)
                        != 0;
        Log.i(TAG, "isDisabledStatus  muteAllSound : " + z + "  amplifyAmbientSound : " + z2);
        boolean z3 = z || z2;
        SoundBalancePreviewPlayer soundBalancePreviewPlayer = this.soundBalancePreviewPlayer;
        if (soundBalancePreviewPlayer != null && z3) {
            soundBalancePreviewPlayer.pauseMediaPlayer();
        }
        return z3;
    }

    private void registerVolumeFilter() {
        this.mContext.registerReceiver(
                this.mVolumeChangeReceiver,
                ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                        "android.media.AUDIO_BECOMING_NOISY",
                        "android.media.STREAM_DEVICES_CHANGED_ACTION"));
    }

    private void unregisterVolumeFilter() {
        this.mContext.unregisterReceiver(this.mVolumeChangeReceiver);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.soundBalancePreviewPlayer = new SoundBalancePreviewPlayer(this.mContext);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.preference = findPreference;
        if (findPreference instanceof A11ySeekBarPreference) {
            A11ySeekBarPreference a11ySeekBarPreference = (A11ySeekBarPreference) findPreference;
            a11ySeekBarPreference.setMax(getMax());
            a11ySeekBarPreference.setMin(getMin());
            a11ySeekBarPreference.mOnSeekBarPreferenceChangeListener = this;
            a11ySeekBarPreference.setStateDescription(
                    getBalanceStateDescription(getSliderPosition()));
            CharSequence text =
                    this.mContext.getText(R.string.accessibility_toggle_primary_balance_left_label);
            if (!TextUtils.equals(text, a11ySeekBarPreference.leftLabel)) {
                a11ySeekBarPreference.leftLabel = text;
                a11ySeekBarPreference.notifyChanged();
            }
            CharSequence text2 =
                    this.mContext.getText(
                            R.string.accessibility_toggle_primary_balance_right_label);
            if (TextUtils.equals(text2, a11ySeekBarPreference.rightLabel)) {
                return;
            }
            a11ySeekBarPreference.rightLabel = text2;
            a11ySeekBarPreference.notifyChanged();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (AccessibilityRune.isSupportAudioFeature("soundbalance")) {
            return isDisabledStatus() ? 5 : 0;
        }
        return 3;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    public float getBalanceDbValue(String str) {
        return Settings.System.getFloat(this.mContext.getContentResolver(), str, 0.0f);
    }

    public List<AudioDeviceInfo> getConnectedDevices() {
        AudioManager audioManager =
                (AudioManager) this.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        if (audioManager == null) {
            return Collections.emptyList();
        }
        AudioDeviceInfo[] devices = audioManager.getDevices(2);
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder("isDeviceConnected()");
        for (AudioDeviceInfo audioDeviceInfo : devices) {
            int type = audioDeviceInfo.getType();
            if (type != 1 && type != 18) {
                arrayList.add(audioDeviceInfo);
                sb.append("  name : ");
                sb.append(audioDeviceInfo.getProductName());
                sb.append("  type : ");
                sb.append(audioDeviceInfo.getType());
                sb.append(",");
            }
        }
        Log.i(TAG, sb.toString());
        return arrayList;
    }

    public abstract String getDbKey();

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMax() {
        return 200;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMin() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getSliderPosition() {
        float balanceDbValue = getBalanceDbValue(getDbKey());
        Log.d(TAG, "getSliderPosition() defaultValue : " + balanceDbValue + " DONE");
        return ((int) (balanceDbValue * 100.0f)) + 100;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(
                Settings.System.getUriFor(getDbKey()),
                Settings.System.getUriFor(DB_RUN_AMPLIFY_AMBIENT_SOUND),
                Settings.Global.getUriFor("all_sound_off"));
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public abstract boolean isCorrectDeviceConnected();

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onPause(LifecycleOwner lifecycleOwner) {
        unregisterVolumeFilter();
        SoundBalancePreviewPlayer soundBalancePreviewPlayer = this.soundBalancePreviewPlayer;
        Log.d(
                "SoundBalancePreviewPlayer",
                "releaseMediaPlayer() mMediaPlayer : " + soundBalancePreviewPlayer.mediaPlayer);
        MediaPlayer mediaPlayer = soundBalancePreviewPlayer.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.release();
            soundBalancePreviewPlayer.mediaPlayer = null;
            soundBalancePreviewPlayer.isPrepared = false;
        }
        soundBalancePreviewPlayer.audioManager.abandonAudioFocusRequest(
                soundBalancePreviewPlayer.audioFocusRequest);
    }

    public void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
        if (this.mTrackingTouch) {
            return;
        }
        SecAccessibilityUtils.applySoundBalanceCenterPositionThreshold(seslSeekBar);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onResume(LifecycleOwner lifecycleOwner) {
        registerVolumeFilter();
        SoundBalancePreviewPlayer soundBalancePreviewPlayer = this.soundBalancePreviewPlayer;
        if (soundBalancePreviewPlayer.mediaPlayer == null) {
            soundBalancePreviewPlayer.mediaPlayer = new MediaPlayer();
            AudioAttributes.Builder builder = new AudioAttributes.Builder();
            builder.setHapticChannelsMuted(true);
            builder.setContentType(2);
            soundBalancePreviewPlayer.mediaPlayer.setAudioAttributes(builder.build());
        }
        try {
            soundBalancePreviewPlayer.mediaPlayer.setOnPreparedListener(soundBalancePreviewPlayer);
            Uri actualDefaultRingtoneUri =
                    RingtoneManager.getActualDefaultRingtoneUri(
                            soundBalancePreviewPlayer.context, 1);
            if (actualDefaultRingtoneUri != null) {
                soundBalancePreviewPlayer.mediaPlayer.setDataSource(
                        soundBalancePreviewPlayer.context, actualDefaultRingtoneUri);
            } else {
                soundBalancePreviewPlayer.mediaPlayer.setDataSource(
                        soundBalancePreviewPlayer.context, Settings.System.DEFAULT_RINGTONE_URI);
            }
            soundBalancePreviewPlayer.mediaPlayer.prepare();
        } catch (IOException
                | IllegalArgumentException
                | IllegalStateException
                | SecurityException e) {
            Log.e("SoundBalancePreviewPlayer", "Exception thrown during preparing sound.", e);
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onStart(LifecycleOwner lifecycleOwner) {
        super.onStart(lifecycleOwner);
    }

    public void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
        this.mTrackingTouch = true;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onStop(LifecycleOwner lifecycleOwner) {
        super.onStop(lifecycleOwner);
    }

    public void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
        this.mTrackingTouch = false;
        SecAccessibilityUtils.applySoundBalanceCenterPositionThreshold(seslSeekBar);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        MediaPlayer mediaPlayer;
        Preference preference = this.preference;
        if (preference instanceof A11ySeekBarPreference) {
            ((A11ySeekBarPreference) preference).setStateDescription(getBalanceStateDescription(i));
        }
        Settings.System.putFloat(
                this.mContext.getContentResolver(), getDbKey(), (i - 100) / 100.0f);
        if (this.soundBalancePreviewPlayer == null || !isCorrectDeviceConnected()) {
            return true;
        }
        SoundBalancePreviewPlayer soundBalancePreviewPlayer = this.soundBalancePreviewPlayer;
        if (soundBalancePreviewPlayer.audioManager.isMusicActive()
                || (mediaPlayer = soundBalancePreviewPlayer.mediaPlayer) == null
                || !soundBalancePreviewPlayer.isPrepared
                || mediaPlayer.isPlaying()) {
            return true;
        }
        Log.d(
                "SoundBalancePreviewPlayer",
                "startMediaPlayer() mMediaPlayer : " + soundBalancePreviewPlayer.mediaPlayer);
        if (soundBalancePreviewPlayer.audioManager.requestAudioFocus(
                        soundBalancePreviewPlayer.audioFocusRequest)
                == 0) {
            Log.d("SoundBalancePreviewPlayer", "startMediaPlayer() requestAudioFocus : fail");
            return true;
        }
        Log.d("SoundBalancePreviewPlayer", "startMediaPlayer() requestAudioFocus : success");
        soundBalancePreviewPlayer.mediaPlayer.start();
        return true;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        int availabilityStatus = getAvailabilityStatus();
        if (availabilityStatus == 3) {
            preference.setVisible(false);
            return;
        }
        preference.setVisible(true);
        preference.setEnabled(availabilityStatus != 5);
        super.updateState(preference);
        if (preference instanceof A11ySeekBarPreference) {
            A11ySeekBarPreference a11ySeekBarPreference = (A11ySeekBarPreference) preference;
            if (isBalanceAdjusted()) {
                ColorStateList colorStateList =
                        this.mContext.getColorStateList(
                                R.color.sound_balance_seek_bar_not_balanced_bg_color);
                if (a11ySeekBarPreference.progressTintList != colorStateList) {
                    a11ySeekBarPreference.progressTintList = colorStateList;
                    a11ySeekBarPreference.notifyChanged();
                }
                ColorStateList colorStateList2 =
                        this.mContext.getColorStateList(
                                R.color.sound_balance_seek_bar_tick_mark_color);
                if (a11ySeekBarPreference.tickMarkTintList != colorStateList2) {
                    a11ySeekBarPreference.tickMarkTintList = colorStateList2;
                    a11ySeekBarPreference.notifyChanged();
                    return;
                }
                return;
            }
            ColorStateList colorStateList3 =
                    this.mContext.getColorStateList(R.color.a11y_slider_progress_normal_color);
            if (a11ySeekBarPreference.progressTintList != colorStateList3) {
                a11ySeekBarPreference.progressTintList = colorStateList3;
                a11ySeekBarPreference.notifyChanged();
            }
            ColorStateList colorStateList4 =
                    this.mContext.getColorStateList(R.color.sesl_tick_mark_seekbar_level);
            if (a11ySeekBarPreference.tickMarkTintList != colorStateList4) {
                a11ySeekBarPreference.tickMarkTintList = colorStateList4;
                a11ySeekBarPreference.notifyChanged();
            }
        }
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
