package com.android.settingslib.spaprivileged.model.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import kotlin.coroutines.Continuation;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface IPackageManagers {
    Object getAppOpPermissionPackages(int i, String str, Continuation continuation);

    PackageInfo getPackageInfoAsUser(int i, String str);

    PackageInfo getPackageInfoAsUser(long j, int i, String str);

    boolean hasGrantPermission(ApplicationInfo applicationInfo, String str);

    boolean hasRequestPermission(ApplicationInfo applicationInfo, String str);

    boolean isPackageInstalledAsUser(int i, String str);
}
