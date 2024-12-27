package com.android.settingslib.spaprivileged.framework.common;

import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ContextsKt {
    public static final Context asUser(Context context, UserHandle userHandle) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(userHandle, "userHandle");
        Context createContextAsUser = context.createContextAsUser(userHandle, 0);
        Intrinsics.checkNotNullExpressionValue(createContextAsUser, "createContextAsUser(...)");
        return createContextAsUser;
    }

    public static final AppOpsManager getAppOpsManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Object systemService = context.getSystemService((Class<Object>) AppOpsManager.class);
        Intrinsics.checkNotNull(systemService);
        return (AppOpsManager) systemService;
    }

    public static final DevicePolicyManager getDevicePolicyManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Object systemService = context.getSystemService((Class<Object>) DevicePolicyManager.class);
        Intrinsics.checkNotNull(systemService);
        return (DevicePolicyManager) systemService;
    }

    public static final UserManager getUserManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Object systemService = context.getSystemService((Class<Object>) UserManager.class);
        Intrinsics.checkNotNull(systemService);
        return (UserManager) systemService;
    }
}
