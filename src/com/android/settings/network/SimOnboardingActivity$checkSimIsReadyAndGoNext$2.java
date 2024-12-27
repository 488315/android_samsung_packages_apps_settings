package com.android.settings.network;

import android.content.IntentFilter;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.network.telephony.SubscriptionRepositoryKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
final class SimOnboardingActivity$checkSimIsReadyAndGoNext$2 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ SimOnboardingActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimOnboardingActivity$checkSimIsReadyAndGoNext$2(
            SimOnboardingActivity simOnboardingActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = simOnboardingActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimOnboardingActivity$checkSimIsReadyAndGoNext$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        SimOnboardingActivity$checkSimIsReadyAndGoNext$2
                simOnboardingActivity$checkSimIsReadyAndGoNext$2 =
                        (SimOnboardingActivity$checkSimIsReadyAndGoNext$2)
                                create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        simOnboardingActivity$checkSimIsReadyAndGoNext$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (!SubscriptionRepositoryKt.requireSubscriptionManager(this.this$0.getContext())
                .isSubscriptionEnabled(SimOnboardingActivity.onboardingService.targetSubId)) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            CarrierConfigChangedReceiver carrierConfigChangedReceiver =
                    new CarrierConfigChangedReceiver(countDownLatch);
            try {
                try {
                    long j =
                            Settings.Global.getLong(
                                    this.this$0.getContext().getContentResolver(),
                                    "euicc_switch_slot_timeout_millis",
                                    25000L);
                    this.this$0
                            .getContext()
                            .registerReceiver(
                                    carrierConfigChangedReceiver,
                                    new IntentFilter(
                                            "android.telephony.action.CARRIER_CONFIG_CHANGED"),
                                    2);
                    Log.d("SimOnboardingActivity", "Start waiting, waitingTime is " + j);
                    countDownLatch.await(j, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Log.e("SimOnboardingActivity", "Failed switching to physical slot.", e);
                }
            } finally {
                this.this$0.getContext().unregisterReceiver(carrierConfigChangedReceiver);
            }
        }
        Log.d("SimOnboardingActivity", "Sim is ready then go to next");
        ((SimOnboardingActivity$callbackListener$1) this.this$0.callbackListener)
                .invoke(SimOnboardingActivity$Companion$CallbackType.CALLBACK_SETUP_NAME);
        return Unit.INSTANCE;
    }
}
