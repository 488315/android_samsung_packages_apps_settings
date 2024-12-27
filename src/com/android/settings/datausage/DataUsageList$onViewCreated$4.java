package com.android.settings.datausage;

import com.android.settings.datausage.lib.NetworkUsageData;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "cycles",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/datausage/lib/NetworkUsageData;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class DataUsageList$onViewCreated$4 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DataUsageList this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsageList$onViewCreated$4(DataUsageList dataUsageList, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataUsageList;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DataUsageList$onViewCreated$4 dataUsageList$onViewCreated$4 =
                new DataUsageList$onViewCreated$4(this.this$0, continuation);
        dataUsageList$onViewCreated$4.L$0 = obj;
        return dataUsageList$onViewCreated$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        DataUsageList$onViewCreated$4 dataUsageList$onViewCreated$4 =
                (DataUsageList$onViewCreated$4) create((List) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        dataUsageList$onViewCreated$4.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List<NetworkUsageData> list = (List) this.L$0;
        DataUsageListAppsController dataUsageListAppsController =
                this.this$0.dataUsageListAppsController;
        if (dataUsageListAppsController != null) {
            dataUsageListAppsController.updateCycles(list);
        }
        return Unit.INSTANCE;
    }
}
