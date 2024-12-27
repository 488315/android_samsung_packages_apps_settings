package com.android.settingslib.spaprivileged.framework.compose;

import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.compose.LocalLifecycleOwnerKt;

import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spaprivileged.framework.common.BroadcastReceiverAsUserFlowKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DisposableBroadcastReceiverAsUserKt {
    public static final void DisposableBroadcastReceiverAsUser(
            final IntentFilter intentFilter,
            final UserHandle userHandle,
            final Function1 onReceive,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(intentFilter, "intentFilter");
        Intrinsics.checkNotNullParameter(userHandle, "userHandle");
        Intrinsics.checkNotNullParameter(onReceive, "onReceive");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1447202675);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Flow broadcastReceiverAsUserFlow =
                BroadcastReceiverAsUserFlowKt.broadcastReceiverAsUserFlow(
                        (Context)
                                composerImpl.consume(
                                        AndroidCompositionLocals_androidKt.LocalContext),
                        intentFilter,
                        userHandle);
        LifecycleOwner lifecycleOwner =
                (LifecycleOwner) composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner);
        composerImpl.startReplaceGroup(770124652);
        boolean z =
                (((i & 896) ^ 384) > 256 && composerImpl.changed(onReceive)) || (i & 384) == 256;
        Object rememberedValue = composerImpl.rememberedValue();
        if (z || rememberedValue == Composer.Companion.Empty) {
            DisposableBroadcastReceiverAsUserKt$DisposableBroadcastReceiverAsUser$1$1
                    disposableBroadcastReceiverAsUserKt$DisposableBroadcastReceiverAsUser$1$1 =
                            new DisposableBroadcastReceiverAsUserKt$DisposableBroadcastReceiverAsUser$1$1(
                                    2,
                                    onReceive,
                                    Intrinsics.Kotlin.class,
                                    "suspendConversion0",
                                    "DisposableBroadcastReceiverAsUser$suspendConversion0(Lkotlin/jvm/functions/Function1;Landroid/content/Intent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
                                    0);
            composerImpl.updateRememberedValue(
                    disposableBroadcastReceiverAsUserKt$DisposableBroadcastReceiverAsUser$1$1);
            rememberedValue =
                    disposableBroadcastReceiverAsUserKt$DisposableBroadcastReceiverAsUser$1$1;
        }
        composerImpl.end(false);
        FlowsKt.collectLatestWithLifecycle(
                broadcastReceiverAsUserFlow,
                lifecycleOwner,
                Lifecycle.State.STARTED,
                (Function2) rememberedValue);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.framework.compose.DisposableBroadcastReceiverAsUserKt$DisposableBroadcastReceiverAsUser$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            DisposableBroadcastReceiverAsUserKt.DisposableBroadcastReceiverAsUser(
                                    intentFilter,
                                    userHandle,
                                    onReceive,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
