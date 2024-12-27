package com.android.settings.spa.network;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.util.Log;

import com.android.settings.network.SimOnboardingService;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.flow.FlowCollector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\n"
                + "\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "Lcom/android/settings/spa/network/PrimarySimRepository$PrimarySimInfo;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$primarySimInfo$1$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ SimOnboardingService $onboardingService;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$primarySimInfo$1$1(
            Context context, SimOnboardingService simOnboardingService, Continuation continuation) {
        super(2, continuation);
        this.$onboardingService = simOnboardingService;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$primarySimInfo$1$1
                simOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$primarySimInfo$1$1 =
                        new SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$primarySimInfo$1$1(
                                this.$context, this.$onboardingService, continuation);
        simOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$primarySimInfo$1$1.L$0 = obj;
        return simOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$primarySimInfo$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$primarySimInfo$1$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            SimOnboardingService simOnboardingService = this.$onboardingService;
            if (((ArrayList) simOnboardingService.userSelectedSubInfoList).isEmpty()) {
                Log.d("SimOnboardingService", "userSelectedSubInfoList is empty");
                list = simOnboardingService.activeSubInfoList;
            } else {
                List list2 = simOnboardingService.userSelectedSubInfoList;
                ArrayList arrayList =
                        new ArrayList(
                                CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = ((ArrayList) list2).iterator();
                while (it.hasNext()) {
                    SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it.next();
                    arrayList.add(
                            new SubscriptionInfo.Builder(subscriptionInfo)
                                    .setDisplayName(
                                            simOnboardingService.getSubscriptionInfoDisplayName(
                                                    subscriptionInfo))
                                    .build());
                }
                list = CollectionsKt___CollectionsKt.toList(arrayList);
            }
            PrimarySimRepository.PrimarySimInfo primarySimInfo =
                    new PrimarySimRepository(this.$context).getPrimarySimInfo(list);
            this.label = 1;
            if (flowCollector.emit(primarySimInfo, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
