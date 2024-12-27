package com.samsung.android.settings.eternal.provider.items;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.Utils;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.media.SemSoundAssistantManager;
import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.settings.asbase.utils.SimUtils;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.asbase.vibration.PatternRestoreHelper;
import com.samsung.android.settings.asbase.vibration.VibPickerConstants;
import com.samsung.android.settings.knox.KnoxUtils;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SoundItem implements Recoverable {
    public static String getBackupNotificationSoundTitle(Context context, int i) {
        Ringtone ringtone = RingtoneManager.getRingtone(context, RingtoneManager.getActualDefaultRingtoneUri(context, i));
        String title = ringtone.getTitle(context);
        if (TextUtils.equals(title, context.getString(17042766))) {
            return "Silent";
        }
        if (RingtoneManager.isInternalRingtoneUri(ringtone.getUri())) {
            return title;
        }
        Log.i("SoundItem", "back up only the internal notification sound.");
        return ApnSettings.MVNO_NONE;
    }

    public static String getDefaultNotificationTitle(int i) {
        String str = ApnSettings.MVNO_NONE;
        if (i == 2) {
            str = SystemProperties.get(RingtoneManager.getOMCRingtonePropertyName(i), ApnSettings.MVNO_NONE);
        }
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        if (i == 2) {
            str = SystemProperties.get("ro.config.notification_sound");
        } else if (i == 256) {
            str = SystemProperties.get("ro.config.notification_sound_2");
        }
        return str.contains(".") ? str.substring(0, str.lastIndexOf(46)) : str;
    }

    public static int getStreamVolume(Context context, int i) {
        return ((AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO)).getStreamVolume(i);
    }

    public static void restoreNotificationSound(Context context, int i, String str) {
        Uri parse = Uri.parse("content://media/internal/audio/media");
        RingtoneManager ringtoneManager = new RingtoneManager(context);
        ringtoneManager.setType(i);
        Cursor cursor = ringtoneManager.getCursor();
        if (cursor != null && cursor.moveToFirst()) {
            while (true) {
                if (str.compareToIgnoreCase(cursor.getString(cursor.getColumnIndex(UniversalCredentialUtil.AGENT_TITLE))) == 0) {
                    RingtoneManager.setActualDefaultRingtoneUri(context, i, Uri.withAppendedPath(parse, ApnSettings.MVNO_NONE + cursor.getInt(cursor.getColumnIndex("_id"))));
                    break;
                }
                if (!cursor.moveToNext()) {
                    break;
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        if (TextUtils.equals(str, "Silent")) {
            RingtoneManager.setActualDefaultRingtoneUri(context, i, null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v6 */
    public static void restoreSeparateAppSound(Context context) {
        String[] strArr;
        ContentResolver contentResolver = context.getContentResolver();
        String string = Settings.System.getString(contentResolver, "restore_multisound_app");
        int i = Settings.System.getInt(contentResolver, "restore_multisound_devicetype", -1);
        if (TextUtils.isEmpty(string)) {
            Log.d("SoundItem", "restoreSeparateAppSound( restore apps : null )");
            return;
        }
        Log.d("SoundItem", "restoreSeparateAppSound( restore apps : " + string + " )");
        if (i == -1) {
            Log.d("SoundItem", "restoreSeparateAppSound( restore Device : -1 )");
            return;
        }
        MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(i, "restoreSeparateAppSound( restore Device : ", " )", "SoundItem");
        AudioManager audioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        int i2 = 2;
        if (i != 0 && i == 1) {
            i2 = 8;
        }
        if (TextUtils.isEmpty(string)) {
            return;
        }
        String[] split = string.split(":");
        int length = split.length;
        ?? r5 = 0;
        int i3 = 0;
        int i4 = 0;
        String str = null;
        while (i4 < length) {
            String str2 = split[i4];
            if (TextUtils.isEmpty(str2)) {
                strArr = split;
            } else {
                strArr = split;
                try {
                    audioManager.setAppDevice(context.getPackageManager().getApplicationInfo(str2, (int) r5).uid, i2, r5);
                    i3++;
                    if (TextUtils.isEmpty(str)) {
                        str = str2;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        String str3 = str;
                        try {
                            sb.append(str3);
                            sb.append(":");
                            sb.append(str2);
                            str = sb.toString();
                        } catch (PackageManager.NameNotFoundException e) {
                            e = e;
                            str = str3;
                            Log.e("SoundItem", "restoreSeparateAppSound::NameNotFoundException", e);
                            i4++;
                            split = strArr;
                            r5 = 0;
                        } catch (IllegalArgumentException e2) {
                            e = e2;
                            str = str3;
                            Log.e("SoundItem", "restoreSeparateAppSound::IllegalArgumentException", e);
                            i4++;
                            split = strArr;
                            r5 = 0;
                        }
                    }
                    try {
                        SemSoundAssistantManager semSoundAssistantManager = new SemSoundAssistantManager(context);
                        if (!semSoundAssistantManager.isPredefinedMultiSoundSupportedPackage(str2)) {
                            semSoundAssistantManager.addToMultiSoundSupportedList(str2);
                        }
                    } catch (PackageManager.NameNotFoundException e3) {
                        e = e3;
                        Log.e("SoundItem", "restoreSeparateAppSound::NameNotFoundException", e);
                        i4++;
                        split = strArr;
                        r5 = 0;
                    } catch (IllegalArgumentException e4) {
                        e = e4;
                        Log.e("SoundItem", "restoreSeparateAppSound::IllegalArgumentException", e);
                        i4++;
                        split = strArr;
                        r5 = 0;
                    }
                } catch (PackageManager.NameNotFoundException e5) {
                    e = e5;
                } catch (IllegalArgumentException e6) {
                    e = e6;
                }
            }
            i4++;
            split = strArr;
            r5 = 0;
        }
        String str4 = str;
        if (i3 > 0) {
            MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(i3, "restoreSeparateAppSound::setMultiSoundOn( added = ", " )", "SoundItem");
            Settings.System.putString(contentResolver, "multisound_app", str4);
            Settings.System.putInt(contentResolver, "multisound_devicetype", i);
            audioManager.setMultiSoundOn(true, false);
        }
        Settings.System.putString(contentResolver, "restore_multisound_app", null);
        Settings.System.putInt(contentResolver, "restore_multisound_devicetype", -1);
    }

    public static void setStreamVolume(Context context, int i, int i2) {
        ((AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO)).setStreamVolume(i, i2, 0);
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x012e, code lost:
    
        if (r7.equals("/Settings/Sound/KeyboardSound") != false) goto L81;
     */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getTestScenes(android.content.Context r6, java.lang.String r7) {
        /*
            Method dump skipped, instructions count: 574
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.eternal.provider.items.SoundItem.getTestScenes(android.content.Context, java.lang.String):java.util.List");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0374, code lost:
    
        if (r3 != null) goto L227;
     */
    /* JADX WARN: Removed duplicated region for block: B:45:0x037d  */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.lib.episode.Scene.Builder getValue(android.content.Context r19, com.samsung.android.lib.episode.SourceInfo r20, java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 2664
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.eternal.provider.items.SoundItem.getValue(android.content.Context, com.samsung.android.lib.episode.SourceInfo, java.lang.String):com.samsung.android.lib.episode.Scene$Builder");
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        SceneResult.Builder builder;
        SceneResult.ResultType resultType;
        SceneResult.ResultType resultType2;
        builder = new SceneResult.Builder(str);
        str.getClass();
        resultType = SceneResult.ResultType.RESULT_FAIL;
        resultType2 = SceneResult.ResultType.RESULT_OK;
        switch (str) {
            case "/Settings/Sound/KeyboardSound":
            case "/Settings/Sound/VolumeLimiterLevel":
            case "/Settings/Sound/RingerMode":
            case "/Settings/Sound/DialingKeypadTone":
            case "/Settings/Sound/NotificationSound":
            case "/Settings/Sound/TouchSound":
            case "/Settings/Sound/ScreenLockSound":
            case "/Settings/Sound/VolumeLimiterPassword":
            case "/Settings/Sound/ChromeCastModeEnabled":
            case "/Settings/Sound/UseVolumeKeyForMedia":
            case "/Settings/Sound/ChargingSound":
            case "/Settings/Sound/VoipExtraVolume":
                builder.mResultType = resultType2;
                break;
            case "/Settings/Sound/VibrationFeedback":
            case "/Settings/Sound/ChargingVibration":
            case "/Settings/Sound/DialingKeypadVibration":
            case "/Settings/Sound/CameraFeedbackVibration":
            case "/Settings/Sound/NavigationGesturesVibration":
            case "/Settings/Sound/KeyboardVibration":
                if (!VibUtils.hasSystemVibrationMenu(context)) {
                    builder.mResultType = resultType;
                    builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                    break;
                } else {
                    builder.mResultType = resultType2;
                    break;
                }
            case "/Settings/Sound/HardPressVibrationMagnitude":
            case "/Settings/Sound/MediaVibrationMagnitude":
            case "/Settings/Sound/CallVibrationMagnitude":
            case "/Settings/Sound/NotificationVibrationMagnitude":
            case "/Settings/Sound/TouchVibrationMagnitude":
                if (!VibUtils.hasVibrator(context)) {
                    builder.mResultType = resultType;
                    builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                    break;
                } else {
                    builder.mResultType = resultType2;
                    break;
                }
            case "/Settings/Sound/SeparateMultiAppsSoundName":
            case "/Settings/Sound/SeparateAppSoundDevice":
            case "/Settings/Sound/SeparateAppSoundName":
            case "/Settings/Sound/SeparateMultiAppSoundDevice":
                if (ActivityManager.getCurrentUser() == 0) {
                    builder.mResultType = resultType2;
                    break;
                } else {
                    builder.mResultType = resultType;
                    builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                    break;
                }
            case "/Settings/Sound/VibrationSoundEnabled":
                builder.mResultType = resultType2;
                break;
            case "/Settings/Sound/NotificationVibrationPattern":
            case "/Settings/Sound/VibrationPattern":
            case "/Settings/Sound/SyncWithRingtoneSim2":
            case "/Settings/Sound/SyncWithRingtone":
            case "/Settings/Sound/VibrationPatternSim2":
            case "/Settings/Sound/SyncWithNotification":
                if (!VibUtils.hasVibrator(context) || !Utils.isVoiceCapable(context)) {
                    builder.mResultType = resultType;
                    builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                    break;
                } else {
                    builder.mResultType = resultType2;
                    break;
                }
                break;
            case "/Settings/Sound/NotificationSoundSim2":
                if (SimUtils.isMultiSimModel()) {
                    builder.mResultType = resultType2;
                    break;
                }
                break;
            case "/Settings/Sound/VibrationWhileRinging":
                if (!VibUtils.isSupportVibrateWhenRing(context)) {
                    builder.mResultType = resultType;
                    builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                    break;
                } else {
                    builder.mResultType = resultType2;
                    break;
                }
        }
        return builder.build();
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {
        Intent intent;
        intent = new Intent();
        intent.setFlags(268435456);
        str.getClass();
        switch (str) {
            case "/Settings/Sound/KeyboardSound":
            case "/Settings/Sound/DialingKeypadTone":
            case "/Settings/Sound/TouchSound":
            case "/Settings/Sound/ScreenLockSound":
            case "/Settings/Sound/ChargingSound":
                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SecSoundSystemSoundSettingsActivity", intent);
                break;
            case "/Settings/Sound/VolumeLimiterLevel":
            case "/Settings/Sound/VolumeLimiterPassword":
                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SecVolumeLimiterSettingsActivity", intent);
                break;
            case "/Settings/Sound/VibrationFeedback":
            case "/Settings/Sound/ChargingVibration":
            case "/Settings/Sound/DialingKeypadVibration":
            case "/Settings/Sound/CameraFeedbackVibration":
            case "/Settings/Sound/NavigationGesturesVibration":
            case "/Settings/Sound/KeyboardVibration":
                if (VibUtils.hasSystemVibrationMenu(context)) {
                    DefaultRingtonePreference$$ExternalSyntheticOutline0.m(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$VibrationSystemIntensitySettingsActivity", intent);
                    break;
                }
                break;
            case "/Settings/Sound/HardPressVibrationMagnitude":
            case "/Settings/Sound/MediaVibrationMagnitude":
            case "/Settings/Sound/CallVibrationMagnitude":
            case "/Settings/Sound/NotificationVibrationMagnitude":
            case "/Settings/Sound/TouchVibrationMagnitude":
                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$VibrationSystemIntensitySettingsActivity", intent);
                break;
            case "/Settings/Sound/SeparateMultiAppsSoundName":
            case "/Settings/Sound/SeparateAppSoundDevice":
            case "/Settings/Sound/SeparateAppSoundName":
            case "/Settings/Sound/SeparateMultiAppSoundDevice":
                if (ActivityManager.getCurrentUser() == 0) {
                    DefaultRingtonePreference$$ExternalSyntheticOutline0.m("com.samsung.android.setting.multisound", "com.samsung.android.setting.multisound.MultiSoundSettingsActivity", intent);
                    break;
                }
                break;
            case "/Settings/Sound/RingerMode":
            case "/Settings/Sound/NotificationSound":
                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SoundSettingsActivity", intent);
                break;
            case "/Settings/Sound/VibrationSoundEnabled":
                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$VibrationSystemIntensitySettingsActivity", intent);
                break;
            case "/Settings/Sound/NotificationVibrationPattern":
            case "/Settings/Sound/VibrationPattern":
            case "/Settings/Sound/SyncWithRingtoneSim2":
            case "/Settings/Sound/SyncWithRingtone":
            case "/Settings/Sound/VibrationPatternSim2":
            case "/Settings/Sound/SyncWithNotification":
                if (VibUtils.hasVibrator(context) && Utils.isVoiceCapable(context)) {
                    DefaultRingtonePreference$$ExternalSyntheticOutline0.m(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SoundSettingsActivity", intent);
                    break;
                }
                break;
            case "/Settings/Sound/NotificationSoundSim2":
                if (SimUtils.isMultiSimModel()) {
                    DefaultRingtonePreference$$ExternalSyntheticOutline0.m(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SoundSettingsActivity", intent);
                    break;
                }
                break;
            case "/Settings/Sound/ChromeCastModeEnabled":
                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SoundSettingsActivity", intent);
                break;
            case "/Settings/Sound/UseVolumeKeyForMedia":
            case "/Settings/Sound/VoipExtraVolume":
                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SecVolumeSettingsActivity", intent);
                break;
            case "/Settings/Sound/VibrationWhileRinging":
                if (VibUtils.isSupportVibrateWhenRing(context)) {
                    DefaultRingtonePreference$$ExternalSyntheticOutline0.m(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SecSoundSystemSoundSettingsActivity", intent);
                    break;
                }
                break;
        }
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult setValue(Context context, String str, Scene scene, SourceInfo sourceInfo) {
        String str2;
        char c;
        int i;
        int i2;
        int findIdByQuery;
        int findIdByQuery2;
        ContentResolver contentResolver = context.getContentResolver();
        String value = scene.getValue(null, false);
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        AudioManager audioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        str.getClass();
        switch (str.hashCode()) {
            case -2033144821:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/KeyboardSound")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -2016034253:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/VolumeLimiterLevel")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1935116966:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/VibrationFeedback")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1811247815:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/HardPressVibrationMagnitude")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1802437649:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/SeparateMultiAppsSoundName")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1794904483:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/RingerMode")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1577794492:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/ChargingVibration")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1235026555:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/MediaVibrationMagnitude")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1208432997:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/DialingKeypadTone")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1031489703:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/CallVibrationMagnitude")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -985307383:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/DialingKeypadVibration")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -879988089:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/VibrationSoundEnabled")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -501007836:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/SeparateAppSoundDevice")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -273148213:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/CameraFeedbackVibration")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -121171961:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/NotificationSound")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -80801882:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/NotificationVibrationMagnitude")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -29756083:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/TouchSound")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 49774971:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/ScreenLockSound")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 117271584:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/TouchVibrationMagnitude")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 199975308:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/VolumeLimiterPassword")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 227006246:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/SystemVolume")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 293869574:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/SystemSoundTheme")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 408688294:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/NotificationVibrationPattern")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 408910460:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/WaitingToneVolume")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 524879586:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/NotificationSoundSim2")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 538137371:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/VibrationPattern")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 617240872:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/ChromeCastModeEnabled")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case 624262946:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/NotificationVolume")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case 840934811:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/SyncWithRingtoneSim2")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 855846679:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/NavigationGesturesVibration")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 1006642646:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/UseVolumeKeyForMedia")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 1197425601:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/ChargingSound")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case 1272666774:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/VibrationWhileRinging")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 1362899417:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/SeparateAppSoundName")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case 1433404289:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/MediaVolume")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case 1470362432:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/SyncWithRingtone")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 1568276333:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/VoipExtraVolume")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 1839319086:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/AlarmVolume")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case 1909825526:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/VibrationPatternSim2")) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case 1951547785:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/SyncWithNotification")) {
                    c = '\'';
                    break;
                }
                c = 65535;
                break;
            case 1955076549:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/BixbyVolume")) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case 1965497917:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/SeparateMultiAppSoundDevice")) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case 1990986347:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/AccessibilityVolume")) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case 2068784551:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/RingVolume")) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case 2122643342:
                str2 = "supportAch";
                if (str.equals("/Settings/Sound/KeyboardVibration")) {
                    c = ',';
                    break;
                }
                c = 65535;
                break;
            default:
                str2 = "supportAch";
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                Settings.System.putInt(contentResolver, "sip_key_feedback_sound", Integer.parseInt(value));
                break;
            case 1:
                int parseInt = Integer.parseInt(value);
                Settings.System.putInt(contentResolver, "volumelimit_on", 1);
                Settings.System.putInt(contentResolver, "volume_limiter_value", parseInt);
                audioManager.enableVolumeLimiter(true);
                audioManager.setVolumeLimiterValue(parseInt);
                break;
            case 2:
                if (!VibUtils.hasSystemVibrationMenu(context)) {
                    Settings.System.putInt(contentResolver, "haptic_feedback_enabled", 0);
                    break;
                } else {
                    Settings.System.putInt(contentResolver, "haptic_feedback_enabled", Integer.parseInt(value));
                    break;
                }
            case 3:
                Settings.System.putInt(contentResolver, "SEM_VIBRATION_FORCE_TOUCH_INTENSITY", Integer.parseInt(value));
                Log.d("SoundItem", "restore hard press vibration magnitude value=" + Integer.parseInt(value));
                break;
            case 4:
            case '!':
                Settings.System.putString(contentResolver, "restore_multisound_app", value);
                restoreSeparateAppSound(context);
                break;
            case 5:
                audioManager.setRingerModeInternal(Integer.parseInt(value));
                break;
            case 6:
                Settings.Secure.putInt(contentResolver, "charging_vibration_enabled", Integer.parseInt(value));
                break;
            case 7:
                Settings.System.putInt(contentResolver, "media_vibration_intensity", Integer.parseInt(value));
                Log.d("SoundItem", "restore media vibration magnitude value=" + Integer.parseInt(value));
                break;
            case '\b':
                Settings.System.putInt(contentResolver, "dtmf_tone", Integer.parseInt(value));
                break;
            case '\t':
                Settings.System.putInt(contentResolver, "VIB_RECVCALL_MAGNITUDE", Integer.parseInt(value));
                Log.d("SoundItem", "restore call vibration magnitude value=" + Integer.parseInt(value));
                break;
            case '\n':
                Settings.System.putInt(contentResolver, "dialing_keypad_vibrate", Integer.parseInt(value));
                break;
            case 11:
                Settings.System.putInt(contentResolver, "vibration_sound_enabled", Integer.parseInt(value));
                Log.d("SoundItem", "restore vibration sound enabled value=" + Integer.parseInt(value));
                break;
            case '\f':
            case ')':
                Settings.System.putInt(contentResolver, "restore_multisound_devicetype", Integer.parseInt(value));
                restoreSeparateAppSound(context);
                break;
            case '\r':
                Settings.System.putInt(contentResolver, "camera_feedback_vibrate", Integer.parseInt(value));
                break;
            case 14:
                restoreNotificationSound(context, 2, value);
                break;
            case 15:
                Settings.System.putInt(contentResolver, "SEM_VIBRATION_NOTIFICATION_INTENSITY", Integer.parseInt(value));
                Log.d("SoundItem", "restore call vibration magnitude value=" + Integer.parseInt(value));
                break;
            case 16:
                Settings.System.putInt(contentResolver, "sound_effects_enabled", Integer.parseInt(value));
                break;
            case 17:
                Settings.System.putInt(contentResolver, "lockscreen_sounds_enabled", Integer.parseInt(value));
                break;
            case 18:
                boolean attributeBoolean = scene.getAttributeBoolean("supportDC");
                int parseInt2 = Integer.parseInt(value);
                if (VibUtils.isSupportDcHaptic(context)) {
                    if (!attributeBoolean) {
                        if (parseInt2 == 5) {
                            i = 3;
                        } else if (parseInt2 >= 3) {
                            i = 2;
                        } else if (parseInt2 >= 1) {
                            i = 1;
                        }
                    }
                    i = parseInt2;
                } else {
                    if (attributeBoolean) {
                        if (parseInt2 == 3) {
                            i = 5;
                        } else if (parseInt2 == 2) {
                            i = 3;
                        }
                    }
                    i = parseInt2;
                }
                Settings.System.putInt(contentResolver, "VIB_FEEDBACK_MAGNITUDE", i);
                Log.d("SoundItem", "restore touch vibration magnitude value=" + Integer.parseInt(value));
                break;
            case 19:
                Settings.System.putInt(contentResolver, "volumelimit_set_password", 1);
                Settings.Secure.putString(contentResolver, "volumelimit_secure_password", value);
                break;
            case 20:
                setStreamVolume(context, 1, Integer.parseInt(value));
                break;
            case 21:
                Settings.System.putString(contentResolver, "system_sound", value);
                break;
            case 22:
                String str3 = str2;
                Log.d("SoundItem", "Restoration of notification pattern!");
                if (VibRune.SUPPORT_SEC_VIBRATION_PICKER) {
                    int attributeInt = scene.getAttributeInt(-1, "sep_Index");
                    if (attributeInt != -1) {
                        i2 = 1;
                        findIdByQuery = PatternRestoreHelper.findIdByQuery(context, 1, attributeInt);
                        if (findIdByQuery == -1) {
                            Log.w("SoundItem", "skip restoration notification pattern by sepIndex");
                            break;
                        }
                    } else {
                        i2 = 1;
                        int parseInt3 = Integer.parseInt(value.substring(value.lastIndexOf(47) + 1));
                        findIdByQuery = PatternRestoreHelper.getIdByLegacyId(context, 1, parseInt3);
                        if (findIdByQuery == -1) {
                            Log.w("SoundItem", "skip restoration notification pattern by legacyId");
                            break;
                        } else {
                            int sepIndexById = PatternRestoreHelper.getSepIndexById(context, 1, findIdByQuery);
                            Preference$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m("legacyId=", ", id=", parseInt3, findIdByQuery, ", sepIndex="), sepIndexById, "SoundItem");
                            attributeInt = sepIndexById;
                        }
                    }
                    List list = VibPickerConstants.SIMPLE_NOTIFICATION_PATTERN;
                    if ((findIdByQuery >= ((ArrayList) VibPickerConstants.COLORFUL_NOTIFICATION_PATTERN).size() + ((ArrayList) list).size() + i2 && !PatternRestoreHelper.supportsLivePattern(context)) || (findIdByQuery >= ((ArrayList) list).size() + 1 && !PatternRestoreHelper.supportsColorfulPattern(context))) {
                        Log.w("SoundItem", "Do not restore!");
                        break;
                    } else {
                        String str4 = "content://com.android.settings.personalvibration.PersonalVibrationProvider/notification/" + findIdByQuery;
                        Settings.System.putString(contentResolver, "default_notification_vibration_pattern", str4);
                        Settings.System.putInt(contentResolver, "notification_vibration_sep_index", attributeInt);
                        StringBuilder sb = new StringBuilder("restore notification pattern sepIndex=");
                        sb.append(attributeInt);
                        AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(sb, ", uri=", str4, "SoundItem");
                        if (VibRune.SUPPORT_SYNC_WITH_HAPTIC) {
                            String str5 = sourceInfo.mOneUIVersion;
                            boolean attributeBoolean2 = scene.getAttributeBoolean(str3);
                            if (str5 == null || Float.parseFloat(str5) <= 4.0f || !attributeBoolean2) {
                                Settings.System.putInt(contentResolver, "sync_vibration_with_notification", 0);
                                Log.d("SoundItem", "Sync with notification is turned off by the OneUI version of the backup device.");
                                break;
                            }
                        }
                    }
                }
                break;
            case 23:
                setStreamVolume(context, 8, Integer.parseInt(value));
                break;
            case 24:
                restoreNotificationSound(context, 256, value);
                break;
            case 25:
                Log.d("SoundItem", "Restoration of ringtone pattern!");
                if (!VibRune.SUPPORT_SEC_VIBRATION_PICKER) {
                    if (Integer.parseInt(value.substring(value.lastIndexOf(47) + 1)) >= ((ArrayList) VibPickerConstants.SIMPLE_RINGTONE_PATTERN_LEGACY).size() + 1 && !PatternRestoreHelper.supportsColorfulPattern(context)) {
                        Log.w("SoundItem", "Do not restore!");
                        break;
                    } else {
                        Settings.System.putString(contentResolver, "default_vibration_pattern", value);
                        Log.d("SoundItem", "restore ringtone pattern uri=".concat(value));
                        break;
                    }
                } else {
                    int attributeInt2 = scene.getAttributeInt(-1, "sep_Index");
                    if (attributeInt2 != -1) {
                        findIdByQuery2 = PatternRestoreHelper.findIdByQuery(context, 0, attributeInt2);
                        if (findIdByQuery2 == -1) {
                            Log.w("SoundItem", "skip restoration ringtone pattern by sepIndex");
                            break;
                        }
                    } else {
                        int parseInt4 = Integer.parseInt(value.substring(value.lastIndexOf(47) + 1));
                        findIdByQuery2 = PatternRestoreHelper.getIdByLegacyId(context, 0, parseInt4);
                        if (findIdByQuery2 == -1) {
                            Log.w("SoundItem", "skip restoration ringtone pattern by legacyId");
                            break;
                        } else {
                            int sepIndexById2 = PatternRestoreHelper.getSepIndexById(context, 0, findIdByQuery2);
                            Preference$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m("legacyId=", ", id=", parseInt4, findIdByQuery2, ", sepIndex="), sepIndexById2, "SoundItem");
                            attributeInt2 = sepIndexById2;
                        }
                    }
                    List list2 = VibPickerConstants.SIMPLE_RINGTONE_PATTERN;
                    if ((findIdByQuery2 >= ((ArrayList) VibPickerConstants.COLORFUL_RINGTONE_PATTERN).size() + ((ArrayList) list2).size() + 1 && !PatternRestoreHelper.supportsLivePattern(context)) || (findIdByQuery2 >= ((ArrayList) list2).size() + 1 && !PatternRestoreHelper.supportsColorfulPattern(context))) {
                        Log.w("SoundItem", "Do not restore!");
                        break;
                    } else {
                        String str6 = "content://com.android.settings.personalvibration.PersonalVibrationProvider/registerinfo/" + findIdByQuery2;
                        Settings.System.putString(contentResolver, "default_vibration_pattern", str6);
                        Settings.System.putInt(contentResolver, "ringtone_vibration_sep_index", attributeInt2);
                        StringBuilder sb2 = new StringBuilder("restore ringtone pattern sepIndex=");
                        sb2.append(attributeInt2);
                        AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(sb2, ", uri=", str6, "SoundItem");
                        if (VibRune.SUPPORT_SYNC_WITH_HAPTIC) {
                            String str7 = sourceInfo.mOneUIVersion;
                            boolean attributeBoolean3 = scene.getAttributeBoolean(str2);
                            if (str7 == null || Float.parseFloat(str7) <= 4.0f || !attributeBoolean3 || KnoxUtils.appRestrictionState) {
                                Settings.System.putInt(contentResolver, "sync_vibration_with_ringtone", 0);
                                Log.d("SoundItem", "Sync with ringtone is turned off by the OneUI version of the backup device.");
                                break;
                            }
                        }
                    }
                }
                break;
            case 26:
                Settings.System.putInt(contentResolver, "wifispeaker_chromecast_mode_enabled", Integer.parseInt(value));
                break;
            case 27:
                setStreamVolume(context, 5, Integer.parseInt(value));
                break;
            case 28:
                if (VibRune.SUPPORT_SYNC_WITH_HAPTIC) {
                    Settings.System.putInt(contentResolver, "sync_vibration_with_ringtone_2", Integer.parseInt(value));
                    Log.d("SoundItem", "restore sync with ringtone dual sim value=" + Integer.parseInt(value));
                    break;
                }
                break;
            case 29:
                Settings.System.putInt(contentResolver, "navigation_gestures_vibrate", Integer.parseInt(value));
                break;
            case 30:
                new SemSoundAssistantManager(context).setVolumeMode(1, "1".equals(value));
                break;
            case 31:
                Settings.Global.putInt(contentResolver, "charging_sounds_enabled", Integer.parseInt(value));
                break;
            case ' ':
                Settings.System.putInt(contentResolver, "vibrate_when_ringing", Integer.parseInt(value));
                break;
            case '\"':
                setStreamVolume(context, 3, Integer.parseInt(value));
                break;
            case '#':
                if (VibRune.SUPPORT_SYNC_WITH_HAPTIC) {
                    Settings.System.putInt(contentResolver, "sync_vibration_with_ringtone", Integer.parseInt(value));
                    Log.d("SoundItem", "restore sync with ringtone value=" + Integer.parseInt(value));
                    break;
                }
                break;
            case '$':
                Settings.System.putInt(contentResolver, "voip_extra_volume", Integer.parseInt(value));
                Log.d("SoundItem", "Restore Voip Extra Volume Value = " + Integer.parseInt(value));
                break;
            case '%':
                setStreamVolume(context, 4, Integer.parseInt(value));
                break;
            case '&':
                Settings.System.putInt(contentResolver, "ringtone_vibration_sep_index_2", Integer.parseInt(value));
                Log.d("SoundItem", "restore dual sim ringtone pattern sepIndex=" + Integer.parseInt(value));
                break;
            case '\'':
                if (VibRune.SUPPORT_SYNC_WITH_HAPTIC) {
                    Settings.System.putInt(contentResolver, "sync_vibration_with_notification", Integer.parseInt(value));
                    Log.d("SoundItem", "restore sync with notification value=" + Integer.parseInt(value));
                    break;
                }
                break;
            case '(':
                setStreamVolume(context, 11, Integer.parseInt(value));
                break;
            case '*':
                setStreamVolume(context, 10, Integer.parseInt(value));
                break;
            case '+':
                setStreamVolume(context, 2, Integer.parseInt(value));
                break;
            case ',':
                Settings.System.putInt(contentResolver, "sip_key_feedback_vibration", Integer.parseInt(value));
                break;
        }
        return null;
    }
}
