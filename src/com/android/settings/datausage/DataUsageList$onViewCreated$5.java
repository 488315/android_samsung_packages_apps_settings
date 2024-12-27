package com.android.settings.datausage;

import com.android.settings.datausage.lib.NetworkCycleChartData;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "chartData",
            "Lcom/android/settings/datausage/lib/NetworkCycleChartData;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class DataUsageList$onViewCreated$5 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DataUsageList this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsageList$onViewCreated$5(DataUsageList dataUsageList, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataUsageList;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DataUsageList$onViewCreated$5 dataUsageList$onViewCreated$5 =
                new DataUsageList$onViewCreated$5(this.this$0, continuation);
        dataUsageList$onViewCreated$5.L$0 = obj;
        return dataUsageList$onViewCreated$5;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        DataUsageList$onViewCreated$5 dataUsageList$onViewCreated$5 =
                (DataUsageList$onViewCreated$5)
                        create((NetworkCycleChartData) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        dataUsageList$onViewCreated$5.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        NetworkCycleChartData networkCycleChartData = (NetworkCycleChartData) this.L$0;
        ChartDataUsagePreferenceController chartDataUsagePreferenceController =
                this.this$0.chartDataUsagePreferenceController;
        if (chartDataUsagePreferenceController != null) {
            chartDataUsagePreferenceController.update(networkCycleChartData);
        }
        return Unit.INSTANCE;
    }
}
