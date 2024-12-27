package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController;
import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundVibrationChargingFeedbackController
        extends SecSoundTogglePreferenceController {
    private SecSwitchPreference mPreference;

    public SecSoundVibrationChargingFeedbackController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secSwitchPreference;
        secSwitchPreference.setTitle(this.mContext.getString(R.string.sec_charging_sounds_title));
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (VibRune.SUPPORT_CHARGING_SYSTEM_VIBRATION
                        && VibUtils.hasSystemVibrationMenu(this.mContext))
                ? 0
                : 3;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController
    public Uri getDatabaseUri() {
        return Settings.Secure.getUriFor("charging_vibration_enabled");
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_sound;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        return TextUtils.equals(preference.getKey(), getPreferenceKey());
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "charging_vibration_enabled", 1)
                == 1;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        LoggingHelper.insertEventLogging(FileType.SDOCX, 37003, z);
        return Settings.Secure.putInt(
                this.mContext.getContentResolver(), "charging_vibration_enabled", z ? 1 : 0);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreference.setEnabled(
                !Rune.isSamsungDexMode(this.mContext)
                        || Utils.isDesktopStandaloneMode(this.mContext));
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
