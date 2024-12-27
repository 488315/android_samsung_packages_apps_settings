package com.android.settingslib.spaprivileged.model.app;

import android.content.pm.ApplicationInfo;
import android.os.UserHandle;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ApplicationInfosKt {
    public static final boolean getInstalled(ApplicationInfo applicationInfo) {
        Intrinsics.checkNotNullParameter(applicationInfo, "<this>");
        return hasFlag(8388608, applicationInfo);
    }

    public static final UserHandle getUserHandle(ApplicationInfo applicationInfo) {
        Intrinsics.checkNotNullParameter(applicationInfo, "<this>");
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(applicationInfo.uid);
        Intrinsics.checkNotNullExpressionValue(userHandleForUid, "getUserHandleForUid(...)");
        return userHandleForUid;
    }

    public static final int getUserId(ApplicationInfo applicationInfo) {
        Intrinsics.checkNotNullParameter(applicationInfo, "<this>");
        return UserHandle.getUserId(applicationInfo.uid);
    }

    public static final boolean hasFlag(int i, ApplicationInfo applicationInfo) {
        Intrinsics.checkNotNullParameter(applicationInfo, "<this>");
        return (i & applicationInfo.flags) > 0;
    }

    public static final String toRoute(ApplicationInfo applicationInfo) {
        Intrinsics.checkNotNullParameter(applicationInfo, "<this>");
        return applicationInfo.packageName + "/" + getUserId(applicationInfo);
    }
}
