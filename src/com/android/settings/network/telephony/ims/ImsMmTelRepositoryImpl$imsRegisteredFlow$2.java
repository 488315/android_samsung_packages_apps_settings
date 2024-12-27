package com.android.settings.network.telephony.ims;

import android.util.Log;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0014\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE,
            "e",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class ImsMmTelRepositoryImpl$imsRegisteredFlow$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ImsMmTelRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImsMmTelRepositoryImpl$imsRegisteredFlow$2(
            ImsMmTelRepositoryImpl imsMmTelRepositoryImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = imsMmTelRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ImsMmTelRepositoryImpl$imsRegisteredFlow$2 imsMmTelRepositoryImpl$imsRegisteredFlow$2 =
                new ImsMmTelRepositoryImpl$imsRegisteredFlow$2(this.this$0, (Continuation) obj3);
        imsMmTelRepositoryImpl$imsRegisteredFlow$2.L$0 = (Throwable) obj2;
        Unit unit = Unit.INSTANCE;
        imsMmTelRepositoryImpl$imsRegisteredFlow$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.w(
                "ImsMmTelRepository",
                "[" + this.this$0.subId + "] error while imsRegisteredFlow",
                (Throwable) this.L$0);
        return Unit.INSTANCE;
    }
}
