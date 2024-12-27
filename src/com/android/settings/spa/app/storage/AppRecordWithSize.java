package com.android.settings.spa.app.storage;

import android.content.pm.ApplicationInfo;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppRecordWithSize implements AppRecord {
    public final ApplicationInfo app;
    public final long size;

    public AppRecordWithSize(ApplicationInfo applicationInfo, long j) {
        this.app = applicationInfo;
        this.size = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppRecordWithSize)) {
            return false;
        }
        AppRecordWithSize appRecordWithSize = (AppRecordWithSize) obj;
        return Intrinsics.areEqual(this.app, appRecordWithSize.app)
                && this.size == appRecordWithSize.size;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        return Long.hashCode(this.size) + (this.app.hashCode() * 31);
    }

    public final String toString() {
        return "AppRecordWithSize(app=" + this.app + ", size=" + this.size + ")";
    }
}
