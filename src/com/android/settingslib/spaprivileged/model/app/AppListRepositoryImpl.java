package com.android.settingslib.spaprivileged.model.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.UserManager;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlagsImpl;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppListRepositoryImpl implements AppListRepository {
    public final Context context;
    public final FeatureFlags featureFlags;
    public final PackageManager packageManager;
    public final UserManager userManager;

    public AppListRepositoryImpl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        FeatureFlagsImpl featureFlagsImpl = new FeatureFlagsImpl();
        this.context = context;
        this.featureFlags = featureFlagsImpl;
        this.packageManager = context.getPackageManager();
        this.userManager = ContextsKt.getUserManager(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$showSystemPredicate(final com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl r4, int r5, boolean r6, kotlin.coroutines.Continuation r7) {
        /*
            r4.getClass()
            boolean r0 = r7 instanceof com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$2
            if (r0 == 0) goto L16
            r0 = r7
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$2 r0 = (com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$2 r0 = new com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$2
            r0.<init>(r4, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r4 = r0.L$0
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl r4 = (com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl) r4
            kotlin.ResultKt.throwOnFailure(r7)
            goto L49
        L2e:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L36:
            kotlin.ResultKt.throwOnFailure(r7)
            if (r6 == 0) goto L3e
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$3 r1 = new kotlin.jvm.functions.Function1() { // from class: com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$3
                static {
                    /*
                        com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$3 r0 = new com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$3
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$3) com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$3.INSTANCE com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$3
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$3.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$3.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        android.content.pm.ApplicationInfo r1 = (android.content.pm.ApplicationInfo) r1
                        java.lang.String r0 = "it"
                        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r0)
                        java.lang.Boolean r0 = java.lang.Boolean.TRUE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$3.invoke(java.lang.Object):java.lang.Object");
                }
            }
            goto L50
        L3e:
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r7 = r4.loadHomeOrLauncherPackages(r5, r0)
            if (r7 != r1) goto L49
            goto L50
        L49:
            java.util.Set r7 = (java.util.Set) r7
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$4 r1 = new com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$showSystemPredicate$4
            r1.<init>()
        L50:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl.access$showSystemPredicate(com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl, int, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object loadAndFilterApps(int i, boolean z, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new AppListRepositoryImpl$loadAndFilterApps$2(this, i, z, null), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadApps(int r11, boolean r12, boolean r13, kotlin.coroutines.Continuation r14) {
        /*
            r10 = this;
            boolean r0 = r14 instanceof com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$loadApps$1
            if (r0 == 0) goto L13
            r0 = r14
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$loadApps$1 r0 = (com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$loadApps$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$loadApps$1 r0 = new com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$loadApps$1
            r0.<init>(r10, r14)
        L18:
            java.lang.Object r14 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r14)     // Catch: java.lang.Exception -> L27
            goto L48
        L27:
            r10 = move-exception
            goto L4b
        L29:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L31:
            kotlin.ResultKt.throwOnFailure(r14)
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$loadApps$2 r14 = new com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$loadApps$2     // Catch: java.lang.Exception -> L27
            r9 = 0
            r4 = r14
            r5 = r10
            r6 = r11
            r7 = r13
            r8 = r12
            r4.<init>(r5, r6, r7, r8, r9)     // Catch: java.lang.Exception -> L27
            r0.label = r3     // Catch: java.lang.Exception -> L27
            java.lang.Object r14 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r14, r0)     // Catch: java.lang.Exception -> L27
            if (r14 != r1) goto L48
            return r1
        L48:
            java.util.List r14 = (java.util.List) r14     // Catch: java.lang.Exception -> L27
            goto L54
        L4b:
            java.lang.String r11 = "AppListRepository"
            java.lang.String r12 = "loadApps failed"
            android.util.Log.e(r11, r12, r10)
            kotlin.collections.EmptyList r14 = kotlin.collections.EmptyList.INSTANCE
        L54:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl.loadApps(int, boolean, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object loadHomeOrLauncherPackages(int i, Continuation continuation) {
        Intent addCategory = new Intent("android.intent.action.MAIN", (Uri) null).addCategory("android.intent.category.LAUNCHER");
        Intrinsics.checkNotNullExpressionValue(addCategory, "addCategory(...)");
        PackageManager.ResolveInfoFlags of = PackageManager.ResolveInfoFlags.of(786944L);
        Intrinsics.checkNotNullExpressionValue(of, "of(...)");
        return CoroutineScopeKt.coroutineScope(new AppListRepositoryImpl$loadHomeOrLauncherPackages$2(this, addCategory, of, i, null), continuation);
    }
}
