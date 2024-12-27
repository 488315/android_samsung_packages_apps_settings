package com.samsung.android.settings.asbase.audio;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.DefaultRingtonePreference;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.development.OnActivityResultListener;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.audio.SoundTheme;
import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.asbase.utils.SoundUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundSystemSoundController extends SecSoundSettingPrefController
        implements OnActivityResultListener {
    private static final String KEY = "system_sound";
    private static final int REQUEST_AUDIO_SYSTEM_SOUND = 5;
    protected static final String TAG = "SecSoundSystemSoundController";
    private SettingsPreferenceFragment mFragment;
    private DefaultRingtonePreference mPreference;

    public SecSoundSystemSoundController(Context context, String str) {
        super(context, KEY);
    }

    private void onSaveSystemSound(String str) {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Log.d(TAG, "onSaveSystemSound( " + str + " )");
        String string = Settings.System.getString(contentResolver, KEY);
        Settings.System.putString(contentResolver, KEY, str);
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

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        DefaultRingtonePreference defaultRingtonePreference =
                (DefaultRingtonePreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = defaultRingtonePreference;
        if (defaultRingtonePreference != null) {
            defaultRingtonePreference.mRingtoneType = 512;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!AudioRune.SUPPORT_SOUND_THEME || AudioRune.SUPPORT_HIDE_SYSTEM_SOUND_SETTING)
                ? 3
                : 0;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        DefaultRingtonePreference defaultRingtonePreference = this.mPreference;
        if (defaultRingtonePreference != null) {
            return defaultRingtonePreference.getSummary();
        }
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        String string = Settings.System.getString(this.mContext.getContentResolver(), KEY);
        if (TextUtils.isEmpty(string)) {
            string = "Galaxy";
        }
        String currentThemeTitle = SoundTheme.getCurrentThemeTitle(this.mContext, string);
        if (TextUtils.equals(string, "Open_theme")) {
            currentThemeTitle = this.mContext.getString(17042764);
        }
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(string);
        builder.mSummary = currentThemeTitle;
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        return builder.build();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        SALogging.insertSALog(String.valueOf(10210));
        try {
            DefaultRingtonePreference defaultRingtonePreference = this.mPreference;
            defaultRingtonePreference.onPrepareRingtonePickerIntent(
                    defaultRingtonePreference.getIntent());
            this.mFragment.startActivityForResult(this.mPreference.getIntent(), 5);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "handlePreferenceTreeClick", e);
            SoundUtils.showToEnableSecSoundPickerDialog(this.mContext);
            return true;
        }
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.development.OnActivityResultListener
    public boolean onActivityResult(int i, int i2, Intent intent) {
        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                "onActivityResult: requestCode : ", ", resultCode : ", i, i2, TAG);
        if (i != 5 || (i2 != -1 && i2 != 1)) {
            return false;
        }
        DefaultRingtonePreference defaultRingtonePreference = this.mPreference;
        if (defaultRingtonePreference != null) {
            defaultRingtonePreference.onActivityResult(i, i2, intent);
        }
        if (i2 == -1
                && intent != null
                && intent.getDataString() != null
                && intent.getDataString() != null) {
            setUpdateRingtoneType(7);
            updateSystemSoundNameAsync();
        }
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        setUpdateRingtoneType(7);
        super.onResume();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        ControlResult.ResultCode resultCode;
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        String value = controlValue.getValue();
        if (TextUtils.isEmpty(value)) {
            resultCode = ControlResult.ResultCode.FAIL;
        } else {
            onSaveSystemSound(value);
            resultCode = ControlResult.ResultCode.SUCCESS;
        }
        builder.mResultCode = resultCode;
        return builder.build();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        DefaultRingtonePreference defaultRingtonePreference = this.mPreference;
        if (defaultRingtonePreference != null) {
            defaultRingtonePreference.getClass();
            SecPreferenceUtils.applySummaryColor(defaultRingtonePreference, true);
        }
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SecSoundSystemSoundController(
            Context context,
            SettingsPreferenceFragment settingsPreferenceFragment,
            Lifecycle lifecycle) {
        super(context, lifecycle, KEY);
        this.mFragment = settingsPreferenceFragment;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {}
}
