package com.android.settingslib.spaprivileged.model.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PackageManagerExtKt {
    public static final ActivityInfo resolveActionForApp(
            PackageManager packageManager, ApplicationInfo app, String str, int i) {
        Intrinsics.checkNotNullParameter(packageManager, "<this>");
        Intrinsics.checkNotNullParameter(app, "app");
        Intent intent = new Intent(str);
        intent.setPackage(app.packageName);
        ResolveInfo resolveActivityAsUser =
                packageManager.resolveActivityAsUser(
                        intent,
                        PackageManager.ResolveInfoFlags.of(i),
                        ApplicationInfosKt.getUserId(app));
        if (resolveActivityAsUser != null) {
            return resolveActivityAsUser.activityInfo;
        }
        return null;
    }
}
