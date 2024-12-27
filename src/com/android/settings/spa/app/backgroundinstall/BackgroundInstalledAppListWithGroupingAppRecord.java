package com.android.settings.spa.app.backgroundinstall;

import android.content.pm.ApplicationInfo;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackgroundInstalledAppListWithGroupingAppRecord implements AppRecord {
    public final ApplicationInfo app;
    public final long dateOfInstall;

    public BackgroundInstalledAppListWithGroupingAppRecord(ApplicationInfo app, long j) {
        Intrinsics.checkNotNullParameter(app, "app");
        this.app = app;
        this.dateOfInstall = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BackgroundInstalledAppListWithGroupingAppRecord)) {
            return false;
        }
        BackgroundInstalledAppListWithGroupingAppRecord
                backgroundInstalledAppListWithGroupingAppRecord =
                        (BackgroundInstalledAppListWithGroupingAppRecord) obj;
        return Intrinsics.areEqual(this.app, backgroundInstalledAppListWithGroupingAppRecord.app)
                && this.dateOfInstall
                        == backgroundInstalledAppListWithGroupingAppRecord.dateOfInstall;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        return Long.hashCode(this.dateOfInstall) + (this.app.hashCode() * 31);
    }

    public final String toString() {
        return "BackgroundInstalledAppListWithGroupingAppRecord(app="
                + this.app
                + ", dateOfInstall="
                + this.dateOfInstall
                + ")";
    }
}
