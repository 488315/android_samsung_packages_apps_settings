package com.android.settings.network.telephony.ims;

import android.util.Log;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "it", ApnSettings.MVNO_NONE},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ int $capability;
    final /* synthetic */ int $subId;
    final /* synthetic */ int $tech;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3(
            int i, int i2, int i3, Continuation continuation) {
        super(2, continuation);
        this.$subId = i;
        this.$capability = i2;
        this.$tech = i3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3
                imsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3 =
                        new ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3(
                                this.$subId, this.$capability, this.$tech, continuation);
        imsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3.Z$0 =
                ((Boolean) obj).booleanValue();
        return imsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3
                imsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3 =
                        (ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3)
                                create(bool, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        imsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        int i = this.$subId;
        int i2 = this.$capability;
        int i3 = this.$tech;
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "[", "] changed: capability=", i, i2, " tech=");
        m.append(i3);
        m.append(" isProvisioned=");
        m.append(z);
        Log.d("ImsFeatureProvisioned", m.toString());
        return Unit.INSTANCE;
    }
}
