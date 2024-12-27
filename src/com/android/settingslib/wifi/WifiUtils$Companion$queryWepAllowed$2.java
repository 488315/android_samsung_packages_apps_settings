package com.android.settingslib.wifi;

import android.net.wifi.WifiManager;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class WifiUtils$Companion$queryWepAllowed$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ WifiManager $this_queryWepAllowed;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiUtils$Companion$queryWepAllowed$2(
            WifiManager wifiManager, Continuation continuation) {
        super(2, continuation);
        this.$this_queryWepAllowed = wifiManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WifiUtils$Companion$queryWepAllowed$2(this.$this_queryWepAllowed, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiUtils$Companion$queryWepAllowed$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            WifiManager wifiManager = this.$this_queryWepAllowed;
            this.L$0 = wifiManager;
            this.label = 1;
            final CancellableContinuationImpl cancellableContinuationImpl =
                    new CancellableContinuationImpl(
                            1, IntrinsicsKt__IntrinsicsJvmKt.intercepted(this));
            cancellableContinuationImpl.initCancellability();
            wifiManager.queryWepAllowed(
                    ExecutorsKt.asExecutor(Dispatchers.Default),
                    new Consumer() { // from class:
                                     // com.android.settingslib.wifi.WifiUtils$Companion$queryWepAllowed$2$1$1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj2) {
                            cancellableContinuationImpl.resumeWith((Boolean) obj2);
                        }
                    });
            obj = cancellableContinuationImpl.getResult();
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
