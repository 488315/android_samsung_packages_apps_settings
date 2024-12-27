package com.android.settings.datausage;

import android.net.NetworkPolicy;

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
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lcom/android/settings/datausage/DataPlanInfo;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DataUsageSummaryPreferenceController$update$dataPlanInfo$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ NetworkPolicy $policy;
    int label;
    final /* synthetic */ DataUsageSummaryPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsageSummaryPreferenceController$update$dataPlanInfo$1(
            DataUsageSummaryPreferenceController dataUsageSummaryPreferenceController,
            NetworkPolicy networkPolicy,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataUsageSummaryPreferenceController;
        this.$policy = networkPolicy;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DataUsageSummaryPreferenceController$update$dataPlanInfo$1(
                this.this$0, this.$policy, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataUsageSummaryPreferenceController$update$dataPlanInfo$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0068, code lost:

       if (r3.getCycleRule() != null) goto L16;
    */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            Method dump skipped, instructions count: 282
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.DataUsageSummaryPreferenceController$update$dataPlanInfo$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
