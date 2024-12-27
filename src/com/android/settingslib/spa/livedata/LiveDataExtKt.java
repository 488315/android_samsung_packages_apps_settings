package com.android.settingslib.spa.livedata;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.livedata.LiveDataAdapterKt;
import androidx.lifecycle.LiveData;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LiveDataExtKt {
    public static final Function0 observeAsCallback(LiveData liveData, Composer composer) {
        Intrinsics.checkNotNullParameter(liveData, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1602607352);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceableGroup(-2027206144);
        final MutableState observeAsState =
                LiveDataAdapterKt.observeAsState(liveData, liveData.getValue(), composerImpl);
        composerImpl.end(false);
        composerImpl.startReplaceGroup(833513459);
        boolean changed = composerImpl.changed(observeAsState);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.livedata.LiveDataExtKt$observeAsCallback$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return observeAsState.getValue();
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
