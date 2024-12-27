package com.android.settings.network.telephony.ims;

import android.telephony.ims.ProvisioningManager;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ImsFeatureProvisionedFlowKt {
    public static final Flow imsFeatureProvisionedFlow(
            int i, int i2, int i3, ProvisioningManager provisioningManager) {
        Intrinsics.checkNotNullParameter(provisioningManager, "provisioningManager");
        return FlowKt.flowOn(
                FlowKt.onEach(
                        FlowKt.buffer$default(
                                new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(
                                        FlowKt.callbackFlow(
                                                new ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$1(
                                                        provisioningManager, i2, i3, null)),
                                        new ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$2(
                                                i, null)),
                                -1),
                        new ImsFeatureProvisionedFlowKt$imsFeatureProvisionedFlow$3(
                                i, i2, i3, null)),
                Dispatchers.Default);
    }
}
