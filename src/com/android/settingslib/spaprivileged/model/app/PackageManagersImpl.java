package com.android.settingslib.spaprivileged.model.app;

import android.app.AppGlobals;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PackageManagersImpl implements IPackageManagers {
    public final Lazy iPackageManager$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settingslib.spaprivileged.model.app.PackageManagersImpl$iPackageManager$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return AppGlobals.getPackageManager();
                        }
                    });

    /* JADX WARN: Removed duplicated region for block: B:13:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0093 A[LOOP:1: B:22:0x008d->B:24:0x0093, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getAppOpPermissionPackages(
            int r5, java.lang.String r6, kotlin.coroutines.Continuation r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof com.android.settingslib.spaprivileged.model.app.PackageManagersImpl$getAppOpPermissionPackages$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.settingslib.spaprivileged.model.app.PackageManagersImpl$getAppOpPermissionPackages$1 r0 = (com.android.settingslib.spaprivileged.model.app.PackageManagersImpl$getAppOpPermissionPackages$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settingslib.spaprivileged.model.app.PackageManagersImpl$getAppOpPermissionPackages$1 r0 = new com.android.settingslib.spaprivileged.model.app.PackageManagersImpl$getAppOpPermissionPackages$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r7)
            goto L56
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.Lazy r7 = r4.iPackageManager$delegate
            java.lang.Object r7 = r7.getValue()
            android.content.pm.IPackageManager r7 = (android.content.pm.IPackageManager) r7
            java.lang.String[] r6 = r7.getAppOpPermissionPackages(r6, r5)
            java.lang.String r7 = "getAppOpPermissionPackages(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r7)
            java.lang.Iterable r6 = kotlin.collections.ArraysKt___ArraysKt.asIterable(r6)
            com.android.settingslib.spaprivileged.model.app.PackageManagersImpl$getAppOpPermissionPackages$$inlined$asyncFilter$1 r7 = new com.android.settingslib.spaprivileged.model.app.PackageManagersImpl$getAppOpPermissionPackages$$inlined$asyncFilter$1
            r2 = 0
            r7.<init>(r6, r2, r4, r5)
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r7, r0)
            if (r7 != r1) goto L56
            return r1
        L56:
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Iterator r5 = r7.iterator()
        L61:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L7e
            java.lang.Object r6 = r5.next()
            r7 = r6
            kotlin.Pair r7 = (kotlin.Pair) r7
            java.lang.Object r7 = r7.getSecond()
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L61
            r4.add(r6)
            goto L61
        L7e:
            java.util.ArrayList r5 = new java.util.ArrayList
            r6 = 10
            int r6 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r4, r6)
            r5.<init>(r6)
            java.util.Iterator r4 = r4.iterator()
        L8d:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto La1
            java.lang.Object r6 = r4.next()
            kotlin.Pair r6 = (kotlin.Pair) r6
            java.lang.Object r6 = r6.getFirst()
            r5.add(r6)
            goto L8d
        La1:
            java.util.Set r4 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r5)
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spaprivileged.model.app.PackageManagersImpl.getAppOpPermissionPackages(int,"
                    + " java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    public final PackageInfo getPackageInfoAsUser(long j, int i, String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return PackageManager.getPackageInfoAsUserCached(packageName, j, i);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    public final boolean hasGrantPermission(ApplicationInfo applicationInfo, String permission) {
        String[] strArr;
        int indexOf;
        Intrinsics.checkNotNullParameter(applicationInfo, "<this>");
        Intrinsics.checkNotNullParameter(permission, "permission");
        String packageName = applicationInfo.packageName;
        Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
        PackageInfo packageInfoAsUserCached =
                PackageManager.getPackageInfoAsUserCached(
                        packageName, 4096L, ApplicationInfosKt.getUserId(applicationInfo));
        if (packageInfoAsUserCached == null
                || (strArr = packageInfoAsUserCached.requestedPermissions) == null
                || (indexOf = ArraysKt___ArraysKt.indexOf(permission, strArr)) < 0) {
            return false;
        }
        int[] iArr = packageInfoAsUserCached.requestedPermissionsFlags;
        if (iArr != null) {
            return (iArr[indexOf] & 2) > 0;
        }
        throw new IllegalStateException("Required value was null.".toString());
    }

    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    public final boolean hasRequestPermission(ApplicationInfo applicationInfo, String permission) {
        String[] strArr;
        Intrinsics.checkNotNullParameter(applicationInfo, "<this>");
        Intrinsics.checkNotNullParameter(permission, "permission");
        String packageName = applicationInfo.packageName;
        Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
        PackageInfo packageInfoAsUserCached =
                PackageManager.getPackageInfoAsUserCached(
                        packageName, 4096L, ApplicationInfosKt.getUserId(applicationInfo));
        if (packageInfoAsUserCached == null
                || (strArr = packageInfoAsUserCached.requestedPermissions) == null) {
            return false;
        }
        return ArraysKt___ArraysKt.contains(permission, strArr);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    public final boolean isPackageInstalledAsUser(int i, String str) {
        ApplicationInfo applicationInfoAsUserCached =
                PackageManager.getApplicationInfoAsUserCached(str, 0L, i);
        if (applicationInfoAsUserCached != null) {
            return ApplicationInfosKt.hasFlag(8388608, applicationInfoAsUserCached);
        }
        return false;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.IPackageManagers
    public final PackageInfo getPackageInfoAsUser(int i, String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return getPackageInfoAsUser(0L, i, packageName);
    }
}
