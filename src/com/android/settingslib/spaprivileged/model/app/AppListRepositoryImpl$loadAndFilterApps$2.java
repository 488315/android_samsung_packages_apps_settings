package com.android.settingslib.spaprivileged.model.app;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Landroid/content/pm/ApplicationInfo;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppListRepositoryImpl$loadAndFilterApps$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $isSystemApp;
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AppListRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListRepositoryImpl$loadAndFilterApps$2(
            AppListRepositoryImpl appListRepositoryImpl,
            int i,
            boolean z,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = appListRepositoryImpl;
        this.$userId = i;
        this.$isSystemApp = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppListRepositoryImpl$loadAndFilterApps$2 appListRepositoryImpl$loadAndFilterApps$2 =
                new AppListRepositoryImpl$loadAndFilterApps$2(
                        this.this$0, this.$userId, this.$isSystemApp, continuation);
        appListRepositoryImpl$loadAndFilterApps$2.L$0 = obj;
        return appListRepositoryImpl$loadAndFilterApps$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppListRepositoryImpl$loadAndFilterApps$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x006c  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 1
            r3 = 2
            if (r1 == 0) goto L24
            if (r1 == r2) goto L1c
            if (r1 != r3) goto L14
            java.lang.Object r0 = r7.L$0
            java.util.Set r0 = (java.util.Set) r0
            kotlin.ResultKt.throwOnFailure(r8)
            goto L57
        L14:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1c:
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.Deferred r1 = (kotlinx.coroutines.Deferred) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L48
        L24:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            kotlinx.coroutines.CoroutineScope r8 = (kotlinx.coroutines.CoroutineScope) r8
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$loadAndFilterApps$2$loadAppsDeferred$1 r1 = new com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$loadAndFilterApps$2$loadAppsDeferred$1
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl r4 = r7.this$0
            int r5 = r7.$userId
            r6 = 0
            r1.<init>(r4, r5, r6)
            kotlinx.coroutines.DeferredCoroutine r1 = kotlinx.coroutines.BuildersKt.async$default(r8, r1)
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl r8 = r7.this$0
            int r4 = r7.$userId
            r7.L$0 = r1
            r7.label = r2
            java.lang.Object r8 = r8.loadHomeOrLauncherPackages(r4, r7)
            if (r8 != r0) goto L48
            return r0
        L48:
            java.util.Set r8 = (java.util.Set) r8
            r7.L$0 = r8
            r7.label = r3
            java.lang.Object r1 = r1.await(r7)
            if (r1 != r0) goto L55
            return r0
        L55:
            r0 = r8
            r8 = r1
        L57:
            java.lang.Iterable r8 = (java.lang.Iterable) r8
            com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl r1 = r7.this$0
            boolean r7 = r7.$isSystemApp
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.Iterator r8 = r8.iterator()
        L66:
            boolean r4 = r8.hasNext()
            if (r4 == 0) goto L93
            java.lang.Object r4 = r8.next()
            r5 = r4
            android.content.pm.ApplicationInfo r5 = (android.content.pm.ApplicationInfo) r5
            r1.getClass()
            boolean r6 = r5.isSystemApp()
            if (r6 == 0) goto L8c
            boolean r6 = r5.isUpdatedSystemApp()
            if (r6 != 0) goto L8c
            java.lang.String r5 = r5.packageName
            boolean r5 = r0.contains(r5)
            if (r5 != 0) goto L8c
            r5 = r2
            goto L8d
        L8c:
            r5 = 0
        L8d:
            if (r5 != r7) goto L66
            r3.add(r4)
            goto L66
        L93:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$loadAndFilterApps$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
