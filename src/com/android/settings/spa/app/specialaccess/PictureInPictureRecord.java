package com.android.settings.spa.app.specialaccess;

import android.content.pm.ApplicationInfo;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;

import com.android.settingslib.spaprivileged.model.app.AppOpsController;
import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PictureInPictureRecord implements AppRecord {
    public final ApplicationInfo app;
    public final AppOpsController appOpsController;
    public final boolean isSupport;

    public PictureInPictureRecord(
            ApplicationInfo app, boolean z, AppOpsController appOpsController) {
        Intrinsics.checkNotNullParameter(app, "app");
        this.app = app;
        this.isSupport = z;
        this.appOpsController = appOpsController;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PictureInPictureRecord)) {
            return false;
        }
        PictureInPictureRecord pictureInPictureRecord = (PictureInPictureRecord) obj;
        return Intrinsics.areEqual(this.app, pictureInPictureRecord.app)
                && this.isSupport == pictureInPictureRecord.isSupport
                && Intrinsics.areEqual(
                        this.appOpsController, pictureInPictureRecord.appOpsController);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        return this.appOpsController.hashCode()
                + TransitionData$$ExternalSyntheticOutline0.m(
                        this.app.hashCode() * 31, 31, this.isSupport);
    }

    public final String toString() {
        return "PictureInPictureRecord(app="
                + this.app
                + ", isSupport="
                + this.isSupport
                + ", appOpsController="
                + this.appOpsController
                + ")";
    }
}
