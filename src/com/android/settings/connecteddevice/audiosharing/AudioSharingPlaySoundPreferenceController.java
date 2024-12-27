package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingPlaySoundPreferenceController
        extends AudioSharingBasePreferenceController {
    private static final String PREF_KEY = "audio_sharing_play_sound";
    private static final String TAG = "AudioSharingPlaySoundPreferenceController";
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private Ringtone mRingtone;

    public AudioSharingPlaySoundPreferenceController(Context context) {
        super(context, PREF_KEY);
        Ringtone ringtone = RingtoneManager.getRingtone(context, getMediaVolumeUri());
        this.mRingtone = ringtone;
        if (ringtone != null) {
            ringtone.setStreamType(3);
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    private Uri getMediaVolumeUri() {
        return Uri.parse("android.resource://" + this.mContext.getPackageName() + "/2131951686");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$displayPreference$0(Preference preference) {
        Ringtone ringtone = this.mRingtone;
        if (ringtone == null) {
            Log.d(TAG, "Skip onClick due to ringtone is null");
            return true;
        }
        try {
            ringtone.setAudioAttributes(
                    new AudioAttributes.Builder(this.mRingtone.getAudioAttributes())
                            .setFlags(128)
                            .addTag("VX_AOSP_SAMPLESOUND")
                            .build());
            if (!this.mRingtone.isPlaying()) {
                this.mRingtone.play();
                this.mMetricsFeatureProvider.action(this.mContext, 1928, new Pair[0]);
            }
        } catch (Throwable th) {
            Log.w(TAG, "Fail to play sample, error = " + th);
        }
        return true;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.connecteddevice.audiosharing.AudioSharingPlaySoundPreferenceController$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference2) {
                            boolean lambda$displayPreference$0;
                            lambda$displayPreference$0 =
                                    AudioSharingPlaySoundPreferenceController.this
                                            .lambda$displayPreference$0(preference2);
                            return lambda$displayPreference$0;
                        }
                    });
        }
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mRingtone == null) {
            return 3;
        }
        BluetoothAdapter.getDefaultAdapter();
        return 3;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return PREF_KEY;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        super.onStop(lifecycleOwner);
        Ringtone ringtone = this.mRingtone;
        if (ringtone == null || !ringtone.isPlaying()) {
            return;
        }
        this.mRingtone.stop();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setRingtone(Ringtone ringtone) {
        this.mRingtone = ringtone;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
