package com.android.settings.biometrics.fingerprint2.debug.data.repository;

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
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "Landroid/view/MotionEvent;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class UdfpsEnrollDebugRepositoryImpl$touchExplorationDebug$1 extends SuspendLambda
        implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UdfpsEnrollDebugRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsEnrollDebugRepositoryImpl$touchExplorationDebug$1(
            UdfpsEnrollDebugRepositoryImpl udfpsEnrollDebugRepositoryImpl,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = udfpsEnrollDebugRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UdfpsEnrollDebugRepositoryImpl$touchExplorationDebug$1
                udfpsEnrollDebugRepositoryImpl$touchExplorationDebug$1 =
                        new UdfpsEnrollDebugRepositoryImpl$touchExplorationDebug$1(
                                this.this$0, continuation);
        udfpsEnrollDebugRepositoryImpl$touchExplorationDebug$1.L$0 = obj;
        return udfpsEnrollDebugRepositoryImpl$touchExplorationDebug$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UdfpsEnrollDebugRepositoryImpl$touchExplorationDebug$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x013b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x010f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0103 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00d8 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00cc A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a1 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0095 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.fingerprint2.debug.data.repository.UdfpsEnrollDebugRepositoryImpl$touchExplorationDebug$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
