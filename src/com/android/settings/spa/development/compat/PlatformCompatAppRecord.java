package com.android.settings.spa.development.compat;

import android.content.pm.ApplicationInfo;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PlatformCompatAppRecord implements AppRecord {
    public final ApplicationInfo app;

    public PlatformCompatAppRecord(ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(app, "app");
        this.app = app;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof PlatformCompatAppRecord)
                && Intrinsics.areEqual(this.app, ((PlatformCompatAppRecord) obj).app);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        return this.app.hashCode();
    }

    public final String toString() {
        return "PlatformCompatAppRecord(app=" + this.app + ")";
    }
}
