package com.android.settings.development;

import android.content.Context;
import android.os.UserManager;

import androidx.preference.Preference;

import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BugReportPreferenceController extends DeveloperOptionsPreferenceController
        implements PreferenceControllerMixin {
    public final Context mContext;
    public final UserManager mUserManager;

    public BugReportPreferenceController(Context context) {
        super(context);
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mContext = context;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bugreport";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !this.mUserManager.hasUserRestriction("no_debugging_features");
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsEnabled() {
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RestrictionPolicy2",
                        "isGoogleCrashReportedAllowed",
                        null);
        if (enterprisePolicyEnabled != -1) {
            this.mPreference.setEnabled(enterprisePolicyEnabled == 1);
        } else {
            super.onDeveloperOptionsEnabled();
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        int enterprisePolicyEnabled;
        if (isAvailable()) {
            int enterprisePolicyEnabled2 =
                    Utils.getEnterprisePolicyEnabled(
                            this.mContext,
                            "content://com.sec.knox.provider/RestrictionPolicy1",
                            "isDeveloperModeAllowed",
                            new String[] {"false"});
            if (enterprisePolicyEnabled2 != -1) {
                preference.setEnabled(enterprisePolicyEnabled2 == 1);
            }
            if (!preference.isEnabled()
                    || (enterprisePolicyEnabled =
                                    Utils.getEnterprisePolicyEnabled(
                                            this.mContext,
                                            "content://com.sec.knox.provider/RestrictionPolicy2",
                                            "isGoogleCrashReportedAllowed",
                                            null))
                            == -1) {
                return;
            }
            preference.setEnabled(enterprisePolicyEnabled == 1);
        }
    }
}
