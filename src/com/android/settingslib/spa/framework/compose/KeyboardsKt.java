package com.android.settingslib.spa.framework.compose;

import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.foundation.lazy.LazyListStateKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.DelegatingSoftwareKeyboardController;
import androidx.compose.ui.platform.SoftwareKeyboardController;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class KeyboardsKt {
    public static final Function0 hideKeyboardAction(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1007875097);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final SoftwareKeyboardController softwareKeyboardController =
                (SoftwareKeyboardController)
                        composerImpl.consume(CompositionLocalsKt.LocalSoftwareKeyboardController);
        composerImpl.startReplaceGroup(886683028);
        boolean changed = composerImpl.changed(softwareKeyboardController);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.framework.compose.KeyboardsKt$hideKeyboardAction$1$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            SoftwareKeyboardController softwareKeyboardController2 =
                                    SoftwareKeyboardController.this;
                            if (softwareKeyboardController2 != null) {
                                ((DelegatingSoftwareKeyboardController) softwareKeyboardController2)
                                        .hide();
                            }
                            return Unit.INSTANCE;
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue);
        }
        Function0 function0 = (Function0) rememberedValue;
        composerImpl.end(false);
        composerImpl.end(false);
        return function0;
    }

    public static final LazyListState rememberLazyListStateAndHideKeyboardWhenStartScroll(
            Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-530403998);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        LazyListState rememberLazyListState = LazyListStateKt.rememberLazyListState(composerImpl);
        SoftwareKeyboardController softwareKeyboardController =
                (SoftwareKeyboardController)
                        composerImpl.consume(CompositionLocalsKt.LocalSoftwareKeyboardController);
        composerImpl.startReplaceGroup(-831771061);
        boolean changed =
                composerImpl.changed(rememberLazyListState)
                        | composerImpl.changed(softwareKeyboardController);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    new KeyboardsKt$rememberLazyListStateAndHideKeyboardWhenStartScroll$1$1(
                            rememberLazyListState, softwareKeyboardController, null);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(composerImpl, rememberLazyListState, (Function2) rememberedValue);
        composerImpl.end(false);
        return rememberLazyListState;
    }
}
