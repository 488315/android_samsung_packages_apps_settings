package com.android.settings.accounts;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AccountRestrictionHelper {
    public final Context mContext;

    public AccountRestrictionHelper(Context context) {
        this.mContext = context;
    }

    public final void enforceRestrictionOnPreference(
            RestrictedPreference restrictedPreference, String str, int i) {
        int i2;
        if (restrictedPreference == null) {
            return;
        }
        if (!RestrictedLockUtilsInternal.hasBaseUserRestriction(this.mContext, i, str)) {
            restrictedPreference.mHelper.checkRestrictionAndSetDisabled(i, str);
            return;
        }
        if (str.equals("no_remove_managed_profile")) {
            DevicePolicyManager devicePolicyManager =
                    (DevicePolicyManager) this.mContext.getSystemService("device_policy");
            if (devicePolicyManager == null
                    ? false
                    : devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()) {
                DevicePolicyManager devicePolicyManager2 =
                        (DevicePolicyManager) this.mContext.getSystemService("device_policy");
                RestrictedLockUtils.EnforcedAdmin enforcedAdmin = null;
                if (devicePolicyManager2 != null) {
                    Iterator it = UserManager.get(this.mContext).getProfiles(i).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            i2 = -1;
                            break;
                        }
                        UserInfo userInfo = (UserInfo) it.next();
                        if (userInfo.id != i && userInfo.isManagedProfile()) {
                            i2 = userInfo.id;
                            break;
                        }
                    }
                    ComponentName profileOwnerAsUser =
                            devicePolicyManager2.getProfileOwnerAsUser(i2);
                    if (profileOwnerAsUser != null) {
                        enforcedAdmin =
                                new RestrictedLockUtils.EnforcedAdmin(
                                        profileOwnerAsUser, str, UserHandle.of(i2));
                    }
                }
                restrictedPreference.setDisabledByAdmin(enforcedAdmin);
                return;
            }
        }
        restrictedPreference.setEnabled(false);
    }
}
