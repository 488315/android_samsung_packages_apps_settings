package com.android.settingslib.deviceinfo;

import android.app.AppGlobals;
import android.app.usage.StorageStatsManager;
import android.os.storage.VolumeInfo;
import android.util.Log;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PrivateStorageInfo {
    public final long freeBytes;
    public final long totalBytes;

    public PrivateStorageInfo(long j, long j2) {
        this.freeBytes = j;
        this.totalBytes = j2;
    }

    public static PrivateStorageInfo getPrivateStorageInfo(
            StorageManagerVolumeProvider storageManagerVolumeProvider) {
        StorageStatsManager storageStatsManager =
                (StorageStatsManager)
                        AppGlobals.getInitialApplication()
                                .getSystemService(StorageStatsManager.class);
        long j = 0;
        long j2 = 0;
        for (VolumeInfo volumeInfo : storageManagerVolumeProvider.mStorageManager.getVolumes()) {
            if (volumeInfo.getType() == 1 && volumeInfo.isMountedReadable()) {
                try {
                    j2 += storageStatsManager.getTotalBytes(volumeInfo.getFsUuid());
                    j += storageStatsManager.getFreeBytes(volumeInfo.getFsUuid());
                } catch (IOException e) {
                    Log.w("PrivateStorageInfo", e);
                }
            }
        }
        return new PrivateStorageInfo(j, j2);
    }
}
