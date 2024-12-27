package com.android.settings.spa.app.appinfo;

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
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppRestoreButton$onReceive$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AppRestoreButton this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppRestoreButton$onReceive$1(
            AppRestoreButton appRestoreButton, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appRestoreButton;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppRestoreButton$onReceive$1 appRestoreButton$onReceive$1 =
                new AppRestoreButton$onReceive$1(this.this$0, continuation);
        appRestoreButton$onReceive$1.L$0 = obj;
        return appRestoreButton$onReceive$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppRestoreButton$onReceive$1) create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x006b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0034  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0069 -> B:6:0x0013). Please report as a decompilation issue!!! */
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
            if (r1 == 0) goto L25
            if (r1 == r2) goto L1d
            if (r1 != r3) goto L15
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r8)
        L13:
            r8 = r1
            goto L2c
        L15:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1d:
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L5f
        L25:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            kotlinx.coroutines.CoroutineScope r8 = (kotlinx.coroutines.CoroutineScope) r8
        L2c:
            boolean r1 = kotlinx.coroutines.CoroutineScopeKt.isActive(r8)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            if (r1 == 0) goto L6c
            com.android.settings.spa.app.appinfo.AppRestoreButton r1 = r7.this$0
            kotlinx.coroutines.flow.StateFlowImpl r1 = r1.buttonTextIndexStateFlow
            java.lang.Object r1 = r1.getValue()
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = r1.intValue()
            int r1 = r1 + r2
            com.android.settings.spa.app.appinfo.AppRestoreButton r5 = r7.this$0
            int[] r6 = r5.buttonTexts
            int r6 = r6.length
            int r1 = r1 % r6
            if (r1 != 0) goto L4d
            int r1 = r1 + 1
        L4d:
            kotlinx.coroutines.flow.StateFlowImpl r5 = r5.buttonTextIndexStateFlow
            java.lang.Integer r6 = new java.lang.Integer
            r6.<init>(r1)
            r7.L$0 = r8
            r7.label = r2
            r5.emit(r6, r7)
            if (r4 != r0) goto L5e
            return r0
        L5e:
            r1 = r8
        L5f:
            r7.L$0 = r1
            r7.label = r3
            r4 = 1000(0x3e8, double:4.94E-321)
            java.lang.Object r8 = kotlinx.coroutines.DelayKt.delay(r4, r7)
            if (r8 != r0) goto L13
            return r0
        L6c:
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.appinfo.AppRestoreButton$onReceive$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
