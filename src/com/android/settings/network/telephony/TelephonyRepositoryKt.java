package com.android.settings.network.telephony;

import android.content.Context;
import android.telephony.TelephonyManager;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TelephonyRepositoryKt {
    public static final Flow telephonyCallbackFlow(Context context, int i, Function1 block) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        return FlowKt.flowOn(
                FlowKt.buffer$default(
                        FlowKt.callbackFlow(
                                new TelephonyRepositoryKt$telephonyCallbackFlow$1(
                                        block, telephonyManager(context, i), null)),
                        -1),
                Dispatchers.Default);
    }

    public static final TelephonyManager telephonyManager(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Object systemService = context.getSystemService((Class<Object>) TelephonyManager.class);
        Intrinsics.checkNotNull(systemService);
        TelephonyManager createForSubscriptionId =
                ((TelephonyManager) systemService).createForSubscriptionId(i);
        Intrinsics.checkNotNullExpressionValue(
                createForSubscriptionId, "createForSubscriptionId(...)");
        return createForSubscriptionId;
    }
}
