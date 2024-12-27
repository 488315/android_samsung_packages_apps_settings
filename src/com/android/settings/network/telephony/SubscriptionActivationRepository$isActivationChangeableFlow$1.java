package com.android.settings.network.telephony;

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
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "isInCall", "isSatelliteModemEnabled"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class SubscriptionActivationRepository$isActivationChangeableFlow$1 extends SuspendLambda
        implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        SubscriptionActivationRepository$isActivationChangeableFlow$1
                subscriptionActivationRepository$isActivationChangeableFlow$1 =
                        new SubscriptionActivationRepository$isActivationChangeableFlow$1(
                                3, (Continuation) obj3);
        subscriptionActivationRepository$isActivationChangeableFlow$1.Z$0 = booleanValue;
        subscriptionActivationRepository$isActivationChangeableFlow$1.Z$1 = booleanValue2;
        return subscriptionActivationRepository$isActivationChangeableFlow$1.invokeSuspend(
                Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf((this.Z$0 || this.Z$1) ? false : true);
    }
}
