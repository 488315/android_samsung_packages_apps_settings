package com.android.settings.wifi;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\u0010\u0004\u001a\u00020\u0003*\u0010\u0012\f\u0012\n"
                + " \u0002*\u0004\u0018\u00010\u00010\u00010\u0000H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"
        },
        d2 = {
            "Lkotlinx/coroutines/channels/ProducerScope;",
            ApnSettings.MVNO_NONE,
            "kotlin.jvm.PlatformType",
            ApnSettings.MVNO_NONE,
            "<anonymous>",
            "(Lkotlinx/coroutines/channels/ProducerScope;)V"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class WepNetworksPreferenceController$wepAllowedFlow$1 extends SuspendLambda
        implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WepNetworksPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WepNetworksPreferenceController$wepAllowedFlow$1(
            WepNetworksPreferenceController wepNetworksPreferenceController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = wepNetworksPreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WepNetworksPreferenceController$wepAllowedFlow$1
                wepNetworksPreferenceController$wepAllowedFlow$1 =
                        new WepNetworksPreferenceController$wepAllowedFlow$1(
                                this.this$0, continuation);
        wepNetworksPreferenceController$wepAllowedFlow$1.L$0 = obj;
        return wepNetworksPreferenceController$wepAllowedFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WepNetworksPreferenceController$wepAllowedFlow$1)
                        create((ProducerScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            this.this$0
                    .getWifiManager()
                    .queryWepAllowed(
                            ExecutorsKt.asExecutor(Dispatchers.Default),
                            new Consumer() { // from class:
                                             // com.android.settings.wifi.WepNetworksPreferenceController$wepAllowedFlow$1.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    ((ProducerCoroutine) ProducerScope.this)
                                            .mo1469trySendJP2dKIU((Boolean) obj2);
                                }
                            });
            AnonymousClass2 anonymousClass2 =
                    new Function0() { // from class:
                                      // com.android.settings.wifi.WepNetworksPreferenceController$wepAllowedFlow$1.2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                            return Unit.INSTANCE;
                        }
                    };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, anonymousClass2, this) == coroutineSingletons) {
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
