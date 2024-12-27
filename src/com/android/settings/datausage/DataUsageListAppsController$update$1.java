package com.android.settings.datausage;

import androidx.preference.PreferenceGroup;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultScheduler;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DataUsageListAppsController$update$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Integer $carrierId;
    final /* synthetic */ long $endTime;
    final /* synthetic */ long $startTime;
    int label;
    final /* synthetic */ DataUsageListAppsController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsageListAppsController$update$1(
            DataUsageListAppsController dataUsageListAppsController,
            Integer num,
            long j,
            long j2,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataUsageListAppsController;
        this.$carrierId = num;
        this.$startTime = j;
        this.$endTime = j2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DataUsageListAppsController$update$1(
                this.this$0, this.$carrierId, this.$startTime, this.$endTime, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataUsageListAppsController$update$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        PreferenceGroup preferenceGroup;
        PreferenceGroup preferenceGroup2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            DataUsageListAppsController$update$1$apps$1
                    dataUsageListAppsController$update$1$apps$1 =
                            new DataUsageListAppsController$update$1$apps$1(
                                    this.this$0,
                                    this.$carrierId,
                                    this.$startTime,
                                    this.$endTime,
                                    null);
            this.label = 1;
            obj =
                    BuildersKt.withContext(
                            defaultScheduler, dataUsageListAppsController$update$1$apps$1, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        List<AppDataUsagePreference> list = (List) obj;
        preferenceGroup = this.this$0.preference;
        if (preferenceGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        preferenceGroup.removeAll();
        for (AppDataUsagePreference appDataUsagePreference : list) {
            preferenceGroup2 = this.this$0.preference;
            if (preferenceGroup2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("preference");
                throw null;
            }
            preferenceGroup2.addPreference(appDataUsagePreference);
        }
        return Unit.INSTANCE;
    }
}
