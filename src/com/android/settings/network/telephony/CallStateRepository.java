package com.android.settings.network.telephony;

import android.content.Context;
import android.telephony.SubscriptionManager;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CallStateRepository {
    public final Context context;
    public final SubscriptionManager subscriptionManager;

    public CallStateRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.subscriptionManager = SubscriptionRepositoryKt.requireSubscriptionManager(context);
    }

    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 isInCallFlow() {
        return FlowKt.onEach(
                FlowKt.flowOn(
                        FlowKt.buffer$default(
                                FlowKt.transformLatest(
                                        SubscriptionRepositoryKt.subscriptionsChangedFlow(
                                                this.context),
                                        new CallStateRepository$isInCallFlow$$inlined$flatMapLatest$1(
                                                null, this)),
                                -1),
                        Dispatchers.Default),
                new CallStateRepository$isInCallFlow$2(2, null));
    }
}
