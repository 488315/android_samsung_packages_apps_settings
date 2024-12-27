package com.android.settings.network;

import android.net.TetheringManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/channels/ProducerScope;", ApnSettings.MVNO_NONE, "Landroid/net/TetheringInterface;"}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
final class TetheredRepository$tetheredInterfacesFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TetheredRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TetheredRepository$tetheredInterfacesFlow$1(TetheredRepository tetheredRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = tetheredRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TetheredRepository$tetheredInterfacesFlow$1 tetheredRepository$tetheredInterfacesFlow$1 = new TetheredRepository$tetheredInterfacesFlow$1(this.this$0, continuation);
        tetheredRepository$tetheredInterfacesFlow$1.L$0 = obj;
        return tetheredRepository$tetheredInterfacesFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TetheredRepository$tetheredInterfacesFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.net.TetheringManager$TetheringEventCallback, com.android.settings.network.TetheredRepository$tetheredInterfacesFlow$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new TetheringManager.TetheringEventCallback() { // from class: com.android.settings.network.TetheredRepository$tetheredInterfacesFlow$1$callback$1
                public final void onTetheredInterfacesChanged(Set interfaces) {
                    Intrinsics.checkNotNullParameter(interfaces, "interfaces");
                    ((ProducerCoroutine) ProducerScope.this).mo1469trySendJP2dKIU(interfaces);
                }
            };
            this.this$0.tetheringManager.registerTetheringEventCallback(ExecutorsKt.asExecutor(Dispatchers.Default), (TetheringManager.TetheringEventCallback) r1);
            final TetheredRepository tetheredRepository = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.settings.network.TetheredRepository$tetheredInterfacesFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* renamed from: invoke */
                public final Object mo1068invoke() {
                    TetheredRepository.this.tetheringManager.unregisterTetheringEventCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
