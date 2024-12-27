package com.android.settings.development;

import android.content.pm.PackageManager;
import android.os.UserManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocalTerminalPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String TERMINAL_APP_PACKAGE = "com.android.terminal";
    public PackageManager mPackageManager;
    public UserManager mUserManager;

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPackageManager = getPackageManager();
        if (!isAvailable() || this.mUserManager.isAdminUser()) {
            return;
        }
        this.mPreference.setEnabled(false);
    }

    public PackageManager getPackageManager() {
        return this.mContext.getPackageManager();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "enable_terminal";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        try {
            return this.mContext.getPackageManager().getPackageInfo(TERMINAL_APP_PACKAGE, 0)
                    != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        this.mPackageManager.setApplicationEnabledSetting(TERMINAL_APP_PACKAGE, 0, 0);
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        if (this.mUserManager.isAdminUser()) {
            this.mPreference.setEnabled(true);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        this.mPackageManager.setApplicationEnabledSetting(
                TERMINAL_APP_PACKAGE, ((Boolean) obj).booleanValue() ? 1 : 0, 0);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        this.mPackageManager.getApplicationEnabledSetting(TERMINAL_APP_PACKAGE)
                                == 1);
    }
}
