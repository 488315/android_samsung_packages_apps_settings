package com.android.settings.development;

import android.app.ActivityManager;
import android.content.Context;
import android.debug.IAdbManager;
import android.os.ServiceManager;
import android.os.UserManager;
import android.sysprop.AdbProperties;
import android.text.TextUtils;

import androidx.fragment.app.FragmentManagerImpl;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ClearAdbKeysPreferenceController extends DeveloperOptionsPreferenceController
        implements PreferenceControllerMixin {
    public final IAdbManager mAdbManager;
    public final DevelopmentSettingsDashboardFragment mFragment;

    public ClearAdbKeysPreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        this.mFragment = developmentSettingsDashboardFragment;
        this.mAdbManager = IAdbManager.Stub.asInterface(ServiceManager.getService("adb"));
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (this.mPreference == null || isAdminUser()) {
            return;
        }
        this.mPreference.setEnabled(false);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "clear_adb_keys";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        StringBuilder sb = Utils.sBuilder;
        if (ActivityManager.isUserAMonkey()
                || !TextUtils.equals(preference.getKey(), "clear_adb_keys")) {
            return false;
        }
        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment = this.mFragment;
        FragmentManagerImpl supportFragmentManager =
                developmentSettingsDashboardFragment.getActivity().getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag("ClearAdbKeysDlg") != null) {
            return true;
        }
        ClearAdbKeysWarningDialog clearAdbKeysWarningDialog = new ClearAdbKeysWarningDialog();
        clearAdbKeysWarningDialog.setTargetFragment(developmentSettingsDashboardFragment, 0);
        clearAdbKeysWarningDialog.show(supportFragmentManager, "ClearAdbKeysDlg");
        return true;
    }

    public boolean isAdminUser() {
        return ((UserManager) this.mContext.getSystemService("user")).isAdminUser();
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return ((Boolean) AdbProperties.secure().orElse(Boolean.FALSE)).booleanValue();
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        if (isAdminUser()) {
            this.mPreference.setEnabled(true);
        }
    }
}
