package com.android.settingslib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.UserHandle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TetherUtil {
    public static boolean isTetherAvailable(Context context) {
        return (((ConnectivityManager) context.getSystemService(ConnectivityManager.class))
                                .isTetheringSupported()
                        || (RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                        context, UserHandle.myUserId(), "no_config_tethering")
                                != null))
                && !RestrictedLockUtilsInternal.hasBaseUserRestriction(
                        context, UserHandle.myUserId(), "no_config_tethering");
    }
}
