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
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lcom/android/settings/network/telephony/DataSubscriptionRepository$DataSubscriptionIds;",
            "defaultSubId",
            ApnSettings.MVNO_NONE,
            "activeSubId"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class DataSubscriptionRepository$dataSummaryFlow$1 extends SuspendLambda
        implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        int intValue2 = ((Number) obj2).intValue();
        DataSubscriptionRepository$dataSummaryFlow$1 dataSubscriptionRepository$dataSummaryFlow$1 =
                new DataSubscriptionRepository$dataSummaryFlow$1(3, (Continuation) obj3);
        dataSubscriptionRepository$dataSummaryFlow$1.I$0 = intValue;
        dataSubscriptionRepository$dataSummaryFlow$1.I$1 = intValue2;
        return dataSubscriptionRepository$dataSummaryFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new DataSubscriptionRepository.DataSubscriptionIds(this.I$0, this.I$1);
    }
}
