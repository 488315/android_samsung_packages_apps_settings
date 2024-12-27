package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserManager;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserIdsSeries {
    public final int mCurrentUserId;
    public final UserInfo mManagedProfileUser;
    public final UserInfo mPrivateUser;
    public final UserManager mUserManager;
    public final List mVisibleUserIds = new ArrayList();

    public UserIdsSeries(Context context) {
        this.mPrivateUser = null;
        this.mManagedProfileUser = null;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        this.mUserManager = userManager;
        this.mCurrentUserId = context.getUserId();
        for (UserInfo userInfo :
                userManager != null ? userManager.getAliveUsers() : new ArrayList()) {
            if (this.mUserManager.isSameProfileGroup(this.mCurrentUserId, userInfo.id)) {
                if (userInfo.isManagedProfile()) {
                    this.mManagedProfileUser = userInfo;
                    ((ArrayList) this.mVisibleUserIds).add(Integer.valueOf(userInfo.id));
                } else if (userInfo.isPrivateProfile()) {
                    this.mPrivateUser = userInfo;
                    if (!userInfo.isQuietModeEnabled()) {
                        ((ArrayList) this.mVisibleUserIds).add(Integer.valueOf(userInfo.id));
                    }
                } else if (!userInfo.isQuietModeEnabled()) {
                    ((ArrayList) this.mVisibleUserIds).add(Integer.valueOf(userInfo.id));
                }
            }
        }
    }
}
