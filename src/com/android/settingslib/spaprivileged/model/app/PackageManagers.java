package com.android.settingslib.spaprivileged.model.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PackageManagers implements IPackageManagers {
    public static final PackageManagers INSTANCE = new PackageManagers();
    public final /* synthetic */ PackageManagersImpl $$delegate_0 = new PackageManagersImpl();

    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    public final Object getAppOpPermissionPackages(int i, String str, Continuation continuation) {
        return this.$$delegate_0.getAppOpPermissionPackages(i, str, continuation);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    public final PackageInfo getPackageInfoAsUser(int i, String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return this.$$delegate_0.getPackageInfoAsUser(i, packageName);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    public final boolean hasGrantPermission(ApplicationInfo applicationInfo, String permission) {
        Intrinsics.checkNotNullParameter(applicationInfo, "<this>");
        Intrinsics.checkNotNullParameter(permission, "permission");
        return this.$$delegate_0.hasGrantPermission(applicationInfo, permission);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    public final boolean hasRequestPermission(ApplicationInfo applicationInfo, String permission) {
        Intrinsics.checkNotNullParameter(applicationInfo, "<this>");
        Intrinsics.checkNotNullParameter(permission, "permission");
        return this.$$delegate_0.hasRequestPermission(applicationInfo, permission);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    public final boolean isPackageInstalledAsUser(int i, String str) {
        return this.$$delegate_0.isPackageInstalledAsUser(i, str);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    public final PackageInfo getPackageInfoAsUser(long j, int i, String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        this.$$delegate_0.getClass();
        return PackageManager.getPackageInfoAsUserCached(packageName, j, i);
    }
}
