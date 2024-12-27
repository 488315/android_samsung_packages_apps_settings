package com.android.settingslib.spa.lifecycle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class FlowExtKt {
    public static final Function0 collectAsCallbackWithLifecycle(Flow flow, Composer composer) {
        Intrinsics.checkNotNullParameter(flow, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1375485664);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final MutableState collectAsStateWithLifecycle =
                androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(
                        flow, null, composerImpl, 56);
        composerImpl.startReplaceGroup(-2054614720);
        boolean changed = composerImpl.changed(collectAsStateWithLifecycle);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.lifecycle.FlowExtKt$collectAsCallbackWithLifecycle$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return collectAsStateWithLifecycle.getValue();
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue);
        }
        Function0 function0 = (Function0) rememberedValue;
        composerImpl.end(false);
        composerImpl.end(false);
        return function0;
    }
}
