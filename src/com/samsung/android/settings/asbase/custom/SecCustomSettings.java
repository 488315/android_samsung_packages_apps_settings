package com.samsung.android.settings.asbase.custom;

import android.content.Context;
import android.content.Intent;
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
import com.android.settings.notification.AlarmRingtonePreferenceController;
import com.android.settings.notification.AlarmVolumePreferenceController;
import com.android.settings.notification.CallVolumePreferenceController;
import com.android.settings.notification.MediaVolumePreferenceController;
import com.android.settings.notification.NotificationRingtonePreferenceController;
import com.android.settings.notification.NotificationVolumePreferenceController;
import com.android.settings.notification.PhoneRingtonePreferenceController;
import com.android.settings.notification.RingVolumePreferenceController;
import com.android.settings.notification.VibrateIconPreferenceController;
import com.android.settings.notification.VolumeSeekBarPreference;
import com.android.settings.notification.VolumeSeekBarPreferenceController;
import com.android.settings.sound.AudioSwitchPreferenceController;
import com.android.settings.sound.HandsFreeProfileOutputPreferenceController;
import com.android.settings.widget.UpdatableListPreferenceDialogFragment;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecCustomSettings extends DashboardFragment implements OnActivityResultListener {
    public UpdatableListPreferenceDialogFragment mDialogFragment;
    public String mHfpOutputControllerKey;
    public RingtonePreference mRequestPreference;
    public final VolumePreferenceCallback mVolumeCallback = new VolumePreferenceCallback();
    public final AnonymousClass1 mHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.android.settings.asbase.custom.SecCustomSettings.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    SeekBarVolumizer seekBarVolumizer;
                    if (message.what == 1
                            && (seekBarVolumizer = SecCustomSettings.this.mVolumeCallback.mCurrent)
                                    != null) {
                        seekBarVolumizer.stopSample();
                    }
                }
            };
    public final String mVibrationPreferencesKey = "vibration_preference_screen";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VolumePreferenceCallback implements VolumeSeekBarPreference.Callback {
        public SeekBarVolumizer mCurrent;

        public VolumePreferenceCallback() {}

        @Override // com.android.settings.notification.VolumeSeekBarPreference.Callback
        public final void onSampleStarting() {
            if (this.mCurrent != null) {
                SecCustomSettings secCustomSettings = SecCustomSettings.this;
                secCustomSettings.mHandler.removeMessages(1);
                secCustomSettings.mHandler.sendEmptyMessageDelayed(1, 2000L);
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
                SecCustomSettings secCustomSettings = SecCustomSettings.this;
                secCustomSettings.mHandler.removeMessages(1);
                secCustomSettings.mHandler.sendEmptyMessageDelayed(1, 2000L);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PhoneRingtonePreferenceController(context));
        arrayList.add(new AlarmRingtonePreferenceController(context));
        arrayList.add(new NotificationRingtonePreferenceController(context));
        arrayList.add(new VibrateIconPreferenceController(context, this, settingsLifecycle));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecCustomSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.as_custom_settings;
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
                (VolumeSeekBarPreferenceController) use(RingVolumePreferenceController.class));
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
                                                         // com.samsung.android.settings.asbase.custom.SecCustomSettings$$ExternalSyntheticLambda0
                            @Override // com.android.settings.sound.AudioSwitchPreferenceController.AudioSwitchCallback
                            public final void onPreferenceDataChanged(
                                    ListPreference listPreference) {
                                UpdatableListPreferenceDialogFragment
                                        updatableListPreferenceDialogFragment =
                                                SecCustomSettings.this.mDialogFragment;
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
                            getFragmentManager().findFragmentByTag("SecCustomSettings");
        }
        replaceEnterpriseStringTitle(
                "sound_work_settings",
                "Settings.WORK_PROFILE_SOUND_SETTINGS_SECTION_HEADER",
                R.string.sec_sound_work_settings);
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
        this.mDialogFragment.show(getFragmentManager(), "SecCustomSettings");
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
