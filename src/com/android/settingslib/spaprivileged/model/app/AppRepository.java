package com.android.settingslib.spaprivileged.model.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

import com.android.settingslib.spaprivileged.framework.compose.StringResourcesKt;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface AppRepository {
    default MutableState produceLabel(ApplicationInfo app, boolean z, Composer composer) {
        Intrinsics.checkNotNullParameter(app, "app");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-775174962);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        MutableState produceState =
                SnapshotStateKt.produceState(
                        StringResourcesKt.placeholder(composerImpl),
                        app,
                        new AppRepository$produceLabel$1(
                                z,
                                this,
                                (Context)
                                        composerImpl.consume(
                                                AndroidCompositionLocals_androidKt.LocalContext),
                                app,
                                null),
                        composerImpl,
                        576);
        composerImpl.end(false);
        return produceState;
    }
}
