package com.samsung.android.settings.asbase.audio;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.media.SemSoundAssistantManager;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundVoipExtraVolumeController extends TogglePreferenceController
        implements LifecycleObserver, OnResume {
    private static final String KEY_VOIP_EXTRA_VOLUME_CONTROL = "voip_extra_volume_control";
    private Preference mPreference;
    private final SemSoundAssistantManager mSemSoundAssistantManager;

    public SecSoundVoipExtraVolumeController(Context context, String str) {
        super(context, str);
        this.mSemSoundAssistantManager = new SemSoundAssistantManager(this.mContext);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(KEY_VOIP_EXTRA_VOLUME_CONTROL);
        if (findPreference != null) {
            this.mPreference = findPreference;
        }
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        if (secSwitchPreference != null) {
            secSwitchPreference.mDescription =
                    this.mContext
                            .getResources()
                            .getString(R.string.sec_voip_extra_volume_control_title);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return AudioRune.SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER ? 0 : 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_VOIP_EXTRA_VOLUME_CONTROL;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_sound;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        LoggingHelper.insertEventLogging(FileType.SDOCX, Preference.DEFAULT_ORDER);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "voip_extra_volume", 0)
                == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        updateState(this.mPreference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (this.mSemSoundAssistantManager == null) {
            return false;
        }
        LoggingHelper.insertEventLogging(FileType.SDOCX, Preference.DEFAULT_ORDER, z);
        this.mSemSoundAssistantManager.setVoipExtraVolumeMode(z);
        return Settings.System.putInt(
                this.mContext.getContentResolver(), "voip_extra_volume", z ? 1 : 0);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (KnoxUtils.isApplicationRestricted(this.mContext, preference.getKey(), "grayout")) {
            preference.setEnabled(false);
        } else {
            preference.setEnabled(true);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
