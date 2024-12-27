package com.android.settingslib.spaprivileged.framework.compose;

import android.content.Context;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class StringResourcesKt {
    public static final String getPlaceholder(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        String string = context.getString(R.string.summary_placeholder);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    public static final String placeholder(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-371784545);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        String stringResource =
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.summary_placeholder);
        composerImpl.end(false);
        return stringResource;
    }
}
