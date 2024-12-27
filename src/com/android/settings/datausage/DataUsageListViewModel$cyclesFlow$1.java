package com.android.settings.datausage;

import android.app.Application;
import android.net.NetworkTemplate;

import com.android.settings.datausage.lib.NetworkCycleBucketRepository;

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
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/datausage/lib/NetworkUsageData;",
            "template",
            "Landroid/net/NetworkTemplate;",
            "buckets",
            "Lcom/android/settings/datausage/lib/NetworkStatsRepository$Companion$Bucket;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DataUsageListViewModel$cyclesFlow$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Application $application;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsageListViewModel$cyclesFlow$1(Application application, Continuation continuation) {
        super(3, continuation);
        this.$application = application;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DataUsageListViewModel$cyclesFlow$1 dataUsageListViewModel$cyclesFlow$1 =
                new DataUsageListViewModel$cyclesFlow$1(this.$application, (Continuation) obj3);
        dataUsageListViewModel$cyclesFlow$1.L$0 = (NetworkTemplate) obj;
        dataUsageListViewModel$cyclesFlow$1.L$1 = (List) obj2;
        return dataUsageListViewModel$cyclesFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new NetworkCycleBucketRepository(
                        this.$application, (NetworkTemplate) this.L$0, (List) this.L$1)
                .loadCycles();
    }
}
