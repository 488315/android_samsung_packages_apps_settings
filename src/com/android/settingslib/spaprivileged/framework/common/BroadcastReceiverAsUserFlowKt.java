package com.android.settingslib.spaprivileged.framework.common;

import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BroadcastReceiverAsUserFlowKt {
    public static final Flow broadcastReceiverAsUserFlow(
            Context context, IntentFilter intentFilter, UserHandle userHandle) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(intentFilter, "intentFilter");
        Intrinsics.checkNotNullParameter(userHandle, "userHandle");
        return FlowKt.flowOn(
                FlowKt.buffer$default(
                        new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(
                                FlowKt.callbackFlow(
                                        new BroadcastReceiverAsUserFlowKt$broadcastReceiverAsUserFlow$1(
                                                context, userHandle, intentFilter, null)),
                                new BroadcastReceiverAsUserFlowKt$broadcastReceiverAsUserFlow$2(
                                        3, null)),
                        -1),
                Dispatchers.Default);
    }
}
