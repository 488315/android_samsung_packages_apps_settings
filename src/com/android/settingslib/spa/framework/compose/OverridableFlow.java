package com.android.settingslib.spa.framework.compose;

import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class OverridableFlow {
    public final ChannelLimitedFlowMerge flow;
    public final BufferedChannel overrideChannel;

    public OverridableFlow(Flow flow) {
        BufferedChannel Channel$default = ChannelKt.Channel$default(0, null, null, 7);
        this.overrideChannel = Channel$default;
        this.flow = FlowKt.merge(new ChannelAsFlow(Channel$default, false), flow);
    }
}
