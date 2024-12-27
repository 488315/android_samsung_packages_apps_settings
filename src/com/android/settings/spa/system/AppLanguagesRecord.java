package com.android.settings.spa.system;

import android.content.pm.ApplicationInfo;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppLanguagesRecord implements AppRecord {
    public final ApplicationInfo app;
    public final boolean isAppLocaleSupported;

    public AppLanguagesRecord(ApplicationInfo applicationInfo, boolean z) {
        this.app = applicationInfo;
        this.isAppLocaleSupported = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppLanguagesRecord)) {
            return false;
        }
        AppLanguagesRecord appLanguagesRecord = (AppLanguagesRecord) obj;
        return Intrinsics.areEqual(this.app, appLanguagesRecord.app)
                && this.isAppLocaleSupported == appLanguagesRecord.isAppLocaleSupported;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isAppLocaleSupported) + (this.app.hashCode() * 31);
    }

    public final String toString() {
        return "AppLanguagesRecord(app="
                + this.app
                + ", isAppLocaleSupported="
                + this.isAppLocaleSupported
                + ")";
    }
}
