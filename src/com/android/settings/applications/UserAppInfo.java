package com.android.settings.applications;

import android.content.pm.ApplicationInfo;
import android.content.pm.UserInfo;
import android.text.TextUtils;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserAppInfo {
    public final ApplicationInfo appInfo;
    public final UserInfo userInfo;

    public UserAppInfo(UserInfo userInfo, ApplicationInfo applicationInfo) {
        this.userInfo = userInfo;
        this.appInfo = applicationInfo;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || UserAppInfo.class != obj.getClass()) {
            return false;
        }
        UserAppInfo userAppInfo = (UserAppInfo) obj;
        return userAppInfo.userInfo.id == this.userInfo.id
                && TextUtils.equals(userAppInfo.appInfo.packageName, this.appInfo.packageName);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.userInfo.id), this.appInfo.packageName);
    }
}
