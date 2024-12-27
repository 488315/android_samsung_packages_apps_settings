package com.android.settings.spa.app.specialaccess;

import android.content.pm.ApplicationInfo;

import com.android.settingslib.spaprivileged.model.app.AppOpsController;
import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InstallUnknownAppsRecord implements AppRecord {
    public final ApplicationInfo app;
    public final AppOpsController appOpsController;

    public InstallUnknownAppsRecord(ApplicationInfo app, AppOpsController appOpsController) {
        Intrinsics.checkNotNullParameter(app, "app");
        this.app = app;
        this.appOpsController = appOpsController;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InstallUnknownAppsRecord)) {
            return false;
        }
        InstallUnknownAppsRecord installUnknownAppsRecord = (InstallUnknownAppsRecord) obj;
        return Intrinsics.areEqual(this.app, installUnknownAppsRecord.app)
                && Intrinsics.areEqual(
                        this.appOpsController, installUnknownAppsRecord.appOpsController);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        return this.appOpsController.hashCode() + (this.app.hashCode() * 31);
    }

    public final String toString() {
        return "InstallUnknownAppsRecord(app="
                + this.app
                + ", appOpsController="
                + this.appOpsController
                + ")";
    }
}
