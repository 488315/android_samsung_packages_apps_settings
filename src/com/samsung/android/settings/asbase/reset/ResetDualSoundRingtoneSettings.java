package com.samsung.android.settings.asbase.reset;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemProperties;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.asbase.utils.SimUtils;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ResetDualSoundRingtoneSettings {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();
    public static String mDefaultNotificationFilename_2;
    public static String mDefaultRingtoneFilename_2;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.reset.ResetDualSoundRingtoneSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            ContentResolver contentResolver = context.getContentResolver();
            SystemProperties.get("ro.config.alarm_alert");
            if (SimUtils.isMultiSimModel()) {
                ResetDualSoundRingtoneSettings.mDefaultRingtoneFilename_2 =
                        SystemProperties.get("ro.config.ringtone_2");
                ResetDualSoundRingtoneSettings.mDefaultNotificationFilename_2 =
                        SystemProperties.get("ro.config.notification_sound_2");
            }
            Uri uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
            Uri uri2 = Settings.System.CONTENT_URI;
            boolean isMultiSimModel = SimUtils.isMultiSimModel();
            if (Settings.System.getInt(contentResolver, "special_edition_ringtone_set", 0) != 1) {
                RingtoneManager.setRingtonesAsInitValue(context, 1);
                if (isMultiSimModel) {
                    ResetDualSoundRingtoneSettings.m1121$$Nest$smSettingDefaultSoundAgain(
                            contentResolver,
                            uri2,
                            uri,
                            "ringtone_2",
                            ResetDualSoundRingtoneSettings.mDefaultRingtoneFilename_2);
                }
            }
            if (Settings.System.getInt(contentResolver, "special_edition_notification_set", 0)
                    != 1) {
                RingtoneManager.setRingtonesAsInitValue(context, 2);
                if (isMultiSimModel) {
                    ResetDualSoundRingtoneSettings.m1121$$Nest$smSettingDefaultSoundAgain(
                            contentResolver,
                            uri2,
                            uri,
                            "notification_sound_2",
                            ResetDualSoundRingtoneSettings.mDefaultNotificationFilename_2);
                }
            }
            if (Settings.System.getInt(contentResolver, "special_edition_alarm_set", 0) != 1) {
                RingtoneManager.setRingtonesAsInitValue(context, 4);
            }
            if (!AudioRune.SUPPORT_SOUND_THEME
                    || Settings.System.getInt(
                                    contentResolver, "special_edition_system_sound_set", 0)
                            == 1) {
                return;
            }
            Settings.System.putString(contentResolver, "system_sound", "Galaxy");
            Settings.System.putString(contentResolver, "prev_system_sound", "Galaxy");
        }
    }

    /* renamed from: -$$Nest$smSettingDefaultSoundAgain, reason: not valid java name */
    public static void m1121$$Nest$smSettingDefaultSoundAgain(
            ContentResolver contentResolver, Uri uri, Uri uri2, String str, String str2) {
        Throwable th;
        Cursor cursor;
        Cursor cursor2 = null;
        try {
            Cursor query = contentResolver.query(uri, null, "name = '" + str + "'", null, null);
            try {
                if (query == null) {
                    Log.d(
                            "ResetDualSoundRingtoneSettings",
                            "Warning! Cursor for " + str + " in setting DB is null.");
                    if (query != null) {
                        query.close();
                        return;
                    }
                    return;
                }
                Cursor query2 =
                        contentResolver.query(uri2, null, "_data like '%" + str2 + "'", null, null);
                if (query2 != null && query2.getCount() != 0) {
                    query2.moveToFirst();
                    int columnIndex = query2.getColumnIndex("_id");
                    Log.d(
                            "ResetDualSoundRingtoneSettings",
                            "Write again. Default "
                                    + str
                                    + " is ["
                                    + query2.getLong(columnIndex)
                                    + "]  Result is ["
                                    + Settings.System.putString(
                                            contentResolver,
                                            str,
                                            ContentUris.withAppendedId(
                                                            uri2, query2.getLong(columnIndex))
                                                    .toString())
                                    + "]");
                    query.close();
                    query2.close();
                }
                Log.w("ResetDualSoundRingtoneSettings", "Cannot find " + str2);
                query.close();
                if (query2 == null) {
                    return;
                }
                query2.close();
            } catch (Throwable th2) {
                th = th2;
                cursor = null;
                cursor2 = query;
                if (cursor2 != null) {
                    cursor2.close();
                }
                if (cursor == null) {
                    throw th;
                }
                cursor.close();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
        }
    }
}
