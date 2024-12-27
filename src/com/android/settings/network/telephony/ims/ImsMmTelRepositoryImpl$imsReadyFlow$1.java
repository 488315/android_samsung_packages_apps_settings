package com.android.settings.network.telephony.ims;

import android.telephony.ims.ImsStateCallback;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/channels/ProducerScope;", ApnSettings.MVNO_NONE}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
final class ImsMmTelRepositoryImpl$imsReadyFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ImsMmTelRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImsMmTelRepositoryImpl$imsReadyFlow$1(ImsMmTelRepositoryImpl imsMmTelRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = imsMmTelRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ImsMmTelRepositoryImpl$imsReadyFlow$1 imsMmTelRepositoryImpl$imsReadyFlow$1 = new ImsMmTelRepositoryImpl$imsReadyFlow$1(this.this$0, continuation);
        imsMmTelRepositoryImpl$imsReadyFlow$1.L$0 = obj;
        return imsMmTelRepositoryImpl$imsReadyFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ImsMmTelRepositoryImpl$imsReadyFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.telephony.ims.ImsStateCallback, com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl$imsReadyFlow$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ImsMmTelRepositoryImpl imsMmTelRepositoryImpl = this.this$0;
            final ?? r1 = new ImsStateCallback() { // from class: com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl$imsReadyFlow$1$callback$1
                @Override // android.telephony.ims.ImsStateCallback
                public final void onAvailable() {
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(ImsMmTelRepositoryImpl.this.subId, "[", "] IMS onAvailable", "ImsMmTelRepository");
                    ((ProducerCoroutine) producerScope).mo1469trySendJP2dKIU(Boolean.TRUE);
                }

                @Override // android.telephony.ims.ImsStateCallback
                public final void onError() {
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(ImsMmTelRepositoryImpl.this.subId, "[", "] IMS onError", "ImsMmTelRepository");
                    ((ProducerCoroutine) producerScope).mo1469trySendJP2dKIU(Boolean.FALSE);
                }

                @Override // android.telephony.ims.ImsStateCallback
                public final void onUnavailable(int i2) {
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(ImsMmTelRepositoryImpl.this.subId, "[", "] IMS onUnavailable", "ImsMmTelRepository");
                    ((ProducerCoroutine) producerScope).mo1469trySendJP2dKIU(Boolean.FALSE);
                }
            };
            this.this$0.imsMmTelManager.registerImsStateCallback(ExecutorsKt.asExecutor(Dispatchers.Default), r1);
            final ImsMmTelRepositoryImpl imsMmTelRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl$imsReadyFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* renamed from: invoke */
                public final Object mo1068invoke() {
                    ImsMmTelRepositoryImpl.this.imsMmTelManager.unregisterImsStateCallback(r1);
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
