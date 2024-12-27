package com.android.settings.spa.app.appinfo;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class HibernationSwitchPresenter$isEligibleFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ HibernationSwitchPresenter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HibernationSwitchPresenter$isEligibleFlow$1(
            HibernationSwitchPresenter hibernationSwitchPresenter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = hibernationSwitchPresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        HibernationSwitchPresenter$isEligibleFlow$1 hibernationSwitchPresenter$isEligibleFlow$1 =
                new HibernationSwitchPresenter$isEligibleFlow$1(this.this$0, continuation);
        hibernationSwitchPresenter$isEligibleFlow$1.L$0 = obj;
        return hibernationSwitchPresenter$isEligibleFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HibernationSwitchPresenter$isEligibleFlow$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0086 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == 0) goto L2a
            if (r1 == r5) goto L26
            if (r1 == r4) goto L1e
            if (r1 != r3) goto L16
            kotlin.ResultKt.throwOnFailure(r10)
            goto L87
        L16:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L1e:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L6a
        L26:
            kotlin.ResultKt.throwOnFailure(r10)
            goto L45
        L2a:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            r1 = r10
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            com.android.settings.spa.app.appinfo.HibernationSwitchPresenter r10 = r9.this$0
            android.content.pm.ApplicationInfo r6 = r10.app
            boolean r6 = r6.isArchived
            if (r6 == 0) goto L46
            java.lang.Boolean r10 = java.lang.Boolean.FALSE
            r9.label = r5
            java.lang.Object r9 = r1.emit(r10, r9)
            if (r9 != r0) goto L45
            return r0
        L45:
            return r2
        L46:
            r9.L$0 = r1
            r9.label = r4
            kotlin.coroutines.SafeContinuation r4 = new kotlin.coroutines.SafeContinuation
            kotlin.coroutines.Continuation r6 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r9)
            r4.<init>(r6)
            android.permission.PermissionControllerManager r6 = r10.permissionControllerManager
            android.content.pm.ApplicationInfo r7 = r10.app
            java.lang.String r7 = r7.packageName
            java.util.concurrent.Executor r10 = r10.executor
            com.android.settings.spa.app.appinfo.HibernationSwitchPresenter$getEligibility$2$1 r8 = new com.android.settings.spa.app.appinfo.HibernationSwitchPresenter$getEligibility$2$1
            r8.<init>()
            r6.getHibernationEligibility(r7, r10, r8)
            java.lang.Object r10 = r4.getOrThrow()
            if (r10 != r0) goto L6a
            return r0
        L6a:
            java.lang.Number r10 = (java.lang.Number) r10
            int r10 = r10.intValue()
            if (r10 == r5) goto L76
            r4 = -1
            if (r10 == r4) goto L76
            goto L77
        L76:
            r5 = 0
        L77:
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r5)
            r4 = 0
            r9.L$0 = r4
            r9.label = r3
            java.lang.Object r9 = r1.emit(r10, r9)
            if (r9 != r0) goto L87
            return r0
        L87:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.appinfo.HibernationSwitchPresenter$isEligibleFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
