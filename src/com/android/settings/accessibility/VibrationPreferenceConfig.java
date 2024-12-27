package com.android.settings.accessibility;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class VibrationPreferenceConfig {
    public static final VibrationEffect PREVIEW_VIBRATION_EFFECT =
            VibrationEffect.createPredefined(0);
    public final AudioManager mAudioManager;
    public final ContentResolver mContentResolver;
    public final int mDefaultIntensity;
    public final VibrationAttributes mPreviewVibrationAttributes;
    public final String mRingerModeSilentSummary;
    public final String mSettingKey;
    public final Vibrator mVibrator;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingObserver extends ContentObserver {
        public Preference mPreference;
        public AbstractPreferenceController mPreferenceController;
        public final AnonymousClass1 mRingerModeChangeReceiver;
        public final Uri mUri;
        public static final Uri MAIN_SWITCH_SETTING_URI = Settings.System.getUriFor("vibrate_on");
        public static final IntentFilter INTERNAL_RINGER_MODE_CHANGED_INTENT_FILTER =
                new IntentFilter("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");

        /* JADX WARN: Type inference failed for: r3v3, types: [com.android.settings.accessibility.VibrationPreferenceConfig$SettingObserver$1] */
        public SettingObserver(VibrationPreferenceConfig vibrationPreferenceConfig) {
            super(new Handler(true));
            this.mUri = Settings.System.getUriFor(vibrationPreferenceConfig.mSettingKey);
            if (vibrationPreferenceConfig.isRestrictedByRingerModeSilent()) {
                this.mRingerModeChangeReceiver =
                        new BroadcastReceiver() { // from class:
                                                  // com.android.settings.accessibility.VibrationPreferenceConfig.SettingObserver.1
                            @Override // android.content.BroadcastReceiver
                            public final void onReceive(Context context, Intent intent) {
                                SettingObserver settingObserver;
                                AbstractPreferenceController abstractPreferenceController;
                                Preference preference;
                                if (!"android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION"
                                                .equals(intent.getAction())
                                        || (abstractPreferenceController =
                                                        (settingObserver = SettingObserver.this)
                                                                .mPreferenceController)
                                                == null
                                        || (preference = settingObserver.mPreference) == null) {
                                    return;
                                }
                                abstractPreferenceController.updateState(preference);
                            }
                        };
            } else {
                this.mRingerModeChangeReceiver = null;
            }
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            AbstractPreferenceController abstractPreferenceController;
            Preference preference;
            if ((!this.mUri.equals(uri) && !MAIN_SWITCH_SETTING_URI.equals(uri))
                    || (abstractPreferenceController = this.mPreferenceController) == null
                    || (preference = this.mPreference) == null) {
                return;
            }
            abstractPreferenceController.updateState(preference);
        }

        public final void register(Context context) {
            AnonymousClass1 anonymousClass1 = this.mRingerModeChangeReceiver;
            if (anonymousClass1 != null) {
                context.registerReceiver(
                        anonymousClass1, INTERNAL_RINGER_MODE_CHANGED_INTENT_FILTER);
            }
            context.getContentResolver().registerContentObserver(this.mUri, false, this);
            context.getContentResolver()
                    .registerContentObserver(MAIN_SWITCH_SETTING_URI, false, this);
        }
    }

    public VibrationPreferenceConfig(Context context, String str, int i) {
        this.mContentResolver = context.getContentResolver();
        Vibrator vibrator = (Vibrator) context.getSystemService(Vibrator.class);
        this.mVibrator = vibrator;
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        this.mRingerModeSilentSummary =
                context.getString(
                        R.string.accessibility_vibration_setting_disabled_for_silent_mode_summary);
        this.mSettingKey = str;
        this.mDefaultIntensity = vibrator.getDefaultVibrationIntensity(i);
        this.mPreviewVibrationAttributes =
                new VibrationAttributes.Builder().setUsage(i).setFlags(5).build();
    }

    public final boolean isPreferenceEnabled() {
        return Settings.System.getInt(this.mContentResolver, "vibrate_on", 1) == 1
                && !(isRestrictedByRingerModeSilent()
                        && this.mAudioManager.getRingerModeInternal() == 0);
    }

    public boolean isRestrictedByRingerModeSilent() {
        return this
                instanceof
                NotificationVibrationIntensityPreferenceController
                        .NotificationVibrationPreferenceConfig;
    }

    public int readIntensity() {
        return Settings.System.getInt(
                this.mContentResolver, this.mSettingKey, this.mDefaultIntensity);
    }

    public boolean updateIntensity(int i) {
        return Settings.System.putInt(this.mContentResolver, this.mSettingKey, i);
    }
}
