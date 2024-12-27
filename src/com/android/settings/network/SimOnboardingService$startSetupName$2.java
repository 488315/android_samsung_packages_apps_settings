package com.android.settings.network;

import android.telephony.SubscriptionManager;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

import java.util.LinkedHashMap;
import java.util.Map;

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
final class SimOnboardingService$startSetupName$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SimOnboardingService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimOnboardingService$startSetupName$2(
            SimOnboardingService simOnboardingService, Continuation continuation) {
        super(2, continuation);
        this.this$0 = simOnboardingService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimOnboardingService$startSetupName$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        SimOnboardingService$startSetupName$2 simOnboardingService$startSetupName$2 =
                (SimOnboardingService$startSetupName$2)
                        create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        simOnboardingService$startSetupName$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SimOnboardingService simOnboardingService = this.this$0;
        for (Map.Entry entry : ((LinkedHashMap) simOnboardingService.renameMutableMap).entrySet()) {
            SubscriptionManager subscriptionManager = simOnboardingService.subscriptionManager;
            if (subscriptionManager != null) {
                new Integer(
                        subscriptionManager.setDisplayName(
                                (String) entry.getValue(),
                                ((Number) entry.getKey()).intValue(),
                                2));
            }
        }
        this.this$0.callback.invoke(
                SimOnboardingActivity$Companion$CallbackType.CALLBACK_SETUP_PRIMARY_SIM);
        return Unit.INSTANCE;
    }
}
