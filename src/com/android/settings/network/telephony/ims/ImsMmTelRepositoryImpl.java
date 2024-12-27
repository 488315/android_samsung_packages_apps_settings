package com.android.settings.network.telephony.ims;

import android.content.Context;
import android.telephony.ims.ImsManager;
import android.telephony.ims.ImsMmTelManager;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ImsMmTelRepositoryImpl {
    public final ImsMmTelManager imsMmTelManager;
    public final int subId;

    public ImsMmTelRepositoryImpl(Context context, int i) {
        ImsMmTelManager imsMmTelManager = new ImsManager(context).getImsMmTelManager(i);
        Intrinsics.checkNotNullExpressionValue(imsMmTelManager, "getImsMmTelManager(...)");
        Intrinsics.checkNotNullParameter(context, "context");
        this.subId = i;
        this.imsMmTelManager = imsMmTelManager;
    }

    public final Flow imsReadyFlow() {
        return FlowKt.flowOn(
                FlowKt.buffer$default(
                        new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(
                                FlowKt.callbackFlow(
                                        new ImsMmTelRepositoryImpl$imsReadyFlow$1(this, null)),
                                new ImsMmTelRepositoryImpl$imsReadyFlow$2(this, null)),
                        -1),
                Dispatchers.Default);
    }

    public final Flow imsRegisteredFlow() {
        return FlowKt.flowOn(
                FlowKt.buffer$default(
                        new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(
                                FlowKt.callbackFlow(
                                        new ImsMmTelRepositoryImpl$imsRegisteredFlow$1(this, null)),
                                new ImsMmTelRepositoryImpl$imsRegisteredFlow$2(this, null)),
                        -1),
                Dispatchers.Default);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isSupported(int r6, int r7, kotlin.coroutines.Continuation r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl$isSupported$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl$isSupported$1 r0 = (com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl$isSupported$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl$isSupported$1 r0 = new com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl$isSupported$1
            r0.<init>(r5, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r8)
            goto L43
        L27:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2f:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.scheduling.DefaultScheduler r8 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl$isSupported$2 r2 = new com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl$isSupported$2
            r4 = 0
            r2.<init>(r6, r7, r5, r4)
            r0.label = r3
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r8, r2, r0)
            if (r8 != r1) goto L43
            return r1
        L43:
            java.lang.String r5 = "withContext(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r8, r5)
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl.isSupported(int,"
                    + " int, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
