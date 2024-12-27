package com.android.settings.network;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.settingslib.RestrictedLockUtilsInternal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkResetRestrictionChecker {
    public final Context mContext;
    public final UserManager mUserManager;

    public NetworkResetRestrictionChecker(Context context) {
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService("user");
    }

    public boolean hasUserBaseRestriction() {
        return RestrictedLockUtilsInternal.hasBaseUserRestriction(
                this.mContext, UserHandle.myUserId(), "no_network_reset");
    }

    public boolean isRestrictionEnforcedByAdmin() {
        return RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, UserHandle.myUserId(), "no_network_reset")
                != null;
    }
}
