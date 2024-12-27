package com.android.settings.spa.network;

import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.settings.network.SimOnboardingService;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
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
final class SimOnboardingPageProviderKt$PageImpl$1$1$nextPage$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ SimOnboardingService $onboardingService;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimOnboardingPageProviderKt$PageImpl$1$1$nextPage$1(
            SimOnboardingService simOnboardingService, Continuation continuation) {
        super(2, continuation);
        this.$onboardingService = simOnboardingService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimOnboardingPageProviderKt$PageImpl$1$1$nextPage$1(
                this.$onboardingService, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        SimOnboardingPageProviderKt$PageImpl$1$1$nextPage$1
                simOnboardingPageProviderKt$PageImpl$1$1$nextPage$1 =
                        (SimOnboardingPageProviderKt$PageImpl$1$1$nextPage$1)
                                create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        simOnboardingPageProviderKt$PageImpl$1$1$nextPage$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SimOnboardingService simOnboardingService = this.$onboardingService;
        int size = ((ArrayList) simOnboardingService.userSelectedSubInfoList).size();
        TelephonyManager telephonyManager = simOnboardingService.telephonyManager;
        if (size < (telephonyManager != null ? telephonyManager.getActiveModemCount() : 0)) {
            List list = simOnboardingService.userSelectedSubInfoList;
            List list2 = simOnboardingService.activeSubInfoList;
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : list2) {
                if (!((ArrayList) simOnboardingService.userSelectedSubInfoList)
                        .contains((SubscriptionInfo) obj2)) {
                    arrayList.add(obj2);
                }
            }
            ((ArrayList) list).addAll(arrayList);
            Log.d(
                    "SimOnboardingService",
                    "addCurrentItemForSelectedSim: userSelectedSubInfoList: "
                            + simOnboardingService.userSelectedSubInfoList);
        }
        return Unit.INSTANCE;
    }
}
