package com.android.settings.connecteddevice.audiosharing;

import android.content.Context;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsCategoryController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingDashboardFragment extends DashboardFragment
        implements AudioSharingSwitchBarController.OnAudioSharingStateChangedListener {
    public AudioSharingCallAudioPreferenceController mAudioSharingCallAudioPreferenceController;
    public AudioSharingDeviceVolumeGroupController mAudioSharingDeviceVolumeGroupController;
    public AudioSharingPlaySoundPreferenceController mAudioSharingPlaySoundPreferenceController;
    public AudioStreamsCategoryController mAudioStreamsCategoryController;
    public SettingsMainSwitchBar mMainSwitchBar;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AudioSharingDashboardFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2048;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.bluetooth_le_audio_sharing;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
        this.mMainSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.setTitle(getText(R.string.audio_sharing_switch_title));
        AudioSharingSwitchBarController audioSharingSwitchBarController =
                new AudioSharingSwitchBarController(settingsActivity, this.mMainSwitchBar, this);
        audioSharingSwitchBarController.init(this);
        getSettingsLifecycle().addObserver(audioSharingSwitchBarController);
        this.mMainSwitchBar.show();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        AudioSharingDeviceVolumeGroupController audioSharingDeviceVolumeGroupController =
                (AudioSharingDeviceVolumeGroupController)
                        use(AudioSharingDeviceVolumeGroupController.class);
        this.mAudioSharingDeviceVolumeGroupController = audioSharingDeviceVolumeGroupController;
        audioSharingDeviceVolumeGroupController.init(this);
        AudioSharingCallAudioPreferenceController audioSharingCallAudioPreferenceController =
                (AudioSharingCallAudioPreferenceController)
                        use(AudioSharingCallAudioPreferenceController.class);
        this.mAudioSharingCallAudioPreferenceController = audioSharingCallAudioPreferenceController;
        audioSharingCallAudioPreferenceController.init(this);
        this.mAudioSharingPlaySoundPreferenceController =
                (AudioSharingPlaySoundPreferenceController)
                        use(AudioSharingPlaySoundPreferenceController.class);
        this.mAudioStreamsCategoryController =
                (AudioStreamsCategoryController) use(AudioStreamsCategoryController.class);
    }

    public final void onAudioSharingStateChanged() {
        this.mAudioSharingDeviceVolumeGroupController.updateVisibility();
        this.mAudioSharingCallAudioPreferenceController.updateVisibility();
        this.mAudioSharingPlaySoundPreferenceController.updateVisibility();
        this.mAudioStreamsCategoryController.updateVisibility();
    }

    public void setControllers(
            AudioSharingDeviceVolumeGroupController audioSharingDeviceVolumeGroupController,
            AudioSharingCallAudioPreferenceController audioSharingCallAudioPreferenceController,
            AudioSharingPlaySoundPreferenceController audioSharingPlaySoundPreferenceController,
            AudioStreamsCategoryController audioStreamsCategoryController) {
        this.mAudioSharingDeviceVolumeGroupController = audioSharingDeviceVolumeGroupController;
        this.mAudioSharingCallAudioPreferenceController = audioSharingCallAudioPreferenceController;
        this.mAudioSharingPlaySoundPreferenceController = audioSharingPlaySoundPreferenceController;
        this.mAudioStreamsCategoryController = audioStreamsCategoryController;
    }
}
