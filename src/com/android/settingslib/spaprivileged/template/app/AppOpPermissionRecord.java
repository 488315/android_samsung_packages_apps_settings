package com.android.settingslib.spaprivileged.template.app;

import android.content.pm.ApplicationInfo;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;

import com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController;
import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppOpPermissionRecord implements AppRecord {
    public final ApplicationInfo app;
    public final AppOpsPermissionController appOpsPermissionController;
    public final boolean hasRequestBroaderPermission;
    public final boolean hasRequestPermission;

    public AppOpPermissionRecord(
            ApplicationInfo app,
            boolean z,
            boolean z2,
            AppOpsPermissionController appOpsPermissionController) {
        Intrinsics.checkNotNullParameter(app, "app");
        this.app = app;
        this.hasRequestBroaderPermission = z;
        this.hasRequestPermission = z2;
        this.appOpsPermissionController = appOpsPermissionController;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppOpPermissionRecord)) {
            return false;
        }
        AppOpPermissionRecord appOpPermissionRecord = (AppOpPermissionRecord) obj;
        return Intrinsics.areEqual(this.app, appOpPermissionRecord.app)
                && this.hasRequestBroaderPermission
                        == appOpPermissionRecord.hasRequestBroaderPermission
                && this.hasRequestPermission == appOpPermissionRecord.hasRequestPermission
                && Intrinsics.areEqual(
                        this.appOpsPermissionController,
                        appOpPermissionRecord.appOpsPermissionController);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        return this.appOpsPermissionController.hashCode()
                + TransitionData$$ExternalSyntheticOutline0.m(
                        TransitionData$$ExternalSyntheticOutline0.m(
                                this.app.hashCode() * 31, 31, this.hasRequestBroaderPermission),
                        31,
                        this.hasRequestPermission);
    }

    public final String toString() {
        return "AppOpPermissionRecord(app="
                + this.app
                + ", hasRequestBroaderPermission="
                + this.hasRequestBroaderPermission
                + ", hasRequestPermission="
                + this.hasRequestPermission
                + ", appOpsPermissionController="
                + this.appOpsPermissionController
                + ")";
    }
}
