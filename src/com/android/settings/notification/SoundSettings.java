package com.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.preference.SeekBarVolumizer;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.RingtonePreference;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.sound.AudioSwitchPreferenceController;
import com.android.settings.sound.HandsFreeProfileOutputPreferenceController;
import com.android.settings.widget.PreferenceCategoryController;
import com.android.settings.widget.UpdatableListPreferenceDialogFragment;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SoundSettings extends DashboardFragment implements OnActivityResultListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.sound_settings);
    static final int STOP_SAMPLE = 1;
    public UpdatableListPreferenceDialogFragment mDialogFragment;
    public String mHfpOutputControllerKey;
    public RingtonePreference mRequestPreference;
    final VolumePreferenceCallback mVolumeCallback = new VolumePreferenceCallback();
    final Handler mHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.android.settings.notification.SoundSettings.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    SeekBarVolumizer seekBarVolumizer;
                    if (message.what == 1
                            && (seekBarVolumizer = SoundSettings.this.mVolumeCallback.mCurrent)
                                    != null) {
                        seekBarVolumizer.stopSample();
                    }
                }
            };
    public final String mVibrationPreferencesKey = "vibration_preference_screen";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.SoundSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return SoundSettings.buildPreferenceControllers(context, null, null);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VolumePreferenceCallback implements VolumeSeekBarPreference.Callback {
        public SeekBarVolumizer mCurrent;

        public VolumePreferenceCallback() {}

        @Override // com.android.settings.notification.VolumeSeekBarPreference.Callback
        public final void onSampleStarting() {
            if (this.mCurrent != null) {
                SoundSettings soundSettings = SoundSettings.this;
                soundSettings.mHandler.removeMessages(1);
                soundSettings.mHandler.sendEmptyMessageDelayed(1, 2000L);
            }
        }

        @Override // com.android.settings.notification.VolumeSeekBarPreference.Callback
        public final void onStartTrackingTouch(SeekBarVolumizer seekBarVolumizer) {
            SeekBarVolumizer seekBarVolumizer2 = this.mCurrent;
            if (seekBarVolumizer2 != null && seekBarVolumizer2 != seekBarVolumizer) {
                seekBarVolumizer2.stopSample();
            }
            this.mCurrent = seekBarVolumizer;
        }

        @Override // com.android.settings.notification.VolumeSeekBarPreference.Callback
        public final void onStreamValueChanged() {
            if (this.mCurrent != null) {
                SoundSettings soundSettings = SoundSettings.this;
                soundSettings.mHandler.removeMessages(1);
                soundSettings.mHandler.sendEmptyMessageDelayed(1, 2000L);
            }
        }
    }

    public static List buildPreferenceControllers(
            Context context, SoundSettings soundSettings, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PhoneRingtonePreferenceController(context));
        arrayList.add(new AlarmRingtonePreferenceController(context));
        arrayList.add(new NotificationRingtonePreferenceController(context));
        DialPadTonePreferenceController dialPadTonePreferenceController =
                new DialPadTonePreferenceController(context, soundSettings, lifecycle);
        dialPadTonePreferenceController.mPreference =
                new DialPadTonePreferenceController.AnonymousClass1(
                        2, "dial_pad_tones", "dtmf_tone", 1, new int[0]);
        ScreenLockSoundPreferenceController screenLockSoundPreferenceController =
                new ScreenLockSoundPreferenceController(context, soundSettings, lifecycle);
        screenLockSoundPreferenceController.mPreference =
                new SettingPref(
                        2, "screen_locking_sounds", "lockscreen_sounds_enabled", 1, new int[0]);
        ChargingSoundPreferenceController chargingSoundPreferenceController =
                new ChargingSoundPreferenceController(context, soundSettings, lifecycle);
        chargingSoundPreferenceController.mPreference =
                new SettingPref(3, "charging_sounds", "charging_sounds_enabled", 1, new int[0]);
        DockingSoundPreferenceController dockingSoundPreferenceController =
                new DockingSoundPreferenceController(context, soundSettings, lifecycle);
        dockingSoundPreferenceController.mPreference =
                new DockingSoundPreferenceController.AnonymousClass1(
                        1, "docking_sounds", "dock_sounds_enabled", 1, new int[0]);
        TouchSoundPreferenceController touchSoundPreferenceController =
                new TouchSoundPreferenceController(context, soundSettings, lifecycle);
        touchSoundPreferenceController.mPreference =
                new TouchSoundPreferenceController.AnonymousClass1(
                        2, "touch_sounds", "sound_effects_enabled", 1, new int[0]);
        DockAudioMediaPreferenceController dockAudioMediaPreferenceController =
                new DockAudioMediaPreferenceController(context, soundSettings, lifecycle);
        dockAudioMediaPreferenceController.mPreference =
                new SettingPref(
                        0,
                        1) { // from class:
                             // com.android.settings.notification.DockAudioMediaPreferenceController.1
                    public AnonymousClass1(int... iArr) {
                        super(1, "dock_audio_media", "dock_audio_media_enabled", 0, iArr);
                    }

                    @Override // com.android.settings.notification.SettingPref
                    public final String getCaption(Resources resources, int i) {
                        if (i == 0) {
                            return resources.getString(R.string.dock_audio_media_disabled);
                        }
                        if (i == 1) {
                            return resources.getString(R.string.dock_audio_media_enabled);
                        }
                        throw new IllegalArgumentException();
                    }

                    @Override // com.android.settings.notification.SettingPref
                    public final boolean isApplicable(Context context2) {
                        return DockAudioMediaPreferenceController.m978$$Nest$misLeDesk(
                                        DockAudioMediaPreferenceController.this)
                                && context2.getResources().getBoolean(R.bool.has_dock_settings);
                    }
                };
        BootSoundPreferenceController bootSoundPreferenceController =
                new BootSoundPreferenceController(context);
        EmergencyTonePreferenceController emergencyTonePreferenceController =
                new EmergencyTonePreferenceController(context, soundSettings, lifecycle);
        emergencyTonePreferenceController.mPreference =
                new EmergencyTonePreferenceController.AnonymousClass1(
                        1, "emergency_tone", "emergency_tone", 0, 1, 2, 0);
        VibrateIconPreferenceController vibrateIconPreferenceController =
                new VibrateIconPreferenceController(context, soundSettings, lifecycle);
        arrayList.add(dialPadTonePreferenceController);
        arrayList.add(screenLockSoundPreferenceController);
        arrayList.add(chargingSoundPreferenceController);
        arrayList.add(dockingSoundPreferenceController);
        arrayList.add(touchSoundPreferenceController);
        arrayList.add(vibrateIconPreferenceController);
        arrayList.add(dockAudioMediaPreferenceController);
        arrayList.add(bootSoundPreferenceController);
        arrayList.add(emergencyTonePreferenceController);
        arrayList.add(
                new PreferenceCategoryController(context, "other_sounds_and_vibrations_category")
                        .setChildren(
                                Arrays.asList(
                                        dialPadTonePreferenceController,
                                        screenLockSoundPreferenceController,
                                        chargingSoundPreferenceController,
                                        dockingSoundPreferenceController,
                                        touchSoundPreferenceController,
                                        vibrateIconPreferenceController,
                                        dockAudioMediaPreferenceController,
                                        bootSoundPreferenceController,
                                        emergencyTonePreferenceController)));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, this, getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SoundSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.SDOCX;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sound_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        RingtonePreference ringtonePreference = this.mRequestPreference;
        if (ringtonePreference != null) {
            ringtonePreference.onActivityResult(i, i2, intent);
            this.mRequestPreference = null;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                (VolumeSeekBarPreferenceController) use(AlarmVolumePreferenceController.class));
        arrayList.add(
                (VolumeSeekBarPreferenceController) use(MediaVolumePreferenceController.class));
        arrayList.add(
                (VolumeSeekBarPreferenceController)
                        use(SeparateRingVolumePreferenceController.class));
        arrayList.add(
                (VolumeSeekBarPreferenceController)
                        use(NotificationVolumePreferenceController.class));
        arrayList.add(
                (VolumeSeekBarPreferenceController) use(CallVolumePreferenceController.class));
        ((HandsFreeProfileOutputPreferenceController)
                        use(HandsFreeProfileOutputPreferenceController.class))
                .setCallback(
                        new AudioSwitchPreferenceController
                                .AudioSwitchCallback() { // from class:
                                                         // com.android.settings.notification.SoundSettings$$ExternalSyntheticLambda0
                            @Override // com.android.settings.sound.AudioSwitchPreferenceController.AudioSwitchCallback
                            public final void onPreferenceDataChanged(
                                    ListPreference listPreference) {
                                UpdatableListPreferenceDialogFragment
                                        updatableListPreferenceDialogFragment =
                                                SoundSettings.this.mDialogFragment;
                                if (updatableListPreferenceDialogFragment == null
                                        || updatableListPreferenceDialogFragment.mAdapter == null) {
                                    return;
                                }
                                updatableListPreferenceDialogFragment.setPreferenceData(
                                        listPreference);
                                updatableListPreferenceDialogFragment.mAdapter
                                        .notifyDataSetChanged();
                            }
                        });
        this.mHfpOutputControllerKey =
                ((HandsFreeProfileOutputPreferenceController)
                                use(HandsFreeProfileOutputPreferenceController.class))
                        .getPreferenceKey();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            VolumeSeekBarPreferenceController volumeSeekBarPreferenceController =
                    (VolumeSeekBarPreferenceController) it.next();
            volumeSeekBarPreferenceController.setCallback(this.mVolumeCallback);
            getSettingsLifecycle().addObserver(volumeSeekBarPreferenceController);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            String string = bundle.getString("selected_preference", null);
            if (!TextUtils.isEmpty(string)) {
                this.mRequestPreference = (RingtonePreference) findPreference(string);
            }
            this.mDialogFragment =
                    (UpdatableListPreferenceDialogFragment)
                            getFragmentManager().findFragmentByTag("SoundSettings");
        }
        replaceEnterpriseStringTitle(
                "sound_work_settings",
                "Settings.WORK_PROFILE_SOUND_SETTINGS_SECTION_HEADER",
                R.string.sound_work_settings);
        boolean booleanExtra =
                getIntent().getBooleanExtra("EXTRA_OPEN_PHONE_RINGTONE_PICKER", false);
        Preference findPreference = findPreference("phone_ringtone");
        if (findPreference == null || !booleanExtra) {
            return;
        }
        onPreferenceTreeClick(findPreference);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onDisplayPreferenceDialog(Preference preference) {
        if (TextUtils.equals(this.mVibrationPreferencesKey, preference.getKey())) {
            super.onDisplayPreferenceDialog(preference);
            return;
        }
        UpdatableListPreferenceDialogFragment newInstance =
                UpdatableListPreferenceDialogFragment.newInstance(
                        this.mHfpOutputControllerKey.equals(preference.getKey()) ? 1416 : 0,
                        preference.getKey());
        this.mDialogFragment = newInstance;
        newInstance.setTargetFragment(this, 0);
        this.mDialogFragment.show(getFragmentManager(), "SoundSettings");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        SeekBarVolumizer seekBarVolumizer = this.mVolumeCallback.mCurrent;
        if (seekBarVolumizer != null) {
            seekBarVolumizer.stopSample();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!(preference instanceof RingtonePreference)) {
            return super.onPreferenceTreeClick(preference);
        }
        writePreferenceClickMetric(preference);
        RingtonePreference ringtonePreference = (RingtonePreference) preference;
        this.mRequestPreference = ringtonePreference;
        ringtonePreference.onPrepareRingtonePickerIntent(ringtonePreference.getIntent());
        getActivity()
                .startActivityForResultAsUser(
                        this.mRequestPreference.getIntent(),
                        200,
                        null,
                        UserHandle.of(this.mRequestPreference.mUserId));
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        RingtonePreference ringtonePreference = this.mRequestPreference;
        if (ringtonePreference != null) {
            bundle.putString("selected_preference", ringtonePreference.getKey());
        }
    }
}
