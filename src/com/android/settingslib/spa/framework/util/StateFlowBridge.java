package com.android.settingslib.spa.framework.util;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StateFlowBridge {
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flow;
    public final StateFlowImpl stateFlow;

    public StateFlowBridge() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.stateFlow = MutableStateFlow;
        this.flow = FlowKt.filterNotNull(MutableStateFlow);
    }

    public final void Sync(final Function0 callback, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1433379747);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Object mo1068invoke = callback.mo1068invoke();
        EffectsKt.LaunchedEffect(
                composerImpl, mo1068invoke, new StateFlowBridge$Sync$1(this, mo1068invoke, null));
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.framework.util.StateFlowBridge$Sync$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            StateFlowBridge.this.Sync(
                                    callback,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
