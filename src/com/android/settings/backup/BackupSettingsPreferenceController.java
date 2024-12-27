package com.android.settings.backup;

import android.content.Intent;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackupSettingsPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public Intent mBackupSettingsIntent;
    public String mBackupSettingsSummary;
    public CharSequence mBackupSettingsTitle;
    public Intent mManufacturerIntent;
    public String mManufacturerLabel;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        Preference findPreference = preferenceScreen.findPreference("backup_settings");
        Preference findPreference2 = preferenceScreen.findPreference("manufacturer_backup");
        findPreference.setIntent(this.mBackupSettingsIntent);
        findPreference.setTitle(this.mBackupSettingsTitle);
        findPreference.setSummary(this.mBackupSettingsSummary);
        findPreference2.setIntent(this.mManufacturerIntent);
        findPreference2.setTitle(this.mManufacturerLabel);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }
}
