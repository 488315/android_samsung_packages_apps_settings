package com.android.settings.bluetooth;

import android.content.Context;
import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import android.media.Spatializer;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.collect.ImmutableSet;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsSpatialAudioController extends BluetoothDetailsController
        implements Preference.OnPreferenceClickListener {
    public static final ImmutableSet SA_PROFILES = ImmutableSet.construct(3, 2, 22, 21);
    AudioDeviceAttributes mAudioDevice;
    public final AudioManager mAudioManager;
    public final AtomicBoolean mHasHeadTracker;
    public final AtomicBoolean mInitialRefresh;
    PreferenceCategory mProfilesContainer;
    public final Spatializer mSpatializer;

    public BluetoothDetailsSpatialAudioController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            CachedBluetoothDevice cachedBluetoothDevice,
            Lifecycle lifecycle) {
        super(context, preferenceFragmentCompat, cachedBluetoothDevice, lifecycle);
        this.mAudioDevice = null;
        this.mHasHeadTracker = new AtomicBoolean(false);
        this.mInitialRefresh = new AtomicBoolean(true);
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getBluetoothFeatureProvider().getClass();
        this.mSpatializer =
                ((AudioManager) context.getSystemService(AudioManager.class)).getSpatializer();
    }

    public TwoStatePreference createHeadTrackingPreference(Context context) {
        SwitchPreferenceCompat switchPreferenceCompat = new SwitchPreferenceCompat(context);
        switchPreferenceCompat.setKey("head_tracking");
        switchPreferenceCompat.setTitle(
                context.getString(R.string.bluetooth_details_head_tracking_title));
        switchPreferenceCompat.setSummary(
                context.getString(R.string.bluetooth_details_head_tracking_summary));
        switchPreferenceCompat.setOnPreferenceClickListener(this);
        return switchPreferenceCompat;
    }

    public TwoStatePreference createSpatialAudioPreference(Context context) {
        SwitchPreferenceCompat switchPreferenceCompat = new SwitchPreferenceCompat(context);
        switchPreferenceCompat.setKey("spatial_audio");
        switchPreferenceCompat.setTitle(
                context.getString(R.string.bluetooth_details_spatial_audio_title));
        switchPreferenceCompat.setSummary(
                context.getString(R.string.bluetooth_details_spatial_audio_summary));
        switchPreferenceCompat.setOnPreferenceClickListener(this);
        return switchPreferenceCompat;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "spatial_audio_group";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        this.mProfilesContainer =
                (PreferenceCategory) preferenceScreen.findPreference("spatial_audio_group");
        refresh();
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mSpatializer.getImmersiveAudioLevel() != 0;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        String key = twoStatePreference.getKey();
        boolean equals = TextUtils.equals(key, "spatial_audio");
        SettingsMetricsFeatureProvider settingsMetricsFeatureProvider =
                this.mMetricsFeatureProvider;
        if (equals) {
            settingsMetricsFeatureProvider.action(
                    ((BluetoothDetailsController) this).mContext,
                    1912,
                    twoStatePreference.isChecked());
            boolean isChecked = twoStatePreference.isChecked();
            AudioDeviceAttributes audioDeviceAttributes = this.mAudioDevice;
            if (audioDeviceAttributes == null) {
                Log.w(
                        "BluetoothSpatialAudioController",
                        "cannot update spatializer enabled for null audio device.");
            } else if (isChecked) {
                this.mSpatializer.addCompatibleAudioDevice(audioDeviceAttributes);
            } else {
                this.mSpatializer.removeCompatibleAudioDevice(audioDeviceAttributes);
            }
            ThreadUtils.postOnBackgroundThread(
                    new BluetoothDetailsSpatialAudioController$$ExternalSyntheticLambda0(
                            this, twoStatePreference, 0));
            return true;
        }
        if (!TextUtils.equals(key, "head_tracking")) {
            Log.w("BluetoothSpatialAudioController", "invalid key name.");
            return false;
        }
        settingsMetricsFeatureProvider.action(
                ((BluetoothDetailsController) this).mContext, 1913, twoStatePreference.isChecked());
        boolean isChecked2 = twoStatePreference.isChecked();
        AudioDeviceAttributes audioDeviceAttributes2 = this.mAudioDevice;
        if (audioDeviceAttributes2 == null) {
            Log.w(
                    "BluetoothSpatialAudioController",
                    "cannot update spatializer head tracking for null audio device.");
        } else {
            this.mSpatializer.setHeadTrackerEnabled(isChecked2, audioDeviceAttributes2);
        }
        return true;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        AudioDeviceAttributes audioDeviceAttributes;
        StringBuilder sb = new StringBuilder("getAvailableDevice() mCachedDevice: ");
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        sb.append(cachedBluetoothDevice);
        sb.append(" profiles: ");
        sb.append(cachedBluetoothDevice.getProfiles());
        Log.i("BluetoothSpatialAudioController", sb.toString());
        Iterator it = cachedBluetoothDevice.getProfiles().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
            if (SA_PROFILES.contains(Integer.valueOf(localBluetoothProfile.getProfileId()))
                    && localBluetoothProfile.isEnabled(cachedBluetoothDevice.mDevice)) {
                int profileId = localBluetoothProfile.getProfileId();
                if (profileId == 2) {
                    audioDeviceAttributes =
                            new AudioDeviceAttributes(
                                    2, 8, cachedBluetoothDevice.mDevice.getAddress());
                } else if (profileId == 21) {
                    audioDeviceAttributes =
                            new AudioDeviceAttributes(
                                    2, 23, cachedBluetoothDevice.mDevice.getAddress());
                } else if (profileId != 22) {
                    Log.i(
                            "BluetoothSpatialAudioController",
                            "unrecognized profile for spatial audio: "
                                    + localBluetoothProfile.getProfileId());
                } else {
                    audioDeviceAttributes =
                            this.mAudioManager.getBluetoothAudioDeviceCategory(
                                                    cachedBluetoothDevice.mDevice.getAddress())
                                            == 2
                                    ? new AudioDeviceAttributes(
                                            2, 27, cachedBluetoothDevice.mDevice.getAddress())
                                    : new AudioDeviceAttributes(
                                            2, 26, cachedBluetoothDevice.mDevice.getAddress());
                }
            }
        }
        audioDeviceAttributes = null;
        this.mAudioDevice = null;
        if (audioDeviceAttributes != null
                && this.mSpatializer.isAvailableForDevice(audioDeviceAttributes)) {
            this.mAudioDevice = audioDeviceAttributes;
        }
        StringBuilder sb2 = new StringBuilder("getAvailableDevice() device : ");
        sb2.append(cachedBluetoothDevice.mDevice.getAnonymizedAddress());
        sb2.append(", is available : ");
        sb2.append(this.mAudioDevice != null);
        sb2.append(", type : ");
        AudioDeviceAttributes audioDeviceAttributes2 = this.mAudioDevice;
        sb2.append(
                audioDeviceAttributes2 == null
                        ? "no type"
                        : Integer.valueOf(audioDeviceAttributes2.getType()));
        Log.d("BluetoothSpatialAudioController", sb2.toString());
        ThreadUtils.postOnBackgroundThread(
                new BluetoothDetailsSpatialAudioController$$ExternalSyntheticLambda1(this, 0));
    }

    public final void refreshSpatialAudioEnabled(TwoStatePreference twoStatePreference) {
        boolean contains =
                this.mSpatializer.getCompatibleAudioDevices().contains(this.mAudioDevice);
        Log.d("BluetoothSpatialAudioController", "refresh() isSpatialAudioOn : " + contains);
        twoStatePreference.setChecked(contains);
        TwoStatePreference twoStatePreference2 =
                (TwoStatePreference) this.mProfilesContainer.findPreference("head_tracking");
        if (twoStatePreference2 == null) {
            twoStatePreference2 =
                    createHeadTrackingPreference(this.mProfilesContainer.getContext());
            this.mProfilesContainer.addPreference(twoStatePreference2);
        }
        boolean z = twoStatePreference.isChecked() && this.mHasHeadTracker.get();
        Log.d(
                "BluetoothSpatialAudioController",
                "refresh() has head tracker : " + this.mHasHeadTracker.get());
        twoStatePreference2.setVisible(z);
        if (z) {
            twoStatePreference2.setChecked(
                    this.mSpatializer.isHeadTrackerEnabled(this.mAudioDevice));
        }
        if (this.mInitialRefresh.compareAndSet(true, false)) {
            Context context = ((BluetoothDetailsController) this).mContext;
            boolean isChecked = twoStatePreference.isChecked();
            SettingsMetricsFeatureProvider settingsMetricsFeatureProvider =
                    this.mMetricsFeatureProvider;
            settingsMetricsFeatureProvider.action(context, 1910, isChecked);
            if (this.mHasHeadTracker.get()) {
                settingsMetricsFeatureProvider.action(
                        ((BluetoothDetailsController) this).mContext,
                        1911,
                        twoStatePreference2.isChecked());
            }
        }
    }

    public void setAvailableDevice(AudioDeviceAttributes audioDeviceAttributes) {
        this.mAudioDevice = audioDeviceAttributes;
    }
}
