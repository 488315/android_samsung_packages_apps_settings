package com.android.settingslib.spa.framework.compose;

import android.content.Context;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RuntimeUtilsKt {
    public static final Object rememberContext(Function1 constructor, Composer composer) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1114928860);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(582398835);
        boolean changed = composerImpl.changed(context);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue = constructor.invoke(context);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        composerImpl.end(false);
        return rememberedValue;
    }
}
