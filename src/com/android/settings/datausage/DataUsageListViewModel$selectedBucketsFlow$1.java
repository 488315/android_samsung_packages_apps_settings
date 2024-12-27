package com.android.settings.datausage;

import com.android.settings.datausage.lib.NetworkStatsRepository;
import com.android.settings.datausage.lib.NetworkUsageData;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0016\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lcom/android/settings/datausage/SelectedBuckets;",
            "selectedCycle",
            "Lcom/android/settings/datausage/lib/NetworkUsageData;",
            "buckets",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/datausage/lib/NetworkStatsRepository$Companion$Bucket;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DataUsageListViewModel$selectedBucketsFlow$1 extends SuspendLambda
        implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DataUsageListViewModel$selectedBucketsFlow$1 dataUsageListViewModel$selectedBucketsFlow$1 =
                new DataUsageListViewModel$selectedBucketsFlow$1(3, (Continuation) obj3);
        dataUsageListViewModel$selectedBucketsFlow$1.L$0 = (NetworkUsageData) obj;
        dataUsageListViewModel$selectedBucketsFlow$1.L$1 = (List) obj2;
        return dataUsageListViewModel$selectedBucketsFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        NetworkUsageData networkUsageData = (NetworkUsageData) this.L$0;
        List list = (List) this.L$1;
        NetworkStatsRepository.Companion companion = NetworkStatsRepository.Companion;
        return new SelectedBuckets(
                networkUsageData,
                NetworkStatsRepository.Companion.filterTime(
                        list, networkUsageData.startTime, networkUsageData.endTime));
    }
}
