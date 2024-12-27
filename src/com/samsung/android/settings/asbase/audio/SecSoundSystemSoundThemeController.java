package com.samsung.android.settings.asbase.audio;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.DefaultRingtonePreference;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.development.OnActivityResultListener;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.asbase.utils.SoundUtils;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundSystemSoundThemeController extends SecSoundSettingPrefController
        implements OnActivityResultListener {
    private static final String KEY = "system_sound_theme";
    private static final int REQUEST_AUDIO_SYSTEM_SOUND = 5;
    private SettingsPreferenceFragment mFragment;
    private DefaultRingtonePreference mPreference;

    public SecSoundSystemSoundThemeController(
            Context context,
            SettingsPreferenceFragment settingsPreferenceFragment,
            Lifecycle lifecycle) {
        super(context, lifecycle, KEY);
        this.mFragment = settingsPreferenceFragment;
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
        return (!AudioRune.SUPPORT_SOUND_THEME
                        || AudioRune.SUPPORT_HIDE_SYSTEM_SOUND_SETTING
                        || VibUtils.hasSystemVibrationMenu(this.mContext))
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

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        LoggingHelper.insertEventLogging(FileType.SDOCX, 10210);
        try {
            DefaultRingtonePreference defaultRingtonePreference = this.mPreference;
            defaultRingtonePreference.onPrepareRingtonePickerIntent(
                    defaultRingtonePreference.getIntent());
            this.mFragment.startActivityForResult(this.mPreference.getIntent(), 5);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.e("SoundSettings", "handlePreferenceTreeClick", e);
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
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
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
                "onActivityResult: requestCode : ", ", resultCode : ", i, i2, "SoundSettings");
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

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {}
}
