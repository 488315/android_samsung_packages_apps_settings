package com.android.settingslib.spaprivileged.template.app;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TogglePermissionAppListKt {
    public static final boolean isSystemOrRootUid(AppRecord appRecord) {
        Intrinsics.checkNotNullParameter(appRecord, "<this>");
        return CollectionsKt__CollectionsKt.listOf((Object[]) new Integer[] {1000, 0})
                .contains(Integer.valueOf(appRecord.getApp().uid));
    }
}
