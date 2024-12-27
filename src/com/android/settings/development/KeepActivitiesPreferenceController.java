package com.android.settings.development;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.os.RemoteException;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class KeepActivitiesPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final int SETTING_VALUE_OFF = 0;
    public IActivityManager mActivityManager;

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mActivityManager = getActivityManager();
    }

    public IActivityManager getActivityManager() {
        return ActivityManager.getService();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "immediately_destroy_activities";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        try {
            this.mActivityManager.setAlwaysFinish(false);
        } catch (RemoteException unused) {
        }
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        try {
            this.mActivityManager.setAlwaysFinish(((Boolean) obj).booleanValue());
            return true;
        } catch (RemoteException unused) {
            return true;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        int i =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(), "always_finish_activities", 0);
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RestrictionPolicy2",
                        "isKillingActivitiesOnLeaveAllowed",
                        null);
        if (enterprisePolicyEnabled != -1) {
            preference.setEnabled(enterprisePolicyEnabled == 1);
        }
        ((TwoStatePreference) this.mPreference).setChecked(i != 0);
    }
}
