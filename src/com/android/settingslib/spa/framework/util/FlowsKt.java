package com.android.settingslib.spa.framework.util;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class FlowsKt {
    public static final StandaloneCoroutine collectLatestWithLifecycle(
            Flow flow,
            LifecycleOwner lifecycleOwner,
            Lifecycle.State minActiveState,
            Function2 action) {
        Intrinsics.checkNotNullParameter(flow, "<this>");
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(minActiveState, "minActiveState");
        Intrinsics.checkNotNullParameter(action, "action");
        return BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(lifecycleOwner),
                null,
                null,
                new FlowsKt$collectLatestWithLifecycle$1(
                        lifecycleOwner, minActiveState, flow, action, null),
                3);
    }

    public static final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 waitFirst(
            ReadonlySharedFlow readonlySharedFlow, ReadonlySharedFlow readonlySharedFlow2) {
        return FlowKt.flowCombine(
                readonlySharedFlow,
                new FlowKt__LimitKt$take$$inlined$unsafeFlow$1(readonlySharedFlow2),
                new FlowsKt$waitFirst$1(3, null));
    }
}
