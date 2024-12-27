package com.android.settings.datausage;

import android.app.Application;
import android.net.NetworkTemplate;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lcom/android/settings/datausage/lib/NetworkCycleChartData;",
            "template",
            "Landroid/net/NetworkTemplate;",
            "selectedBuckets",
            "Lcom/android/settings/datausage/SelectedBuckets;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DataUsageListViewModel$chartDataFlow$1 extends SuspendLambda
        implements Function3 {
    final /* synthetic */ Application $application;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsageListViewModel$chartDataFlow$1(
            Application application, Continuation continuation) {
        super(3, continuation);
        this.$application = application;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DataUsageListViewModel$chartDataFlow$1 dataUsageListViewModel$chartDataFlow$1 =
                new DataUsageListViewModel$chartDataFlow$1(this.$application, (Continuation) obj3);
        dataUsageListViewModel$chartDataFlow$1.L$0 = (NetworkTemplate) obj;
        dataUsageListViewModel$chartDataFlow$1.L$1 = (SelectedBuckets) obj2;
        return dataUsageListViewModel$chartDataFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00c8 A[LOOP:0: B:17:0x00c2->B:19:0x00c8, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008e  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            Method dump skipped, instructions count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.DataUsageListViewModel$chartDataFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
