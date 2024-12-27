package com.android.settings;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.samsung.android.audio.SoundTheme;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.asbase.utils.SimUtils;
import com.samsung.android.settings.asbase.utils.SoundUtils;
import com.sec.ims.im.ImIntent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class DefaultRingtonePreference extends RingtonePreference {
    public DefaultRingtonePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.settings.RingtonePreference
    public final boolean onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        return true;
    }

    @Override // com.android.settings.RingtonePreference
    public void onPrepareRingtonePickerIntent(Intent intent) {
        super.onPrepareRingtonePickerIntent(intent);
        intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", false);
        int i = this.mRingtoneType;
        if (i == 1 || i == 128) {
            if (!intent.hasExtra("neutral_button")) {
                intent.putExtra("neutral_button", AudioRune.SUPPORT_ADD_BUTTON);
            }
            intent.putExtra("neutral_button_text", getContext().getString(R.string.common_add));
            intent.putExtra(
                    "android.intent.extra.ringtone.TITLE",
                    getContext().getString(R.string.incoming_call_volume_title));
            if (SoundUtils.isCallBGEnabled(this.mUserContext)) {
                intent.putExtra(
                        "android.intent.extra.rintone.enabled_call_background",
                        SoundUtils.checkDialer(this.mUserContext));
            }
        } else if (i == 2 || i == 256) {
            intent.putExtra(
                    "android.intent.extra.ringtone.TITLE",
                    getContext().getString(R.string.sec_sound_notification));
        }
        boolean z = AudioRune.SUPPORT_SOUND_THEME;
        intent.putExtra("android.samsung.intent.extra.ringtone.SHOW_OPEN_THEME", true);
        if (AudioRune.SUPPORT_HAPTIC_PLAYBACK_WITH_RINGTONE) {
            intent.putExtra(
                    ImIntent.Extras.EXTRA_FROM, KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        }
        if (SimUtils.isMultiSimEnabled(getContext())) {
            intent.putExtra("android.samsung.intent.extra.ringtone.DUAL_SIM_MODE", true);
            int i2 = this.mRingtoneType != 2 ? 128 : 256;
            if (this.mSim2FirstInDualMode) {
                intent.putExtra(
                        "android.samsung.intent.extra.ringtone.SIM2_FIRST_DUAL_SIM_MODE", true);
            }
            intent.putExtra(
                    "android.samsung.intent.extra.ringtone.EXISTING_URI_2",
                    RingtoneManager.getActualDefaultRingtoneUri(getContext(), i2));
        }
        DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                "com.samsung.android.secsoundpicker",
                "com.samsung.android.secsoundpicker.SecSoundPickerActivity",
                intent);
    }

    @Override // com.android.settings.RingtonePreference
    public Uri onRestoreRingtone() {
        return RingtoneManager.getActualDefaultRingtoneUri(this.mUserContext, this.mRingtoneType);
    }

    @Override // com.android.settings.RingtonePreference
    public final void onSaveRingtone(Uri uri, int i) {
        if (uri == null) {
            RingtoneManager.setActualDefaultRingtoneUri(this.mUserContext, i, uri);
            return;
        }
        if (RingtoneManager.isDefault(uri)) {
            Log.d("DefaultRingtonePreference", "onSaveRingtone() : Not changed " + uri);
        } else {
            if (isValidRingtoneUri(uri)) {
                RingtoneManager.setActualDefaultRingtoneUri(this.mUserContext, i, uri);
                return;
            }
            Log.e(
                    "DefaultRingtonePreference",
                    "onSaveRingtone for URI:" + uri + " ignored: invalid ringtone Uri");
        }
    }

    @Override // com.android.settings.RingtonePreference
    public final void onSaveSystemSound(String str) {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        ContentResolver contentResolver = this.mUserContext.getContentResolver();
        Log.d("DefaultRingtonePreference", "onSaveSystemSound( " + str + " )");
        String string = Settings.System.getString(contentResolver, "system_sound");
        Settings.System.putString(contentResolver, "system_sound", str);
        if (!TextUtils.equals(str, "Open_theme")) {
            Settings.System.putString(contentResolver, "prev_system_sound", str);
        }
        if (TextUtils.equals(string, "Open_theme")) {
            if (SoundTheme.isSoundThemeCategory(str)) {
                String string2 = Settings.Global.getString(contentResolver, "theme_touch_sound");
                String string3 =
                        Settings.System.getString(contentResolver, "default_key_sound_path");
                String string4 =
                        Settings.System.getString(contentResolver, "backspace_key_sound_path");
                String string5 =
                        Settings.System.getString(contentResolver, "modifier_key_sound_path");
                if (TextUtils.equals(str, "Galaxy")) {
                    str6 = null;
                    str7 = null;
                    str8 = null;
                } else {
                    str8 =
                            ComposerKt$$ExternalSyntheticOutline0.m(
                                    "system/media/audio/ui/TW_SIP_", str, ".ogg");
                    str7 =
                            ComposerKt$$ExternalSyntheticOutline0.m(
                                    "system/media/audio/ui/S_SIP_Backspace_", str, ".ogg");
                    str6 =
                            ComposerKt$$ExternalSyntheticOutline0.m(
                                    "system/media/audio/ui/SIP_Modifier_", str, ".ogg");
                }
                Settings.Global.putString(contentResolver, "theme_touch_sound", null);
                Settings.System.putString(contentResolver, "default_key_sound_path", str8);
                Settings.System.putString(contentResolver, "backspace_key_sound_path", str7);
                Settings.System.putString(contentResolver, "modifier_key_sound_path", str6);
                Settings.Global.putString(contentResolver, "backup_theme_touch_sound", string2);
                if (string3 != null) {
                    str8 = string3;
                }
                Settings.System.putString(contentResolver, "backup_key_sound_path", str8);
                if (string4 != null) {
                    str7 = string4;
                }
                Settings.System.putString(contentResolver, "backup_backspace_key_sound_path", str7);
                if (string5 != null) {
                    str6 = string5;
                }
                Settings.System.putString(contentResolver, "backup_modifier_key_sound_path", str6);
                if (AudioRune.SUPPORT_SOUND_THEME_ONEUI31_EXTENSION) {
                    String m =
                            TextUtils.equals(str, "Galaxy")
                                    ? null
                                    : ComposerKt$$ExternalSyntheticOutline0.m(
                                            "system/media/audio/ui/Dial_", str, ".ogg");
                    String string6 =
                            Settings.System.getString(contentResolver, "dialer_sound_theme_path");
                    Settings.System.putString(contentResolver, "dialer_sound_theme_path", m);
                    if (string6 != null) {
                        m = string6;
                    }
                    Settings.System.putString(contentResolver, "backup_dialer_sound_theme_path", m);
                    return;
                }
                return;
            }
            return;
        }
        if (TextUtils.equals(str, "Open_theme")) {
            String string7 = Settings.Global.getString(contentResolver, "backup_theme_touch_sound");
            String string8 = Settings.System.getString(contentResolver, "backup_key_sound_path");
            String string9 =
                    Settings.System.getString(contentResolver, "backup_backspace_key_sound_path");
            String string10 =
                    Settings.System.getString(contentResolver, "backup_modifier_key_sound_path");
            Settings.Global.putString(contentResolver, "backup_theme_touch_sound", null);
            Settings.System.putString(contentResolver, "backup_key_sound_path", null);
            Settings.System.putString(contentResolver, "backup_backspace_key_sound_path", null);
            Settings.System.putString(contentResolver, "backup_modifier_key_sound_path", null);
            Settings.Global.putString(contentResolver, "theme_touch_sound", string7);
            if (AudioRune.SUPPORT_SOUND_THEME_ONEUI31_EXTENSION) {
                str3 = Settings.System.getString(contentResolver, "backup_dialer_sound_theme_path");
                Settings.System.putString(contentResolver, "backup_dialer_sound_theme_path", null);
                str2 = string8;
                str4 = string9;
                str5 = string10;
            } else {
                str4 = string9;
                str5 = string10;
                str3 = null;
                str2 = string8;
            }
        } else {
            if (TextUtils.equals(str, "Galaxy")) {
                str3 = null;
                str4 = null;
                str5 = null;
            } else {
                String m2 =
                        ComposerKt$$ExternalSyntheticOutline0.m(
                                "system/media/audio/ui/TW_SIP_", str, ".ogg");
                str4 =
                        ComposerKt$$ExternalSyntheticOutline0.m(
                                "system/media/audio/ui/S_SIP_Backspace_", str, ".ogg");
                str5 =
                        ComposerKt$$ExternalSyntheticOutline0.m(
                                "system/media/audio/ui/SIP_Modifier_", str, ".ogg");
                str3 =
                        AudioRune.SUPPORT_SOUND_THEME_ONEUI31_EXTENSION
                                ? ComposerKt$$ExternalSyntheticOutline0.m(
                                        "system/media/audio/ui/Dial_", str, ".ogg")
                                : null;
                str2 = m2;
            }
        }
        Settings.System.putString(contentResolver, "default_key_sound_path", str2);
        Settings.System.putString(contentResolver, "backspace_key_sound_path", str4);
        Settings.System.putString(contentResolver, "modifier_key_sound_path", str5);
        if (AudioRune.SUPPORT_SOUND_THEME_ONEUI31_EXTENSION) {
            Settings.System.putString(contentResolver, "dialer_sound_theme_path", str3);
        }
    }

    public void setActualDefaultRingtoneUri(Uri uri) {
        RingtoneManager.setActualDefaultRingtoneUri(this.mUserContext, this.mRingtoneType, uri);
    }

    @Override // com.android.settings.RingtonePreference
    public final void onSaveRingtone(Uri uri) {
        if (uri == null) {
            setActualDefaultRingtoneUri(uri);
            return;
        }
        if (RingtoneManager.isDefault(uri)) {
            Log.d("DefaultRingtonePreference", "onSaveRingtone() : Not changed " + uri);
        } else {
            if (!isValidRingtoneUri(uri)) {
                Log.e(
                        "DefaultRingtonePreference",
                        "onSaveRingtone for URI:" + uri + " ignored: invalid ringtone Uri");
                return;
            }
            setActualDefaultRingtoneUri(uri);
        }
    }
}
