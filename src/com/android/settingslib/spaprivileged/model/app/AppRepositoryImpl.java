package com.android.settingslib.spaprivileged.model.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.IconDrawableFactory;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.SnapshotStateKt;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppRepositoryImpl implements AppRepository {
    public final Context context;
    public final IconDrawableFactory iconDrawableFactory;
    public final PackageManager packageManager;

    public AppRepositoryImpl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.packageManager = context.getPackageManager();
        this.iconDrawableFactory = IconDrawableFactory.newInstance(context);
    }

    public final MutableState produceIcon(ApplicationInfo app, Composer composer) {
        Intrinsics.checkNotNullParameter(app, "app");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1049907493);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        MutableState produceState =
                SnapshotStateKt.produceState(
                        null,
                        app,
                        new AppRepositoryImpl$produceIcon$1(this, app, null),
                        composerImpl,
                        582);
        composerImpl.end(false);
        return produceState;
    }

    public final MutableState produceIconContentDescription(
            ApplicationInfo app, Composer composer) {
        Intrinsics.checkNotNullParameter(app, "app");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1542626046);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        MutableState produceState =
                SnapshotStateKt.produceState(
                        null,
                        app,
                        new AppRepositoryImpl$produceIconContentDescription$1(this, app, null),
                        composerImpl,
                        582);
        composerImpl.end(false);
        return produceState;
    }
}
