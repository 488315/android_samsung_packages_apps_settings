package com.android.settingslib.spa.framework.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.DisposableEffectScope;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.compose.LocalLifecycleOwnerKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LifecycleEffectKt {
    public static final void LifecycleEffect(final Function0 function0, final Function0 function02, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1809068594);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 14) == 0) {
            i3 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i5 = 2 & i2;
        if (i5 != 0) {
            i3 |= 48;
        } else if ((i & 112) == 0) {
            i3 |= composerImpl.changedInstance(function02) ? 32 : 16;
        }
        if ((i3 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                function0 = new Function0() { // from class: com.android.settingslib.spa.framework.compose.LifecycleEffectKt$LifecycleEffect$1
                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                        return Unit.INSTANCE;
                    }
                };
            }
            if (i5 != 0) {
                function02 = new Function0() { // from class: com.android.settingslib.spa.framework.compose.LifecycleEffectKt$LifecycleEffect$2
                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                        return Unit.INSTANCE;
                    }
                };
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final LifecycleOwner lifecycleOwner = (LifecycleOwner) composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner);
            EffectsKt.DisposableEffect(lifecycleOwner, new Function1() { // from class: com.android.settingslib.spa.framework.compose.LifecycleEffectKt$LifecycleEffect$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r3v2, types: [androidx.lifecycle.LifecycleObserver, com.android.settingslib.spa.framework.compose.LifecycleEffectKt$LifecycleEffect$3$observer$1] */
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    DisposableEffectScope DisposableEffect = (DisposableEffectScope) obj;
                    Intrinsics.checkNotNullParameter(DisposableEffect, "$this$DisposableEffect");
                    final Function0 function03 = function0;
                    final Function0 function04 = function02;
                    final ?? r3 = new LifecycleEventObserver() { // from class: com.android.settingslib.spa.framework.compose.LifecycleEffectKt$LifecycleEffect$3$observer$1
                        @Override // androidx.lifecycle.LifecycleEventObserver
                        public final void onStateChanged(LifecycleOwner lifecycleOwner2, Lifecycle.Event event) {
                            if (event == Lifecycle.Event.ON_START) {
                                Function0.this.mo1068invoke();
                            } else if (event == Lifecycle.Event.ON_STOP) {
                                function04.mo1068invoke();
                            }
                        }
                    };
                    LifecycleOwner.this.getLifecycle().addObserver(r3);
                    final LifecycleOwner lifecycleOwner2 = LifecycleOwner.this;
                    return new DisposableEffectResult() { // from class: com.android.settingslib.spa.framework.compose.LifecycleEffectKt$LifecycleEffect$3$invoke$$inlined$onDispose$1
                        @Override // androidx.compose.runtime.DisposableEffectResult
                        public final void dispose() {
                            LifecycleOwner.this.getLifecycle().removeObserver(r3);
                        }
                    };
                }
            }, composerImpl);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.framework.compose.LifecycleEffectKt$LifecycleEffect$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    LifecycleEffectKt.LifecycleEffect(Function0.this, function02, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
