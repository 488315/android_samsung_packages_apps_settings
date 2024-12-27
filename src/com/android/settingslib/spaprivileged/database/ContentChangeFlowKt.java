package com.android.settingslib.spaprivileged.database;

import android.content.Context;
import android.net.Uri;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ContentChangeFlowKt {
    public static final Flow contentChangeFlow(Context context, Uri uri, boolean z) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(uri, "uri");
        return FlowKt.flowOn(
                FlowKt.buffer$default(
                        FlowKt.callbackFlow(
                                new ContentChangeFlowKt$contentChangeFlow$1(context, uri, z, null)),
                        -1),
                Dispatchers.Default);
    }
}
