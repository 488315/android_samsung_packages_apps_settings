package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.Intent;
import android.icu.text.MessageFormat;
import android.media.AudioManager;
import android.os.UserHandle;
import android.service.notification.Condition;
import android.service.notification.ZenModeConfig;
import android.text.format.DateFormat;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.utils.VoiceSettingsActivity;

import com.sec.ims.presence.ServiceTuple;

import java.util.HashMap;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeVoiceActivity extends VoiceSettingsActivity {
    public final CharSequence buildMessage(int i, int i2, CharSequence charSequence) {
        MessageFormat messageFormat =
                new MessageFormat(getResources().getString(i), Locale.getDefault());
        HashMap hashMap = new HashMap();
        hashMap.put("count", Integer.valueOf(i2));
        hashMap.put("time", charSequence);
        return messageFormat.format(hashMap);
    }

    @Override // com.android.settings.utils.VoiceSettingsActivity
    public final boolean onVoiceSettingInteraction(Intent intent) {
        int i;
        Condition condition;
        CharSequence string;
        if (intent.hasExtra("android.settings.extra.do_not_disturb_mode_enabled")) {
            int i2 = -1;
            int intExtra =
                    intent.getIntExtra("android.settings.extra.do_not_disturb_mode_minutes", -1);
            if (intent.getBooleanExtra(
                    "android.settings.extra.do_not_disturb_mode_enabled", false)) {
                condition =
                        intExtra > 0
                                ? ZenModeConfig.toTimeCondition(
                                        this, intExtra, UserHandle.myUserId())
                                : null;
                i = 3;
            } else {
                i = 0;
                condition = null;
            }
            if (condition != null) {
                NotificationManager.from(this).setZenMode(i, condition.id, "ZenModeVoiceActivity");
            } else {
                NotificationManager.from(this).setZenMode(i, null, "ZenModeVoiceActivity");
            }
            AudioManager audioManager =
                    (AudioManager) getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
            if (audioManager != null) {
                audioManager.adjustStreamVolume(5, 0, 1);
            }
            if (i == 0) {
                i2 = R.string.zen_mode_summary_always;
            } else if (i == 3) {
                i2 = R.string.zen_mode_summary_alarms_only_indefinite;
            }
            if (intExtra < 0 || i == 0) {
                string = getString(i2);
            } else {
                CharSequence format =
                        DateFormat.format(
                                DateFormat.getBestDateTimePattern(
                                        Locale.getDefault(),
                                        DateFormat.is24HourFormat(this, UserHandle.myUserId())
                                                ? "Hm"
                                                : "hma"),
                                System.currentTimeMillis() + (60000 * intExtra));
                string =
                        intExtra < 60
                                ? buildMessage(
                                        R.string.zen_mode_summary_alarms_only_by_minute,
                                        intExtra,
                                        format)
                                : intExtra % 60 != 0
                                        ? getResources()
                                                .getString(
                                                        R.string
                                                                .zen_mode_summary_alarms_only_by_time,
                                                        format)
                                        : buildMessage(
                                                R.string.zen_mode_summary_alarms_only_by_hour,
                                                intExtra / 60,
                                                format);
            }
            if (getVoiceInteractor() != null) {
                getVoiceInteractor()
                        .submitRequest(new VoiceSettingsActivity.AnonymousClass1(string));
            }
        } else {
            Log.v(
                    "ZenModeVoiceActivity",
                    "Missing extra android.provider.Settings.EXTRA_DO_NOT_DISTURB_MODE_ENABLED");
            finish();
        }
        return false;
    }
}
