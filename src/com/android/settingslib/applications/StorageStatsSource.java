package com.android.settingslib.applications;

import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StorageStatsSource {
    public final StorageStatsManager mStorageStatsManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface AppStorageStats {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppStorageStatsImpl implements AppStorageStats {
        public StorageStats mStats;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ExternalStorageStats {
        public long totalBytes;

        public ExternalStorageStats(long j, long j2, long j3, long j4, long j5) {
            this.totalBytes = j;
        }
    }

    public StorageStatsSource(Context context) {
        this.mStorageStatsManager =
                (StorageStatsManager) context.getSystemService(StorageStatsManager.class);
    }
}
