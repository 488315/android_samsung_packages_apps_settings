package com.android.settings.spa.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.UserHandle;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppUtilKt {
    public static final void startUninstallActivity(
            Context context, String packageName, UserHandle userHandle, boolean z) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(userHandle, "userHandle");
        Intent intent =
                new Intent(
                        "android.intent.action.UNINSTALL_PACKAGE",
                        Uri.parse("package:".concat(packageName)));
        intent.putExtra("android.intent.extra.UNINSTALL_ALL_USERS", z);
        context.startActivityAsUser(intent, userHandle);
    }
}
