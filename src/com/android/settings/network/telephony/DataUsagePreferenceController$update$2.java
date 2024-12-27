package com.android.settings.network.telephony;

import android.net.NetworkTemplate;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u0001*\u00020\u0004H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lkotlin/Pair;",
            "Lcom/android/settings/datausage/lib/DataUsageFormatter$FormattedDataUsage;",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DataUsagePreferenceController$update$2 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ DataUsagePreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsagePreferenceController$update$2(
            DataUsagePreferenceController dataUsagePreferenceController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataUsagePreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DataUsagePreferenceController$update$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataUsagePreferenceController$update$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        NetworkTemplate networkTemplate;
        Pair dataUsageSummaryAndEnabled;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DataUsagePreferenceController dataUsagePreferenceController = this.this$0;
        networkTemplate = dataUsagePreferenceController.getNetworkTemplate();
        dataUsagePreferenceController.networkTemplate = networkTemplate;
        dataUsageSummaryAndEnabled = this.this$0.getDataUsageSummaryAndEnabled();
        return dataUsageSummaryAndEnabled;
    }
}
