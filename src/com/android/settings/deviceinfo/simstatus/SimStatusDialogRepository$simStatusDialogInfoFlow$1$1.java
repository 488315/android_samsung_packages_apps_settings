package com.android.settings.deviceinfo.simstatus;

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
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lcom/android/settings/deviceinfo/simstatus/SimStatusDialogRepository$SimStatusDialogInfo;",
            "signalStrength",
            ApnSettings.MVNO_NONE,
            "imsRegistered",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class SimStatusDialogRepository$simStatusDialogInfoFlow$1$1 extends SuspendLambda
        implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SimStatusDialogRepository$simStatusDialogInfoFlow$1$1
                simStatusDialogRepository$simStatusDialogInfoFlow$1$1 =
                        new SimStatusDialogRepository$simStatusDialogInfoFlow$1$1(
                                3, (Continuation) obj3);
        simStatusDialogRepository$simStatusDialogInfoFlow$1$1.L$0 = (String) obj;
        simStatusDialogRepository$simStatusDialogInfoFlow$1$1.L$1 = (Boolean) obj2;
        return simStatusDialogRepository$simStatusDialogInfoFlow$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new SimStatusDialogRepository.SimStatusDialogInfo(
                (String) this.L$0, (Boolean) this.L$1);
    }
}
