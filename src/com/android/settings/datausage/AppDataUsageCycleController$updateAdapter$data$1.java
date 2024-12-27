package com.android.settings.datausage;

import android.util.Log;
import android.util.Range;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;

import com.android.settings.datausage.lib.AppDataUsageDetailsRepository;
import com.android.settings.datausage.lib.IAppDataUsageDetailsRepository;
import com.android.settings.datausage.lib.NetworkStatsRepository;
import com.android.settings.datausage.lib.NetworkUsageDetailsData;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
import java.util.Iterator;

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
            "Lcom/android/settings/datausage/lib/NetworkUsageDetailsData;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class AppDataUsageCycleController$updateAdapter$data$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ long $endTime;
    final /* synthetic */ long $startTime;
    int label;
    final /* synthetic */ AppDataUsageCycleController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsageCycleController$updateAdapter$data$1(
            AppDataUsageCycleController appDataUsageCycleController,
            long j,
            long j2,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = appDataUsageCycleController;
        this.$startTime = j;
        this.$endTime = j2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppDataUsageCycleController$updateAdapter$data$1(
                this.this$0, this.$startTime, this.$endTime, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppDataUsageCycleController$updateAdapter$data$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IAppDataUsageDetailsRepository iAppDataUsageDetailsRepository;
        String str;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            iAppDataUsageDetailsRepository = this.this$0.repository;
            if (iAppDataUsageDetailsRepository == null) {
                Intrinsics.throwUninitializedPropertyAccessException("repository");
                throw null;
            }
            Range range = new Range(new Long(this.$startTime), new Long(this.$endTime));
            this.label = 1;
            AppDataUsageDetailsRepository appDataUsageDetailsRepository =
                    (AppDataUsageDetailsRepository) iAppDataUsageDetailsRepository;
            NetworkStatsRepository networkStatsRepository =
                    appDataUsageDetailsRepository.networkStatsRepository;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Iterator it = appDataUsageDetailsRepository.withSdkSandboxUids.iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                str = AppDataUsageDetailsRepository.TAG;
                if (!hasNext) {
                    break;
                }
                int intValue = ((Number) it.next()).intValue();
                try {
                    arrayList.addAll(
                            networkStatsRepository.queryDetailsForUidTagState(range, intValue, 1));
                    arrayList2.addAll(
                            networkStatsRepository.queryDetailsForUidTagState(range, intValue, 2));
                } catch (SecurityException e) {
                    Log.e(str, "queryNetworkStatsData", e);
                }
            }
            Iterator it2 = arrayList.iterator();
            long j = 0;
            while (it2.hasNext()) {
                j += ((NetworkStatsRepository.Companion.Bucket) it2.next()).bytes;
            }
            Iterator it3 = arrayList2.iterator();
            long j2 = 0;
            while (it3.hasNext()) {
                j2 += ((NetworkStatsRepository.Companion.Bucket) it3.next()).bytes;
            }
            StringBuilder m =
                    SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                            j, "backgroundUsage = ", "; foregroundUsage = ");
            m.append(j2);
            Log.i(str, m.toString());
            obj = new NetworkUsageDetailsData(range, j + j2, j2, j);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
