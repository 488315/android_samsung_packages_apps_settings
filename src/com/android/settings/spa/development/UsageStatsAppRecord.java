package com.android.settings.spa.development;

import android.app.usage.UsageStats;
import android.content.pm.ApplicationInfo;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UsageStatsAppRecord implements AppRecord {
    public final ApplicationInfo app;
    public final UsageStats usageStats;

    public UsageStatsAppRecord(ApplicationInfo applicationInfo, UsageStats usageStats) {
        this.app = applicationInfo;
        this.usageStats = usageStats;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UsageStatsAppRecord)) {
            return false;
        }
        UsageStatsAppRecord usageStatsAppRecord = (UsageStatsAppRecord) obj;
        return Intrinsics.areEqual(this.app, usageStatsAppRecord.app)
                && Intrinsics.areEqual(this.usageStats, usageStatsAppRecord.usageStats);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        int hashCode = this.app.hashCode() * 31;
        UsageStats usageStats = this.usageStats;
        return hashCode + (usageStats == null ? 0 : usageStats.hashCode());
    }

    public final String toString() {
        return "UsageStatsAppRecord(app=" + this.app + ", usageStats=" + this.usageStats + ")";
    }
}
