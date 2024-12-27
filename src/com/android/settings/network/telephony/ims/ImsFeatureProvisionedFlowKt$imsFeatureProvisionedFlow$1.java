package com.android.settings.network.telephony.ims;

import android.telephony.ims.ProvisioningManager;
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
public final class ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $capability;
    final /* synthetic */ ProvisioningManager $provisioningManager;
    final /* synthetic */ int $tech;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1(ProvisioningManager provisioningManager, int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.$provisioningManager = provisioningManager;
        this.$capability = i;
        this.$tech = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1 imsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1 = new ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1(this.$provisioningManager, this.$capability, this.$tech, continuation);
        imsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1.L$0 = obj;
        return imsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.telephony.ims.ProvisioningManager$FeatureProvisioningCallback, com.android.settings.network.telephony.ims.ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final int i2 = this.$capability;
            final int i3 = this.$tech;
            final ?? r1 = new ProvisioningManager.FeatureProvisioningCallback() { // from class: com.android.settings.network.telephony.ims.ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1$callback$1
                @Override // android.telephony.ims.ProvisioningManager.FeatureProvisioningCallback
                public final void onFeatureProvisioningChanged(int i4, int i5, boolean z) {
                    if (i2 == i4 && i3 == i5) {
                        ((ProducerCoroutine) producerScope).mo1469trySendJP2dKIU(Boolean.valueOf(z));
                    }
                }

                @Override // android.telephony.ims.ProvisioningManager.FeatureProvisioningCallback
                public final void onRcsFeatureProvisioningChanged(int i4, int i5, boolean z) {
                }
            };
            this.$provisioningManager.registerFeatureProvisioningChangedCallback(ExecutorsKt.asExecutor(Dispatchers.Default), r1);
            ProducerCoroutine producerCoroutine = (ProducerCoroutine) producerScope;
            producerCoroutine.mo1469trySendJP2dKIU(Boolean.valueOf(this.$provisioningManager.getProvisioningStatusForCapability(this.$capability, this.$tech)));
            final ProvisioningManager provisioningManager = this.$provisioningManager;
            Function0 function0 = new Function0() { // from class: com.android.settings.network.telephony.ims.ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* renamed from: invoke */
                public final Object mo1068invoke() {
                    provisioningManager.unregisterFeatureProvisioningChangedCallback(r1);
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
