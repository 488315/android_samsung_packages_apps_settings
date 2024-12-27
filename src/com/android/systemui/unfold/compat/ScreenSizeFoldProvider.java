package com.android.systemui.unfold.compat;

import android.content.Context;
import android.content.res.Configuration;

import com.android.systemui.unfold.updates.FoldProvider$FoldCallback;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ScreenSizeFoldProvider {
    public final List callbacks;
    public boolean isFolded;

    public ScreenSizeFoldProvider(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.callbacks = new ArrayList();
        Configuration configuration = context.getResources().getConfiguration();
        Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
        onConfigurationChange(configuration);
    }

    public final void onConfigurationChange(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        boolean z = newConfig.smallestScreenWidthDp < 600;
        if (z == this.isFolded) {
            return;
        }
        this.isFolded = z;
        Iterator it = this.callbacks.iterator();
        while (it.hasNext()) {
            ((FoldProvider$FoldCallback) it.next()).onFoldUpdated(this.isFolded);
        }
    }

    public final void registerCallback(FoldProvider$FoldCallback callback, Executor executor) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(executor, "executor");
        this.callbacks.add(callback);
        callback.onFoldUpdated(this.isFolded);
    }
}
