package com.android.settingslib.spa.framework.common;

import android.os.Bundle;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface SpaLogger {
    default void event(String id, LogEvent event, LogCategory logCategory, Bundle extraData) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(extraData, "extraData");
    }
}
