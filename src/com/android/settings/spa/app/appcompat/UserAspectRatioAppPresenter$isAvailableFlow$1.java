package com.android.settings.spa.app.appcompat;

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
final class UserAspectRatioAppPresenter$isAvailableFlow$1 extends SuspendLambda
        implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserAspectRatioAppPresenter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserAspectRatioAppPresenter$isAvailableFlow$1(
            UserAspectRatioAppPresenter userAspectRatioAppPresenter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userAspectRatioAppPresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserAspectRatioAppPresenter$isAvailableFlow$1
                userAspectRatioAppPresenter$isAvailableFlow$1 =
                        new UserAspectRatioAppPresenter$isAvailableFlow$1(
                                this.this$0, continuation);
        userAspectRatioAppPresenter$isAvailableFlow$1.L$0 = obj;
        return userAspectRatioAppPresenter$isAvailableFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserAspectRatioAppPresenter$isAvailableFlow$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0041 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 1
            if (r1 == 0) goto L15
            if (r1 != r2) goto Ld
            kotlin.ResultKt.throwOnFailure(r5)
            goto L42
        Ld:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L15:
            kotlin.ResultKt.throwOnFailure(r5)
            java.lang.Object r5 = r4.L$0
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            com.android.settings.spa.app.appcompat.UserAspectRatioAppPresenter r1 = r4.this$0
            android.content.Context r1 = r1.context
            boolean r1 = com.android.settings.applications.appcompat.UserAspectRatioManager.isFeatureEnabled(r1)
            if (r1 == 0) goto L34
            com.android.settings.spa.app.appcompat.UserAspectRatioAppPresenter r1 = r4.this$0
            com.android.settings.applications.appcompat.UserAspectRatioManager r3 = r1.manager
            android.content.pm.ApplicationInfo r1 = r1.app
            boolean r1 = r3.canDisplayAspectRatioUi(r1)
            if (r1 == 0) goto L34
            r1 = r2
            goto L35
        L34:
            r1 = 0
        L35:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            r4.label = r2
            java.lang.Object r4 = r5.emit(r1, r4)
            if (r4 != r0) goto L42
            return r0
        L42:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.appcompat.UserAspectRatioAppPresenter$isAvailableFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
