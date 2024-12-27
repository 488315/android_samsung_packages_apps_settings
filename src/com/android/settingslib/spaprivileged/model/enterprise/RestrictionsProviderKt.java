package com.android.settingslib.spaprivileged.model.enterprise;

import android.content.Context;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RestrictionsProviderKt {
    public static final MutableState rememberRestrictedMode(
            Function2 function2, Restrictions restrictions, Composer composer) {
        Intrinsics.checkNotNullParameter(function2, "<this>");
        Intrinsics.checkNotNullParameter(restrictions, "restrictions");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1695236386);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(-540680283);
        boolean changed = composerImpl.changed(restrictions);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue = (RestrictionsProviderImpl) function2.invoke(context, restrictions);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        RestrictionsProviderImpl restrictionsProviderImpl =
                (RestrictionsProviderImpl) rememberedValue;
        composerImpl.end(false);
        restrictionsProviderImpl.getClass();
        composerImpl.startReplaceGroup(-1165004247);
        MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        restrictionsProviderImpl.restrictedMode, null, composerImpl, 56);
        composerImpl.end(false);
        composerImpl.end(false);
        return collectAsStateWithLifecycle;
    }
}
