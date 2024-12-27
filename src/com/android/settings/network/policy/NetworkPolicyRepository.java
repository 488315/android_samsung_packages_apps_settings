package com.android.settings.network.policy;

import android.content.Context;
import android.net.NetworkPolicyManager;
import android.net.NetworkTemplate;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkPolicyRepository {
    public final NetworkPolicyManager networkPolicyManager;

    public NetworkPolicyRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService((Class<Object>) NetworkPolicyManager.class);
        Intrinsics.checkNotNull(systemService);
        this.networkPolicyManager = (NetworkPolicyManager) systemService;
    }

    public final Flow networkPolicyFlow(NetworkTemplate networkTemplate) {
        Intrinsics.checkNotNullParameter(networkTemplate, "networkTemplate");
        return FlowKt.flowOn(
                new SafeFlow(
                        new NetworkPolicyRepository$networkPolicyFlow$1(
                                this, networkTemplate, null)),
                Dispatchers.Default);
    }
}
