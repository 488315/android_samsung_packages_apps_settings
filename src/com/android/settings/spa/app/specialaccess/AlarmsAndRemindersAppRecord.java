package com.android.settings.spa.app.specialaccess;

import android.content.pm.ApplicationInfo;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;

import com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController;
import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AlarmsAndRemindersAppRecord implements AppRecord {
    public final ApplicationInfo app;
    public final AppOpsPermissionController controller;
    public final boolean isChangeable;
    public final boolean isTrumped;

    public AlarmsAndRemindersAppRecord(
            ApplicationInfo app,
            boolean z,
            boolean z2,
            AppOpsPermissionController appOpsPermissionController) {
        Intrinsics.checkNotNullParameter(app, "app");
        this.app = app;
        this.isTrumped = z;
        this.isChangeable = z2;
        this.controller = appOpsPermissionController;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AlarmsAndRemindersAppRecord)) {
            return false;
        }
        AlarmsAndRemindersAppRecord alarmsAndRemindersAppRecord = (AlarmsAndRemindersAppRecord) obj;
        return Intrinsics.areEqual(this.app, alarmsAndRemindersAppRecord.app)
                && this.isTrumped == alarmsAndRemindersAppRecord.isTrumped
                && this.isChangeable == alarmsAndRemindersAppRecord.isChangeable
                && Intrinsics.areEqual(this.controller, alarmsAndRemindersAppRecord.controller);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        return this.controller.hashCode()
                + TransitionData$$ExternalSyntheticOutline0.m(
                        TransitionData$$ExternalSyntheticOutline0.m(
                                this.app.hashCode() * 31, 31, this.isTrumped),
                        31,
                        this.isChangeable);
    }

    public final String toString() {
        return "AlarmsAndRemindersAppRecord(app="
                + this.app
                + ", isTrumped="
                + this.isTrumped
                + ", isChangeable="
                + this.isChangeable
                + ", controller="
                + this.controller
                + ")";
    }
}
