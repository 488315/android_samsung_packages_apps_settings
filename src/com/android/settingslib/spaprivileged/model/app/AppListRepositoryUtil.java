package com.android.settingslib.spaprivileged.model.app;

import android.content.Context;

import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppListRepositoryUtil {
    public static final Set getSystemPackageNames(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (Set)
                BuildersKt.runBlocking(
                        EmptyCoroutineContext.INSTANCE,
                        new AppListRepositoryImpl$getSystemPackageNamesBlocking$1(
                                new AppListRepositoryImpl(context), i, null));
    }
}
