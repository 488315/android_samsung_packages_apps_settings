package com.android.settings.datausage;

import com.android.settings.datausage.lib.AppDataUsageDetailsRepository;
import com.android.settings.datausage.lib.IAppDataUsageDetailsRepository;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/datausage/lib/NetworkUsageDetailsData;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class AppDataUsageCycleController$update$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ AppDataUsageCycleController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsageCycleController$update$2(
            AppDataUsageCycleController appDataUsageCycleController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appDataUsageCycleController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppDataUsageCycleController$update$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppDataUsageCycleController$update$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IAppDataUsageDetailsRepository iAppDataUsageDetailsRepository;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            iAppDataUsageDetailsRepository = this.this$0.repository;
            if (iAppDataUsageDetailsRepository == null) {
                Intrinsics.throwUninitializedPropertyAccessException("repository");
                throw null;
            }
            this.label = 1;
            obj =
                    ((AppDataUsageDetailsRepository) iAppDataUsageDetailsRepository)
                            .queryDetailsForCycles(this);
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
