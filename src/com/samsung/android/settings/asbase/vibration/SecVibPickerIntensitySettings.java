package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.util.SemLog;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVibPickerIntensitySettings extends DashboardFragment {
    public static final AudioAttributes RING_VIBRATION_ATTRIBUTES =
            new AudioAttributes.Builder()
                    .setContentType(4)
                    .setUsage(6)
                    .setHapticChannelsMuted(false)
                    .semAddAudioTag("VIRTUAL_VIB_SOUND")
                    .build();
    public Context mContext;
    public int mDefaultSepIndex;
    public boolean mIsSecondSimEnabled;
    public SecVibrationSeekBarPreference mNotificationSeekBar;
    public int mRepeat;
    public SecVibrationSeekBarPreference mRingtoneSeekBar;
    public String mSepIndexDbName;
    public SepIndexObserver mSepIndexObserver;
    public SecVibrationIntensityHelper mVibIntensityHelper;
    public SecVibrationIntensityHelper.AnonymousClass1 mVibSeekBarTouchListener;
    public int mType = -1;
    public boolean mConfigurationChanged = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SepIndexObserver extends ContentObserver {
        public SepIndexObserver() {
            super(new Handler(Looper.getMainLooper()));
            AudioAttributes audioAttributes =
                    SecVibPickerIntensitySettings.RING_VIBRATION_ATTRIBUTES;
            SecVibPickerIntensitySettings.this
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor(
                                    SecVibPickerIntensitySettings.this.mSepIndexDbName),
                            false,
                            this);
            SemLog.d("SecVibPickerIntensitySettings", "register observer");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            SecVibPickerIntensitySettings secVibPickerIntensitySettings =
                    SecVibPickerIntensitySettings.this;
            AudioAttributes audioAttributes =
                    SecVibPickerIntensitySettings.RING_VIBRATION_ATTRIBUTES;
            secVibPickerIntensitySettings.setEffect();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return VibPickerActivity.class.getName();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecVibPickerIntensitySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.MDMN_PUSHCALL_TO_PRIMARY;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.as_vibpicker_intensity_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mConfigurationChanged = true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        Uri actualDefaultRingtoneUri;
        Ringtone semGetRingtone;
        super.onCreatePreferences(bundle, str);
        this.mContext = getContext();
        SecVibrationIntensityHelper secVibrationIntensityHelper =
                new SecVibrationIntensityHelper(getContext());
        this.mVibIntensityHelper = secVibrationIntensityHelper;
        secVibrationIntensityHelper.mRingtoneController =
                (SecVibrationIntensityIncomingCallController)
                        use(SecVibrationIntensityIncomingCallController.class);
        this.mVibIntensityHelper.mNotificationController =
                (SecVibrationIntensityNotificationController)
                        use(SecVibrationIntensityNotificationController.class);
        this.mVibSeekBarTouchListener = this.mVibIntensityHelper.mVibSeekBarTouchListener;
        if (getArguments() != null) {
            this.mType = getArguments().getInt("type");
            this.mSepIndexDbName = getArguments().getString("indexDb");
            this.mDefaultSepIndex = getArguments().getInt("defaultIndex");
            this.mRepeat = getArguments().getInt("repeat");
            this.mIsSecondSimEnabled = getArguments().getBoolean("isSecondSimActivated");
        }
        int i = this.mType;
        if (i != 0) {
            if (i == 1) {
                SecVibrationSeekBarPreference secVibrationSeekBarPreference =
                        (SecVibrationSeekBarPreference)
                                getPreferenceScreen().findPreference("notification_vibration");
                this.mNotificationSeekBar = secVibrationSeekBarPreference;
                if (secVibrationSeekBarPreference != null) {
                    secVibrationSeekBarPreference.mTouchSeekBarCallBack =
                            this.mVibSeekBarTouchListener;
                    secVibrationSeekBarPreference.setTitle(
                            R.string.sec_notification_vibration_intensity);
                }
                removePreference("ring_vibration");
                return;
            }
            return;
        }
        SecVibrationSeekBarPreference secVibrationSeekBarPreference2 =
                (SecVibrationSeekBarPreference)
                        getPreferenceScreen().findPreference("ring_vibration");
        this.mRingtoneSeekBar = secVibrationSeekBarPreference2;
        if (secVibrationSeekBarPreference2 != null) {
            secVibrationSeekBarPreference2.mTouchSeekBarCallBack = this.mVibSeekBarTouchListener;
            secVibrationSeekBarPreference2.setTitle(R.string.sec_call_vibration_intensity);
            if (this.mIsSecondSimEnabled) {
                if (VibRune.SUPPORT_SYNC_WITH_HAPTIC
                        && (actualDefaultRingtoneUri =
                                        RingtoneManager.getActualDefaultRingtoneUri(
                                                this.mContext, 128))
                                != null
                        && AudioManager.hasHapticChannels(this.mContext, actualDefaultRingtoneUri)
                        && (semGetRingtone =
                                        RingtoneManager.semGetRingtone(
                                                this.mContext, 0, actualDefaultRingtoneUri))
                                != null) {
                    semGetRingtone.setAudioAttributes(RING_VIBRATION_ATTRIBUTES);
                    semGetRingtone.setVolume(0.0f);
                    semGetRingtone.setLooping(false);
                    this.mRingtoneSeekBar.mRingtone = semGetRingtone;
                }
                this.mRingtoneSeekBar.mSyncDbName = "sync_vibration_with_ringtone_2";
                setEffect();
            }
        }
        removePreference("notification_vibration");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        SemLog.i("SecVibPickerIntensitySettings", "onPause");
        SecVibrationIntensityHelper secVibrationIntensityHelper = this.mVibIntensityHelper;
        if (secVibrationIntensityHelper != null) {
            secVibrationIntensityHelper.mStopFlag = true;
            if (this.mConfigurationChanged) {
                this.mConfigurationChanged = false;
            } else {
                secVibrationIntensityHelper.stopVibration();
            }
        }
        if (this.mSepIndexObserver != null) {
            getContentResolver().unregisterContentObserver(this.mSepIndexObserver);
            this.mSepIndexObserver = null;
            SemLog.d("SecVibPickerIntensitySettings", "unregister observer");
        }
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        SemLog.i("SecVibPickerIntensitySettings", "onResume");
        SecVibrationIntensityHelper secVibrationIntensityHelper = this.mVibIntensityHelper;
        if (secVibrationIntensityHelper != null) {
            secVibrationIntensityHelper.mStopFlag = false;
        }
        if (this.mSepIndexObserver == null) {
            this.mSepIndexObserver = new SepIndexObserver();
        }
        super.onResume();
    }

    public final void setEffect() {
        VibrationEffect semCreateHaptic =
                VibrationEffect.semCreateHaptic(
                        Settings.System.getInt(
                                getContentResolver(), this.mSepIndexDbName, this.mDefaultSepIndex),
                        this.mRepeat);
        int i = this.mType;
        if (i == 0) {
            this.mRingtoneSeekBar.mVibrationEffect = semCreateHaptic;
        } else if (i == 1) {
            this.mNotificationSeekBar.mVibrationEffect = semCreateHaptic;
        }
    }
}
