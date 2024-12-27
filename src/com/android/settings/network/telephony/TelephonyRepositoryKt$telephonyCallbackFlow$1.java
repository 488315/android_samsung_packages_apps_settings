package com.android.settings.network.telephony;

import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "T",
            "Lkotlinx/coroutines/channels/ProducerScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class TelephonyRepositoryKt$telephonyCallbackFlow$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Function1 $block;
    final /* synthetic */ TelephonyManager $this_telephonyCallbackFlow;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TelephonyRepositoryKt$telephonyCallbackFlow$1(
            Function1 function1, TelephonyManager telephonyManager, Continuation continuation) {
        super(2, continuation);
        this.$block = function1;
        this.$this_telephonyCallbackFlow = telephonyManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TelephonyRepositoryKt$telephonyCallbackFlow$1
                telephonyRepositoryKt$telephonyCallbackFlow$1 =
                        new TelephonyRepositoryKt$telephonyCallbackFlow$1(
                                this.$block, this.$this_telephonyCallbackFlow, continuation);
        telephonyRepositoryKt$telephonyCallbackFlow$1.L$0 = obj;
        return telephonyRepositoryKt$telephonyCallbackFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TelephonyRepositoryKt$telephonyCallbackFlow$1)
                        create((ProducerScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final TelephonyCallback telephonyCallback =
                    (TelephonyCallback) this.$block.invoke(producerScope);
            this.$this_telephonyCallbackFlow.registerTelephonyCallback(
                    ExecutorsKt.asExecutor(Dispatchers.Default), telephonyCallback);
            final TelephonyManager telephonyManager = this.$this_telephonyCallbackFlow;
            Function0 function0 =
                    new Function0() { // from class:
                                      // com.android.settings.network.telephony.TelephonyRepositoryKt$telephonyCallbackFlow$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            telephonyManager.unregisterTelephonyCallback(telephonyCallback);
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
