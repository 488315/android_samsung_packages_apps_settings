package com.android.settings.development;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import androidx.fragment.app.FragmentManagerImpl;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DesktopModePreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener,
                PreferenceControllerMixin,
                RebootConfirmationDialogHost {
    static final int SETTING_VALUE_OFF = 0;
    static final int SETTING_VALUE_ON = 1;
    public final DevelopmentSettingsDashboardFragment mFragment;

    public DesktopModePreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        this.mFragment = developmentSettingsDashboardFragment;
    }

    public String getBuildType() {
        return Build.TYPE;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "force_desktop_mode_on_external_displays";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "force_desktop_mode_on_external_displays", 0);
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "force_desktop_mode_on_external_displays",
                booleanValue ? 1 : 0);
        if (!booleanValue) {
            return true;
        }
        FragmentManagerImpl supportFragmentManager =
                this.mFragment.getActivity().getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag("FreeformPrefRebootDlg") != null) {
            return true;
        }
        new RebootConfirmationDialogFragment(
                        R.string.reboot_dialog_force_desktop_mode,
                        R.string.reboot_dialog_reboot_later,
                        this)
                .show(supportFragmentManager, "FreeformPrefRebootDlg");
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        Settings.Global.getInt(
                                        this.mContext.getContentResolver(),
                                        "force_desktop_mode_on_external_displays",
                                        0)
                                != 0);
    }
}
