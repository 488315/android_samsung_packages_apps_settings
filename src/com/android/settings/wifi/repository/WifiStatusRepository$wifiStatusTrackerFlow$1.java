package com.android.settings.wifi.repository;

import android.content.Intent;

import com.android.settingslib.spaprivileged.framework.common.BroadcastReceiverFlowKt;
import com.android.settingslib.wifi.WifiStatusTracker;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;

import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/channels/ProducerScope;",
            "Lcom/android/settingslib/wifi/WifiStatusTracker;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class WifiStatusRepository$wifiStatusTrackerFlow$1 extends SuspendLambda
        implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WifiStatusRepository this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\f\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"
            },
            d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "intent", "Landroid/content/Intent;"},
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.wifi.repository.WifiStatusRepository$wifiStatusTrackerFlow$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Ref$ObjectRef<WifiStatusTracker> $wifiStatusTracker;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Ref$ObjectRef ref$ObjectRef, Continuation continuation) {
            super(2, continuation);
            this.$wifiStatusTracker = ref$ObjectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 =
                    new AnonymousClass2(this.$wifiStatusTracker, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            AnonymousClass2 anonymousClass2 =
                    (AnonymousClass2) create((Intent) obj, (Continuation) obj2);
            Unit unit = Unit.INSTANCE;
            anonymousClass2.invokeSuspend(unit);
            return unit;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Intent intent = (Intent) this.L$0;
            WifiStatusTracker wifiStatusTracker = this.$wifiStatusTracker.element;
            if (wifiStatusTracker.mWifiManager != null
                    && intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                wifiStatusTracker.updateWifiState();
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiStatusRepository$wifiStatusTrackerFlow$1(
            WifiStatusRepository wifiStatusRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = wifiStatusRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiStatusRepository$wifiStatusTrackerFlow$1 wifiStatusRepository$wifiStatusTrackerFlow$1 =
                new WifiStatusRepository$wifiStatusTrackerFlow$1(this.this$0, continuation);
        wifiStatusRepository$wifiStatusTrackerFlow$1.L$0 = obj;
        return wifiStatusRepository$wifiStatusTrackerFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiStatusRepository$wifiStatusTrackerFlow$1)
                        create((ProducerScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v2, types: [T, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element =
                    this.this$0.wifiStatusTrackerFactory.invoke(
                            new Runnable() { // from class:
                                             // com.android.settings.wifi.repository.WifiStatusRepository$wifiStatusTrackerFlow$1.1
                                /* JADX WARN: Multi-variable type inference failed */
                                @Override // java.lang.Runnable
                                public final void run() {
                                    WifiStatusTracker wifiStatusTracker =
                                            (WifiStatusTracker) Ref$ObjectRef.this.element;
                                    if (wifiStatusTracker != null) {
                                        ((ProducerCoroutine) producerScope)
                                                ._channel.mo1469trySendJP2dKIU(wifiStatusTracker);
                                    }
                                }
                            });
            FlowKt.launchIn(
                    FlowKt.onEach(
                            BroadcastReceiverFlowKt.broadcastReceiverFlow(
                                    this.this$0.context, WifiStatusRepository.INTENT_FILTER),
                            new AnonymousClass2(ref$ObjectRef, null)),
                    producerScope);
            ((WifiStatusTracker) ref$ObjectRef.element).setListening(true);
            ((WifiStatusTracker) ref$ObjectRef.element).fetchInitialState();
            ProducerCoroutine producerCoroutine = (ProducerCoroutine) producerScope;
            producerCoroutine.mo1469trySendJP2dKIU(ref$ObjectRef.element);
            Function0 function0 =
                    new Function0() { // from class:
                                      // com.android.settings.wifi.repository.WifiStatusRepository$wifiStatusTrackerFlow$1.3
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Ref$ObjectRef.this.element.setListening(false);
                            return Unit.INSTANCE;
                        }
                    };
            this.label = 1;
            if (ProduceKt.awaitClose(producerCoroutine, function0, this) == coroutineSingletons) {
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